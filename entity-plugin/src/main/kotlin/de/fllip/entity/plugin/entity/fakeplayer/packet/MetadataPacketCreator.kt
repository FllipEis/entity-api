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

package de.fllip.entity.plugin.entity.fakeplayer.packet

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.utility.MinecraftReflection
import com.comphenix.protocol.wrappers.WrappedDataWatcher
import com.comphenix.protocol.wrappers.WrappedWatchableObject
import com.google.common.collect.Lists
import com.google.inject.Singleton
import de.fllip.entity.api.entity.fakeplayer.EntityPose
import de.fllip.entity.plugin.entity.packet.getByteSerializer
import de.fllip.entity.plugin.entity.packet.getSerializer
import de.fllip.entity.plugin.entity.packet.AbstractEntityPacketCreator

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 22.04.2021
 * Time: 21:30
 */
@Singleton
class MetadataPacketCreator : AbstractEntityPacketCreator() {

    fun createSecondLayer(entityId: Int): PacketContainer {
        return createPacket(PacketType.Play.Server.ENTITY_METADATA, entityId).apply {
            watchableCollectionModifier
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
        }
    }

    fun createPose(entityId: Int, entityPose: EntityPose): PacketContainer {
        val metadataContainer = createPacket(PacketType.Play.Server.ENTITY_METADATA, entityId)

        val watchableObjects = Lists.newArrayList<WrappedWatchableObject>()

        watchableObjects.add(
            WrappedWatchableObject(
                WrappedDataWatcher.WrappedDataWatcherObject(
                    6,
                    getSerializer("s")
                ),
                MinecraftReflection.getMinecraftClass("EntityPose").enumConstants.first { it.toString() == entityPose.toString() }
            )
        )

        if (entityPose == EntityPose.CROUCHING || entityPose == EntityPose.STANDING) {
            val isSneaking = entityPose == EntityPose.CROUCHING

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

        return metadataContainer.apply {
            watchableCollectionModifier
                .write(0, watchableObjects)
        }
    }

}