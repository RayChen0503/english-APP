# Round 4 - Realtime Collaboration Sync Contract

This round strengthens the multiplayer collaboration layer. It still does not deploy Firebase or a live school backend, but it defines how teacher and volunteer handoff notes should sync across devices.

## What Changed

- Added `CollaborationSyncContract`.
- Added collaboration schema version `4`.
- Added stable `eventId` generation for handoff notes.
- Added merge behavior for duplicate or conflicting notes:
  - Same `eventId` means the same handoff event.
  - If the same event appears locally and remotely, keep the note with the latest `createdAt`.
- Updated collaboration payloads to include:
  - `schemaVersion`
  - `collaborationSchemaVersion`
  - `classId`
  - `userId`
  - `roleLabel`
  - `collectionPath`
  - `eventId` per note
  - `syncMetadata`
- Updated remote collaboration fetch/push requests to declare collaboration schema version and sync metadata.
- Updated local de-duplication to use stable `eventId` instead of timestamp-sensitive keys.
- Added unit tests for event IDs, merge behavior, sync metadata, and role permission.

## Sync Direction

| Mode | Meaning |
| --- | --- |
| `bidirectional` | Push local notes first, then fetch remote notes |
| `fetch-only` | Pull remote notes without pushing local updates |

## Permission Rule

- Students cannot create official teacher/volunteer collaboration notes.
- Teachers and volunteers can create official collaboration notes.
- Backend rules must enforce the same rule server-side.

## Conflict Rule

```text
same eventId keeps latest createdAt
```

This lets a teacher and a volunteer update the same handoff event without creating duplicate cards.

## Remaining Work For Live Sync

- Connect to a real Firebase/Firestore or school backend.
- Add realtime listeners or polling.
- Add server timestamps.
- Add assigned-volunteer rules instead of broad class-level staff access.
- Add integration tests against staging backend.
