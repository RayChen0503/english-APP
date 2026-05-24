package tw.edu.citizenaction.soracompanion.qa

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class StoreReleaseContractTest {
    @Test
    fun playStoreListingContainsRequiredEnglishPlusMetadata() {
        val listing = StoreReleaseContract.playStoreListing()

        assertEquals("English+", listing.appName)
        assertTrue(listing.shortDescription.length in 20..80)
        assertTrue(listing.fullDescription.contains("偏鄉"))
        assertTrue(listing.fullDescription.contains("情緒斷點"))
        assertTrue(listing.contactEmail.contains("@"))
    }

    @Test
    fun privacyDeclarationCoversStudentLearningDataAndAiProxy() {
        val privacy = StoreReleaseContract.privacyDeclaration()

        assertTrue(privacy.dataTypes.contains("student learning records"))
        assertTrue(privacy.dataTypes.contains("class collaboration notes"))
        assertTrue(privacy.dataTypes.contains("AI support context"))
        assertEquals("server-side proxy", privacy.aiKeyLocation)
        assertFalse(privacy.allowsProductionKeyOnDevice)
    }

    @Test
    fun releaseChecklistBlocksStoreUntilSigningAndBackendAreReady() {
        val gate = StoreReleaseContract.storeReadinessGate(
            signedRelease = false,
            privacyPolicyUrlReady = true,
            backendDeployed = false,
            classroomConsentReady = true
        )

        assertFalse(gate.readyForStore)
        assertTrue(gate.blockers.contains("signed release"))
        assertTrue(gate.blockers.contains("backend deployment"))
    }

    @Test
    fun classroomPilotCanProceedBeforePublicStoreLaunch() {
        val gate = StoreReleaseContract.classroomPilotGate(
            debugApkBuilt = true,
            teacherBriefReady = true,
            testDeviceReady = true
        )

        assertTrue(gate.readyForPilot)
        assertEquals(emptyList<String>(), gate.blockers)
    }
}
