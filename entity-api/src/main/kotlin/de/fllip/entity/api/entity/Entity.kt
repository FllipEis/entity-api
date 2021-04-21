package de.fllip.entity.api.entity

import com.google.inject.assistedinject.Assisted
import com.google.inject.assistedinject.AssistedInject
import java.util.*
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 21.04.2021
 * Time: 15:58
 *
 * Base class of all entities
 */
interface Entity {

    /**
     * @return the entity's unique id
     */
    fun getUniqueId(): UUID

    /**
     * @return the entity's entity id
     */
    fun getEntityId(): Int

    /**
     * Base class of all entity factories
     */
    interface Factory

}