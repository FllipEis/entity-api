package de.fllip.entity.plugin.entity.fakeplayer.packet

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.WrappedDataWatcher
import com.comphenix.protocol.wrappers.WrappedWatchableObject
import com.google.inject.Singleton
import de.fllip.entity.plugin.entity.getByteSerializer
import de.fllip.entity.plugin.entity.packet.AbstractEntityPacketCreator

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 22.04.2021
 * Time: 21:30
 */
@Singleton
class MetadataPacketCreator: AbstractEntityPacketCreator() {

    fun create(entityId: Int): PacketContainer {
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

}