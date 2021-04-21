package de.fllip.entity.plugin.entity

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import de.fllip.entity.api.entity.Entity
import java.util.*

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 21.04.2021
 * Time: 20:43
 */
class DefaultEntity @Inject constructor(
    @Assisted private val unqiueId: UUID,
    @Assisted private val entityId: Int
): Entity {

    override fun getUniqueId(): UUID {
        return unqiueId
    }

    override fun getEntityId(): Int {
        return entityId
    }

}