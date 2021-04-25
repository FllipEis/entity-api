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

package de.fllip.entity.api.entity.configuration

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
 * Time: 19:12
 *
 * Base class to configurate an [Entity]
 */
interface EntityConfigurationAdapter {

    /**
     * Sets the display name of the entity
     *
     * @param name the name to set
     * @return this instance of the configuration
     */
    fun withDisplayName(name: String): EntityConfigurationAdapter

    /**
     * Sets the display name of the entity
     *
     * @param displayNameHandler the function to set the name
     * @return this instance of the configuration
     */
    fun withDisplayName(displayNameHandler: (Player) -> String): EntityConfigurationAdapter

    /**
     * Sets a holgram with multiple lines on top of the entity
     *
     * @param lines the lines to set
     * @return this instance of the configuration
     */
    fun withHologramLines(vararg lines: String): EntityConfigurationAdapter

    /**
     * Sets a holgram line on top of the entity
     *
     * @param line the line to set
     * @return this instance of the configuration
     */
    fun withHologramLine(line: String): EntityConfigurationAdapter

    /**
     * Sets a holgram line on top of the entity
     *
     * @param hologramLineHandler the function to set the line
     * @return this instance of the configuration
     */
    fun withHologramLine(hologramLineHandler:  (Player) -> String): EntityConfigurationAdapter

    /**
     * Sets the location of the entity
     *
     * @param location the location where entity should spawn
     * @return this instance of the configuration
     */
    fun withLocation(location: Location): EntityConfigurationAdapter

    /**
     * Sets the spawn handler of the entity
     *
     * @param spawnHandler the function to set if the entity should be rendered for the player.
     *  Called when the entity spawns
     * @return this instance of the configuration
     */
    fun withSpawnHandler(spawnHandler: (Player) -> Boolean): EntityConfigurationAdapter

    /**
     * Sets the spawn handler of the entity
     *
     * @param interactHandler the consumer to listen for interactions
     * @return this instance of the configuration
     */
    fun withInteractHandler(interactHandler: Consumer<EntityInteractResult>): EntityConfigurationAdapter

    /**
     * Equips the fake player
     *
     * @param itemSlot the slot of the item
     * @param itemStack the item to set
     * @return this instance of the configuration
     */
    fun withEquipmentItem(itemSlot: EquipmentItemSlot, itemStack: ItemStack): EntityConfigurationAdapter


    /**
     * Equips the fake player
     *
     * @param equipmentItem the equipment item to set
     * @return this instance of the configuration
     */
    fun withEquipmentItem(equipmentItem: EquipmentItem): EntityConfigurationAdapter


    /**
     * Equips the fake player
     *
     * @param equipmentItems the equipment items to set
     * @return this instance of the configuration
     */
    fun withEquipmentItems(vararg equipmentItems: EquipmentItem): EntityConfigurationAdapter

    /**
     * @return the configured display name handler
     */
    fun getDisplayNameHandler(): (Player) -> String

    /**
     * @return the configured hologram lines
     */
    fun getHologramLinesHandler(): List<(Player) -> String>

    /**
     * @return the configured location
     */
    fun getLocation(): Location?

    /**
     * @return the configured spawn handler
     */
    fun getSpawnHandler(): (Player) -> Boolean

    /**
     * @return the configured interact handler
     */
    fun getInteractHandler(): Consumer<EntityInteractResult>

    /**
     * @return the items to equip the fake player
     */
    fun getEquipmentItems(): List<EquipmentItem>

}