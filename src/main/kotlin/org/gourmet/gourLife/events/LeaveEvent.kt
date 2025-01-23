package org.gourmet.gourLife.events

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import org.gourmet.gourLife.GourLife
import org.gourmet.gourLife.jsonManager.JsonDataLoader
import org.gourmet.gourLife.utils.Utils
import org.gourmet.gourLife.utils.Utils.toMini

class LeaveEvent : Listener {
    private val config: FileConfiguration = GourLife.instance.config
    private val prefix: String? = config.getString("prefix")

    @EventHandler
    fun onLeave(event: PlayerQuitEvent) {
        if (config.getBoolean("leave-messages")) {
            return
        }

        var message = config.getString("leave")
        message = message!!.replace("%player%", event.player.name)
        event.quitMessage("$prefix $message".toMini())
    }
}