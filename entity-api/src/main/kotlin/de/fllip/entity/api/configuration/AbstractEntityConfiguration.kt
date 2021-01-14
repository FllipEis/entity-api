/*
 * MIT License
 *
 * Copyright (c) 2020 Philipp Eistrach
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

package de.fllip.entity.api.configuration

import com.google.common.collect.Lists
import de.fllip.entity.api.entity.interact.EntityInteractResult
import de.fllip.entity.api.json.JsonHelper
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.function.Consumer

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 30.12.2020
 * Time: 20:00
 */
abstract class AbstractEntityConfiguration<T> {

    var displayNameHandler: (Player) -> String = {""}
    var hologramLinesHandlers: ArrayList<(Player) -> String> = Lists.newArrayList()
    var location: Location? = null
    var handleSpawn: ((Player) -> Boolean)? = null
    var handleInteract: Consumer<EntityInteractResult>? = null
    var handleTeleport: Consumer<Location>? = null

    fun withDisplayName(name: String): T {
        this.displayNameHandler = {name}
        return (this as T)
    }

    fun withDisplayName(displayNameHandler:  (Player) -> String): T {
        this.displayNameHandler = displayNameHandler
        return (this as T)
    }

    fun withHologramLines(vararg lines: String): T {
        lines.forEach { line ->
            hologramLinesHandlers.add { line }
        }
        return (this as T)
    }

    fun withHologramLine(line: String): T {
        return this.withHologramLine { line }
    }

    fun withHologramLine(hologramLineHandler:  (Player) -> String): T {
        this.hologramLinesHandlers.add { hologramLineHandler.invoke(it) }
        return (this as T)
    }

    fun withLocation(location: Location): T {
        this.location = location
        return (this as T)
    }

    fun withSpawnHandler(handleSpawn: (Player) -> Boolean): T {
        this.handleSpawn = handleSpawn
        return (this as T)
    }

    fun withInteractHandler(handleInteract: Consumer<EntityInteractResult>): T {
        this.handleInteract = handleInteract
        return (this as T)
    }

    fun withTeleportHandler(handleTeleport: Consumer<Location>): T {
        this.handleTeleport = handleTeleport
        return (this as T)
    }

}