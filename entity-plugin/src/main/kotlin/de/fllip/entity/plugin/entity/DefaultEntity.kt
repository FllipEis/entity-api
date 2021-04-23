package de.fllip.entity.plugin.entity

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import de.fllip.entity.api.entity.Entity
import de.fllip.entity.api.entity.EntityConfigurationAdapter
import de.fllip.entity.api.entity.item.EquipmentItem
import de.fllip.entity.plugin.renderer.EntityRenderer
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 21.04.2021
 * Time: 20:43
 */
class DefaultEntity @Inject constructor(
    @Assisted private val configurationAdapter: EntityConfigurationAdapter,
    private val entityRenderer: EntityRenderer
): Entity {

    private val unqiueId = UUID.randomUUID()
    private val entityId = ThreadLocalRandom.current().nextInt(Short.MAX_VALUE.toInt())

    override fun getUniqueId(): UUID {
        return unqiueId
    }

    override fun getEntityId(): Int {
        return entityId
    }

    override fun getEntityConfiguration(): EntityConfigurationAdapter {
        return configurationAdapter
    }

    override fun spawn(player: Player) {
    }

    override fun despawn(player: Player) {
    }

    override fun updateDisplayName(player: Player) {
    }

    override fun updateEquipment(player: Player, equipmentItems: List<EquipmentItem>) {
    }

    override fun startRendering() {
    }

    override fun stopRendering() {
    }
}