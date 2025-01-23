package org.gourmet.gourLife.commands

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.gourmet.gourLife.GourLife
import org.gourmet.gourLife.jsonManager.JsonDataLoader
import org.gourmet.gourLife.utils.Utils.toMini
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Subcommand

@Command("life")
object LifeCMD {

    private val jsonDataLoader: JsonDataLoader = GourLife.jsonDataLoader
    private val config: FileConfiguration = GourLife.instance.config
    private val prefix: String? = config.getString("prefix")

    @Subcommand("check")
    fun checkLife(player: Player){
        val playerLife: Int = jsonDataLoader.getPlayerLives(player)

        var message = config.getString("your-life")
        message = message?.replace("%life%", "$playerLife")
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
