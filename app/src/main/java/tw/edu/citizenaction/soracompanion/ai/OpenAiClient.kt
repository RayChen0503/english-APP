package tw.edu.citizenaction.soracompanion.ai

import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

data class AiSupportResult(
    val diagnosis: String,
    val studentFeedback: String,
    val handoffSummary: String,
    val source: String
)

class OpenAiClient(
    private val apiKey: String,
    private val model: String = DEFAULT_MODEL
) {
    fun generateSupport(
        question: String,
        concept: String,
        answerContext: String,
        moodLabel: String,
        wrongAttempts: Int
    ): AiSupportResult {
        val input = JSONObject()
            .put("question", question)
            .put("concept", concept)
            .put("answerContext", answerContext)
            .put("moodLabel", moodLabel)
            .put("wrongAttempts", wrongAttempts)

        val body = JSONObject()
            .put("model", model)
            .put(
                "instructions",
                "You are English+ support AI for rural junior-high English learning. " +
                    "Reply in Traditional Chinese. Be warm, brief, and practical. " +
                    "Return only JSON with diagnosis, studentFeedback, and handoffSummary."
            )
            .put(
                "input",
                "Create emotional breakpoint support from this learning context: $input"
            )
            .put("max_output_tokens", 420)

        val response = postJson("https://api.openai.com/v1/responses", body)
        val text = extractOutputText(JSONObject(response))
        return parseSupportJson(text)
    }

    private fun postJson(endpoint: String, body: JSONObject): String {
        val connection = (URL(endpoint).openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            connectTimeout = 20_000
            readTimeout = 45_000
            doOutput = true
            setRequestProperty("Authorization", "Bearer $apiKey")
            setRequestProperty("Content-Type", "application/json")
        }

        OutputStreamWriter(connection.outputStream, Charsets.UTF_8).use { writer ->
            writer.write(body.toString())
        }

        val stream = if (connection.responseCode in 200..299) {
            connection.inputStream
        } else {
            connection.errorStream
        }
        val text = BufferedReader(InputStreamReader(stream, Charsets.UTF_8)).use { it.readText() }
        if (connection.responseCode !in 200..299) {
            throw IllegalStateException("OpenAI API error ${connection.responseCode}: $text")
        }
        return text
    }

    private fun extractOutputText(response: JSONObject): String {
        response.optString("output_text").takeIf { it.isNotBlank() }?.let { return it }
        val output = response.optJSONArray("output") ?: JSONArray()
        val parts = mutableListOf<String>()
        for (i in 0 until output.length()) {
            val item = output.optJSONObject(i) ?: continue
            val content = item.optJSONArray("content") ?: continue
            for (j in 0 until content.length()) {
                val block = content.optJSONObject(j) ?: continue
                val text = block.optString("text")
                if (text.isNotBlank()) parts.add(text)
            }
        }
        return parts.joinToString("\n").ifBlank {
            throw IllegalStateException("OpenAI response did not contain text output.")
        }
    }

    private fun parseSupportJson(text: String): AiSupportResult {
        val clean = text.trim()
            .removePrefix("```json")
            .removePrefix("```")
            .removeSuffix("```")
            .trim()
        val json = JSONObject(clean)
        return AiSupportResult(
            diagnosis = json.optString("diagnosis", "AI 已回覆，但沒有提供診斷欄位。"),
            studentFeedback = json.optString("studentFeedback", "AI 已回覆，但沒有提供學生回饋欄位。"),
            handoffSummary = json.optString("handoffSummary", "AI 已回覆，但沒有提供志工摘要欄位。"),
            source = "OpenAI Responses API / $model"
        )
    }

    companion object {
        const val DEFAULT_MODEL = "gpt-5-mini"
    }
}
