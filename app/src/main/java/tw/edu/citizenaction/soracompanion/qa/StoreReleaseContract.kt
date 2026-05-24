package tw.edu.citizenaction.soracompanion.qa

data class PlayStoreListing(
    val appName: String,
    val shortDescription: String,
    val fullDescription: String,
    val contactEmail: String
)

data class PrivacyDeclaration(
    val dataTypes: List<String>,
    val aiKeyLocation: String,
    val allowsProductionKeyOnDevice: Boolean,
    val studentDataPolicy: String
)

data class StoreReadinessGate(
    val readyForStore: Boolean,
    val blockers: List<String>
)

data class ClassroomPilotGate(
    val readyForPilot: Boolean,
    val blockers: List<String>
)

object StoreReleaseContract {
    const val STORE_RELEASE_SCHEMA_VERSION = 9

    fun playStoreListing(): PlayStoreListing {
        return PlayStoreListing(
            appName = "English+",
            shortDescription = "偏鄉學生英語學習與情緒斷點接力支持平台原型",
            fullDescription = "English+ 是為偏鄉學生設計的低壓英語學習原型，聚焦短任務、情緒斷點、AI 支援、老師與志工接力，以及可回報的學習證據。",
            contactEmail = "english-plus@example.edu"
        )
    }

    fun privacyDeclaration(): PrivacyDeclaration {
        return PrivacyDeclaration(
            dataTypes = listOf(
                "student learning records",
                "class collaboration notes",
                "AI support context",
                "local account role and class code",
                "offline sync status"
            ),
            aiKeyLocation = "server-side proxy",
            allowsProductionKeyOnDevice = false,
            studentDataPolicy = "Collect only learning context needed for support, reports, sync, and teacher/volunteer handoff."
        )
    }

    fun storeReadinessGate(
        signedRelease: Boolean,
        privacyPolicyUrlReady: Boolean,
        backendDeployed: Boolean,
        classroomConsentReady: Boolean
    ): StoreReadinessGate {
        val blockers = buildList {
            if (!signedRelease) add("signed release")
            if (!privacyPolicyUrlReady) add("privacy policy URL")
            if (!backendDeployed) add("backend deployment")
            if (!classroomConsentReady) add("classroom consent")
        }
        return StoreReadinessGate(blockers.isEmpty(), blockers)
    }

    fun classroomPilotGate(
        debugApkBuilt: Boolean,
        teacherBriefReady: Boolean,
        testDeviceReady: Boolean
    ): ClassroomPilotGate {
        val blockers = buildList {
            if (!debugApkBuilt) add("debug APK")
            if (!teacherBriefReady) add("teacher brief")
            if (!testDeviceReady) add("test device")
        }
        return ClassroomPilotGate(blockers.isEmpty(), blockers)
    }
}
