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
    fun login(
        username: String,
        password: String,
        classCode: String,
        provider: String = AuthContract.PROVIDER_SCHOOL
    ): AuthSession {
        val problems = AuthContract.validateLoginInput(username, password, classCode)
        if (problems.isNotEmpty()) {
            throw IllegalArgumentException(problems.joinToString("; "))
        }
        if (!AuthContract.isValidEndpoint(endpoint)) {
            throw IllegalArgumentException("Auth endpoint must be HTTPS or a local development URL")
        }

        val response = postJson(AuthContract.buildLoginPayload(username, password, classCode, provider))
        val json = JSONObject(response.ifBlank { "{}" })
        val rawProvider = json.optString("provider", provider)
        return AuthSession(
            displayName = json.optString("displayName", username.trim()),
            roleLabel = AuthContract.normalizeRole(json.optString("roleLabel", inferRole(username))),
            classCode = json.optString("classCode", classCode.trim().ifBlank { "REMOTE-AUTH" }),
            token = json.optString("token", json.optString("idToken", "")),
            provider = AuthContract.providerDisplayName(rawProvider)
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
            lower.contains("teacher") -> AuthContract.ROLE_TEACHER
            lower.contains("mentor") || lower.contains("volunteer") -> AuthContract.ROLE_VOLUNTEER
            else -> AuthContract.ROLE_STUDENT
        }
    }
}
