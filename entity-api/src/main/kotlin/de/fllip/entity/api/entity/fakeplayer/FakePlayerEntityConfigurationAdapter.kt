/*
 * Copyright (c) 2021 Philipp Eistrach
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.fllip.entity.api.entity.fakeplayer

import de.fllip.entity.api.entity.configuration.EntityConfigurationAdapter
import org.bukkit.entity.Player

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 21.04.2021
 * Time: 19:19
 *
 * Configuration class for fake player entities
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