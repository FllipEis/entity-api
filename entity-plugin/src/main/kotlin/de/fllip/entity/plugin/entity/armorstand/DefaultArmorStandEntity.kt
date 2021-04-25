/*
 * Copyright (c) 2021 Philipp Eistrach
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.fllip.entity.plugin.entity.armorstand

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import de.fllip.entity.api.entity.armorstand.ArmorStandEntity
import de.fllip.entity.api.entity.armorstand.ArmorStandEntityConfigurationAdapter
import de.fllip.entity.api.entity.configuration.EntityConfigurationAdapter
import de.fllip.entity.api.entity.item.EquipmentItem
import de.fllip.entity.plugin.EntityPlugin
import de.fllip.entity.plugin.entity.packet.*
import de.fllip.entity.plugin.renderer.DefaultEntityRenderer
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 23.04.2021
 * Time: 14:53
 */
class DefaultArmorStandEntity @Inject constructor(
    @Assisted private val configurationAdapter: ArmorStandEntityConfigurationAdapter,
    private val entityRenderer: DefaultEntityRenderer,
    private val spawnPacketCreator: SpawnPacketCreator,
    private val metadataPacketCreator: MetadataPacketCreator,
    private val headRotationPacketCreator: HeadRotationPacketCreator,
    private val equipmentPacketCreator: EquipmentPacketCreator,
    private val destroyPacketCreator: DestroyPacketCreator,
) : AbstractEntityPacketSender(entityRenderer), ArmorStandEntity {

    override fun getEntityConfiguration(): EntityConfigurationAdapter {
        return configurationAdapter
    }

    override fun spawn(player: Player) {
        val location = configurationAdapter.getLocation() ?: return

        val entityId = getEntityId()
        val headRotationContainer = headRotationPacketCreator.create(entityId, location)
        sendPackets(
            player,
            spawnPacketCreator.create(this, EntityType.ARMOR_STAND),
            metadataPacketCreator.createVisibleMetadata(this, player),
            headRotationContainer.first,
            headRotationContainer.second,
            equipmentPacketCreator.create(entityId, configurationAdapter.getEquipmentItems())
        )
    }

    override fun despawn(player: Player) {
        entityRenderer.removePlayerFromRenderList(this, player)
        sendPackets(player, destroyPacketCreator.create(getEntityId()))
    }

    override fun updateDisplayName(player: Player) {
        sendPacket(player, metadataPacketCreator.createNameMetadata(this, player))
    }

    override fun updateEquipment(player: Player, equipmentItems: List<EquipmentItem>) {
        sendPacket(player, equipmentPacketCreator.create(getEntityId(), equipmentItems))
    }
}