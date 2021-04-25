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

package de.fllip.entity.api.entity

import com.google.common.collect.Lists
import de.fllip.entity.api.EntityAPI
import de.fllip.entity.api.entity.armorstand.ArmorStandEntityConfiguration
import org.bukkit.Bukkit
import java.util.*
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 23.04.2021
 * Time: 20:56
 */
abstract class AbstractEntity(
    private val entityRenderer: EntityRenderer
) : Entity {

    private val uniqueId = UUID(ThreadLocalRandom.current().nextLong(), 0)
    private val entityId = ThreadLocalRandom.current().nextInt(Short.MAX_VALUE.toInt())

    private val childEntities = Lists.newArrayList<Entity>()

    override fun getUniqueId(): UUID {
        return uniqueId
    }

    override fun getEntityId(): Int {
        return entityId
    }

    override fun addChild(entity: Entity) {
        childEntities.add(entity)
    }

    override fun removeChild(uniqueId: UUID) {
        childEntities.removeIf { it.getUniqueId() == uniqueId }
    }

    override fun getChilds(): List<Entity> {
        return childEntities
    }

    override fun startRendering() {
        entityRenderer.addEntity(this)
        val configurationAdapter = getEntityConfiguration()

        val location = configurationAdapter.getLocation() ?: return

        configurationAdapter.getHologramLinesHandler().forEachIndexed { index, displayNameHandler ->
                EntityAPI.instance.armorStandEntityFactory.create(
                    ArmorStandEntityConfiguration()
                        .withDisplayName(displayNameHandler)
                        .withLocation(location.clone().add(0.0, 0.3 * (index) + 0.225, 0.0))
                        .withSpawnHandler { configurationAdapter.getSpawnHandler().invoke(it) }
                        .withVisibility(false)
                ).startRendering()
            }
    }

    override fun stopRendering() {
        entityRenderer.removeEntity(getUniqueId())
    }

}