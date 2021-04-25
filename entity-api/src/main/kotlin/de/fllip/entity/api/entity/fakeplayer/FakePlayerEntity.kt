package de.fllip.entity.api.entity.fakeplayer

import de.fllip.entity.api.entity.Entity
import org.bukkit.entity.Player

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 21.04.2021
 * Time: 19:53
 *
 * Class for fake player entities
 */
interface FakePlayerEntity : Entity {

    /**
     * Updates the posen of this entity
     *
     * @param player the player to whom the entity pose should be updated
     * @param entityPose the pose the entity should update
     */
    fun updateEntityPose(player: Player, entityPose: EntityPose)

    /**
     * Factory for creating fake players
     */
    interface Factory: Entity.Factory {

        /**
         * Creates a fake player entity
         *
         * @param configurationAdapter the configuration for the entity
         * @return the instance of the entity
         */
        fun create(configurationAdapter: FakePlayerEntityConfigurationAdapter): FakePlayerEntity

    }

}