package tw.edu.citizenaction.soracompanion.cloud

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

data class CloudSyncResult(
    val statusCode: Int,
    val responseText: String
)

class CloudBackendClient(private val endpoint: String) {
    fun sync(payload: JSONObject): CloudSyncResult {
        return post(payload)
    }

    fun fetchCollaboration(classCode: String, since: Long): JSONObject {
        val scope = CloudDataContract.buildScope(classCode, "remote-collaboration", "老師")
        val metadata = CollaborationSyncContract.buildCollaborationSyncMetadata(scope, pushFirst = false)
        val body = JSONObject()
            .put("action", "fetchCollaboration")
            .put("type", "collaboration_feed")
            .put("schemaVersion", CloudDataContract.SCHEMA_VERSION)
            .put("collaborationSchemaVersion", CollaborationSyncContract.COLLABORATION_SCHEMA_VERSION)
            .put("classCode", classCode)
            .put("classId", scope.classId)
            .put("collectionPath", scope.collaborationCollectionPath)
            .put("syncMetadata", JSONObject(metadata))
            .put("since", since)
            .put("app", "English+")
        val result = post(body)
        val responseText = result.responseText.trim()
        return when {
            responseText.isBlank() -> JSONObject().put("collaborationNotes", emptyList<String>())
            responseText.startsWith("[") -> JSONObject().put("collaborationNotes", org.json.JSONArray(responseText))
            else -> JSONObject(responseText)
        }
    }

    fun pushCollaboration(payload: JSONObject): CloudSyncResult {
        return post(
            JSONObject()
                .put("action", "pushCollaboration")
                .put("type", "collaboration_push")
                .put("schemaVersion", CloudDataContract.SCHEMA_VERSION)
                .put("collaborationSchemaVersion", CollaborationSyncContract.COLLABORATION_SCHEMA_VERSION)
                .put("app", "English+")
                .put("payload", payload)
        )
    }

    fun fetchQuestionBank(classCode: String, since: Long): JSONObject {
        val scope = CloudDataContract.buildScope(classCode, "question-bank-reader", "teacher")
        val body = JSONObject()
            .put("action", "fetchQuestionBank")
            .put("type", "question_bank_feed")
            .put("schemaVersion", CloudDataContract.SCHEMA_VERSION)
            .put("questionBankSchemaVersion", QuestionBankContract.QUESTION_BANK_SCHEMA_VERSION)
            .put("classCode", classCode)
            .put("classId", scope.classId)
            .put("collectionPath", scope.questionBankCollectionPath)
            .put("since", since)
            .put("app", "English+")
        val result = post(body)
        val responseText = result.responseText.trim()
        return when {
            responseText.isBlank() -> JSONObject().put("questionBank", emptyList<String>())
            responseText.startsWith("[") -> JSONObject().put("questionBank", org.json.JSONArray(responseText))
            else -> JSONObject(responseText)
        }
    }

    fun pushQuestionBank(payload: JSONObject): CloudSyncResult {
        return post(
            JSONObject()
                .put("action", "pushQuestionBank")
                .put("type", "question_bank_push")
                .put("schemaVersion", CloudDataContract.SCHEMA_VERSION)
                .put("questionBankSchemaVersion", QuestionBankContract.QUESTION_BANK_SCHEMA_VERSION)
                .put("app", "English+")
                .put("payload", payload)
        )
    }

    private fun post(payload: JSONObject): CloudSyncResult {
        val connection = (URL(endpoint).openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            connectTimeout = 15_000
            readTimeout = 30_000
            doOutput = true
            setRequestProperty("Content-Type", "application/json; charset=utf-8")
            setRequestProperty("Accept", "application/json, text/plain, */*")
        }

        OutputStreamWriter(connection.outputStream, Charsets.UTF_8).use { writer ->
            writer.write(payload.toString())
        }

        val stream = if (connection.responseCode in 200..299) {
            connection.inputStream
        } else {
            connection.errorStream
        }
        val responseText = BufferedReader(InputStreamReader(stream, Charsets.UTF_8)).use { it.readText() }
        if (connection.responseCode !in 200..299) {
            throw IllegalStateException("Cloud backend error ${connection.responseCode}: $responseText")
        }
        return CloudSyncResult(connection.responseCode, responseText)
    }
}
