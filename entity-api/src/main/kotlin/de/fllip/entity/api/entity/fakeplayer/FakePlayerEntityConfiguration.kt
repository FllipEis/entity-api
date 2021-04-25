package de.fllip.entity.api.entity.fakeplayer

import de.fllip.entity.api.entity.configuration.AbstractEntityConfiguration
import org.bukkit.entity.Player

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 21.04.2021
 * Time: 19:21
 */
class FakePlayerEntityConfiguration : AbstractEntityConfiguration<FakePlayerEntityConfiguration>(),
    FakePlayerEntityConfigurationAdapter {

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

    override fun getSkinDataHandler(): (Player) -> SkinData {
        return skinDataHandler
    }

    override fun getLookAtPlayer(): Boolean {
        return lookAtPlayer
    }

}