package de.fllip.entity.plugin.entity.packet

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketContainer
import com.google.inject.Singleton
import de.fllip.entity.api.entity.Entity
import org.bukkit.entity.EntityType

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 23.04.2021
 * Time: 14:58
 */
@Singleton
class SpawnPacketCreator : AbstractEntityPacketCreator() {

    fun create(entity: Entity, entityType: EntityType): PacketContainer {
        val location = entity.getEntityConfiguration().getLocation()!!

        val isPlayer = entityType == EntityType.PLAYER
        val packetType = if (isPlayer) PacketType.Play.Server.NAMED_ENTITY_SPAWN else PacketType.Play.Server.SPAWN_ENTITY

        return createPacket(packetType, entity.getEntityId()).apply {
            uuiDs.write(0, entity.getUniqueId())
            doubles
                .write(0, location.x)
                .write(1, location.y)
                .write(2, location.z)

            if (!isPlayer) {
                entityTypeModifier.write(0, entityType)
            }
        }
    }

}