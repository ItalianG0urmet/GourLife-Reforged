package org.gourmet.gourLife.placeholder

import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.gourmet.gourLife.GourLife
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

class PlaceHolderHearts : PlaceholderExpansion() {

    companion object{
        private const val IDENTIFIER = "glife"
        private const val AUTHOR = "gourmet"
        private const val VERSION = "1.0.0"
    }

    override fun onRequest(player: OfflinePlayer?, params: String): String {
        if (player == null) {
            return ""
        }

        //Life
        if (params.equals("life", ignoreCase = true)) {
            val viteString = "" + GourLife.jsonDataLoader.getPlayerLives(player as Player)
            return "&c❤ $viteString".replace("&", "§")
        }

        return "error"
    }

    override fun getIdentifier(): String {
        return IDENTIFIER
    }

    override fun getAuthor(): String {
        return AUTHOR
    }

    override fun getVersion(): String {
        return VERSION
    }
}