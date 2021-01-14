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

package de.fllip.entity.api.task

import de.fllip.entity.api.configuration.AbstractEntityConfiguration
import de.fllip.entity.api.entity.AbstractEntity
import de.fllip.entity.api.entity.tracking.EntityTrackingRange
import de.fllip.entity.api.player.hasOnlineTime
import de.fllip.entity.api.service.EntityService
import org.bukkit.Bukkit
import org.bukkit.entity.Player

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 03.01.2021
 * Time: 15:20
 */
abstract class AbstractEntityTask(
    val entityService: EntityService
) : Runnable {

    override fun run() {
        val onlinePlayers = Bukkit.getOnlinePlayers()
        if (onlinePlayers.isEmpty()) {
            return
        }

        entityService.entities
            .filter { it.location != null }
            .forEach { entity ->
                entity.removeOfflineRenders()

                onlinePlayers
                    .filter { it.hasOnlineTime(3) }
                    .forEach {
                        val inRange = EntityTrackingRange.inRange(it.location, entity.location!!, 50.0)

                        val rendered = entity.isRendered(it)

                        handle(it, entity, inRange, rendered)
                    }
            }
    }

    abstract fun <T : AbstractEntityConfiguration<T>> handle(player: Player, entity: AbstractEntity<T>, inRange: Boolean, rendered: Boolean)

}