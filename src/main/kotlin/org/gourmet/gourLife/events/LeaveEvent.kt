package org.gourmet.gourLife.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import org.gourmet.gourLife.GourLife
import org.gourmet.gourLife.utils.Utils.toMini

class LeaveEvent : Listener {
    private val config = GourLife.instance.config
    private val prefix = config.getString("prefix") ?: "[ErrorPrefix]"
    private val configManager = GourLife.configManager

    @EventHandler
    fun onLeave(event: PlayerQuitEvent) {
        if (config.getBoolean("leave-messages"))
            return

        var message = configManager.leaveMessages.toString()
        message = message.replace("%player%", event.player.name)
        event.quitMessage("$prefix $message".toMini())
    }
}