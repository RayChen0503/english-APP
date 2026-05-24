package tw.edu.citizenaction.soracompanion.qa

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidationContractTest {
    @Test
    fun automatedChecksCoverTestsAndBothApkBuilds() {
        val commands = ValidationContract.automatedChecks().map { it.gradleTask }

        assertTrue(commands.contains(":app:testDebugUnitTest"))
        assertTrue(commands.contains(":app:assembleDebug"))
        assertTrue(commands.contains(":app:assembleRelease"))
    }

    @Test
    fun smokeTestTouchesCorePrototypeFlows() {
        val flows = ValidationContract.manualSmokeTest().map { it.flowId }

        assertEquals(10, flows.size)
        assertTrue(flows.contains("student-home-next-action"))
        assertTrue(flows.contains("teacher-handoff"))
        assertTrue(flows.contains("ai-security-fallback"))
        assertTrue(flows.contains("sync-center"))
        assertTrue(flows.contains("question-bank"))
        assertTrue(flows.contains("report-export"))
    }

    @Test
    fun releaseGateBlocksLaunchUntilManualDeviceTestingIsComplete() {
        val gate = ValidationContract.releaseReadinessGate(
            automatedChecksPassed = true,
            manualSmokeTestPassed = false,
            physicalDeviceTested = false
        )

        assertFalse(gate.readyForStore)
        assertTrue(gate.blockers.contains("manual smoke test"))
        assertTrue(gate.blockers.contains("physical device test"))
    }

    @Test
    fun releaseGateAllowsClassroomPrototypeWhenRequiredChecksPass() {
        val gate = ValidationContract.releaseReadinessGate(
            automatedChecksPassed = true,
            manualSmokeTestPassed = true,
            physicalDeviceTested = true
        )

        assertTrue(gate.readyForStore)
        assertEquals(emptyList<String>(), gate.blockers)
    }
}
