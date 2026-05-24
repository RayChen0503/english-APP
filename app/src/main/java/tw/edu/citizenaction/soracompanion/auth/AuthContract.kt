package tw.edu.citizenaction.soracompanion.auth

import org.json.JSONObject

object AuthContract {
    const val ROLE_STUDENT = "學生"
    const val ROLE_TEACHER = "老師"
    const val ROLE_VOLUNTEER = "志工"

    const val PROVIDER_DEMO = "demo"
    const val PROVIDER_FIREBASE = "firebase"
    const val PROVIDER_GOOGLE = "google"
    const val PROVIDER_SCHOOL = "school"

    fun normalizeRole(rawRole: String): String {
        val normalized = rawRole.trim().lowercase()
        return when {
            normalized.contains("teacher") || normalized.contains("老師") -> ROLE_TEACHER
            normalized.contains("volunteer") || normalized.contains("mentor") || normalized.contains("志工") -> ROLE_VOLUNTEER
            normalized.contains("student") || normalized.contains("學生") -> ROLE_STUDENT
            else -> ROLE_STUDENT
        }
    }

    fun isStudentRole(roleLabel: String): Boolean = normalizeRole(roleLabel) == ROLE_STUDENT

    fun isStaffRole(roleLabel: String): Boolean = !isStudentRole(roleLabel)

    fun isValidEndpoint(endpoint: String): Boolean {
        val url = endpoint.trim()
        return url.startsWith("https://") ||
            url.startsWith("http://localhost") ||
            url.startsWith("http://127.0.0.1") ||
            url.startsWith("http://10.0.2.2")
    }

    fun validateLoginInput(username: String, password: String, classCode: String): List<String> {
        val problems = mutableListOf<String>()
        if (username.trim().isBlank()) problems.add("account is required")
        if (password.isBlank()) problems.add("password is required")
        if (classCode.trim().isBlank()) problems.add("class code is required")
        return problems
    }

    fun buildLoginPayload(
        username: String,
        password: String,
        classCode: String,
        provider: String = PROVIDER_SCHOOL
    ): JSONObject {
        val data = buildLoginPayloadData(username, password, classCode, provider)
        return JSONObject()
            .put("schemaVersion", data.getValue("schemaVersion"))
            .put("app", data.getValue("app"))
            .put("username", data.getValue("username"))
            .put("password", data.getValue("password"))
            .put("classCode", data.getValue("classCode"))
            .put("provider", data.getValue("provider"))
    }

    fun buildLoginPayloadData(
        username: String,
        password: String,
        classCode: String,
        provider: String = PROVIDER_SCHOOL
    ): Map<String, Any> {
        return mapOf(
            "schemaVersion" to 2,
            "app" to "English+",
            "username" to username.trim(),
            "password" to password,
            "classCode" to classCode.trim(),
            "provider" to provider
        )
    }

    fun providerDisplayName(provider: String): String {
        return when (provider.trim().lowercase()) {
            PROVIDER_FIREBASE -> "Firebase Auth"
            PROVIDER_GOOGLE -> "Google Sign-In"
            PROVIDER_SCHOOL -> "School SSO"
            PROVIDER_DEMO -> "Demo Mode"
            else -> provider.ifBlank { "Remote Auth" }
        }
    }
}
