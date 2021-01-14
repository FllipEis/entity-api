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

package de.fllip.entity.api.entity.type.armorstand

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.WrappedChatComponent
import com.comphenix.protocol.wrappers.WrappedDataWatcher
import com.google.inject.Inject
import de.fllip.entity.api.configuration.ArmorStandConfiguration
import de.fllip.entity.api.creator.EntityCreator
import de.fllip.entity.api.datawatcher.getByteSerializer
import de.fllip.entity.api.datawatcher.getSerializer
import de.fllip.entity.api.entity.AbstractEntity
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import java.util.*

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 13.01.2021
 * Time: 19:49
 */
class ArmorStandEntity @Inject constructor(
    configuration: ArmorStandConfiguration,
    creator: EntityCreator
) : AbstractEntity<ArmorStandConfiguration>(configuration, creator) {

    override fun spawn(player: Player) {
        val spawnContainer = createPacket(PacketType.Play.Server.SPAWN_ENTITY, entityId)
        spawnContainer.uuiDs.write(0, uniqueId)
        spawnContainer.entityTypeModifier.write(0, EntityType.ARMOR_STAND)

        spawnContainer.doubles
            .write(0, location!!.x)
            .write(1, location!!.y)
            .write(2, location!!.z)

        val metadataContainer = PacketContainer(PacketType.Play.Server.ENTITY_METADATA)
        metadataContainer.modifier.writeDefaults()
        metadataContainer.integers.write(0, entityId)

        val dataWatcher = WrappedDataWatcher(metadataContainer.watchableCollectionModifier.read(0))

        val isInvisibleIndex = WrappedDataWatcher.WrappedDataWatcherObject(0, getByteSerializer())
        dataWatcher.setObject(isInvisibleIndex, (if (configuration.visible) 0 else 0x20).toByte())

        val nameValue =
            WrappedDataWatcher.WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true))
        val nameVisible = WrappedDataWatcher.WrappedDataWatcherObject(3, getSerializer("i"))
        dataWatcher.setObject(
            nameValue,
            Optional.of(WrappedChatComponent.fromText(configuration.displayNameHandler.invoke(player)).handle)
        )
        dataWatcher.setObject(nameVisible, true)

        metadataContainer.watchableCollectionModifier.write(0, dataWatcher.watchableObjects)

        sendPackets(player, spawnContainer, metadataContainer)
    }

}