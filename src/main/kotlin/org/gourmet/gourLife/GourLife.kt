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

    companion object{
        lateinit var instance: GourLife
        lateinit var jsonDataLoader: JsonDataLoader
    }

    override fun onEnable() {
        instance = this
        saveDefaultConfig()
        jsonDataLoader = JsonDataLoader(this)
        jsonDataLoader.loadPlayerData()
        placeHolderInit()

        Bukkit.getPluginManager().registerEvents(DeathEvent(), this)
        Bukkit.getPluginManager().registerEvents(KillEvent(), this)
        Bukkit.getPluginManager().registerEvents(LeaveEvent(), this)
        Bukkit.getPluginManager().registerEvents(JoinEvent(), this)

        PlaceHolderHearts().register()

        val handler = BukkitLamp.builder(this).build()
        handler.register(
            LifeCMD
        )
    }

    override fun onDisable() {
        saveDefaultConfig()
    }


    private fun placeHolderInit() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            for (p in Bukkit.getOnlinePlayers()) {
               if (p.hasPermission("glife.error")) {
                    p.sendMessage("PlaceHolderApi required")
                }
            }
             Bukkit.getPluginManager().disablePlugin(this)
       }
    }
}
