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