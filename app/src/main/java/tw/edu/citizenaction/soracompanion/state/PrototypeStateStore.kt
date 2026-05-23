package tw.edu.citizenaction.soracompanion.state

import android.content.Context
import tw.edu.citizenaction.soracompanion.model.AppState
import tw.edu.citizenaction.soracompanion.model.LocalAccount
import tw.edu.citizenaction.soracompanion.model.Mood
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
