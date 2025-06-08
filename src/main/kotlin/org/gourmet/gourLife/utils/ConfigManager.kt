package org.gourmet.gourLife.utils

import org.gourmet.gourLife.GourLife

class ConfigManager() {

    private val config = GourLife.instance.config

    lateinit var PREFIX: String
    lateinit var NO_PLAYER: String
    lateinit var RESET_LIFE: String
    lateinit var NO_PERM: String
    lateinit var CHECK_LIFE: String
    lateinit var YOUR_LIFE: String
    lateinit var GIVE_LIFE: String
    lateinit var RECEIVE_LIFE: String
    lateinit var CANT_FIND_PLAYER: String
    lateinit var CANT_SEND_TO_YOURSELF: String
    lateinit var DEATH: String
    lateinit var FINAL_DEATH: String
    lateinit var JOIN: String
    lateinit var LEAVE: String
    lateinit var GAINED_LIFE: String
    lateinit var SET_LIFE: String
    lateinit var HELP_COMMANDS: String

    var joinMessages = true
    var leaveMessages = true
    var deathMessage = true
    var finalMessage = true
    var lifeNumber = 3
    var executeCommand = false
    var consoleCommand = "tempban %player% 15d Eliminated"
    var resetLifeRejoin = true
    var resetLifeCount = 1
    var webhookTitle = "%player% eliminated"
    var webhookMessage = "This player is eliminated from the server"
    var webhookOnFinal = false
    var webhookColor = 16711680

    init {
        load()
    }

    fun load() {
        GourLife.instance.saveDefaultConfig()

        try {
            // Settings
            joinMessages = config.getBoolean("Join-messages", true)
            leaveMessages = config.getBoolean("leave-messages", true)
            deathMessage = config.getBoolean("death-message", true)
            finalMessage = config.getBoolean("final-message", true)
            lifeNumber = config.getInt("life-number", 3)
            executeCommand = config.getBoolean("execute-command", false)
            consoleCommand = config.getString("console-command") ?: "tempban %player% 15d Eliminated"

            resetLifeRejoin = config.getBoolean("reset-life-rejoin", true)
            resetLifeCount = config.getInt("reset-life-count", 1)

            webhookTitle = config.getString("webhook-title") ?: "%player% eliminated"
            webhookMessage = config.getString("webhook-message") ?: "This player is eliminated from the server"
            webhookOnFinal = config.getBoolean("webhook-on-final", false)
            webhookColor = config.getInt("webhook-color", 16711680)

            // Messages
            PREFIX = config.getString("prefix") ?: "<gray>[<white>ServerName<gray>]<dark_gray> » "
            NO_PLAYER = config.getString("no-player") ?: "<red>Player not found"
            RESET_LIFE = config.getString("reset-life") ?: "<yellow>%player% is back in the game"
            NO_PERM = config.getString("no-permission") ?: "<red>You don't have the required permissions"
            CHECK_LIFE = config.getString("check-life") ?: "<red>%player% has %life% lives"
            YOUR_LIFE = config.getString("your-life") ?: "<aqua>You have %life% lives"
            GIVE_LIFE = config.getString("give-life") ?: "<aqua>You have donated 1 life"
            RECEIVE_LIFE = config.getString("receive-life") ?: "<aqua>You have received 1 life from %player%"
            CANT_FIND_PLAYER = config.getString("cant-find-player") ?: "<red>Player not found"
            CANT_SEND_TO_YOURSELF = config.getString("cant-send-to-yourself") ?: "<red>You can't do that to yourself"
            DEATH = config.getString("death") ?: "<yellow>%player% has died and now has <red>❤ %lifes% lives"
            FINAL_DEATH = config.getString("final-death") ?: "<yellow>%player% has died permanently"
            JOIN = config.getString("join") ?: "<yellow>%player% has joined the server!"
            LEAVE = config.getString("leave") ?: "<red>%player% has left the server"
            GAINED_LIFE = config.getString("gained-life") ?: "<aqua>You have gained 1 life"
            SET_LIFE = config.getString("set-life") ?: "<aqua>New life count: %life%"
            HELP_COMMANDS = config.getString("help-commands") ?: """
            <red>
            Usage:
             <gray>give: <white>/life give <player>
             <gray>check: <white>/life check <player>
        """.trimIndent()
        }catch (e: Exception){
            Logger.warning("Can't load config: ${e.printStackTrace()}")
        }

    }
}