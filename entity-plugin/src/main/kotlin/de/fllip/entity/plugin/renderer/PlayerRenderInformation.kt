package de.fllip.entity.plugin.renderer

import org.bukkit.entity.Player
import java.util.*

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 22.04.2021
 * Time: 11:09
 */
data class PlayerRenderInformation(
    val player: Player,
    val entityUniqueId: UUID
)