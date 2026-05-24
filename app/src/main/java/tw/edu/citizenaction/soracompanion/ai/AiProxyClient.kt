package tw.edu.citizenaction.soracompanion.ai

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class AiProxyClient(private val endpoint: String) {
    fun generateSupport(
        question: String,
        concept: String,
        answerContext: String,
        moodLabel: String,
        wrongAttempts: Int,
        classCode: String
    ): AiSupportResult {
        val body = JSONObject()
            .put("action", "generateSupport")
            .put("type", "ai_support_proxy")
            .put("schemaVersion", AiSecurityContract.AI_SECURITY_SCHEMA_VERSION)
            .put("app", "English+")
            .put("classCode", classCode)
            .put("security", JSONObject(AiSecurityContract.proxyPayloadMetadata(classCode, "android-client")))
            .put("question", question)
            .put("concept", concept)
            .put("answerContext", answerContext)
            .put("moodLabel", moodLabel)
            .put("wrongAttempts", wrongAttempts)

        val text = postJson(endpoint, body)
        val response = JSONObject(text.ifBlank { "{}" })
        response.optJSONObject("result")?.let { return parseResult(it, "AI Proxy") }
        return parseResult(response, "AI Proxy")
    }

    private fun postJson(endpoint: String, body: JSONObject): String {
        val connection = (URL(endpoint).openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            connectTimeout = 20_000
            readTimeout = 45_000
            doOutput = true
            setRequestProperty("Content-Type", "application/json; charset=utf-8")
            setRequestProperty("Accept", "application/json")
        }
        OutputStreamWriter(connection.outputStream, Charsets.UTF_8).use { writer ->
            writer.write(body.toString())
        }
        val stream = if (connection.responseCode in 200..299) connection.inputStream else connection.errorStream
        val responseText = BufferedReader(InputStreamReader(stream, Charsets.UTF_8)).use { it.readText() }
        if (connection.responseCode !in 200..299) {
            throw IllegalStateException("AI proxy error ${connection.responseCode}: $responseText")
        }
        return responseText
    }

    private fun parseResult(json: JSONObject, sourceLabel: String): AiSupportResult {
        return AiSupportResult(
            diagnosis = json.optString("diagnosis", "後端 AI 已收到資料，但沒有回傳診斷欄位。"),
            studentFeedback = json.optString("studentFeedback", "後端 AI 已收到資料，但沒有回傳學生回饋欄位。"),
            handoffSummary = json.optString("handoffSummary", "後端 AI 已收到資料，但沒有回傳接力摘要欄位。"),
            source = json.optString("source", sourceLabel)
        )
    }
}
