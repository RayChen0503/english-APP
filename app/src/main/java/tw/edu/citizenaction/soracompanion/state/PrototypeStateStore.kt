package tw.edu.citizenaction.soracompanion.state

import android.content.Context
import tw.edu.citizenaction.soracompanion.model.AppState
import tw.edu.citizenaction.soracompanion.model.Mood

class PrototypeStateStore(context: Context) {
    private val prefs = context.getSharedPreferences("sora_companion_state", Context.MODE_PRIVATE)

    fun load(): AppState {
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
            selectedAccountName = prefs.getString("selectedAccountName", "林家豪") ?: "林家豪"
        )
    }

    fun save(state: AppState) {
        prefs.edit()
            .putString("mood", state.mood.name)
            .putInt("minutes", state.minutes)
            .putInt("confidence", state.confidence)
            .putInt("completedTasks", state.completedTasks)
            .putInt("currentQuestionIndex", state.currentQuestionIndex)
            .putInt("actionDoneCount", state.actionDoneCount)
            .putInt("managedStudentCount", state.managedStudentCount)
            .putInt("offlinePendingCount", state.offlinePendingCount)
            .putString("selectedAccountName", state.selectedAccountName)
            .apply()
    }
}
