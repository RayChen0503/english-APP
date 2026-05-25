package tw.edu.citizenaction.soracompanion.qa

data class PilotStep(
    val stepId: String,
    val owner: String,
    val title: String,
    val evidenceToCollect: String
)

data class PilotPlan(
    val duration: String,
    val steps: List<PilotStep>
)

data class ConsentPacket(
    val requiredItems: List<String>,
    val allowsPublicStudentRanking: Boolean
)

data class PilotSuccessMetric(
    val metricId: String,
    val label: String,
    val evidenceQuestion: String
)

data class PilotReadinessGate(
    val readyForPilot: Boolean,
    val blockers: List<String>
)

object ClassroomPilotContract {
    const val PILOT_SCHEMA_VERSION = 10

    fun defaultPilotPlan(): PilotPlan {
        return PilotPlan(
            duration = "45 minutes",
            steps = listOf(
                PilotStep("teacher-brief", "teacher", "Explain low-pressure learning goal", "Teacher can describe why the app is not a ranking tool."),
                PilotStep("student-check-in", "student", "Student chooses mood and task length", "Student understands today only needs a small task."),
                PilotStep("student-short-task", "student", "Student completes or gets stuck on one English item", "App captures answer, repair hint, and breakpoint."),
                PilotStep("ai-support", "student", "AI support or local fallback gives a short repair step", "Student sees a non-punitive next step."),
                PilotStep("handoff-review", "volunteer", "Volunteer reviews handoff summary", "Volunteer can say what to do next without rereading everything."),
                PilotStep("teacher-dashboard", "teacher", "Teacher checks roster/action queue", "Teacher can identify who needs follow-up."),
                PilotStep("sync-check", "teacher", "Teacher checks sync state", "Pending/synced status is understandable."),
                PilotStep("question-bank-check", "teacher", "Teacher checks question bank level/skill", "Teacher sees how tasks are selected."),
                PilotStep("report-share", "teacher", "Teacher opens report/export flow", "Report includes learning, support, and handoff evidence."),
                PilotStep("feedback-form", "student", "Student gives quick feedback", "Student can state whether they would return to use it.")
            )
        )
    }

    fun consentPacket(): ConsentPacket {
        return ConsentPacket(
            requiredItems = listOf(
                "student learning data notice",
                "AI support context notice",
                "guardian or school consent",
                "teacher/volunteer access explanation",
                "opt-out path"
            ),
            allowsPublicStudentRanking = false
        )
    }

    fun successMetrics(): List<PilotSuccessMetric> {
        return listOf(
            PilotSuccessMetric("student-return-willingness", "Student return willingness", "Would the student use this again for a short English task?"),
            PilotSuccessMetric("teacher-actionability", "Teacher actionability", "Can the teacher decide what to do next within one minute?"),
            PilotSuccessMetric("handoff-clarity", "Volunteer handoff clarity", "Can the volunteer understand the breakpoint and next step?"),
            PilotSuccessMetric("low-pressure-fit", "Low-pressure fit", "Did the app reduce pressure compared with test-first practice?"),
            PilotSuccessMetric("sync-understanding", "Sync understanding", "Can staff tell what is saved locally and what is synced?")
        )
    }

    fun pilotReadinessGate(
        consentPacketReady: Boolean,
        teacherBriefReady: Boolean,
        testDeviceReady: Boolean,
        feedbackFormReady: Boolean
    ): PilotReadinessGate {
        val blockers = buildList {
            if (!consentPacketReady) add("consent packet")
            if (!teacherBriefReady) add("teacher brief")
            if (!testDeviceReady) add("test device")
            if (!feedbackFormReady) add("feedback form")
        }
        return PilotReadinessGate(blockers.isEmpty(), blockers)
    }
}
