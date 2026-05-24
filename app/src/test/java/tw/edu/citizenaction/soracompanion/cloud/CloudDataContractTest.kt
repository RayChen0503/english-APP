package tw.edu.citizenaction.soracompanion.cloud

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import tw.edu.citizenaction.soracompanion.auth.AuthContract

class CloudDataContractTest {
    @Test
    fun buildsStableClassAndUserPaths() {
        val scope = CloudDataContract.buildScope(
            classCode = " class-8a ",
            accountName = " Ray Chen ",
            roleLabel = AuthContract.ROLE_STUDENT
        )

        assertEquals("CLASS-8A", scope.classId)
        assertEquals("ray-chen", scope.userId)
        assertEquals("classes/CLASS-8A/students/ray-chen", scope.studentDocumentPath)
        assertEquals("classes/CLASS-8A/collaborationNotes", scope.collaborationCollectionPath)
    }

    @Test
    fun studentCanOnlyReadOwnLearningData() {
        val student = CloudDataContract.buildScope("CLASS-8A", "Ray Chen", AuthContract.ROLE_STUDENT)

        assertTrue(CloudDataContract.canReadStudentData(student, "ray-chen"))
        assertFalse(CloudDataContract.canReadStudentData(student, "other-student"))
        assertFalse(CloudDataContract.canWriteQuestionBank(student))
    }

    @Test
    fun teacherAndVolunteerCanReadClassScopedHandoffData() {
        val teacher = CloudDataContract.buildScope("CLASS-8A", "Teacher Lin", AuthContract.ROLE_TEACHER)
        val volunteer = CloudDataContract.buildScope("CLASS-8A", "Emily Mentor", AuthContract.ROLE_VOLUNTEER)

        assertTrue(CloudDataContract.canReadStudentData(teacher, "ray-chen"))
        assertTrue(CloudDataContract.canReadStudentData(volunteer, "ray-chen"))
        assertTrue(CloudDataContract.canWriteCollaboration(volunteer))
        assertTrue(CloudDataContract.canWriteQuestionBank(teacher))
        assertFalse(CloudDataContract.canWriteQuestionBank(volunteer))
    }

    @Test
    fun syncMetadataDeclaresSchemaAndCollections() {
        val scope = CloudDataContract.buildScope("CLASS-8A", "Teacher Lin", AuthContract.ROLE_TEACHER)
        val metadata = CloudDataContract.buildSyncMetadata(scope, "android-demo")

        assertEquals(3, metadata["schemaVersion"])
        assertEquals("English+", metadata["app"])
        assertEquals("android-demo", metadata["deviceLabel"])
        assertEquals("CLASS-8A", metadata["classId"])
        assertEquals("teacher-lin", metadata["userId"])
        assertEquals(AuthContract.ROLE_TEACHER, metadata["roleLabel"])
        assertTrue((metadata["collections"] as List<*>).contains("learningEvents"))
        assertTrue((metadata["collections"] as List<*>).contains("questionBank"))
    }
}
