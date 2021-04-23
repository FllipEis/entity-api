package de.fllip.entity.plugin.entity.packet

import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import de.fllip.entity.api.entity.Entity
import de.fllip.entity.plugin.renderer.EntityRenderer
import org.bukkit.entity.Player

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 22.04.2021
 * Time: 20:31
 */
abstract class AbstractEntityPacketSender(
    private val entityRenderer: EntityRenderer
): Entity {

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