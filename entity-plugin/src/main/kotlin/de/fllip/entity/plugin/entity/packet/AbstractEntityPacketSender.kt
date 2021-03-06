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

import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import de.fllip.entity.api.entity.AbstractEntity
import de.fllip.entity.api.entity.Entity
import de.fllip.entity.plugin.renderer.DefaultEntityRenderer
import org.bukkit.entity.Player

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 22.04.2021
 * Time: 20:31
 */
abstract class AbstractEntityPacketSender(
    private val entityRenderer: DefaultEntityRenderer
): AbstractEntity(entityRenderer) {

    protected fun sendPacket(packetContainer: PacketContainer) {
        sendPackets(packetContainer)
    }

    protected fun sendPacket(player: Player, packetContainer: PacketContainer) {
        sendPackets(player, packetContainer)
    }

    protected fun sendPackets(player: Player, vararg packets: PacketContainer) {
        packets.forEach {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, it)
        }
    }

    protected fun sendPackets(vararg packets: PacketContainer) {
        entityRenderer.getEntityRenderList(this).forEach {
            sendPackets(it.player, *packets)
        }
    }

}