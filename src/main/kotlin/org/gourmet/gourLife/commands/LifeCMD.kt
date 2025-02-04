package org.gourmet.gourLife.commands

import org.bukkit.entity.Player
import org.gourmet.gourLife.GourLife
import org.gourmet.gourLife.utils.Utils.toMini
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Subcommand

@Command("life")
object LifeCMD {

    private val jsonDataLoader = GourLife.jsonDataLoader
    private val config = GourLife.instance.config
    private val prefix = config.getString("prefix") ?: "[ErrorPrefix]"

    @Subcommand("check")
    fun checkLife(player: Player){
        val playerLivesCount = jsonDataLoader.getPlayerLives(player)
        var message = config.getString("your-life")
        message = message?.replace("%life%", "$playerLivesCount")
        player.sendMessage("$prefix $message".toMini())
    }

    @Subcommand("check <target>")
    fun checkLife(player: Player, target: Player){
        val targetLife: Int = jsonDataLoader.getPlayerLives(target)

        var message = config.getString("check-life")
        message = message?.replace("%life%", "$targetLife")
        message = message?.replace("%player%", player.name)
        player.sendMessage("$prefix $message".toMini())
    }

    @Subcommand("set <target> <life>")
    fun giveLife(player: Player, target:Player, life: Int){

        if (!player.hasPermission("glife.admin")) return

        var message = config.getString("set-life")
        message = message?.replace("%life%", "$life")
        player.sendMessage("$prefix $message".toMini())

        jsonDataLoader.setPlayerLives(target, life)
        jsonDataLoader.savePlayerData()
    }
}
