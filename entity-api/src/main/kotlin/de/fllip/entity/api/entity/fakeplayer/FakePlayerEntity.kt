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

package de.fllip.entity.api.entity.fakeplayer

import de.fllip.entity.api.entity.Entity
import org.bukkit.entity.Player

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 21.04.2021
 * Time: 19:53
 *
 * Class for fake player entities
 */
interface FakePlayerEntity : Entity {

    /**
     * Updates the posen of this entity
     *
     * @param player the player to whom the entity pose should be updated
     * @param entityPose the pose the entity should update
     */
    fun updateEntityPose(player: Player, entityPose: EntityPose)

    /**
     * Factory for creating fake players
     */
    interface Factory: Entity.Factory {

        /**
         * Creates a fake player entity
         *
         * @param configurationAdapter the configuration for the entity
         * @return the instance of the entity
         */
        fun create(configurationAdapter: FakePlayerEntityConfigurationAdapter): FakePlayerEntity

    }

}