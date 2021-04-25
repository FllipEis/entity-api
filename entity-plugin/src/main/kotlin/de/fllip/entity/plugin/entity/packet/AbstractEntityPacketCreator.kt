package de.fllip.entity.plugin.entity.packet

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketContainer
import de.fllip.entity.api.entity.configuration.EntityConfigurationAdapter
import org.bukkit.entity.Player

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 22.04.2021
 * Time: 20:47
 */
abstract class AbstractEntityPacketCreator {

    protected fun createPacket(packetType: PacketType, entityId: Int? = null): PacketContainer {
        return PacketContainer(packetType).apply {
            if (entityId != null) {
                integers.write(0, entityId)
            }
        }
    }

    protected fun invokeDisplayName(configurationAdapter: EntityConfigurationAdapter, player: Player): String {
        val displayName = configurationAdapter.getDisplayNameHandler().invoke(player)

        if (displayName.length > 16) {
            return displayName.substring(0, 16)
        }

        return displayName
    }

}