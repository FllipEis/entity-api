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

import com.google.inject.Inject
import com.google.inject.Singleton
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
 * Date: 01.01.2021
 * Time: 18:13
 */
@Singleton
class EntityRenderTask @Inject constructor(
    entityService: EntityService
) : AbstractEntityTask(entityService) {

    override fun <T : AbstractEntityConfiguration<T>> handle(player: Player, entity: AbstractEntity<T>, inRange: Boolean, rendered: Boolean) {
        if (inRange && !rendered) {
            entity.render(player)
        } else if (!inRange && rendered) {
            entity.despawn(player)
        }
    }

}