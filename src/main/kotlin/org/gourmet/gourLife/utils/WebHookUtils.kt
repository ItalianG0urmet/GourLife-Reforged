package org.gourmet.gourLife.utils

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.gourmet.gourLife.GourLife
import org.json.JSONObject

object WebHookUtils {

    private val config: FileConfiguration = GourLife.instance.config

    fun finalKillWebHook(player: Player) {
        if(!config.getBoolean("webhook-on-final")) return

        val webHook: String = config.getString("webhook") ?: "null"
        val color: Int = config.getInt("webhook-color") ?: 16711680
        val title: String = config.getString("webhook-title").toString()
            .replace("%player%", player.name)
        val message: String = config.getString("webhook-message").toString()
            .replace("%player%", player.name)

        if(webHook == "null"){
            GourLife.instance.logger.warning("Invalid WebHook")
            return
        }
        val client = OkHttpClient()

        val json = JSONObject()
        json.put("content", "")
        json.put("embeds", listOf(
            JSONObject().apply {
                put("title", title)
                put("description", message)
                put("color", color)
            }
        ))

        val body = RequestBody.create("application/json; charset=utf-8".toMediaType(), json.toString())

        val request = Request.Builder()
            .url(webHook)
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw Exception("Error in webhook: ${response.code}")
            }
        }
    }

}