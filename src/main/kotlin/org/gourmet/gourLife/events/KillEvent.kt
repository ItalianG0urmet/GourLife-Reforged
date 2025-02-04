package org.gourmet.gourLife.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.gourmet.gourLife.GourLife
import org.gourmet.gourLife.utils.Utils.toMini

class KillEvent : Listener {

    private val config = GourLife.instance.config
    private val jsonDataLoader = GourLife.jsonDataLoader
    private val prefix = config.getString("prefix") ?: "[ErrorPrefix]"
    private val gainedLifeMessage = config.getString("gained-life")

    @EventHandler
    fun onkill(event: PlayerDeathEvent) {
        val player = event.entity
        val killer = player.killer ?: return

        val livesKiller: Int = jsonDataLoader.getPlayerLives(killer)
        jsonDataLoader.setPlayerLives(killer, livesKiller + 1)
        killer.sendMessage("$prefix $gainedLifeMessage".toMini())

        jsonDataLoader.savePlayerData()
    }
}