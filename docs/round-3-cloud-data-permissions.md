# Round 3 - Cloud Data And Permissions Contract

This round prepares English+ for Firebase Firestore or a school backend. It does not deploy a real backend yet; it defines the data contract and permission model the backend should implement.

## What Changed

- Added `CloudDataContract`.
- Added a stable cloud schema version: `3`.
- Added normalized cloud scope:
  - `classId`
  - `userId`
  - `roleLabel`
- Added standard document paths:
  - `classes/{classId}`
  - `classes/{classId}/students/{userId}`
  - `classes/{classId}/collaborationNotes`
  - `classes/{classId}/questionBank`
- Added synced collection declarations:
  - `appState`
  - `learningEvents`
  - `collaborationNotes`
  - `offlineSyncItems`
  - `questionBank`
- Updated cloud sync payloads to include scope, paths, role, and collection metadata.
- Updated collaboration fetch/push requests to use the same schema version.
- Added unit tests for cloud paths, role permissions, and sync metadata.

## Permission Rules

| Role | Student data | Collaboration notes | Question bank |
| --- | --- | --- | --- |
| Student | Can read own student document only | Cannot write official notes | Cannot edit |
| Teacher | Can read class student data | Can write | Can edit |
| Volunteer | Can read assigned/class handoff data | Can write | Cannot edit |

The app-side contract is not a substitute for backend security rules. Firestore rules or school backend authorization must enforce the same rules server-side.

## Example Sync Metadata

```json
{
  "schemaVersion": 3,
  "app": "English+",
  "deviceLabel": "android-demo",
  "classId": "CLASS-8A",
  "userId": "teacher-lin",
  "roleLabel": "老師",
  "classPath": "classes/CLASS-8A",
  "studentPath": "classes/CLASS-8A/students/teacher-lin",
  "collections": [
    "appState",
    "learningEvents",
    "collaborationNotes",
    "offlineSyncItems",
    "questionBank"
  ]
}
```

## Remaining Work For Real Cloud Deployment

- Create Firebase project or school backend.
- Deploy database collections.
- Add server-side authorization rules.
- Issue real user tokens from the auth system.
- Replace prototype endpoint entry with environment-based backend config.
- Add integration tests against a staging backend.
