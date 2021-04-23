package de.fllip.entity.plugin.entity.fakeplayer.packet

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketContainer
import com.google.inject.Singleton
import de.fllip.entity.api.entity.Entity
import de.fllip.entity.plugin.entity.packet.AbstractEntityPacketCreator

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 22.04.2021
 * Time: 21:30
 */
@Singleton
class SpawnPacketCreator: AbstractEntityPacketCreator() {

    fun create(entity: Entity): PacketContainer {
        val location = entity.getEntityConfiguration().getLocation()!!
        return createPacket(PacketType.Play.Server.NAMED_ENTITY_SPAWN, entity.getEntityId()).apply {
            uuiDs.write(0, entity.getUniqueId())
            doubles
                .write(0, location.x)
                .write(1, location.y)
                .write(2, location.z)

        }
    }

}