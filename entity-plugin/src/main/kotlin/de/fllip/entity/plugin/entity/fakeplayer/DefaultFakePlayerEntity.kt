package de.fllip.entity.plugin.entity.fakeplayer

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import de.fllip.entity.api.entity.fakeplayer.FakePlayerEntity
import java.util.*

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 21.04.2021
 * Time: 20:45
 */
class DefaultFakePlayerEntity@Inject constructor(
    @Assisted private val unqiueId: UUID,
    @Assisted private val entityId: Int
): FakePlayerEntity {

    override fun getUniqueId(): UUID {
        return unqiueId
    }

    override fun getEntityId(): Int {
        return entityId
    }

}