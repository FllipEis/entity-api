package de.fllip.entity.api.entity.result

import de.fllip.entity.api.entity.Entity
import org.bukkit.entity.Player

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 21.04.2021
 * Time: 21:12
 */
data class EntityInteractResult (
    val player: Player,
    val entity: Entity,
    val action : EntityInteractAction
)