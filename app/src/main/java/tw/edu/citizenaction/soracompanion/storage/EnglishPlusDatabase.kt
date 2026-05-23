package tw.edu.citizenaction.soracompanion.storage

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import tw.edu.citizenaction.soracompanion.model.AppState
import tw.edu.citizenaction.soracompanion.model.Mood

data class LearningEvent(
    val type: String,
    val title: String,
    val detail: String,
    val createdAt: Long = System.currentTimeMillis()
)

data class StorageSnapshot(
    val stateSaved: Boolean,
    val eventCount: Int,
    val latestEventTitle: String
)

class EnglishPlusDatabase(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE app_state (
                id INTEGER PRIMARY KEY CHECK (id = 1),
                mood TEXT NOT NULL,
                minutes INTEGER NOT NULL,
                confidence INTEGER NOT NULL,
                completed_tasks INTEGER NOT NULL,
                current_question_index INTEGER NOT NULL,
                action_done_count INTEGER NOT NULL,
                managed_student_count INTEGER NOT NULL,
                offline_pending_count INTEGER NOT NULL,
                selected_account_name TEXT NOT NULL,
                mentor_reply_count INTEGER NOT NULL,
                learning_event_count INTEGER NOT NULL,
                repaired_mistake_count INTEGER NOT NULL,
                custom_task_count INTEGER NOT NULL,
                updated_at INTEGER NOT NULL
            )
            """.trimIndent()
        )
        db.execSQL(
            """
            CREATE TABLE learning_events (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                type TEXT NOT NULL,
                title TEXT NOT NULL,
                detail TEXT NOT NULL,
                created_at INTEGER NOT NULL
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS learning_events (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    type TEXT NOT NULL,
                    title TEXT NOT NULL,
                    detail TEXT NOT NULL,
                    created_at INTEGER NOT NULL
                )
                """.trimIndent()
            )
        }
    }

    fun loadState(): AppState? {
        readableDatabase.query(
            "app_state",
            null,
            "id = 1",
            null,
            null,
            null,
            null
        ).use { cursor ->
            if (!cursor.moveToFirst()) return null
            val moodName = cursor.getString(cursor.getColumnIndexOrThrow("mood"))
            val mood = Mood.values().firstOrNull { it.name == moodName } ?: Mood.Okay
            return AppState(
                mood = mood,
                minutes = cursor.getInt(cursor.getColumnIndexOrThrow("minutes")),
                confidence = cursor.getInt(cursor.getColumnIndexOrThrow("confidence")),
                completedTasks = cursor.getInt(cursor.getColumnIndexOrThrow("completed_tasks")),
                currentQuestionIndex = cursor.getInt(cursor.getColumnIndexOrThrow("current_question_index")),
                actionDoneCount = cursor.getInt(cursor.getColumnIndexOrThrow("action_done_count")),
                managedStudentCount = cursor.getInt(cursor.getColumnIndexOrThrow("managed_student_count")),
                offlinePendingCount = cursor.getInt(cursor.getColumnIndexOrThrow("offline_pending_count")),
                selectedAccountName = cursor.getString(cursor.getColumnIndexOrThrow("selected_account_name")),
                mentorReplyCount = cursor.getInt(cursor.getColumnIndexOrThrow("mentor_reply_count")),
                learningEventCount = cursor.getInt(cursor.getColumnIndexOrThrow("learning_event_count")),
                repairedMistakeCount = cursor.getInt(cursor.getColumnIndexOrThrow("repaired_mistake_count")),
                customTaskCount = cursor.getInt(cursor.getColumnIndexOrThrow("custom_task_count"))
            )
        }
    }

    fun saveState(state: AppState) {
        val values = ContentValues().apply {
            put("id", 1)
            put("mood", state.mood.name)
            put("minutes", state.minutes)
            put("confidence", state.confidence)
            put("completed_tasks", state.completedTasks)
            put("current_question_index", state.currentQuestionIndex)
            put("action_done_count", state.actionDoneCount)
            put("managed_student_count", state.managedStudentCount)
            put("offline_pending_count", state.offlinePendingCount)
            put("selected_account_name", state.selectedAccountName)
            put("mentor_reply_count", state.mentorReplyCount)
            put("learning_event_count", state.learningEventCount)
            put("repaired_mistake_count", state.repairedMistakeCount)
            put("custom_task_count", state.customTaskCount)
            put("updated_at", System.currentTimeMillis())
        }
        writableDatabase.insertWithOnConflict("app_state", null, values, SQLiteDatabase.CONFLICT_REPLACE)
    }

    fun addLearningEvent(event: LearningEvent) {
        val values = ContentValues().apply {
            put("type", event.type)
            put("title", event.title)
            put("detail", event.detail)
            put("created_at", event.createdAt)
        }
        writableDatabase.insert("learning_events", null, values)
    }

    fun snapshot(): StorageSnapshot {
        val stateSaved = loadState() != null
        val eventCount = readableDatabase.rawQuery("SELECT COUNT(*) FROM learning_events", null).use { cursor ->
            if (cursor.moveToFirst()) cursor.getInt(0) else 0
        }
        val latest = readableDatabase.rawQuery(
            "SELECT title FROM learning_events ORDER BY created_at DESC, id DESC LIMIT 1",
            null
        ).use { cursor ->
            if (cursor.moveToFirst()) cursor.getString(0) else "尚未寫入事件"
        }
        return StorageSnapshot(stateSaved, eventCount, latest)
    }

    companion object {
        private const val DB_NAME = "english_plus_local.db"
        private const val DB_VERSION = 2
    }
}
