package tw.edu.citizenaction.soracompanion.cloud

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import tw.edu.citizenaction.soracompanion.auth.AuthContract
import tw.edu.citizenaction.soracompanion.model.CollaborationNote

class CollaborationSyncContractTest {
    @Test
    fun eventIdIsStableAcrossDevicesForSameHandoffContent() {
        val note = CollaborationNote(
            actor = "Emily",
            role = AuthContract.ROLE_VOLUNTEER,
            target = "Ray",
            note = "Practice He is twice.",
            status = "已回覆",
            createdAt = 1000L
        )
        val sameNoteLater = note.copy(createdAt = 5000L)

        assertEquals(
            CollaborationSyncContract.eventId(note),
            CollaborationSyncContract.eventId(sameNoteLater)
        )
    }

    @Test
    fun mergeRemoteNotesDeduplicatesAndKeepsLatestStatus() {
        val local = listOf(
            CollaborationNote("Emily", AuthContract.ROLE_VOLUNTEER, "Ray", "Practice He is twice.", "待處理", 1000L)
        )
        val remote = listOf(
            CollaborationNote("Emily", AuthContract.ROLE_VOLUNTEER, "Ray", "Practice He is twice.", "已回覆", 3000L),
            CollaborationNote("Teacher Lin", AuthContract.ROLE_TEACHER, "Ray", "Check mood tomorrow.", "本週追蹤", 2000L)
        )

        val merged = CollaborationSyncContract.mergeNotes(local, remote)

        assertEquals(2, merged.size)
        assertEquals("已回覆", merged.first { it.actor == "Emily" }.status)
        assertEquals(3000L, merged.first { it.actor == "Emily" }.createdAt)
    }

    @Test
    fun declaresBidirectionalSyncMetadata() {
        val scope = CloudDataContract.buildScope("CLASS-8A", "Teacher Lin", AuthContract.ROLE_TEACHER)
        val metadata = CollaborationSyncContract.buildCollaborationSyncMetadata(scope, pushFirst = true)

        assertEquals(4, metadata["collaborationSchemaVersion"])
        assertEquals("bidirectional", metadata["direction"])
        assertEquals(scope.collaborationCollectionPath, metadata["collectionPath"])
        assertEquals(true, metadata["pushBeforeFetch"])
    }

    @Test
    fun studentsCannotWriteOfficialCollaborationNotes() {
        val student = CloudDataContract.buildScope("CLASS-8A", "Ray", AuthContract.ROLE_STUDENT)
        val teacher = CloudDataContract.buildScope("CLASS-8A", "Teacher Lin", AuthContract.ROLE_TEACHER)

        assertFalse(CollaborationSyncContract.canCreateOfficialNote(student))
        assertTrue(CollaborationSyncContract.canCreateOfficialNote(teacher))
    }
}
