package de.fllip.entity.api.entity.fakeplayer

import de.fllip.entity.api.entity.EntityConfigurationAdapter
import de.fllip.entity.api.entity.item.EquipmentItem
import de.fllip.entity.api.entity.item.EquipmentItemSlot
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 21.04.2021
 * Time: 19:19
 */
interface FakePlayerEntityConfigurationAdapter : EntityConfigurationAdapter {

    /**
     * Sets the skin of the fake player
     *
     * @param skinData the skin to set
     * @return this instance of the configuration
     */
    fun withSkinData(skinData: SkinData): FakePlayerEntityConfigurationAdapter

    /**
     * Sets the skin of the fake player
     *
     * @param skinDataHandler the function to set the skin
     * @return this instance of the configuration
     */
    fun withSkinData(skinDataHandler: ((Player) -> SkinData)): FakePlayerEntityConfigurationAdapter

    /**
     * Sets if the fake player should always look at the player
     *
     * @param lookAtPlayer when true the entity should look at the player
     * @return this instance of the configuration
     */
    fun withLookAtPlayer(lookAtPlayer: Boolean): FakePlayerEntityConfigurationAdapter

    /**
     * @return the configured skin data handler
     */
    fun getSkinDataHandler(): (Player) -> SkinData

    /**
     * @return how look at player is configuered
     */
    fun getLookAtPlayer(): Boolean

}