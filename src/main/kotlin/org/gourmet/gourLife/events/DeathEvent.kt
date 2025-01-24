package org.gourmet.gourLife.events

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.gourmet.gourLife.GourLife
import org.gourmet.gourLife.jsonManager.JsonDataLoader
import org.gourmet.gourLife.utils.Utils

class DeathEvent : Listener {

    private val jsonDataLoader: JsonDataLoader = GourLife.jsonDataLoader
    private val config: FileConfiguration = GourLife.instance.config
    private val prefix: String = config.getString("prefix") ?: "[ErrorPrefix]"

    @EventHandler
    fun playerDeathEvent(event: PlayerDeathEvent) {

        val player = event.entity
        val lives = jsonDataLoader.getPlayerLives(player)

        if (lives > 1) {
            removeOneLife(player, lives)
        } else {
            playerEliminated(player)
        }

    }

    private fun removeOneLife(player: Player, lives: Int){
        val newLife: Int = lives - 1
        jsonDataLoader.setPlayerLives(player, newLife)
        jsonDataLoader.savePlayerData()

        var deathMessage = config.getString("death")
        deathMessage = deathMessage!!.replace("%player%", player.name)
        deathMessage = deathMessage.replace("%lifes%", "" + newLife)
        if (config.getBoolean("death-message"))
            Utils.sendMessageAll("$prefix $deathMessage")

    }

    private fun playerEliminated(player: Player) {
        var message = config.getString("final-death")
        message = message!!.replace("%player%", player.name)
        if (config.getBoolean("final-message")) {
            Utils.sendMessageAll("$prefix $message")
        }

        player.gameMode = GameMode.SPECTATOR

        if (config.getBoolean("execute-command")) {
            var consoleCommand = config.getString("console-command")
            consoleCommand = consoleCommand!!.replace("%player%", player.name)

            GourLife.instance.logger.info("Esecuzione comando console: $consoleCommand")

            try {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), consoleCommand)
            } catch (e: Exception) {
                GourLife.instance.logger.warning("Errore nell'esecuzione del comando: $consoleCommand")
                e.printStackTrace()
            }
        }

        jsonDataLoader.setPlayerLives(player, 0)
        jsonDataLoader.savePlayerData()
    }
}