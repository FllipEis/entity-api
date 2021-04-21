package de.fllip.entity.api

import com.google.inject.Inject
import com.google.inject.Injector
import de.fllip.entity.api.entity.Entity
import de.fllip.entity.api.entity.fakeplayer.FakePlayerEntity

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 21.04.2021
 * Time: 15:46
 *
 * Offers all functions of the api
 */
class EntityAPI @Inject constructor(
    val fakePlayerEntityFactory: FakePlayerEntity.Factory,
    private val injector: Injector
) {

    /**
     * @param entityFactoryClass the class of the factory
     * @return the instance of the factory
     */
    fun <T: Entity.Factory> getEntityFactoryByClass(entityFactoryClass: Class<T>): T {
        return injector.getInstance(entityFactoryClass)
    }

    companion object {

        @JvmStatic
        @Inject
        lateinit var instance: EntityAPI
            private set

    }


}