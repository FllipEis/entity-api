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

package de.fllip.entity.api.creator

import com.google.inject.Inject
import com.google.inject.Injector
import com.google.inject.Singleton
import de.fllip.entity.api.configuration.AbstractEntityConfiguration
import de.fllip.entity.api.configuration.ArmorStandConfiguration
import de.fllip.entity.api.configuration.FakePlayerEntityConfiguration
import de.fllip.entity.api.entity.AbstractEntity
import de.fllip.entity.api.entity.type.armorstand.ArmorStandEntity
import de.fllip.entity.api.entity.type.fakeplayer.FakePlayerEntity
import de.fllip.entity.api.entity.update.EntityUpdateType
import de.fllip.entity.api.module.EntityCreateChildModule
import de.fllip.entity.api.service.EntityService
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 29.12.2020
 * Time: 17:05
 */
@Singleton
class EntityCreator @Inject constructor(
    private val injector: Injector,
    private val entityService: EntityService
) {

    fun <E : AbstractEntity<C>, C : AbstractEntityConfiguration<C>> create(
        entityClass: Class<out E>,
        configurationClass: Class<C>,
        configuration: C
    ): E {
        val childInjector = injector.createChildInjector(EntityCreateChildModule(configurationClass, configuration))
        val entity = childInjector.getInstance(entityClass)

        val holograms = createHologram(entity.location!!.clone(), configuration.hologramLinesHandlers)

        entityService.addEntity(entity)
        if (configuration.handleTeleport == null) {
            configuration.withTeleportHandler { location ->
                holograms.forEachIndexed { index, armorStandEntity ->
                    armorStandEntity.update(
                        EntityUpdateType.TELEPORT,
                        location.clone().add(0.0, 0.3 * (index) + 0.125, 0.0)
                    )
                }
            }
        }

        return entity
    }

    fun createFakePlayer(configuration: FakePlayerEntityConfiguration): FakePlayerEntity {
        return create(
            FakePlayerEntity::class.java,
            FakePlayerEntityConfiguration::class.java,
            configuration
        )
    }

    fun createArmorStand(configuration: ArmorStandConfiguration): ArmorStandEntity {
        return create(
            ArmorStandEntity::class.java,
            ArmorStandConfiguration::class.java,
            configuration
        )
    }

    fun createHologram(location: Location, vararg lines: String) {
        createHologram(location, lines.map { line -> { line } })
    }

    private fun createHologram(location: Location, lines: List<(Player) -> String>): List<ArmorStandEntity> {
        return lines.mapIndexed { index, displayName ->
            createArmorStand(
                ArmorStandConfiguration()
                    .withDisplayName(displayName)
                    .withLocation(location.clone().add(0.0, 0.3 * (index) + 0.125, 0.0))
                    .withVisibility(false)
            )
        }
    }

}