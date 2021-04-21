package de.fllip.entity.plugin.entity.fakeplayer

import de.fllip.entity.api.entity.fakeplayer.FakePlayerEntityConfigurationAdapter
import org.bukkit.entity.Player

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

}