package org.gourmet.gourLife.events

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.gourmet.gourLife.GourLife
import org.gourmet.gourLife.utils.Utils.toMini

class JoinEvent : Listener {
    private val config = GourLife.instance.config
    private val jsonDataLoader = GourLife.jsonDataLoader
    private val prefix = config.getString("prefix") ?: "ErrorPrefix"

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {

        val player: Player = event.player
        val playerName: String = event.player.name

        rejoinDeathPlayer(player)


        if (config.getBoolean("Join-messages")) {
            var message: String = config.getString("join").toString()
            message = message.replace("%player%", playerName)
            event.joinMessage("$prefix $message".toMini())
        }

        jsonDataLoader.addPlayer(player)
    }

    private fun rejoinDeathPlayer(player: Player){
        if(jsonDataLoader.getPlayerLives(player) > 0) return
        if(!config.getBoolean("reset-life-rejoin")) return


        val rejoinLives: Int = config.getInt("reset-life-count")
        jsonDataLoader.setPlayerLives(player, rejoinLives)
        jsonDataLoader.savePlayerData()

    }
}