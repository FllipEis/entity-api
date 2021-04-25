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