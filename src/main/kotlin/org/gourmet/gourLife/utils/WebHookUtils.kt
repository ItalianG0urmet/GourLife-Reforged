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
    private val configManager: ConfigManager = GourLife.configManager

    fun finalKillWebHook(player: Player) {
        if(!configManager.webhookOnFinal) return

        val webHook: String = config.getString("webhook") ?: "null"
        val color: Int = configManager.webhookColor ?: 16711680
        val title: String = configManager.webhookTitle
            .replace("%player%", player.name)
        val message: String = configManager.webhookMessage
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

        //Execute
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw Exception("Error in webhook: ${response.code}")
            }
        }
    }

}