package de.fllip.entity.plugin.entity.packet

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.EnumWrappers
import com.comphenix.protocol.wrappers.Pair
import com.google.inject.Singleton
import de.fllip.entity.api.entity.item.EquipmentItem

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 22.04.2021
 * Time: 21:13
 */
@Singleton
class EquipmentPacketCreator: AbstractEntityPacketCreator() {

    fun create(enityId: Int, equipmentItems: List<EquipmentItem>): PacketContainer {
        val slotStackPairList = equipmentItems.map {
            Pair(EnumWrappers.ItemSlot.valueOf(it.itemSlot.toString()), it.itemStack)
        }

        return createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT, enityId).apply {
            slotStackPairLists.write(0, slotStackPairList)
        }
    }

}