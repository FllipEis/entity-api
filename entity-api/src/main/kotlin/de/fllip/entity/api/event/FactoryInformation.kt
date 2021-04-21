package de.fllip.entity.api.event

import com.google.inject.Module
import com.google.inject.assistedinject.FactoryModuleBuilder
import de.fllip.entity.api.entity.Entity

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 21.04.2021
 * Time: 20:27
 *
 * Class for storing information to create a new factory
 */
data class FactoryInformation <T: Entity> (
    val factoryClass: Class<out Entity.Factory>,
    val entityInterfaceClass: Class<T>,
    val entityClass: Class<out T>
) {

    /**
     * Builds a guice module for this factory with this information
     *
     * @return the guice module
     */
    fun buildModule(): Module {
        return FactoryModuleBuilder()
            .implement(entityInterfaceClass, entityClass)
            .build(factoryClass)
    }

}