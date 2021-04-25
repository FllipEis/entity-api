package de.fllip.entity.api.entity.armorstand

import de.fllip.entity.api.entity.configuration.EntityConfigurationAdapter

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 23.04.2021
 * Time: 14:41
 *
 * Configuration class for fake player entities
 */
interface ArmorStandEntityConfigurationAdapter : EntityConfigurationAdapter {

    /**
     * Sets the visibility of the armor stand
     *
     * @param visible if true the armor stand is visible
     * @return this instance of the configuration
     */
    fun withVisibility(visible: Boolean): ArmorStandEntityConfigurationAdapter

    /**
     * @return if armor stand is visible
     */
    fun isVisible(): Boolean

}