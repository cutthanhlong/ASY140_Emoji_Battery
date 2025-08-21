package com.example.basekotlin.data.call_api

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class EmojiDataRepository {

    suspend fun getEmojiData(url: String): EmojiDataResponse = withContext(Dispatchers.IO) {
        try {
            // Tạo TrustManager để trust tất cả certificates
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<X509Certificate>?, authType: String?
                ) {
                }

                override fun checkServerTrusted(
                    chain: Array<X509Certificate>?, authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            })

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.socketFactory)

            val allHostsValid = HostnameVerifier { _, _ -> true }
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid)

            val urlObj = URL(url)
            val connection = urlObj.openConnection() as HttpsURLConnection
            connection.requestMethod = "GET"

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                parseJsonResponse(response)
            } else {
                Log.e("API_ERROR", "HTTP Response Code: ${connection.responseCode}")
                null
            }
        } catch (e: Exception) {
            Log.e("API_ERROR", "HTTPS failed, trying HTTP", e)
            try {
                // Fallback to HTTP
                val urlObj = URL(url)
                val connection = urlObj.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    parseJsonResponse(response)
                } else {
                    Log.e("API_ERROR", "HTTP Response Code: ${connection.responseCode}")
                    null
                }
            } catch (ex: Exception) {
                Log.e("API_ERROR", "Both HTTPS and HTTP failed", ex)
                null
            }
        } as EmojiDataResponse
    }

    private fun parseJsonResponse(jsonString: String): EmojiDataResponse? {
        return try {
            val jsonObject = JSONObject(jsonString)

            // Parse Icons
            val icons = parseIconsArray(jsonObject.optJSONArray("icons"))

            // Parse Batteries
            val batteries = parseBatteriesArray(jsonObject.optJSONArray("batteries"))

            // Parse Color Templates
            val colorTemplates =
                parseColorTemplatesArray(jsonObject.optJSONArray("color_templates"))

            // Parse Battery Templates
            val batteryTemplates =
                parseBatteryTemplatesArray(jsonObject.optJSONArray("battery_templates"))

            // Parse Emojis
            val emojis = parseEmojisArray(jsonObject.optJSONArray("emojis"))

            // Parse Animations
            val animations = parseAnimationsArray(jsonObject.optJSONArray("animations"))

            EmojiDataResponse(
                icons = icons,
                batteries = batteries,
                colorTemplates = colorTemplates,
                batteryTemplates = batteryTemplates,
                emojis = emojis,
                animations = animations
            )
        } catch (e: Exception) {
            Log.e("API_ERROR", "Error parsing JSON", e)
            null
        }
    }

    private fun parseIconsArray(jsonArray: JSONArray?): List<IconModel> {
        if (jsonArray == null) return emptyList()

        return (0 until jsonArray.length()).mapNotNull { i ->
            try {
                val obj = jsonArray.getJSONObject(i)
                IconModel(
                    code = obj.getString("code"),
                    fileName = obj.getString("file_name"),
                    url = obj.getString("url")
                )
            } catch (e: Exception) {
                Log.e("PARSE_ERROR", "Error parsing icon at index $i", e)
                null
            }
        }
    }

    private fun parseBatteriesArray(jsonArray: JSONArray?): List<BatteryModel> {
        if (jsonArray == null) return emptyList()

        return (0 until jsonArray.length()).mapNotNull { i ->
            try {
                val obj = jsonArray.getJSONObject(i)
                BatteryModel(
                    code = obj.getString("code"),
                    fileName = obj.getString("file_name"),
                    url = obj.getString("url")
                )
            } catch (e: Exception) {
                Log.e("PARSE_ERROR", "Error parsing battery at index $i", e)
                null
            }
        }
    }

    private fun parseColorTemplatesArray(jsonArray: JSONArray?): List<ColorTemplateModel> {
        if (jsonArray == null) return emptyList()

        return (0 until jsonArray.length()).mapNotNull { i ->
            try {
                val obj = jsonArray.getJSONObject(i)
                ColorTemplateModel(
                    code = obj.getString("code"),
                    colorBgName = obj.getString("color_bg_name"),
                    hexColor = obj.getString("hex_color"),
                    fileName = obj.getString("file_name"),
                    url = obj.getString("url")
                )
            } catch (e: Exception) {
                Log.e("PARSE_ERROR", "Error parsing color template at index $i", e)
                null
            }
        }
    }

    private fun parseBatteryTemplatesArray(jsonArray: JSONArray?): List<BatteryTemplateModel> {
        if (jsonArray == null) return emptyList()

        return (0 until jsonArray.length()).mapNotNull { i ->
            try {
                val obj = jsonArray.getJSONObject(i)
                BatteryTemplateModel(
                    code = obj.getString("code"),
                    batteryName = obj.getString("battery_name"),
                    iconName = obj.getString("icon_name"),
                    urlBgName = obj.getString("url_bg_name"),
                    urlIconName = obj.getString("url_icon_name")
                )
            } catch (e: Exception) {
                Log.e("PARSE_ERROR", "Error parsing battery template at index $i", e)
                null
            }
        }
    }

    private fun parseEmojisArray(jsonArray: JSONArray?): List<EmojiModel> {
        if (jsonArray == null) return emptyList()

        return (0 until jsonArray.length()).mapNotNull { i ->
            try {
                val obj = jsonArray.getJSONObject(i)
                EmojiModel(
                    fileName = obj.getString("file_name"), url = obj.getString("url")
                )
            } catch (e: Exception) {
                Log.e("PARSE_ERROR", "Error parsing emoji at index $i", e)
                null
            }
        }
    }

    private fun parseAnimationsArray(jsonArray: JSONArray?): List<AnimationModel> {
        if (jsonArray == null) return emptyList()

        return (0 until jsonArray.length()).mapNotNull { i ->
            try {
                val obj = jsonArray.getJSONObject(i)
                AnimationModel(
                    stt = obj.getInt("stt"),
                    animation = obj.getString("animation"),
                    animationJsonUrl = obj.getString("animation_json_url"),
                    animationImageUrl = obj.getString("animation_image_url")
                )
            } catch (e: Exception) {
                Log.e("PARSE_ERROR", "Error parsing animation at index $i", e)
                null
            }
        }
    }
}