package org.gourmet.gourLife.utils

import org.bukkit.Bukkit
import org.gourmet.gourLife.utils.Utils.toMini

object Logger {
    private const val PREFIX: String = "<light_purple>[GourLife]</light_purple> "

    fun info(text: String?) {
        sendText("<white>$text</white>")
    }

    fun warning(text: String?) {
        sendText("<red>$text</red>")
    }

    fun watch(text: String?) {
        sendText("<obfuscated>$text</obfuscated>")
    }

    private fun sendText(text: String?) {
        Bukkit.getConsoleSender().sendMessage((PREFIX + text).toMini())
    }
}
