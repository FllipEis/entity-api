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

package de.fllip.entity.api.entity

import de.fllip.entity.api.entity.configuration.EntityConfigurationAdapter
import de.fllip.entity.api.entity.item.EquipmentItem
import org.bukkit.entity.Player
import java.util.*

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 21.04.2021
 * Time: 15:58
 *
 * Base class of all entities
 */
interface Entity {

    /**
     * @return this entity's unique id
     */
    fun getUniqueId(): UUID

    /**
     * @return this entity's entity id
     */
    fun getEntityId(): Int

    /**
     * @return this entity's entity configuration
     */
    fun getEntityConfiguration(): EntityConfigurationAdapter

    /**
     * Spawns the entity for a player
     *
     * @param player the player to whom this entity should be spawned
     */
    fun spawn(player: Player)

    /**
     * Despawns the entity for a player
     *
     * @param player the player to whom the entity should be despawned
     */
    fun despawn(player: Player)

    /**
     * Updates the display name of this entity
     *
     * @param player the player to whom the entity name should be updated
     */
    fun updateDisplayName(player: Player)

    /**
     * Updates the equipment of this entity
     *
     * @param player the player to whom the entity equipment should be updated
     */
    fun updateEquipment(player: Player, equipmentItems: List<EquipmentItem>)

    /**
     * Called every tick
     *
     * @param player the player to whom this method should be called
     * @param inRangeWithRange if player is in range of this entity and the range as double
     * @param isRendered if this entity is already rendered
     */
    fun onTick(player: Player, inRangeWithRange: Pair<Boolean, Double>, isRendered: Boolean) {}

    /**
     * Adds a child enttiy to this entity
     *
     * @param entity the entity to add
     */
    fun addChild(entity: Entity)

    /**
     * Removes a child enttiy from this entity
     *
     * @param uniqueId the entity uuid to remove
     */
    fun removeChild(uniqueId: UUID)

    /**
     * @return all child entities of this entity
     */
    fun getChilds(): List<Entity>

    /**
     * Adds this entity to the render list that it should be rendered for players
     */
    fun startRendering()

    /**
     * Removes this entity from the render list that it should not be rendered for players
     */
    fun stopRendering()

    /**
     * Base class of all entity factories
     */
    interface Factory

}