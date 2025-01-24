package org.gourmet.gourLife.events

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.gourmet.gourLife.GourLife
import org.gourmet.gourLife.jsonManager.JsonDataLoader
import org.gourmet.gourLife.utils.Utils.toMini

class JoinEvent : Listener {
    private val config: FileConfiguration = GourLife.instance.config
    private val jsonDataLoader: JsonDataLoader = GourLife.jsonDataLoader
    private val prefix: String = config.getString("prefix") ?: "ErrorPrefix"

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        if (!config.getBoolean("Join-messages")) return

        val player: Player = event.player
        val playerName: String = event.player.name

        var message: String = config.getString("join").toString()
        message = message.replace("%player%", playerName)
        event.joinMessage("$prefix $message".toMini())

        jsonDataLoader.addPlayer(player)
    }
}