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

package de.fllip.entity.plugin

import de.fllip.entity.api.EntityAPI
import de.fllip.entity.api.configuration.FakePlayerEntityConfiguration
import de.fllip.entity.api.entity.animation.EntityAnimationType
import de.fllip.entity.api.entity.interact.EntityInteractAction
import de.fllip.entity.api.entity.type.fakeplayer.SkinData
import de.fllip.entity.api.entity.update.EntityUpdateType
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin


/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 29.12.2020
 * Time: 17:10
 */
class EntityPlugin : JavaPlugin() {

    override fun onEnable() {
        EntityAPI.init(this)
        val spawnLocation = Bukkit.getWorld("world")!!.spawnLocation
         EntityAPI.instance.creator.createFakePlayer(
            FakePlayerEntityConfiguration()
                .withDisplayName("§aTest123")
                .withHologramLine { "§bHallo ${it.player!!.name}" }
                .withHologramLines("Test", "Test2")
                .withLocation(spawnLocation)
                .withLookAtPlayer(false)
                .withMainHandItem(Material.IRON_SWORD)
                .withSkinData(
                    SkinData(
                        "ewogICJ0aW1lc3RhbXAiIDogMTYwODQ2Mzk2MDIxNSwKICAicHJvZmlsZUlkIiA6ICJmNzg4YzU5ZGY2MzU0M2MxOGMzY2M5YjczMzM4NGZlNSIsCiAgInByb2ZpbGVOYW1lIiA6ICJCd2VlZjY5IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2U3YjhiYjBlNmYyYWVjMDJmMTQ1OGI0MDM2NDg2YjFiMzcwODU2M2U3YTVlY2QxNjYzZWI3MWFlOGMxYWI3ZmIiCiAgICB9CiAgfQp9",
                        "p35JRZPm3XS2JM4IH46OVcX6vTZ990YEAEWhhn4Qz8mKxy5rKEfE0mapcryABLMKOmVnyjspDPieHUqTREqoZcpO+hJA4PvolaF5Hb7i3VLeWOHDg5+tpIsXIs9Pr/hiZlGCHUG57Nc34Ejs8XHpxyPfxlRxl5X6D9mwYRL+w6LBLJfY4Fbx1OQug++oIbMAyn67YQ37ROFKhZhZTO4qQzmTKct5eeAzv+frrGQSv+wTWIk6H6IOcTevq1FNwLRcVgXRPbATi5ROllcSWfZvpNLntgt8+LBPd1mbnBWzNZpvYUu30eMKo0qpHXtcPCeX90xyGUsNVnfOUOOcf0GXhPZyY0HhX7481vzDReobsiYql5DcLafmzO+sn71TXC0PsmvTd11gJ/zavFwbWfqTbtYyJQgWxARxgHRUwIwqG23snDJk8Xoj5nDA9H8rNODJUl9gqhyGiGMT5la9A07ASCMIMH5zeUWR/oRMakA2jcwnYpBUvwOg3cO6Bwm6qxE+QAkJxOZbe13LDErYy63RagInqx2htQV2vc7PerVl1tn6kqKRJLxVLsNVcfd6VW1Zy2EXndFsBjfD4siTXMUWsJ5A2FKmEbBzFSaBU2Wit3UUUboaFWBP6swjWfY6Ye9ol1BCq0TZf0LyaBHYs/O8wNSt983wxLdoOFTdfaQO/4k="
                    )
                )
                .withInteractHandler {
                    if (it.action == EntityInteractAction.ATTACK) {
                        it.entity.update(it.player, EntityUpdateType.ANIMATION, EntityAnimationType.TAKE_DAMAGE)
                    }
                }
         )
    }

}