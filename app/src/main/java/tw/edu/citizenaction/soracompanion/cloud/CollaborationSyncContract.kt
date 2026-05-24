package tw.edu.citizenaction.soracompanion.cloud

import tw.edu.citizenaction.soracompanion.model.CollaborationNote

object CollaborationSyncContract {
    const val COLLABORATION_SCHEMA_VERSION = 4

    fun eventId(note: CollaborationNote): String {
        return listOf(note.actor, note.role, note.target, note.note)
            .joinToString("|")
            .lowercase()
            .replace(Regex("[^a-z0-9\\u4e00-\\u9fff]+"), "-")
            .trim('-')
            .ifBlank { "collaboration-note" }
    }

    fun mergeNotes(
        localNotes: List<CollaborationNote>,
        remoteNotes: List<CollaborationNote>
    ): List<CollaborationNote> {
        return (localNotes + remoteNotes)
            .groupBy { eventId(it) }
            .map { (_, notes) -> notes.maxBy { it.createdAt } }
            .sortedByDescending { it.createdAt }
    }

    fun buildCollaborationSyncMetadata(
        scope: CloudAccessScope,
        pushFirst: Boolean
    ): Map<String, Any> {
        return mapOf(
            "collaborationSchemaVersion" to COLLABORATION_SCHEMA_VERSION,
            "direction" to if (pushFirst) "bidirectional" else "fetch-only",
            "pushBeforeFetch" to pushFirst,
            "classId" to scope.classId,
            "userId" to scope.userId,
            "roleLabel" to scope.roleLabel,
            "collectionPath" to scope.collaborationCollectionPath,
            "conflictRule" to "same eventId keeps latest createdAt"
        )
    }

    fun canCreateOfficialNote(scope: CloudAccessScope): Boolean {
        return CloudDataContract.canWriteCollaboration(scope)
    }
}
