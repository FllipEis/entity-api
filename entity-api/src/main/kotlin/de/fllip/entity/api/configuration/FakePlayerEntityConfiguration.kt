/*
 * MIT License
 *
 * Copyright (c) 2020 Philipp Eistrach
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

package de.fllip.entity.api.configuration

import de.fllip.entity.api.entity.type.fakeplayer.SkinData
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 30.12.2020
 * Time: 20:02
 */
class FakePlayerEntityConfiguration : AbstractEntityConfiguration<FakePlayerEntityConfiguration>() {

    var lookAtPlayer = false
    var mainHandItem: ItemStack? = null
    var offHandItem: ItemStack? = null
    var skinDataHandler: ((Player) -> SkinData)? = null

    fun withMainHandItem(mainHandItem: ItemStack): FakePlayerEntityConfiguration {
        this.mainHandItem = mainHandItem
        return this
    }

    fun withMainHandItem(mainHandMaterial: Material): FakePlayerEntityConfiguration {
        this.mainHandItem = ItemStack(mainHandMaterial, 1)
        return this
    }

    fun withOffHandItem(offHandItem: ItemStack): FakePlayerEntityConfiguration {
        this.offHandItem = offHandItem
        return this
    }

    fun withOffHandItem(offHandMaterial: Material): FakePlayerEntityConfiguration {
        this.offHandItem = ItemStack(offHandMaterial, 1)
        return this
    }

    fun withLookAtPlayer(lookAtPlayer: Boolean): FakePlayerEntityConfiguration {
        this.lookAtPlayer = lookAtPlayer
        return this
    }

    fun withSkinData(skinData: SkinData): FakePlayerEntityConfiguration {
        return withSkinData { skinData }
    }

    fun withSkinData(skinDataHandler: ((Player) -> SkinData)): FakePlayerEntityConfiguration {
        this.skinDataHandler = skinDataHandler
        return this
    }

}