package org.gourmet.gourLife

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.gourmet.gourLife.commands.LifeCMD
import org.gourmet.gourLife.events.DeathEvent
import org.gourmet.gourLife.events.JoinEvent
import org.gourmet.gourLife.events.KillEvent
import org.gourmet.gourLife.events.LeaveEvent
import org.gourmet.gourLife.jsonManager.JsonDataLoader
import org.gourmet.gourLife.placeholder.PlaceHolderHearts
import revxrsal.commands.bukkit.BukkitLamp

class GourLife : JavaPlugin() {

    companion object {
        lateinit var instance: GourLife
            private set
        lateinit var jsonDataLoader: JsonDataLoader
            private set
    }

    override fun onEnable() {
        instance = this
        saveDefaultConfig()
        logger.info("GourLife starting...")

        try {
            jsonDataLoader = JsonDataLoader(this)
            jsonDataLoader.loadPlayerData()
        } catch (e: Exception) {
            logger.severe("Error: ${e.message}")
            Bukkit.getPluginManager().disablePlugin(this)
            return
        }

        placeHolderInit()
        registerEvents()

        BukkitLamp.builder(this).build().register(LifeCMD)

        logger.info("GourLife started!")
    }

    override fun onDisable() {
        saveDefaultConfig()
        jsonDataLoader.savePlayerData()
        logger.info("GourLife disabled")
    }

    private fun placeHolderInit() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            logger.severe("Missing PlaceHolderAPI")
            Bukkit.getPluginManager().disablePlugin(this)
            return
        }
        PlaceHolderHearts().register()
    }

    private fun registerEvents() {
        val pluginManager = Bukkit.getPluginManager()
        listOf(DeathEvent(), KillEvent(), LeaveEvent(), JoinEvent()).forEach { event ->
            pluginManager.registerEvents(event, this)
        }
    }
}