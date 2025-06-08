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
import org.gourmet.gourLife.utils.ConfigManager
import org.gourmet.gourLife.utils.Logger
import revxrsal.commands.bukkit.BukkitLamp

class GourLife : JavaPlugin() {

    companion object {
        lateinit var instance: GourLife private set
        lateinit var jsonDataLoader: JsonDataLoader private set
        lateinit var configManager: ConfigManager private set
    }

    override fun onEnable() {
        instance = this
        saveDefaultConfig()
        Logger.info("GourLife starting...")

        configManager = ConfigManager()
        initJsonData()
        placeHolderInit()
        registerEventsAndCommands()

        Logger.info("GourLife started!")
    }

    override fun onDisable() {
        saveDefaultConfig()
        jsonDataLoader.savePlayerData()
        Logger.info("GourLife disabled")
    }

    /* Impl */
    private fun initJsonData(){
        try {
            jsonDataLoader = JsonDataLoader(this)
            jsonDataLoader.loadPlayerData()
        } catch (e: Exception) {
            Logger.warning("Error: ${e.message}")
            Bukkit.getPluginManager().disablePlugin(this)
            return
        }
    }

    private fun placeHolderInit() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            Logger.warning("Missing PlaceHolderAPI")
            Bukkit.getPluginManager().disablePlugin(this)
            return
        }
        PlaceHolderHearts().register()
    }

    private fun registerEventsAndCommands() {

        //Commands
        val pluginManager = Bukkit.getPluginManager()
        listOf(
            DeathEvent(),
            KillEvent(),
            LeaveEvent(),
            JoinEvent()
        ).forEach { event -> pluginManager.registerEvents(event, this) }

        //Events
        val handler = BukkitLamp.builder(this).build()
        handler.register(
            LifeCMD
        )
    }
}