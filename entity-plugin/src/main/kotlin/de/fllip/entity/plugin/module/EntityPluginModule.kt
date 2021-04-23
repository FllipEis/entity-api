package de.fllip.entity.plugin.module

import com.google.inject.AbstractModule
import com.google.inject.Module
import com.google.inject.assistedinject.FactoryModuleBuilder
import de.fllip.entity.api.EntityAPI
import de.fllip.entity.api.entity.Entity
import de.fllip.entity.api.entity.fakeplayer.FakePlayerEntity
import de.fllip.entity.api.event.FactoryInformation
import de.fllip.entity.plugin.EntityPlugin
import de.fllip.entity.plugin.entity.fakeplayer.DefaultFakePlayerEntity

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 21.04.2021
 * Time: 20:33
 */
class EntityPluginModule(
    private val plugin: EntityPlugin,
    private val factoryInformationList: List<FactoryInformation<*>>
) : AbstractModule() {

    override fun configure() {
        requestStaticInjection(EntityAPI::class.java)
        bind(EntityPlugin::class.java).toInstance(plugin)

        try {
            factoryInformationList.forEach {
                install(it.buildModule())
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

}