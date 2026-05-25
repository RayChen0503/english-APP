package tw.edu.citizenaction.soracompanion.qa

data class TeacherBrief(
    val title: String,
    val talkingPoints: List<String>,
    val demoReminder: String
)

data class ConsentNotice(
    val sections: Map<String, String>,
    val allowPublicRanking: Boolean
)

data class FeedbackQuestion(
    val audience: String,
    val question: String,
    val responseType: String
)

data class FeedbackForm(
    val title: String,
    val questions: List<FeedbackQuestion>
)

data class ObservationSheet(
    val title: String,
    val fields: List<String>
)

object PilotMaterialsContract {
    const val MATERIALS_SCHEMA_VERSION = 11

    fun teacherBrief(): TeacherBrief {
        return TeacherBrief(
            title = "English+ 班級試用老師說明",
            talkingPoints = listOf(
                "English+ 是低壓英語學習與情緒斷點接力原型，不是比較名次或刷題工具。",
                "學生先回報狀態，再做一個短任務，平台會把卡住的位置變成可修復的一小步。",
                "情緒斷點會被整理成老師或志工看得懂的證據與下一步。",
                "老師與志工的重點是接住學生、降低重複說明成本，而不是增加作業量。",
                "試用後請回饋：摘要是否能行動、學生是否願意下次再用、同步/報告是否清楚。"
            ),
            demoReminder = "Start with student home, then task, AI fallback, handoff, sync, question bank, and report."
        )
    }

    fun consentNotice(): ConsentNotice {
        return ConsentNotice(
            sections = linkedMapOf(
                "資料會用在哪裡" to "本次試用只用於學習支持、情緒斷點分析、老師/志工接力與課程成果檢核。",
                "會記錄哪些資料" to "可能記錄心情狀態、答題狀況、修復提示、協作紀錄、同步狀態與回饋內容。",
                "誰可以看到" to "試用資料只提供授課老師、課程小組與必要志工查看，不公開展示個別學生資料。",
                "AI 如何使用" to "AI 只用於產生學習支持與接力摘要；正式版應透過後端代理，不讓手機保存正式 API Key。",
                "退出方式" to "學生或家長可提出不參與或停止使用，停止後不再新增該學生的試用紀錄。"
            ),
            allowPublicRanking = false
        )
    }

    fun feedbackForm(): FeedbackForm {
        return FeedbackForm(
            title = "English+ 班級試用回饋表",
            questions = listOf(
                FeedbackQuestion("student", "你下次遇到英文卡住時，會想再用 English+ 嗎？為什麼？", "short text"),
                FeedbackQuestion("student", "今天的任務長度讓你覺得可以開始，還是仍然有壓力？", "choice + short text"),
                FeedbackQuestion("teacher", "老師能不能在一分鐘內看出學生下一步需要什麼支持？", "scale + note"),
                FeedbackQuestion("teacher", "週報與接力摘要是否足以作為課後追蹤依據？", "scale + note"),
                FeedbackQuestion("volunteer", "志工看到摘要後，是否知道要先說什麼、帶哪一小步？", "scale + note")
            )
        )
    }

    fun observationSheet(): ObservationSheet {
        return ObservationSheet(
            title = "English+ 試用觀察紀錄",
            fields = listOf(
                "student returned after support",
                "student understood next small task",
                "teacher next action identified",
                "handoff summary understandable",
                "AI/local fallback message felt low-pressure",
                "sync/report state was understandable",
                "question bank level and skill were understandable"
            )
        )
    }
}
