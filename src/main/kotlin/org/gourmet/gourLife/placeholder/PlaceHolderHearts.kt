package org.gourmet.gourLife.placeholder

import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.gourmet.gourLife.GourLife
import org.gourmet.gourLife.utils.Utils
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.gourmet.gourLife.utils.Utils.toMini

class PlaceHolderHearts : PlaceholderExpansion() {

    override fun canRegister(): Boolean {
        return true
    }

    override fun persist(): Boolean {
        return true
    }

    override fun onRequest(player: OfflinePlayer?, params: String): String {
        if (player == null) {
            return ""
        }

        if (params.equals("life", ignoreCase = true)) {
            val viteString = "" + GourLife.jsonDataLoader.getPlayerLives(player as Player)
            return "&c❤ $viteString".replace("&", "§")
        }

        return "null"
    }

    override fun getIdentifier(): String {
        return "glife"
    }

    override fun getAuthor(): String {
        return "gourmet"
    }

    override fun getVersion(): String {
        return "1.0.0"
    }
}