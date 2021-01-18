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

package de.fllip.entity.api.entity.type.fakeplayer

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.injector.netty.WirePacket
import com.comphenix.protocol.utility.MinecraftReflection
import com.comphenix.protocol.wrappers.*
import com.google.common.collect.Lists
import com.google.inject.Inject
import com.mojang.datafixers.util.Pair
import de.fllip.entity.api.configuration.FakePlayerEntityConfiguration
import de.fllip.entity.api.creator.EntityCreator
import de.fllip.entity.api.datawatcher.getByteSerializer
import de.fllip.entity.api.datawatcher.getSerializer
import de.fllip.entity.api.entity.AbstractEntity
import de.fllip.entity.api.entity.animation.EntityAnimationType
import de.fllip.entity.api.entity.item.ItemInformation
import de.fllip.entity.api.entity.pose.EntityPose
import de.fllip.entity.api.entity.tracking.EntityTrackingRange
import de.fllip.entity.api.entity.update.UpdateAction
import de.fllip.entity.api.scheduler.later
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 29.12.2020
 * Time: 17:14
 */
class FakePlayerEntity @Inject constructor(
    private val plugin: JavaPlugin,
    configuration: FakePlayerEntityConfiguration,
    creator: EntityCreator
) : AbstractEntity<FakePlayerEntityConfiguration>(configuration, creator) {

    val lookAtPlayer = configuration.lookAtPlayer

    override fun spawn(player: Player) {
        if (location == null) {
            return
        }

        val spawnContainer = createPacket(PacketType.Play.Server.NAMED_ENTITY_SPAWN, entityId)
        val metaDataContainer = createPacket(PacketType.Play.Server.ENTITY_METADATA, entityId)
        val playerInfoAddContainer = createPlayerInfoContainer(player, EnumWrappers.PlayerInfoAction.ADD_PLAYER)
        val headRotationContainer = createHeadRotationContainer(location!!)

        spawnContainer.uuiDs.write(0, uniqueId)

        spawnContainer.doubles
            .write(0, location!!.x)
            .write(1, location!!.y)
            .write(2, location!!.z)

        metaDataContainer.watchableCollectionModifier
            .write(
                0, listOf(
                    WrappedWatchableObject(
                        WrappedDataWatcher.WrappedDataWatcherObject(
                            16,
                            getByteSerializer()
                        ),
                        (127).toByte()
                    )
                )
            )

        sendPackets(
            player,
            playerInfoAddContainer,
            spawnContainer,
            headRotationContainer.first,
            headRotationContainer.second,
            metaDataContainer
        )

        later(plugin, 4) {
            val playerInfoRemoveContainer =
                createPlayerInfoContainer(player, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER)
            sendPackets(player, playerInfoRemoveContainer)

            val mainHandItem = configuration.mainHandItem
            if (mainHandItem != null) {
                sendEntityEquipmentPackets(player, ItemInformation(EnumWrappers.ItemSlot.MAINHAND, mainHandItem))
            }

            val offHandItem = configuration.offHandItem
            if (offHandItem != null) {
                sendEntityEquipmentPackets(player, ItemInformation(EnumWrappers.ItemSlot.OFFHAND, offHandItem))
            }
        }
    }

    override fun handleTick(player: Player) {
        val inRange = EntityTrackingRange.inRange(player.location, location!!, 20.0)

        if (lookAtPlayer && inRange) {
            val xDifference = player.location.x - location!!.x
            val yDifference = player.location.y - location!!.y
            val zDifference = player.location.z - location!!.z

            val r = sqrt(xDifference.pow(2) + yDifference.pow(2) + zDifference.pow(2))

            var yaw = (-atan2(xDifference, zDifference) / Math.PI * 180.0).toFloat()
            yaw = if (yaw < 0) yaw + 360 else yaw

            val pitch = (-asin(yDifference / r) / Math.PI * 180.0).toFloat()

            val entityHeadRotation = createHeadRotationContainer(yaw, pitch)
            sendPackets(player, entityHeadRotation.first, entityHeadRotation.second)
        }
    }

    @UpdateAction("ITEM_CHANGE")
    fun changeItemInHands(player: Player, itemInformationList: List<ItemInformation>) {
        sendEntityEquipmentPackets(player, itemInformationList)
    }

    @UpdateAction("ANIMATION", 1)
    fun fakePlayerAnimation(player: Player, animationType: EntityAnimationType) {
        when (animationType) {
            EntityAnimationType.TAKE_DAMAGE -> {
                player.playSound(location!!, Sound.ENTITY_PLAYER_HURT, 1F, 1F)
            }
        }

    }

    @UpdateAction("POSE")
    fun pose(player: Player, pose: EntityPose) {
        val metadataContainer = createPacket(PacketType.Play.Server.ENTITY_METADATA, entityId)

        val watchableObjects = Lists.newArrayList<WrappedWatchableObject>()

        watchableObjects.add(
            WrappedWatchableObject(
                WrappedDataWatcher.WrappedDataWatcherObject(
                    6,
                    getSerializer("s")
                ),
                EntityPose.toMinecraftEntityPose(pose)
            )
        )

        if (pose == EntityPose.CROUCHING || pose == EntityPose.STANDING) {
            val isSneaking = pose == EntityPose.CROUCHING

            watchableObjects.add(
                WrappedWatchableObject(
                    WrappedDataWatcher.WrappedDataWatcherObject(
                        0,
                        getByteSerializer()
                    ),
                    (if (isSneaking) 0x02 else 0).toByte()
                )
            )
        }

        metadataContainer.watchableCollectionModifier
            .write(0, watchableObjects)

        sendPackets(player, metadataContainer)
    }

    private fun createPlayerInfoContainer(player: Player, action: EnumWrappers.PlayerInfoAction): PacketContainer {
        val playerInfoContainer = createPacket(PacketType.Play.Server.PLAYER_INFO)
        playerInfoContainer.playerInfoAction.write(0, action)

        val displayName = invokeDisplayName(player)

        val wrappedGameProfile = WrappedGameProfile(uniqueId, displayName)

        val skinData = configuration.skinDataHandler?.invoke(player)
        if (skinData != null) {
            wrappedGameProfile.properties.put(
                "textures",
                WrappedSignedProperty("textures", skinData.value, skinData.signature)
            )
        }

        val playerInfoData = PlayerInfoData(
            wrappedGameProfile,
            20,
            EnumWrappers.NativeGameMode.NOT_SET,
            WrappedChatComponent.fromText("")
        )
        playerInfoContainer.playerInfoDataLists.write(0, Collections.singletonList(playerInfoData))

        return playerInfoContainer
    }

}