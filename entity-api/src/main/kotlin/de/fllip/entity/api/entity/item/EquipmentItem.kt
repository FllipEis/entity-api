package de.fllip.entity.api.entity.item

import org.bukkit.inventory.ItemStack

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 22.04.2021
 * Time: 18:01
 */
data class EquipmentItem(
    val itemSlot: EquipmentItemSlot,
    val itemStack: ItemStack
)