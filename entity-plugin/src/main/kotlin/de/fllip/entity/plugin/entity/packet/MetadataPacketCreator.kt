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

            val dataWatcher = WrappedDataWatcher(watchableCollectionModifier.read(0))

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