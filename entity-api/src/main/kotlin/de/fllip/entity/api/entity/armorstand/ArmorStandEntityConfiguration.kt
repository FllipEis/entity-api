package de.fllip.entity.api.entity.armorstand

import de.fllip.entity.api.entity.configuration.AbstractEntityConfiguration

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 23.04.2021
 * Time: 14:41
 */
class ArmorStandEntityConfiguration : AbstractEntityConfiguration<ArmorStandEntityConfiguration>(),
    ArmorStandEntityConfigurationAdapter {

    private var visible: Boolean = true

    override fun withVisibility(visible: Boolean): ArmorStandEntityConfigurationAdapter {
        this.visible = visible
        return this
    }

    override fun isVisible(): Boolean {
        return visible
    }

}