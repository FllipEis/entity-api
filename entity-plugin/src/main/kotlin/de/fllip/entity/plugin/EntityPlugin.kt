package de.fllip.entity.plugin

import com.google.inject.Guice
import de.fllip.entity.api.entity.fakeplayer.FakePlayerEntity
import de.fllip.entity.api.event.FactoryInformation
import de.fllip.entity.api.event.GuiceInitializeEvent
import de.fllip.entity.plugin.entity.fakeplayer.DefaultFakePlayerEntity
import de.fllip.entity.plugin.module.EntityPluginModule
import de.fllip.entity.plugin.renderer.EntityRenderer
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
            /*FactoryInformation(
                Entity.Factory::class.java,
                Entity::class.java,
                DefaultEntity::class.java
            )*/
        )
    }

    override fun onEnable() {
        val guiceInitializeEvent = GuiceInitializeEvent(DEFAULT_FACTORY_INFORMATION_LIST)
        Bukkit.getPluginManager().callEvent(guiceInitializeEvent)
        val injector = Guice.createInjector(EntityPluginModule(this, guiceInitializeEvent.factoryInformationList))

        val entityRenderer = injector.getInstance(EntityRenderer::class.java)

        Bukkit.getScheduler().runTaskTimer(this, Runnable {
            entityRenderer?.render()
        }, 1, 1)

    }

}