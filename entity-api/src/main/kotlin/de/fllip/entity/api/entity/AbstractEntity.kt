package de.fllip.entity.api.entity

import com.google.common.collect.Lists
import de.fllip.entity.api.EntityAPI
import de.fllip.entity.api.entity.armorstand.ArmorStandEntityConfiguration
import org.bukkit.Bukkit
import java.util.*
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 23.04.2021
 * Time: 20:56
 */
abstract class AbstractEntity(
    private val entityRenderer: EntityRenderer
) : Entity {

    private val uniqueId = UUID(ThreadLocalRandom.current().nextLong(), 0)
    private val entityId = ThreadLocalRandom.current().nextInt(Short.MAX_VALUE.toInt())

    private val childEntities = Lists.newArrayList<Entity>()

    override fun getUniqueId(): UUID {
        return uniqueId
    }

    override fun getEntityId(): Int {
        return entityId
    }

    override fun addChild(entity: Entity) {
        childEntities.add(entity)
    }

    override fun removeChild(uniqueId: UUID) {
        childEntities.removeIf { it.getUniqueId() == uniqueId }
    }

    override fun getChilds(): List<Entity> {
        return childEntities
    }

    override fun startRendering() {
        entityRenderer.addEntity(this)
        val configurationAdapter = getEntityConfiguration()

        val location = configurationAdapter.getLocation() ?: return

        configurationAdapter.getHologramLinesHandler().forEachIndexed { index, displayNameHandler ->
                EntityAPI.instance.armorStandEntityFactory.create(
                    ArmorStandEntityConfiguration()
                        .withDisplayName(displayNameHandler)
                        .withLocation(location.clone().add(0.0, 0.3 * (index) + 0.225, 0.0))
                        .withSpawnHandler { configurationAdapter.getSpawnHandler().invoke(it) }
                        .withVisibility(false)
                ).startRendering()
            }
    }

    override fun stopRendering() {
        entityRenderer.removeEntity(getUniqueId())
    }

}