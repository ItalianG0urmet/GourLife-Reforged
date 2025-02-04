package org.gourmet.gourLife.events

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.gourmet.gourLife.GourLife
import org.gourmet.gourLife.utils.Utils
import org.gourmet.gourLife.utils.WebHookUtils

class DeathEvent : Listener {

    private val jsonDataLoader = GourLife.jsonDataLoader
    private val config = GourLife.instance.config
    private val prefix = config.getString("prefix") ?: "[ErrorPrefix]"

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
        val newLivesCount = lives - 1
        jsonDataLoader.setPlayerLives(player, newLivesCount)
        jsonDataLoader.savePlayerData()

        var deathMessage = config.getString("death")
        deathMessage = deathMessage!!.replace("%player%", player.name)
        deathMessage = deathMessage.replace("%lifes%", "" + newLivesCount)
        if (config.getBoolean("death-message"))
            Utils.sendMessageAll("$prefix $deathMessage")

    }

    private fun playerEliminated(player: Player) {
        var eliminatedPlayerMessage = config.getString("final-death")
        eliminatedPlayerMessage = eliminatedPlayerMessage!!.replace("%player%", player.name)
        WebHookUtils.finalKillWebHook(player)
        if (config.getBoolean("final-message")) {
            Utils.sendMessageAll("$prefix $eliminatedPlayerMessage")
        }

        if (config.getBoolean("execute-command")) {
            var consoleCommand = config.getString("console-command")
            consoleCommand = consoleCommand!!.replace("%player%", player.name)

            GourLife.instance.logger.info("Error in final command: $consoleCommand")

            try {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), consoleCommand)
            } catch (e: Exception) {
                GourLife.instance.logger.warning("Error in final command: $consoleCommand")
                e.printStackTrace()
            }
        }

        jsonDataLoader.setPlayerLives(player, 0)
        jsonDataLoader.savePlayerData()
    }
}