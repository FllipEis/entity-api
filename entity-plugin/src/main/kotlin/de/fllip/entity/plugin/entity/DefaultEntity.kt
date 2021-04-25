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

package de.fllip.entity.plugin.entity

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import de.fllip.entity.api.entity.AbstractEntity
import de.fllip.entity.api.entity.Entity
import de.fllip.entity.api.entity.configuration.EntityConfigurationAdapter
import de.fllip.entity.api.entity.item.EquipmentItem
import de.fllip.entity.plugin.renderer.DefaultEntityRenderer
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 21.04.2021
 * Time: 20:43
 */
class DefaultEntity @Inject constructor(
    @Assisted private val configurationAdapter: EntityConfigurationAdapter,
    private val entityRenderer: DefaultEntityRenderer
): AbstractEntity(entityRenderer) {

    private val unqiueId = UUID.randomUUID()
    private val entityId = ThreadLocalRandom.current().nextInt(Short.MAX_VALUE.toInt())

    override fun getUniqueId(): UUID {
        return unqiueId
    }

    override fun getEntityId(): Int {
        return entityId
    }

    override fun getEntityConfiguration(): EntityConfigurationAdapter {
        return configurationAdapter
    }

    override fun spawn(player: Player) {
    }

    override fun despawn(player: Player) {
    }

    override fun updateDisplayName(player: Player) {
    }

    override fun updateEquipment(player: Player, equipmentItems: List<EquipmentItem>) {
    }

}