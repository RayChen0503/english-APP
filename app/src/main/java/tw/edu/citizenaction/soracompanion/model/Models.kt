package tw.edu.citizenaction.soracompanion.model

enum class Role { Student, Mentor }

enum class Screen { Home, CheckIn, Lesson, AiCoach, Breakpoints, Handoff, Map, Roster, Mentor, Report, Profile, Journey, Intervention, HelpRequest, StudentDetail, Contract, Reflection, ActionQueue, Account, StudentManager, AiLab, SyncCenter }

enum class Mood(
    val label: String,
    val description: String,
    val color: String,
    val defaultMinutes: Int,
    val confidenceDelta: Int,
    val planName: String
) {
    Good("狀態穩定", "可以做短任務加一題挑戰題。", "#0F766E", 8, 3, "一般任務"),
    Okay("普通但可嘗試", "先做 5 分鐘，答對後再加題。", "#246BFD", 5, 1, "低壓任務"),
    Low("有點累", "只做一題修復任務，先拿回可完成感。", "#B45309", 3, 0, "修復任務")
}

data class StudentProfile(
    val name: String,
    val age: Int,
    val location: String,
    val grade: String,
    val goal: String,
    val constraint: String,
    val mentor: String,
    val learningStyle: String,
    val supportNeed: String
)

data class LearningModule(
    val title: String,
    val subtitle: String,
    val progress: Int,
    val nextStep: String,
    val status: String
)

data class Question(
    val prompt: String,
    val options: List<String>,
    val answer: String,
    val explanation: String,
    val concept: String,
    val type: String = "選擇題",
    val repairHint: String = explanation
)

data class Breakpoint(
    val title: String,
    val severity: String,
    val evidence: String,
    val aiAction: String,
    val mentorAction: String
)

data class StudentRow(
    val name: String,
    val risk: String,
    val issue: String,
    val status: String
)

data class Metric(
    val label: String,
    val value: String,
    val color: String
)

data class ActionItem(
    val title: String,
    val subtitle: String,
    val action: () -> Unit
)

data class StudyTask(
    val title: String,
    val minutes: Int,
    val difficulty: String,
    val reason: String,
    val status: String
)

data class SupportMessage(
    val sender: String,
    val time: String,
    val content: String,
    val tone: String
)

data class WeeklySignal(
    val label: String,
    val value: String,
    val note: String,
    val color: String
)

data class MistakeRecord(
    val concept: String,
    val wrongPattern: String,
    val repairStep: String,
    val status: String
)

data class OfflinePack(
    val title: String,
    val size: String,
    val duration: String,
    val content: String
)

data class MentorCheck(
    val label: String,
    val status: String,
    val note: String,
    val color: String
)

data class HandoffPriority(
    val title: String,
    val owner: String,
    val nextAction: String,
    val urgency: String
)

data class JourneyStep(
    val stage: String,
    val studentFeeling: String,
    val platformResponse: String,
    val handoffRule: String
)

data class InterventionStep(
    val trigger: String,
    val designAction: String,
    val studentCopy: String,
    val evidence: String
)

data class DesignPrinciple(
    val title: String,
    val detail: String,
    val productProof: String
)

data class HelpRequestOption(
    val reason: String,
    val studentText: String,
    val platformAction: String,
    val route: String
)

data class LearningContract(
    val title: String,
    val studentPromise: String,
    val platformPromise: String,
    val mentorPromise: String
)

data class ReflectionPrompt(
    val title: String,
    val studentChoice: String,
    val platformResponse: String,
    val confidenceDelta: Int
)

data class TeacherAction(
    val title: String,
    val owner: String,
    val status: String,
    val due: String,
    val evidence: String,
    val nextStep: String
)

data class SyncRecord(
    val title: String,
    val status: String,
    val detail: String
)

data class LocalAccount(
    val displayName: String,
    val roleLabel: String,
    val classCode: String,
    val loginState: String
)

data class AiScenario(
    val title: String,
    val input: String,
    val diagnosis: String,
    val feedback: String,
    val handoffSummary: String
)

data class AppState(
    val mood: Mood,
    val minutes: Int,
    val confidence: Int,
    val completedTasks: Int,
    val currentQuestionIndex: Int,
    val actionDoneCount: Int = 0,
    val managedStudentCount: Int = 5,
    val offlinePendingCount: Int = 1,
    val selectedAccountName: String = "林家豪"
)
