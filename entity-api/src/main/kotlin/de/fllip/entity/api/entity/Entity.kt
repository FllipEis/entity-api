package de.fllip.entity.api.entity

import com.google.inject.assistedinject.Assisted
import com.google.inject.assistedinject.AssistedInject
import de.fllip.entity.api.entity.item.EquipmentItem
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.ThreadLocalRandom

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