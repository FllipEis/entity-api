package de.fllip.entity.api.entity

import org.bukkit.entity.Player

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

}