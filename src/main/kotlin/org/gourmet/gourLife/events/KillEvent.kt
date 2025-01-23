package org.gourmet.gourLife.events

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.gourmet.gourLife.GourLife
import org.gourmet.gourLife.jsonManager.JsonDataLoader
import org.gourmet.gourLife.utils.Utils
import org.gourmet.gourLife.utils.Utils.toMini

class KillEvent : Listener {
    private val config: FileConfiguration = GourLife.instance.config
    private val jsonDataLoader: JsonDataLoader = GourLife.jsonDataLoader
    private val prefix: String? = config.getString("prefix")
    private val gainedLifeMessage = config.getString("gained-life")

    @EventHandler
    fun onkill(event: PlayerDeathEvent) {
        val player = event.entity
        val killer = player.killer ?: return

        var lives: Int = jsonDataLoader.getPlayerLives(killer)
        jsonDataLoader.setPlayerLives(killer, lives + 1)
        killer.sendMessage("$prefix $gainedLifeMessage".toMini())

        lives = jsonDataLoader.getPlayerLives(player)
        jsonDataLoader.setPlayerLives(player, lives - 1)
        player.sendMessage("$prefix $gainedLifeMessage".toMini())

        jsonDataLoader.savePlayerData()
    }
}