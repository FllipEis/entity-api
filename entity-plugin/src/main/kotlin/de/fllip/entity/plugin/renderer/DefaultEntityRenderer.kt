package de.fllip.entity.plugin.renderer

import com.google.common.collect.Lists
import com.google.inject.Inject
import com.google.inject.Singleton
import de.fllip.entity.api.entity.Entity
import de.fllip.entity.api.entity.EntityRenderer
import de.fllip.entity.plugin.EntityPlugin
import de.fllip.entity.plugin.later
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.*

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 22.04.2021
 * Time: 10:56
 */
@Singleton
class DefaultEntityRenderer @Inject constructor(
    private val plugin: EntityPlugin
): EntityRenderer {

    val entities = Lists.newArrayList<Entity>()!!
    private val renderList = Lists.newArrayList<PlayerRenderInformation>()

    override fun addEntity(entity: Entity) {
        entities.add(entity)
    }

    override fun removeEntity(uniqueId: UUID) {
        entities.removeIf { it.getUniqueId() == uniqueId }
    }

    override fun getRenderList(): List<Entity> {
        return entities
    }

    fun getEntityById(entityId: Int): Entity? {
        return entities.firstOrNull { it.getEntityId() == entityId }
    }

    fun removePlayerFromRenderList(entity: Entity, player: Player) {
        renderList.removeIf { it.entityUniqueId == entity.getUniqueId() && it.player == player }
    }

    fun getEntityRenderList(entity: Entity): List<PlayerRenderInformation> {
        return renderList.filter { it.entityUniqueId == entity.getUniqueId() }
    }

    fun render() {
        val onlinePlayers = Bukkit.getOnlinePlayers()

        val filteredEntities = entities
            .filter { it.getEntityConfiguration().getLocation() != null }

        filteredEntities.forEach {
            removeOfflineRenderes(it)
        }

        if (onlinePlayers.isEmpty()) {
            return
        }

        filteredEntities.forEach { entity ->

            val location = entity.getEntityConfiguration().getLocation()!!

            onlinePlayers.forEach {
                val rangeWithRange = inRange(it.location, location, 50.0)
                val inRange = rangeWithRange.first
                val isRendered = isRendered(entity, it)

                entity.onTick(it, rangeWithRange, isRendered)

                if (inRange && !isRendered) {
                    render(entity, it)
                } else if (!inRange && isRendered) {
                    removePlayerFromRenderList(entity, it)
                    entity.despawn(it)
                }
            }
        }
    }

    private fun render(entity: Entity, player: Player) {
        val canRender = canRender(entity, player)

        if (canRender) {
            renderList.add(PlayerRenderInformation(player, entity.getUniqueId()))
            later(plugin, 8) {
                entity.spawn(player)
            }
        }
    }

    private fun removeOfflineRenderes(entity: Entity) {
        renderList.removeIf { it.entityUniqueId == entity.getUniqueId() && !it.player.isOnline }
    }

    private fun isRendered(entity: Entity, player: Player): Boolean {
        return renderList.any { it.entityUniqueId == entity.getUniqueId() && it.player.uniqueId == player.uniqueId }
    }

    private fun canRender(entity: Entity, player: Player): Boolean {
        return entity.getEntityConfiguration().getSpawnHandler().invoke(player)
    }

    private fun inRange(
        location: Location,
        rangedLocation: Location,
        rangeOfLocation: Double
    ): Pair<Boolean, Double> {
        return try {
            val range = location.distance(rangedLocation)
            Pair(range < rangeOfLocation, range)
        } catch (ex: Exception) {
            Pair(false, 0.0)
        }
    }

}