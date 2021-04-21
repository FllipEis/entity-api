package de.fllip.entity.api.entity.fakeplayer

import de.fllip.entity.api.entity.EntityConfigurationAdapter
import de.fllip.entity.api.entity.fakeplayer.FakePlayerEntityConfigurationAdapter
import de.fllip.entity.api.entity.result.EntityInteractResult
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.function.Consumer

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 21.04.2021
 * Time: 19:21
 */
class FakePlayerEntityConfiguration : FakePlayerEntityConfigurationAdapter {

    override fun withDisplayName(name: String): FakePlayerEntityConfigurationAdapter {
        return this
    }

    override fun withDisplayName(displayNameHandler: (Player) -> String): FakePlayerEntityConfigurationAdapter {
        return this
    }

    override fun withHologramLines(vararg lines: String): EntityConfigurationAdapter {
        return this
    }

    override fun withHologramLine(hologramLineHandler: (Player) -> String): EntityConfigurationAdapter {
        return this
    }

    override fun withLocation(location: Location): EntityConfigurationAdapter {
        return this
    }

    override fun withSpawnHandler(spawnHandler: (Player) -> Boolean): EntityConfigurationAdapter {
        return this
    }

    override fun withInteractHandler(interactHandler: Consumer<EntityInteractResult>): EntityConfigurationAdapter {
        return this
    }

}