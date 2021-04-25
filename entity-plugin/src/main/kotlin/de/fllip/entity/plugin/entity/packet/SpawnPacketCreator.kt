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