package de.fllip.entity.api.entity.fakeplayer

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
 * Date: 21.04.2021
 * Time: 19:21
 */
class FakePlayerEntityConfiguration : FakePlayerEntityConfigurationAdapter {

    private var displayNameHandler: (Player) -> String = { "" }
    private var hologramLinesHandlers: MutableList<(Player) -> String> = Lists.newArrayList()
    private var location: Location? = null
    private var spawnHandler: ((Player) -> Boolean) = { true }
    private var interactHandler: Consumer<EntityInteractResult> = Consumer{}
    private var equipmentItems: MutableList<EquipmentItem> = Lists.newArrayList()

    private var skinDataHandler: (Player) -> SkinData = { SkinData("", "") }
    private var lookAtPlayer: Boolean = false

    override fun withSkinData(skinData: SkinData): FakePlayerEntityConfiguration {
        return withSkinData { skinData }
    }

    override fun withSkinData(skinDataHandler: (Player) -> SkinData): FakePlayerEntityConfiguration {
        this.skinDataHandler = skinDataHandler
        return this
    }

    override fun withLookAtPlayer(lookAtPlayer: Boolean): FakePlayerEntityConfiguration {
        this.lookAtPlayer = lookAtPlayer
        return this
    }

    override fun withDisplayName(name: String): FakePlayerEntityConfiguration {
        return withDisplayName { name }
    }

    override fun withDisplayName(displayNameHandler: (Player) -> String): FakePlayerEntityConfiguration {
        this.displayNameHandler = displayNameHandler
        return this
    }

    override fun withHologramLines(vararg lines: String): FakePlayerEntityConfiguration {
        lines.forEach { line ->
            hologramLinesHandlers.add { line }
        }
        return this
    }

    override fun withHologramLine(line: String): FakePlayerEntityConfiguration {
        return withHologramLine { line }
    }

    override fun withHologramLine(hologramLineHandler: (Player) -> String): FakePlayerEntityConfiguration {
        hologramLinesHandlers.add(hologramLineHandler)
        return this
    }

    override fun withLocation(location: Location): FakePlayerEntityConfiguration {
        this.location = location
        return this
    }

    override fun withSpawnHandler(spawnHandler: (Player) -> Boolean): FakePlayerEntityConfiguration {
        this.spawnHandler = spawnHandler
        return this
    }

    override fun withInteractHandler(interactHandler: Consumer<EntityInteractResult>): FakePlayerEntityConfiguration {
        this.interactHandler = interactHandler
        return this
    }

    override fun withEquipmentItem(
        itemSlot: EquipmentItemSlot,
        itemStack: ItemStack
    ): FakePlayerEntityConfiguration {
        equipmentItems.add(EquipmentItem(itemSlot, itemStack))
        return this
    }

    override fun withEquipmentItem(equipmentItem: EquipmentItem): FakePlayerEntityConfiguration {
        equipmentItems.add(equipmentItem)
        return this
    }

    override fun withEquipmentItems(vararg equipmentItems: EquipmentItem): FakePlayerEntityConfiguration {
        this.equipmentItems.addAll(equipmentItems.toList())
        return this
    }

    override fun getSkinDataHandler(): (Player) -> SkinData {
        return skinDataHandler
    }

    override fun getLookAtPlayer(): Boolean {
        return lookAtPlayer
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