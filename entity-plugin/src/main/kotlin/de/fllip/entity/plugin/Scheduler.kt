package de.fllip.entity.plugin

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 22.04.2021
 * Time: 11:37
 */
fun later(plugin: JavaPlugin, delay: Long, runnable: Runnable) {
    Bukkit.getScheduler().runTaskLater(plugin, runnable, delay)
}