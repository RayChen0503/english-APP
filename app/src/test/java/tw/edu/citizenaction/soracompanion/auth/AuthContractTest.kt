package tw.edu.citizenaction.soracompanion.auth

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AuthContractTest {
    @Test
    fun normalizesSupportedUserRoles() {
        assertEquals(AuthContract.ROLE_STUDENT, AuthContract.normalizeRole("student"))
        assertEquals(AuthContract.ROLE_TEACHER, AuthContract.normalizeRole("teacher"))
        assertEquals(AuthContract.ROLE_VOLUNTEER, AuthContract.normalizeRole("volunteer"))
        assertEquals(AuthContract.ROLE_VOLUNTEER, AuthContract.normalizeRole("mentor"))
        assertEquals(AuthContract.ROLE_STUDENT, AuthContract.normalizeRole(""))
    }

    @Test
    fun validatesProductionAndLocalAuthEndpoints() {
        assertTrue(AuthContract.isValidEndpoint("https://example.com/auth/login"))
        assertTrue(AuthContract.isValidEndpoint("http://10.0.2.2:5001/auth/login"))
        assertTrue(AuthContract.isValidEndpoint("http://localhost:5001/auth/login"))
        assertFalse(AuthContract.isValidEndpoint("http://example.com/auth/login"))
        assertFalse(AuthContract.isValidEndpoint("not-a-url"))
    }

    @Test
    fun reportsMissingLoginFieldsBeforeNetworkRequest() {
        val problems = AuthContract.validateLoginInput("", "", "")

        assertTrue(problems.any { it.contains("account") })
        assertTrue(problems.any { it.contains("password") })
        assertTrue(problems.any { it.contains("class") })
    }

    @Test
    fun buildsStableLoginPayloadForBackendAdapters() {
        val payload = AuthContract.buildLoginPayloadData(
            username = "student@example.com",
            password = "secret",
            classCode = "CLASS-8A",
            provider = AuthContract.PROVIDER_FIREBASE
        )

        assertEquals("student@example.com", payload["username"])
        assertEquals("secret", payload["password"])
        assertEquals("CLASS-8A", payload["classCode"])
        assertEquals("English+", payload["app"])
        assertEquals(AuthContract.PROVIDER_FIREBASE, payload["provider"])
        assertEquals(2, payload["schemaVersion"])
    }
}
