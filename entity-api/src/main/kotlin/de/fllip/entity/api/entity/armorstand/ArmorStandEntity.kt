package de.fllip.entity.api.entity.armorstand

import de.fllip.entity.api.entity.Entity

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 23.04.2021
 * Time: 14:40
 *
 * Class for armor stand entities
 */
interface ArmorStandEntity : Entity {

    /**
     * Factory for creating fake players
     */
    interface Factory: Entity.Factory {

        /**
         * Creates a armor stand entity
         *
         * @param configurationAdapter the configuration for the entity
         * @return the instance of the entity
         */
        fun create(configurationAdapter: ArmorStandEntityConfigurationAdapter): ArmorStandEntity

    }

}