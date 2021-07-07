/*
 * Copyright (c) 2021 Philipp Eistrach
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

package de.fllip.entity.plugin.entity.packet

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.WrappedChatComponent
import com.comphenix.protocol.wrappers.WrappedDataWatcher
import com.google.inject.Singleton
import de.fllip.entity.api.entity.Entity
import de.fllip.entity.api.entity.armorstand.ArmorStandEntityConfigurationAdapter
import org.bukkit.entity.Player
import java.util.*

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 23.04.2021
 * Time: 14:58
 */
@Singleton
class MetadataPacketCreator : AbstractEntityPacketCreator() {

    fun createVisibleMetadata(entity: Entity, player: Player): PacketContainer {
        return createNameMetadata(entity, player).apply {
            val dataWatcher = WrappedDataWatcher(watchableCollectionModifier.read(0))

            val isInvisibleIndex = WrappedDataWatcher.WrappedDataWatcherObject(0, getByteSerializer())
            dataWatcher.setObject(
                isInvisibleIndex,
                (if ((entity.getEntityConfiguration() as ArmorStandEntityConfigurationAdapter).isVisible()) 0 else 0x20).toByte()
            )

            watchableCollectionModifier.write(0, dataWatcher.watchableObjects)
        }
    }

    fun createNameMetadata(entity: Entity, player: Player): PacketContainer {
        return createPacket(PacketType.Play.Server.ENTITY_METADATA, entity.getEntityId()).apply {
            modifier.writeDefaults()

            val dataWatcher = WrappedDataWatcher()

            val nameValue =
                WrappedDataWatcher.WrappedDataWatcherObject(
                    2,
                    WrappedDataWatcher.Registry.getChatComponentSerializer(true)
                )
            val nameVisible = WrappedDataWatcher.WrappedDataWatcherObject(3, getSerializer("i"))
            dataWatcher.setObject(
                nameValue,
                Optional.of(
                    WrappedChatComponent.fromText(
                        invokeDisplayName(
                            entity.getEntityConfiguration(),
                            player
                        )
                    ).handle
                )
            )
            dataWatcher.setObject(nameVisible, true)

            watchableCollectionModifier.write(0, dataWatcher.watchableObjects)
        }
    }

}