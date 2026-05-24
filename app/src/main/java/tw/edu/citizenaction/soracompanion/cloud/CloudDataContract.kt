package tw.edu.citizenaction.soracompanion.cloud

import tw.edu.citizenaction.soracompanion.auth.AuthContract

data class CloudAccessScope(
    val classId: String,
    val userId: String,
    val roleLabel: String
) {
    val classDocumentPath: String = "classes/$classId"
    val studentDocumentPath: String = "classes/$classId/students/$userId"
    val collaborationCollectionPath: String = "classes/$classId/collaborationNotes"
    val questionBankCollectionPath: String = "classes/$classId/questionBank"
}

object CloudDataContract {
    const val SCHEMA_VERSION = 3

    val syncedCollections = listOf(
        "appState",
        "learningEvents",
        "collaborationNotes",
        "offlineSyncItems",
        "questionBank"
    )

    fun buildScope(classCode: String, accountName: String, roleLabel: String): CloudAccessScope {
        return CloudAccessScope(
            classId = normalizeId(classCode, fallback = "DEMO-CLASS").uppercase(),
            userId = normalizeId(accountName, fallback = "demo-user"),
            roleLabel = AuthContract.normalizeRole(roleLabel)
        )
    }

    fun buildSyncMetadata(scope: CloudAccessScope, deviceLabel: String): Map<String, Any> {
        return mapOf(
            "schemaVersion" to SCHEMA_VERSION,
            "app" to "English+",
            "deviceLabel" to deviceLabel,
            "classId" to scope.classId,
            "userId" to scope.userId,
            "roleLabel" to scope.roleLabel,
            "classPath" to scope.classDocumentPath,
            "studentPath" to scope.studentDocumentPath,
            "collections" to syncedCollections
        )
    }

    fun canReadStudentData(scope: CloudAccessScope, targetUserId: String): Boolean {
        return when {
            AuthContract.isStudentRole(scope.roleLabel) -> scope.userId == normalizeId(targetUserId, fallback = "")
            AuthContract.isStaffRole(scope.roleLabel) -> true
            else -> false
        }
    }

    fun canWriteCollaboration(scope: CloudAccessScope): Boolean {
        return AuthContract.isStaffRole(scope.roleLabel)
    }

    fun canWriteQuestionBank(scope: CloudAccessScope): Boolean {
        return AuthContract.normalizeRole(scope.roleLabel) == AuthContract.ROLE_TEACHER
    }

    private fun normalizeId(value: String, fallback: String): String {
        val normalized = value.trim()
            .lowercase()
            .replace(Regex("[^a-z0-9]+"), "-")
            .trim('-')
        return normalized.ifBlank { fallback }
    }
}
