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
import com.comphenix.protocol.wrappers.EnumWrappers
import com.comphenix.protocol.wrappers.Pair
import com.google.inject.Singleton
import de.fllip.entity.api.entity.item.EquipmentItem
import de.fllip.entity.api.entity.item.EquipmentItemSlot
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 22.04.2021
 * Time: 21:13
 */
@Singleton
class EquipmentPacketCreator: AbstractEntityPacketCreator() {

    companion object {
        private val AIR_ITEM = ItemStack(Material.AIR)
    }

    fun create(enityId: Int, equipmentItems: List<EquipmentItem>): PacketContainer {
        var slotStackPairList = equipmentItems.map {
            Pair(EnumWrappers.ItemSlot.valueOf(it.itemSlot.toString()), it.itemStack)
        }

        if (slotStackPairList.isEmpty()) {
            slotStackPairList = EquipmentItemSlot.values().map {
                Pair(EnumWrappers.ItemSlot.valueOf(it.toString()), AIR_ITEM)
            }
        }

        return createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT, enityId).apply {
            slotStackPairLists.write(0, slotStackPairList)
        }
    }

}