package org.gourmet.gourLife.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Sound

object Utils {

    val minimessage: MiniMessage = MiniMessage.builder().build()
    fun String.toMini(): Component = minimessage.deserialize(this)

    fun sendMessageAll(send: String?) {
        for (player in Bukkit.getOnlinePlayers()) {
            player.sendMessage(send!!.toMini())
        }
    }

}