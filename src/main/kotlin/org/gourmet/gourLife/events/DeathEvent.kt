package org.gourmet.gourLife.events

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.gourmet.gourLife.GourLife
import org.gourmet.gourLife.utils.Logger
import org.gourmet.gourLife.utils.Utils
import org.gourmet.gourLife.utils.WebHookUtils

class DeathEvent : Listener {

    private val jsonDataLoader = GourLife.jsonDataLoader
    private val configManager = GourLife.configManager
    private val prefix = configManager.PREFIX

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

        //Json manager
        val newLivesCount = lives - 1
        jsonDataLoader.setPlayerLives(player, newLivesCount)
        jsonDataLoader.savePlayerData()

        //Message init
        var deathMessage = configManager.DEATH
        deathMessage = deathMessage.replace("%player%", player.name)
        deathMessage = deathMessage.replace("%lifes%", "" + newLivesCount)

        if (configManager.deathMessage)
            Utils.sendMessageAll("$prefix $deathMessage")

    }

    private fun playerEliminated(player: Player) {

        //Message init
        var eliminatedPlayerMessage = configManager.FINAL_DEATH
        eliminatedPlayerMessage = eliminatedPlayerMessage.replace("%player%", player.name)

        //Webhook
        WebHookUtils.finalKillWebHook(player)

        if (configManager.finalMessage) {
            Utils.sendMessageAll("$prefix $eliminatedPlayerMessage")
        }

        //Custom command on player final death
        if (configManager.executeCommand) {
            var consoleCommand = configManager.consoleCommand
            consoleCommand = consoleCommand.replace("%player%", player.name)

            Logger.warning("Error in final command: $consoleCommand")

            try {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), consoleCommand)
            } catch (e: Exception) {
                Logger.warning("Error in final command \"$consoleCommand\": ${e.printStackTrace()}")
            }
        }

        jsonDataLoader.setPlayerLives(player, 0)
        jsonDataLoader.savePlayerData()
    }
}