package tw.edu.citizenaction.soracompanion.qa

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ClassroomPilotContractTest {
    @Test
    fun pilotPlanIncludesTeacherStudentAndVolunteerMoments() {
        val plan = ClassroomPilotContract.defaultPilotPlan()
        val moments = plan.steps.map { it.owner }

        assertEquals(10, ClassroomPilotContract.PILOT_SCHEMA_VERSION)
        assertTrue(moments.contains("teacher"))
        assertTrue(moments.contains("student"))
        assertTrue(moments.contains("volunteer"))
        assertEquals("45 minutes", plan.duration)
    }

    @Test
    fun consentPacketCoversStudentDataAndOptOut() {
        val packet = ClassroomPilotContract.consentPacket()

        assertTrue(packet.requiredItems.contains("student learning data notice"))
        assertTrue(packet.requiredItems.contains("AI support context notice"))
        assertTrue(packet.requiredItems.contains("guardian or school consent"))
        assertTrue(packet.requiredItems.contains("opt-out path"))
        assertFalse(packet.allowsPublicStudentRanking)
    }

    @Test
    fun successMetricsFocusOnUsefulnessNotScores() {
        val metrics = ClassroomPilotContract.successMetrics().map { it.metricId }

        assertTrue(metrics.contains("student-return-willingness"))
        assertTrue(metrics.contains("teacher-actionability"))
        assertTrue(metrics.contains("handoff-clarity"))
        assertFalse(metrics.contains("public-rank-score"))
    }

    @Test
    fun pilotGateBlocksWhenConsentOrTestDeviceIsMissing() {
        val gate = ClassroomPilotContract.pilotReadinessGate(
            consentPacketReady = false,
            teacherBriefReady = true,
            testDeviceReady = false,
            feedbackFormReady = true
        )

        assertFalse(gate.readyForPilot)
        assertTrue(gate.blockers.contains("consent packet"))
        assertTrue(gate.blockers.contains("test device"))
    }
}
