package tw.edu.citizenaction.soracompanion.auth

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

data class AuthSession(
    val displayName: String,
    val roleLabel: String,
    val classCode: String,
    val token: String,
    val provider: String
)

class AuthClient(private val endpoint: String) {
    fun login(username: String, password: String, classCode: String): AuthSession {
        val body = JSONObject()
            .put("username", username)
            .put("password", password)
            .put("classCode", classCode)
            .put("app", "English+")

        val response = postJson(body)
        val json = JSONObject(response.ifBlank { "{}" })
        return AuthSession(
            displayName = json.optString("displayName", username),
            roleLabel = json.optString("roleLabel", inferRole(username)),
            classCode = json.optString("classCode", classCode.ifBlank { "REMOTE-AUTH" }),
            token = json.optString("token", json.optString("idToken", "")),
            provider = json.optString("provider", "校內/Firebase 後端")
        )
    }

    private fun postJson(body: JSONObject): String {
        val connection = (URL(endpoint).openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            connectTimeout = 15_000
            readTimeout = 30_000
            doOutput = true
            setRequestProperty("Content-Type", "application/json; charset=utf-8")
            setRequestProperty("Accept", "application/json, text/plain, */*")
        }

        OutputStreamWriter(connection.outputStream, Charsets.UTF_8).use { writer ->
            writer.write(body.toString())
        }

        val stream = if (connection.responseCode in 200..299) {
            connection.inputStream
        } else {
            connection.errorStream
        }
        val responseText = BufferedReader(InputStreamReader(stream, Charsets.UTF_8)).use { it.readText() }
        if (connection.responseCode !in 200..299) {
            throw IllegalStateException("Auth backend error ${connection.responseCode}: $responseText")
        }
        return responseText
    }

    private fun inferRole(username: String): String {
        val lower = username.lowercase()
        return when {
            lower.contains("teacher") || lower.contains("老師") -> "老師"
            lower.contains("mentor") || lower.contains("volunteer") -> "雲端志工"
            else -> "學生"
        }
    }
}
