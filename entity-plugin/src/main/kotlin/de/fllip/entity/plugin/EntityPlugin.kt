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

package de.fllip.entity.plugin

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import com.google.inject.Guice
import de.fllip.entity.api.entity.armorstand.ArmorStandEntity
import de.fllip.entity.api.entity.fakeplayer.FakePlayerEntity
import de.fllip.entity.api.entity.result.EntityInteractAction
import de.fllip.entity.api.entity.result.EntityInteractResult
import de.fllip.entity.api.event.FactoryInformation
import de.fllip.entity.api.event.GuiceInitializeEvent
import de.fllip.entity.plugin.entity.armorstand.DefaultArmorStandEntity
import de.fllip.entity.plugin.entity.fakeplayer.DefaultFakePlayerEntity
import de.fllip.entity.plugin.module.EntityPluginModule
import de.fllip.entity.plugin.renderer.DefaultEntityRenderer
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.annotation.dependency.Dependency
import org.bukkit.plugin.java.annotation.dependency.DependsOn
import org.bukkit.plugin.java.annotation.plugin.ApiVersion
import org.bukkit.plugin.java.annotation.plugin.Plugin


/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 21.04.2021
 * Time: 20:22
 */
@Plugin(
    name = "EntityPlugin",
    version = "2.0"
)
@ApiVersion(ApiVersion.Target.v1_15)
@DependsOn(Dependency("ProtocolLib"))
class EntityPlugin : JavaPlugin() {

    companion object {
        private val DEFAULT_FACTORY_INFORMATION_LIST: MutableList<FactoryInformation<*>> = arrayListOf(
            FactoryInformation(
                FakePlayerEntity.Factory::class.java,
                FakePlayerEntity::class.java,
                DefaultFakePlayerEntity::class.java
            ),
            FactoryInformation(
                ArmorStandEntity.Factory::class.java,
                ArmorStandEntity::class.java,
                DefaultArmorStandEntity::class.java
            )
        )
    }

    override fun onEnable() {
        val guiceInitializeEvent = GuiceInitializeEvent(DEFAULT_FACTORY_INFORMATION_LIST)
        Bukkit.getPluginManager().callEvent(guiceInitializeEvent)
        val injector = Guice.createInjector(EntityPluginModule(this, guiceInitializeEvent.factoryInformationList))

        val entityRenderer = injector.getInstance(DefaultEntityRenderer::class.java)

        Bukkit.getScheduler().runTaskTimer(this, Runnable {
            entityRenderer?.render()
        }, 1, 1)

        ProtocolLibrary.getProtocolManager()
            .addPacketListener(object : PacketAdapter(this, PacketType.Play.Client.USE_ENTITY) {

                override fun onPacketReceiving(event: PacketEvent) {
                    val container = event.packet
                    val entityId = container.integers.read(0)

                    val entity = entityRenderer.getEntityById(entityId) ?: return
                    val action = container.entityUseActions.read(0)

                    entity.getEntityConfiguration().getInteractHandler().accept(
                        EntityInteractResult(event.player, entity, EntityInteractAction.valueOf(action.toString()))
                    )
                }

            })

    }

}