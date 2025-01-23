package org.gourmet.gourLife.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Sound

object Utils {

    val minimessage: MiniMessage = MiniMessage.builder().build()
    fun String.toMini(): Component = minimessage.deserialize(this)

    /**
     * Send message to all the player
     * on the server
     * @param send
     */
    fun sendMessageAll(send: String?) {
        for (player in Bukkit.getOnlinePlayers()) {
            player.sendMessage(send!!.toMini())
        }
    }

    /**
     * Se
     * @param send
     */
    fun sendTitleAll(send: String?) {
        for (player in Bukkit.getOnlinePlayers()) {
            player.sendMessage(send!!.toMini())
        }
    }

    /**
     * Send a title to all the player
     * on the server and a sound
     *
     * @param title Princiapl title text
     * @param subtitle Text under the titler
     * @param playSound Sound to play when the title appear
     */
    fun sendTitleAllEX(title: String?, subtitle: String?, fadeIn: Int, stay: Int, fadeOut: Int, playSound: Boolean) {
        for (p in Bukkit.getOnlinePlayers()) {
            p.sendTitle(title, subtitle, fadeIn, stay, fadeOut)
            if (playSound) {
                p.playSound(p.location, Sound.AMBIENT_CAVE, 1.0f, 1.0f)
            }
        }
    }
}