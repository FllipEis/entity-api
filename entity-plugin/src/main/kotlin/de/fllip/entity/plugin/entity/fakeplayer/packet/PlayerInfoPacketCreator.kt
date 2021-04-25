package de.fllip.entity.plugin.entity.fakeplayer.packet

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.*
import com.google.inject.Singleton
import de.fllip.entity.api.entity.Entity
import de.fllip.entity.api.entity.fakeplayer.FakePlayerEntityConfigurationAdapter
import de.fllip.entity.plugin.entity.packet.AbstractEntityPacketCreator
import org.bukkit.entity.Player
import java.util.*

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 22.04.2021
 * Time: 20:54
 */
@Singleton
class PlayerInfoPacketCreator: AbstractEntityPacketCreator() {

    fun create(entity: Entity, player: Player, action: EnumWrappers.PlayerInfoAction): PacketContainer {
        val configurationAdapter = entity.getEntityConfiguration() as FakePlayerEntityConfigurationAdapter

        val displayName = invokeDisplayName(configurationAdapter, player)

        val wrappedGameProfile = WrappedGameProfile(entity.getUniqueId(), displayName)

        val skinData = configurationAdapter.getSkinDataHandler().invoke(player)
        if (!skinData.isEmpty()) {
            wrappedGameProfile.properties.put(
                "textures",
                WrappedSignedProperty("textures", skinData.value, skinData.signature)
            )
        }

        val playerInfoData = PlayerInfoData(
            wrappedGameProfile,
            20,
            EnumWrappers.NativeGameMode.NOT_SET,
            WrappedChatComponent.fromText("")
        )

        return createPacket(PacketType.Play.Server.PLAYER_INFO).apply {
            playerInfoAction.write(0, action)
            playerInfoDataLists.write(0, Collections.singletonList(playerInfoData))
        }
    }

}