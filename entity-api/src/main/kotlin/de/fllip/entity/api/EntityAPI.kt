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

package de.fllip.entity.api

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import com.google.inject.Guice
import com.google.inject.Inject
import com.google.inject.Injector
import com.google.inject.Singleton
import de.fllip.entity.api.configuration.AbstractEntityConfiguration
import de.fllip.entity.api.creator.EntityCreator
import de.fllip.entity.api.entity.interact.EntityInteractAction
import de.fllip.entity.api.entity.interact.EntityInteractResult
import de.fllip.entity.api.listener.PlayerJoinListener
import de.fllip.entity.api.module.EntityModule
import de.fllip.entity.api.service.EntityService
import de.fllip.entity.api.task.EntityRenderTask
import de.fllip.entity.api.task.EntityTickTask
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin


/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 29.12.2020
 * Time: 17:02
 */
@Singleton
class EntityAPI @Inject constructor(
    private val injector: JavaPlugin,
    val creator: EntityCreator,
    val service: EntityService
) {

    companion object {

        @JvmStatic
        lateinit var instance: EntityAPI
            private set

        @JvmStatic
        fun init(javaPlugin: JavaPlugin) {
            val injector = Guice.createInjector(EntityModule(javaPlugin))
            instance = injector.getInstance(EntityAPI::class.java)
            register(injector, javaPlugin)
        }

        @JvmStatic
        fun register(injector: Injector, javaPlugin: JavaPlugin) {
            val entityService = injector.getInstance(EntityService::class.java)

            Bukkit.getPluginManager().registerEvents(injector.getInstance(PlayerJoinListener::class.java), javaPlugin)

            Bukkit.getScheduler().runTaskTimer(javaPlugin, injector.getInstance(EntityRenderTask::class.java), 1, 1)
            Bukkit.getScheduler().runTaskTimer(javaPlugin, injector.getInstance(EntityTickTask::class.java), 1, 1)

            ProtocolLibrary.getProtocolManager()
                .addPacketListener(object : PacketAdapter(javaPlugin, PacketType.Play.Client.USE_ENTITY) {

                    override fun onPacketReceiving(event: PacketEvent) {
                        val container = event.packet
                        val entityId = container.integers.read(0)

                        val entity = entityService.getEntityByEntityId(entityId) ?: return
                        val action = container.entityUseActions.read(0)

                        (entity.configuration as AbstractEntityConfiguration<*>).handleInteract?.accept(
                            EntityInteractResult(event.player, entity, EntityInteractAction.fromEntityUseAction(action))
                        )
                    }

                })
        }

    }

}