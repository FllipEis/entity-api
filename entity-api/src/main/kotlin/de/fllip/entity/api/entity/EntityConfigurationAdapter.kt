package de.fllip.entity.api.entity

import de.fllip.entity.api.entity.result.EntityInteractResult
import org.bukkit.Location
import org.bukkit.entity.Player
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
    fun withDisplayName(name: String): EntityConfigurationAdapter = withDisplayName { name }

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
    fun withHologramLine(line: String): EntityConfigurationAdapter = withHologramLine { line }

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

}