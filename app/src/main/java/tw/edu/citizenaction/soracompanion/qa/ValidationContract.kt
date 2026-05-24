package tw.edu.citizenaction.soracompanion.qa

data class AutomatedCheck(
    val name: String,
    val gradleTask: String,
    val purpose: String
)

data class SmokeTestStep(
    val flowId: String,
    val screen: String,
    val expectedEvidence: String
)

data class ReleaseReadinessGate(
    val readyForStore: Boolean,
    val blockers: List<String>
)

object ValidationContract {
    const val VALIDATION_SCHEMA_VERSION = 8

    fun automatedChecks(): List<AutomatedCheck> {
        return listOf(
            AutomatedCheck(
                name = "Unit tests",
                gradleTask = ":app:testDebugUnitTest",
                purpose = "Validate data contracts, auth, cloud sync, question bank, API security, and design system rules."
            ),
            AutomatedCheck(
                name = "Debug APK",
                gradleTask = ":app:assembleDebug",
                purpose = "Confirm Android Studio runnable prototype builds for emulator or phone demos."
            ),
            AutomatedCheck(
                name = "Release APK",
                gradleTask = ":app:assembleRelease",
                purpose = "Confirm a release variant can be produced before store-readiness work."
            )
        )
    }

    fun manualSmokeTest(): List<SmokeTestStep> {
        return listOf(
            SmokeTestStep("student-home-next-action", "Home", "Today-first action is visible without hunting."),
            SmokeTestStep("student-check-in", "CheckIn", "Mood changes produce short, low-pressure tasks."),
            SmokeTestStep("lesson-answer-flow", "Lesson", "Correct and wrong answers update learning state."),
            SmokeTestStep("ai-security-fallback", "AiLab", "No secure proxy falls back to local simulated support."),
            SmokeTestStep("teacher-handoff", "Handoff", "Teacher or volunteer can see evidence and next action."),
            SmokeTestStep("collaboration-sync", "Handoff", "Remote collaboration push/fetch entry is visible."),
            SmokeTestStep("sync-center", "SyncCenter", "Pending and synced states are understandable."),
            SmokeTestStep("question-bank", "QuestionBank", "Level, skill, source, and review state render."),
            SmokeTestStep("report-export", "Report", "Text/HTML/share report flow is reachable."),
            SmokeTestStep("design-system", "DesignSystem", "Tokens and component rules are visible in app.")
        )
    }

    fun releaseReadinessGate(
        automatedChecksPassed: Boolean,
        manualSmokeTestPassed: Boolean,
        physicalDeviceTested: Boolean
    ): ReleaseReadinessGate {
        val blockers = buildList {
            if (!automatedChecksPassed) add("automated checks")
            if (!manualSmokeTestPassed) add("manual smoke test")
            if (!physicalDeviceTested) add("physical device test")
        }
        return ReleaseReadinessGate(
            readyForStore = blockers.isEmpty(),
            blockers = blockers
        )
    }
}
