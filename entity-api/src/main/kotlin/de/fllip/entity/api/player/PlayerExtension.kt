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

package de.fllip.entity.api.player

import org.bukkit.entity.Player
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.TimeUnit

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 01.01.2021
 * Time: 18:41
 */

fun Player.getJoinTimeStamp(): Long {
    return this.getMetadata("entity-api-join").firstOrNull()?.asLong()?: 0
}

fun Player.setJoinTimeStamp(javaPlugin: JavaPlugin, timeStamp: Long): Long {
    this.setMetadata("entity-api-join", FixedMetadataValue(javaPlugin, timeStamp))
    return timeStamp
}

fun Player.hasOnlineTime(time: Long): Boolean {
    val joinTimeStamp = this.getJoinTimeStamp()
    return System.currentTimeMillis() >= (joinTimeStamp + TimeUnit.SECONDS.toMillis(time))
}