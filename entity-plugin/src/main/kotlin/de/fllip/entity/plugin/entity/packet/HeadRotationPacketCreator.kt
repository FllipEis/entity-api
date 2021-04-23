package de.fllip.entity.plugin.entity.packet

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketContainer
import com.google.inject.Singleton
import de.fllip.entity.api.entity.Entity
import org.bukkit.Location
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 22.04.2021
 * Time: 20:36
 */
@Singleton
class HeadRotationPacketCreator: AbstractEntityPacketCreator() {

    fun create(entity: Entity, location: Location, locationToLook: Location): Pair<PacketContainer, PacketContainer> {
        val xDifference = locationToLook.x - location.x
        val yDifference = locationToLook.y - location.y
        val zDifference = locationToLook.z - location.z

        val r = sqrt(xDifference.pow(2) + yDifference.pow(2) + zDifference.pow(2))

        var yaw = (-atan2(xDifference, zDifference) / Math.PI * 180.0).toFloat()
        yaw = if (yaw < 0) yaw + 360 else yaw

        val pitch = (-asin(yDifference / r) / Math.PI * 180.0).toFloat()

        return create(entity.getEntityId(), yaw, pitch)
    }

    fun create(entityId: Int, location: Location): Pair<PacketContainer, PacketContainer> {
        return create(entityId, location.yaw, location.pitch)
    }

    private fun create(entityId: Int, yaw: Float, pitch: Float): Pair<PacketContainer, PacketContainer> {
        val lookContainer = createPacket(PacketType.Play.Server.REL_ENTITY_MOVE_LOOK, entityId).apply {
            bytes
                .write(0, (yaw * 256F / 360F).toInt().toByte())
                .write(1, (pitch * 256F / 360F).toInt().toByte())

            booleans.write(0, true)
        }

        val headRotationContainer = createPacket(PacketType.Play.Server.ENTITY_HEAD_ROTATION, entityId).apply {
            bytes.write(0, (yaw * 256F / 360F).toInt().toByte())
        }

        return Pair(lookContainer, headRotationContainer)
    }

}