/*
 * MIT License
 *
 * Copyright (c) 2020 Philipp Eistrach
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.fllip.entity.api.entity

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.injector.netty.WirePacket
import com.comphenix.protocol.utility.MinecraftReflection
import com.google.common.collect.Lists
import de.fllip.entity.api.`class`.ClassHelper
import de.fllip.entity.api.configuration.AbstractEntityConfiguration
import de.fllip.entity.api.creator.EntityCreator
import de.fllip.entity.api.entity.animation.EntityAnimationType
import de.fllip.entity.api.entity.item.ItemInformation
import de.fllip.entity.api.entity.update.IEntityUpdateType
import de.fllip.entity.api.entity.update.UpdateAction
import de.fllip.entity.api.player.hasOnlineTime
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 29.12.2020
 * Time: 17:04
 */
abstract class AbstractEntity<T : AbstractEntityConfiguration<T>>(
    val configuration: T,
    val creator: EntityCreator
) {
    val uniqueId = UUID.randomUUID()!!
    val entityId = ThreadLocalRandom.current().nextInt(Short.MAX_VALUE.toInt())

    var location: Location? = configuration.location
        protected set

    val renderList = Lists.newArrayList<Player>()

    fun render(player: Player) {
        val canSee = configuration.handleSpawn?.invoke(player) ?: true

        if (canSee) {
            renderList.add(player)
            spawn(player)
        }
    }

    fun <T> update(updateType: IEntityUpdateType, data: T) {
        Bukkit.getOnlinePlayers().forEach {
            update(it, updateType, data)
        }
    }

    fun <T> update(player: Player, updateType: IEntityUpdateType, data: T) {
        ClassHelper.getMethodsOfClasses(true, this::class.java, AbstractEntity::class.java)
            .filter {
                it.isAnnotationPresent(UpdateAction::class.java)
                        && it.getAnnotation(UpdateAction::class.java).updateType == updateType.toString()
            }
            .sortedBy { it.getAnnotation(UpdateAction::class.java).priority }
            .forEach {
                it.invoke(this, player, data)
            }
    }

    abstract fun spawn(player: Player)


    fun despawn(player: Player) {
        renderList.removeIf { it.uniqueId == player.uniqueId }
        val destroyContainer = createPacket(PacketType.Play.Server.ENTITY_DESTROY)

        destroyContainer.integerArrays.write(0, intArrayOf(entityId))

        sendPackets(player, destroyContainer)
    }

    open fun handleTick(player: Player) {}

    @UpdateAction("TELEPORT", 0)
    fun teleport(player: Player, location: Location) {
        val oldLocation = this.location
        this.location = location
        configuration.handleTeleport?.accept(location)
        if (oldLocation == null) {
            spawn(player)
            return
        }


        val teleportContainer = createPacket(PacketType.Play.Server.ENTITY_TELEPORT, entityId)
        val entityHeadRotation = createHeadRotationContainer(location)

        teleportContainer.doubles
            .write(0, location.x)
            .write(1, location.y)
            .write(2, location.z)

        sendPackets(teleportContainer, entityHeadRotation.first, entityHeadRotation.second)
    }


    @UpdateAction("ANIMATION", 0)
    fun animation(player: Player, animationType: EntityAnimationType) {
        val animationContainer = createPacket(PacketType.Play.Server.ANIMATION, entityId)

        animationContainer.integers.write(1, animationType.animationId)

        sendPackets(player, animationContainer)

        when (animationType) {
            EntityAnimationType.TAKE_DAMAGE -> {
                val statusContainer = createPacket(PacketType.Play.Server.ENTITY_STATUS, entityId)

                statusContainer.bytes.write(0, 2)

                sendPackets(player, statusContainer)
            }
        }
    }

    protected fun sendEntityEquipmentPackets(player: Player, itemInformation: ItemInformation) {
        sendEntityEquipmentPackets(player, listOf(itemInformation))
    }

    protected fun sendEntityEquipmentPackets(player: Player, itemInformationList: List<ItemInformation>) {
        val entityEquipPacketClass = MinecraftReflection.getMinecraftClass("PacketPlayOutEntityEquipment")
        val enumItemSlotClass = MinecraftReflection.getMinecraftClass("EnumItemSlot")
        val enumItemSlotValueOfMethod = enumItemSlotClass.getMethod("valueOf", String::class.java)

        val entityEquipmentPacket =
            entityEquipPacketClass.getDeclaredConstructor(Int::class.java, List::class.java).newInstance(
                entityId,
                itemInformationList.map {
                    com.mojang.datafixers.util.Pair(
                        enumItemSlotValueOfMethod.invoke(null, it.itemSlot.toString()),
                        MinecraftReflection.getMinecraftItemStack(it.itemStack)
                    )
                }
            )

        ProtocolLibrary.getProtocolManager().sendWirePacket(player, WirePacket.fromPacket(entityEquipmentPacket))
    }

    protected fun createHeadRotationContainer(location: Location): Pair<PacketContainer, PacketContainer> {
        return createHeadRotationContainer(location.yaw, location.pitch)
    }

    protected fun createHeadRotationContainer(yaw: Float, pitch: Float): Pair<PacketContainer, PacketContainer> {
        val lookContainer = createPacket(PacketType.Play.Server.REL_ENTITY_MOVE_LOOK, entityId)
        val headRotationContainer = createPacket(PacketType.Play.Server.ENTITY_HEAD_ROTATION, entityId)

        lookContainer.bytes
            .write(0, (yaw * 256F / 360F).toInt().toByte())
            .write(1, (pitch * 256F / 360F).toInt().toByte())

        lookContainer.booleans.write(0, true)

        headRotationContainer.bytes
            .write(0, (yaw * 256F / 360F).toInt().toByte())


        return Pair(lookContainer, headRotationContainer)
    }

    protected fun createPacket(packetType: PacketType, entityId: Int? = null): PacketContainer {
        val packetContainer = PacketContainer(packetType)

        if (entityId != null) {
            packetContainer.integers.write(0, entityId)
        }
        return packetContainer
    }

    fun sendPackets(player: Player, vararg packets: PacketContainer) {
        packets.forEach {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, it)
        }
    }

    fun sendPackets(vararg packets: PacketContainer) {
        renderList.forEach {
            sendPackets(it, *packets)
        }
    }

    fun isRendered(player: Player): Boolean {
        return renderList.contains(player)
    }

    fun removeOfflineRenders() {
        renderList.removeIf {
            it == null || !it.hasOnlineTime(3)
        }
    }

}