package de.fllip.entity.plugin.module

import com.google.inject.AbstractModule
import com.google.inject.Module
import com.google.inject.assistedinject.FactoryModuleBuilder
import de.fllip.entity.api.EntityAPI
import de.fllip.entity.api.entity.Entity
import de.fllip.entity.api.event.FactoryInformation

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 21.04.2021
 * Time: 20:33
 */
class EntityPluginModule(
    private val factoryInformationList: List<FactoryInformation<*>>
) : AbstractModule() {

    override fun configure() {
        requestStaticInjection(EntityAPI::class.java)

        factoryInformationList.forEach {
            install(it.buildModule())
        }
    }

}