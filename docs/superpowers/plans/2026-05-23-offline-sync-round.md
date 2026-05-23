# Round 5: Offline and Sync Prototype

Goal: make offline support visible as saved product behavior instead of static labels.

Implemented scope:

- Add `OfflineSyncItem` model for sync queue entries.
- Add SQLite `offline_sync_items` table and database upgrade to version 5.
- Track downloaded offline packs locally.
- Record pending sync items from answers, reflections, AI handoff summaries, teacher student changes, and mentor collaboration.
- Refresh the pending count from the database instead of relying only on an in-memory counter.
- Show queue entries in Offline Packs, Sync Center, and storage status.
- Add "mark all synced" behavior that updates saved queue status.

Verification:

- Run `:app:assembleDebug` after implementation.
