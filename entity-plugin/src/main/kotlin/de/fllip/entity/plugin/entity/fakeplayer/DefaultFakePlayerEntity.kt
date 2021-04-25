package de.fllip.entity.plugin.entity.fakeplayer

import com.comphenix.protocol.wrappers.EnumWrappers
import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import de.fllip.entity.api.entity.fakeplayer.EntityPose
import de.fllip.entity.api.entity.fakeplayer.FakePlayerEntity
import de.fllip.entity.api.entity.fakeplayer.FakePlayerEntityConfigurationAdapter
import de.fllip.entity.api.entity.item.EquipmentItem
import de.fllip.entity.plugin.EntityPlugin
import de.fllip.entity.plugin.entity.fakeplayer.packet.MetadataPacketCreator
import de.fllip.entity.plugin.entity.fakeplayer.packet.PlayerInfoPacketCreator
import de.fllip.entity.plugin.entity.packet.*
import de.fllip.entity.plugin.later
import de.fllip.entity.plugin.renderer.DefaultEntityRenderer
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 21.04.2021
 * Time: 20:45
 */
class DefaultFakePlayerEntity @Inject constructor(
    @Assisted private val configurationAdapter: FakePlayerEntityConfigurationAdapter,
    private val plugin: EntityPlugin,
    private val entityRenderer: DefaultEntityRenderer,
    private val spawnPacketCreator: SpawnPacketCreator,
    private val metadataPacketCreator: MetadataPacketCreator,
    private val playerInfoPacketCreator: PlayerInfoPacketCreator,
    private val headRotationPacketCreator: HeadRotationPacketCreator,
    private val equipmentPacketCreator: EquipmentPacketCreator,
    private val destroyPacketCreator: DestroyPacketCreator,
) : AbstractEntityPacketSender(entityRenderer), FakePlayerEntity {

    private val lookAtPlayer = configurationAdapter.getLookAtPlayer()

    override fun getEntityConfiguration(): FakePlayerEntityConfigurationAdapter {
        return configurationAdapter
    }

    override fun spawn(player: Player) {
        val location = configurationAdapter.getLocation() ?: return

        val entityId = getEntityId()
        val headRotationContainer = headRotationPacketCreator.create(entityId, location)
        sendPackets(
            player,
            playerInfoPacketCreator.create(this, player, EnumWrappers.PlayerInfoAction.ADD_PLAYER),
            spawnPacketCreator.create(this, EntityType.PLAYER),
            metadataPacketCreator.createSecondLayer(entityId),
            headRotationContainer.first,
            headRotationContainer.second,
        )

        later(plugin, 4) {
            sendPackets(
                player,
                playerInfoPacketCreator.create(this, player, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER),
                equipmentPacketCreator.create(entityId, configurationAdapter.getEquipmentItems())
            )
        }

    }

    override fun despawn(player: Player) {
        entityRenderer.removePlayerFromRenderList(this, player)
        sendPackets(player, destroyPacketCreator.create(getEntityId()))
    }

    override fun updateDisplayName(player: Player) {
        despawn(player)
    }

    override fun updateEquipment(player: Player, equipmentItems: List<EquipmentItem>) {
        sendPacket(player, equipmentPacketCreator.create(getEntityId(), equipmentItems))
    }

    override fun updateEntityPose(player: Player, entityPose: EntityPose) {
        sendPacket(metadataPacketCreator.createPose(getEntityId(), entityPose))
    }

    override fun onTick(player: Player, inRangeWithRange: Pair<Boolean, Double>, isRendered: Boolean) {
        if (lookAtPlayer && inRangeWithRange.second < 20.0) {
            val location = configurationAdapter.getLocation() ?: return
            val headRotationContainer = headRotationPacketCreator.create(this, location, player.location)
            sendPackets(player, headRotationContainer.first, headRotationContainer.second)
        }
    }

}