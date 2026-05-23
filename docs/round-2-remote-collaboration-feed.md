# Round 2 - Remote Collaboration Feed

This round moves teacher/mentor collaboration from local-only records toward a backend-ready shared feed.

## Prototype Behavior

- The Sync Center cloud backend URL is reused as the collaboration endpoint.
- The app can push local collaboration notes before pulling the latest remote feed.
- The app can also pull remote notes without pushing first.
- Imported notes are de-duplicated locally by actor, role, target, note, status, and createdAt before they are written into SQLite.
- Successful imports create a local sync record so teachers can see that remote collaboration data entered the device.

## Backend Contract

Push request:

```json
{
  "action": "pushCollaboration",
  "type": "collaboration_push",
  "schemaVersion": 1,
  "app": "English+",
  "payload": {
    "classCode": "YILAN-CHENGZHI-8A",
    "exportedAt": 0,
    "collaborationNotes": []
  }
}
```

Fetch request:

```json
{
  "action": "fetchCollaboration",
  "type": "collaboration_feed",
  "schemaVersion": 1,
  "app": "English+",
  "classCode": "YILAN-CHENGZHI-8A",
  "since": 0
}
```

Accepted fetch responses:

```json
{ "collaborationNotes": [] }
```

```json
{ "notes": [] }
```

```json
{ "payload": { "collaborationNotes": [] } }
```

The app also accepts a raw JSON array as a feed response.
