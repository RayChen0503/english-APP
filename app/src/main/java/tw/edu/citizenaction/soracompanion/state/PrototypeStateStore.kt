package tw.edu.citizenaction.soracompanion.state

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import tw.edu.citizenaction.soracompanion.model.AppState
import tw.edu.citizenaction.soracompanion.model.CollaborationNote
import tw.edu.citizenaction.soracompanion.model.LocalAccount
import tw.edu.citizenaction.soracompanion.model.Mood
import tw.edu.citizenaction.soracompanion.model.OfflineSyncItem
import tw.edu.citizenaction.soracompanion.storage.EnglishPlusDatabase
import tw.edu.citizenaction.soracompanion.storage.LearningEvent
import tw.edu.citizenaction.soracompanion.storage.StorageSnapshot

class PrototypeStateStore(context: Context) {
    private val prefs = context.getSharedPreferences("sora_companion_state", Context.MODE_PRIVATE)
    private val database = EnglishPlusDatabase(context.applicationContext)

    fun load(): AppState {
        database.loadState()?.let { return it }
        val migrated = loadLegacyPrefs()
        database.saveState(migrated)
        database.addLearningEvent(
            LearningEvent(
                type = "migration",
                title = "已建立本機資料庫",
                detail = "從舊版展示狀態建立 English+ SQLite 本機資料。"
            )
        )
        return migrated
    }

    fun save(state: AppState) {
        database.saveState(state)
    }

    fun recordEvent(type: String, title: String, detail: String) {
        database.addLearningEvent(LearningEvent(type, title, detail))
    }

    fun localAccounts(defaultAccounts: List<LocalAccount>): List<LocalAccount> {
        return database.loadAccounts(defaultAccounts)
    }

    fun markAccountUsed(displayName: String) {
        database.markAccountUsed(displayName)
    }

    fun addCollaborationNote(note: CollaborationNote) {
        database.addCollaborationNote(note)
    }

    fun collaborationNotes(limit: Int = 12): List<CollaborationNote> {
        return database.loadCollaborationNotes(limit)
    }

    fun addOfflineSyncItem(item: OfflineSyncItem) {
        database.addOfflineSyncItem(item)
    }

    fun offlineSyncItems(limit: Int = 16): List<OfflineSyncItem> {
        return database.loadOfflineSyncItems(limit)
    }

    fun markOfflineSyncItemsSynced() {
        database.markOfflineSyncItemsSynced()
    }

    fun pendingSyncCount(): Int = database.pendingSyncCount()

    fun downloadedPackTitles(): Set<String> = database.downloadedPackTitles()

    fun saveCloudBackendUrl(url: String) {
        prefs.edit().putString("cloud_backend_url", url.trim()).apply()
    }

    fun cloudBackendUrl(): String {
        return prefs.getString("cloud_backend_url", "")?.trim().orEmpty()
    }

    fun hasCloudBackend(): Boolean {
        val url = cloudBackendUrl()
        return url.startsWith("https://") || url.startsWith("http://")
    }

    fun cloudSyncPayload(deviceLabel: String): JSONObject {
        val state = load()
        val snapshot = storageSnapshot()
        val payload = JSONObject()
            .put("schemaVersion", 1)
            .put("app", "English+")
            .put("deviceLabel", deviceLabel)
            .put("exportedAt", System.currentTimeMillis())
            .put(
                "state",
                JSONObject()
                    .put("mood", state.mood.label)
                    .put("minutes", state.minutes)
                    .put("confidence", state.confidence)
                    .put("completedTasks", state.completedTasks)
                    .put("currentQuestionIndex", state.currentQuestionIndex)
                    .put("selectedAccountName", state.selectedAccountName)
                    .put("mentorReplyCount", state.mentorReplyCount)
                    .put("repairedMistakeCount", state.repairedMistakeCount)
                    .put("customTaskCount", state.customTaskCount)
            )
            .put(
                "snapshot",
                JSONObject()
                    .put("eventCount", snapshot.eventCount)
                    .put("collaborationCount", snapshot.collaborationCount)
                    .put("pendingSyncCount", snapshot.pendingSyncCount)
                    .put("downloadedPackCount", snapshot.downloadedPackCount)
                    .put("latestEventTitle", snapshot.latestEventTitle)
            )
            .put("learningEvents", JSONArray(database.loadLearningEvents().map { event ->
                JSONObject()
                    .put("type", event.type)
                    .put("title", event.title)
                    .put("detail", event.detail)
                    .put("createdAt", event.createdAt)
            }))
            .put("collaborationNotes", JSONArray(database.loadCollaborationNotes().map { note ->
                JSONObject()
                    .put("actor", note.actor)
                    .put("role", note.role)
                    .put("target", note.target)
                    .put("note", note.note)
                    .put("status", note.status)
                    .put("createdAt", note.createdAt)
            }))
            .put("offlineSyncItems", JSONArray(database.loadOfflineSyncItems().map { item ->
                JSONObject()
                    .put("title", item.title)
                    .put("category", item.category)
                    .put("detail", item.detail)
                    .put("status", item.status)
                    .put("updatedAt", item.updatedAt)
            }))
        return payload
    }

    fun saveOpenAiApiKey(apiKey: String) {
        prefs.edit().putString("openai_api_key", apiKey.trim()).apply()
    }

    fun openAiApiKey(): String {
        return prefs.getString("openai_api_key", "")?.trim().orEmpty()
    }

    fun hasOpenAiApiKey(): Boolean = openAiApiKey().startsWith("sk-")

    fun storageSnapshot(): StorageSnapshot = database.snapshot()

    private fun loadLegacyPrefs(): AppState {
        val moodName = prefs.getString("mood", Mood.Okay.name) ?: Mood.Okay.name
        val mood = Mood.values().firstOrNull { it.name == moodName } ?: Mood.Okay
        return AppState(
            mood = mood,
            minutes = prefs.getInt("minutes", mood.defaultMinutes),
            confidence = prefs.getInt("confidence", 46),
            completedTasks = prefs.getInt("completedTasks", 3),
            currentQuestionIndex = prefs.getInt("currentQuestionIndex", 0),
            actionDoneCount = prefs.getInt("actionDoneCount", 0),
            managedStudentCount = prefs.getInt("managedStudentCount", 5),
            offlinePendingCount = prefs.getInt("offlinePendingCount", 1),
            selectedAccountName = prefs.getString("selectedAccountName", "林家豪") ?: "林家豪",
            mentorReplyCount = prefs.getInt("mentorReplyCount", 0),
            learningEventCount = prefs.getInt("learningEventCount", 0),
            repairedMistakeCount = prefs.getInt("repairedMistakeCount", 0),
            customTaskCount = prefs.getInt("customTaskCount", 0)
        )
    }
}
