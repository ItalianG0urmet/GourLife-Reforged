package org.gourmet.gourLife.jsonManager

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.bukkit.entity.Player
import org.gourmet.gourLife.GourLife
import org.gourmet.gourLife.utils.Logger
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.util.*


class JsonDataLoader(gourLife: GourLife) {
    private var data = File(gourLife.dataFolder, "data.json")
    private var gson: Gson = GsonBuilder().setPrettyPrinting().create()
    private val playerLives: MutableMap<UUID, Int> = HashMap()

    init {
        if (!data.exists()) {
            try {
                data.createNewFile()
                FileWriter(data).use { writer ->
                    gson.toJson(JsonObject(), writer)
                }
            } catch (e: IOException) {
                Logger.warning("Json error: ${e.printStackTrace()}")
            }
        }

    }

    fun loadPlayerData() {
        try {
            FileReader(data).use { reader ->
                val jsonObject = JsonParser.parseReader(reader).asJsonObject
                for (key in jsonObject.keySet()) {
                    val playerId = UUID.fromString(key)
                    val lives = jsonObject[key].asInt
                    playerLives[playerId] = lives
                }
            }
        } catch (e: IOException) {
            Logger.warning("Can't load players: ${e.printStackTrace()}")
        }
    }

    fun savePlayerData() {
        val jsonObject = JsonObject()
        for ((uuid, lives) in playerLives) {
            jsonObject.addProperty(uuid.toString(), lives)
        }

        try {
            FileWriter(data).use { writer ->
                gson.toJson(jsonObject, writer)
            }
        } catch (e: IOException) {
            Logger.warning("Can't save players: ${e.printStackTrace()}")
        }
    }

    fun addPlayer(player: Player) {
        playerLives.putIfAbsent(player.uniqueId, 3)
    }

    fun getPlayerLives(player: Player): Int {
        return playerLives.getOrDefault(player.uniqueId, 0)
    }

    fun setPlayerLives(player: Player, lives: Int) {
        playerLives[player.uniqueId] = lives
    }
}