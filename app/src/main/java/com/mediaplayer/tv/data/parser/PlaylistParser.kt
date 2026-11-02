package com.mediaplayer.tv.data.parser

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

data class MediaStream(
    val title: String,
    val url: String,
    val logo: String? = null,
    val group: String? = null
)

class PlaylistParser {

    suspend fun parsePlaylist(urlString: String): List<MediaStream> = withContext(Dispatchers.IO) {
        if (urlString.endsWith(".json", true) || urlString.contains("w3u")) {
            parseW3u(urlString)
        } else {
            parseM3u(urlString)
        }
    }

    private fun parseM3u(urlString: String): List<MediaStream> {
        val list = mutableListOf<MediaStream>()
        try {
            val url = URL(urlString)
            val reader = BufferedReader(InputStreamReader(url.openStream()))
            var line: String?
            var title = "Unknown Stream"
            var logo: String? = null
            var group: String? = "General"

            while (reader.readLine().also { line = it } != null) {
                val trimmed = line!!.trim()
                if (trimmed.startsWith("#EXTINF:")) {
                    title = trimmed.substringAfterLast(",", "Unknown").trim()
                    logo = extractAttribute(trimmed, "tvg-logo")
                    group = extractAttribute(trimmed, "group-title") ?: "General"
                } else if (!trimmed.startsWith("#") && trimmed.isNotEmpty()) {
                    list.add(MediaStream(title = title, url = trimmed, logo = logo, group = group))
                }
            }
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    private fun parseW3u(urlString: String): List<MediaStream> {
        val list = mutableListOf<MediaStream>()
        try {
            val url = URL(urlString)
            val scanner = java.util.Scanner(url.openStream(), "UTF-8").useDelimiter("\\A")
            val jsonString = if (scanner.hasNext()) scanner.next() else ""
            
            val jsonObject = JSONObject(jsonString)
            val stations = jsonObject.optJSONArray("stations") ?: jsonObject.optJSONArray("items")
            
            if (stations != null) {
                for (i in 0 until stations.length()) {
                    val station = stations.getJSONObject(i)
                    val name = station.optString("name", "Unknown")
                    val mediaUrl = station.optString("url", "")
                    val icon = station.optString("image", null)
                    
                    if (mediaUrl.isNotEmpty()) {
                        list.add(MediaStream(title = name, url = mediaUrl, logo = icon, group = "W3U Playlist"))
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    private fun extractAttribute(line: String, attr: String): String? {
        val pattern = Regex("""$attr="([^"]*)"""")
        val match = pattern.find(line)
        return match?.groupValues?.get(1)
    }
}