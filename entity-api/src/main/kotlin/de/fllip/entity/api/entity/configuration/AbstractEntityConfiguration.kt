package de.fllip.entity.api.entity.configuration

import com.google.common.collect.Lists
import de.fllip.entity.api.entity.item.EquipmentItem
import de.fllip.entity.api.entity.item.EquipmentItemSlot
import de.fllip.entity.api.entity.result.EntityInteractResult
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.function.Consumer

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 23.04.2021
 * Time: 14:46
 */
abstract class AbstractEntityConfiguration <T: EntityConfigurationAdapter> : EntityConfigurationAdapter {

    private var displayNameHandler: (Player) -> String = { "" }
    private var hologramLinesHandlers: MutableList<(Player) -> String> = Lists.newArrayList()
    private var location: Location? = null
    private var spawnHandler: ((Player) -> Boolean) = { true }
    private var interactHandler: Consumer<EntityInteractResult> = Consumer{}
    private var equipmentItems: MutableList<EquipmentItem> = Lists.newArrayList()

    override fun withDisplayName(name: String): T {
        return withDisplayName { name }
    }

    override fun withDisplayName(displayNameHandler: (Player) -> String): T {
        this.displayNameHandler = displayNameHandler
        return this as T
    }

    override fun withHologramLines(vararg lines: String): T {
        lines.forEach { line ->
            hologramLinesHandlers.add { line }
        }
        return this as T
    }

    override fun withHologramLine(line: String): T {
        return withHologramLine { line }
    }

    override fun withHologramLine(hologramLineHandler: (Player) -> String): T {
        hologramLinesHandlers.add(hologramLineHandler)
        return this as T
    }

    override fun withLocation(location: Location): T {
        this.location = location
        return this as T
    }

    override fun withSpawnHandler(spawnHandler: (Player) -> Boolean): T {
        this.spawnHandler = spawnHandler
        return this as T
    }

    override fun withInteractHandler(interactHandler: Consumer<EntityInteractResult>): T {
        this.interactHandler = interactHandler
        return this as T
    }

    override fun withEquipmentItem(
        itemSlot: EquipmentItemSlot,
        itemStack: ItemStack
    ): T {
        equipmentItems.add(EquipmentItem(itemSlot, itemStack))
        return this as T
    }

    override fun withEquipmentItem(equipmentItem: EquipmentItem): T {
        equipmentItems.add(equipmentItem)
        return this as T
    }

    override fun withEquipmentItems(vararg equipmentItems: EquipmentItem): T {
        this.equipmentItems.addAll(equipmentItems.toList())
        return this as T
    }

    override fun getDisplayNameHandler(): (Player) -> String {
        return displayNameHandler
    }

    override fun getHologramLinesHandler(): List<(Player) -> String> {
        return hologramLinesHandlers
    }

    override fun getLocation(): Location? {
        return location
    }

    override fun getSpawnHandler(): (Player) -> Boolean {
        return spawnHandler
    }

    override fun getInteractHandler(): Consumer<EntityInteractResult> {
        return interactHandler
    }

    override fun getEquipmentItems(): List<EquipmentItem> {
        return equipmentItems
    }
    
}