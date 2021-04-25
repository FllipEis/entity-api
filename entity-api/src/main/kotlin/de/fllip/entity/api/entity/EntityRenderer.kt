package de.fllip.entity.api.entity

import java.util.*

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 23.04.2021
 * Time: 20:53
 *
 * Manages all entities that should be rendered
 */
interface EntityRenderer {

    /**
     * Adds an entity to the render list
     *
     * @param entity the entity to add
     */
    fun addEntity(entity: Entity)

    /**
     * Removes an entity from the render list
     *
     * @param uniqueId the entity unique id to remove
     */
    fun removeEntity(uniqueId: UUID)

    /**
     * @return all entities that can be rendered
     */
    fun getRenderList(): List<Entity>

}