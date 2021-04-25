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