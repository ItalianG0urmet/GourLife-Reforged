package org.gourmet.gourLife.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.gourmet.gourLife.GourLife
import org.gourmet.gourLife.utils.Utils.toMini

class KillEvent : Listener {

    private val jsonDataLoader = GourLife.jsonDataLoader
    private val prefix = GourLife.configManager.PREFIX
    private val gainedLifeMessage = GourLife.configManager.GAINED_LIFE

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