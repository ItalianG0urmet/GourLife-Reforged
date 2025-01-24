package org.gourmet.gourLife.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit

object Utils {

    private val minimessage: MiniMessage = MiniMessage.builder().build()
    fun String.toMini(): Component = minimessage.deserialize(this)

    fun sendMessageAll(send: String?) {
        for (player in Bukkit.getOnlinePlayers()) {
            player.sendMessage(send!!.toMini())
        }
    }

}