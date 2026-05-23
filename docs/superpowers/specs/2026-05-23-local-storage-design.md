# Local Storage Design

## Goal

Move English+ from screen-only prototype state toward real local data persistence.

## First Storage Slice

This round stores two kinds of data on the device:

1. `app_state`: current student mode, mood, progress counters, selected account, pending sync count, and prototype operation state.
2. `learning_events`: timestamped records for answers, reflections, help requests, migration, and sync simulation.

## Approach

Use Android SQLite through a small `SQLiteOpenHelper` wrapper for this first functional round.

This avoids making the current UI prototype depend on network downloads or a large framework migration while still creating a real persistent database file. The later Room/Firebase rounds can wrap or migrate this storage boundary.

## User-Facing Proof

The app should show storage status inside the learning map and sync center:

- whether state exists in the local database
- how many learning events have been written
- what the latest recorded event is
- how many items still wait for sync

## Scope Boundaries

- Do not add login or cloud sync in this round.
- Do not replace all fake repository data.
- Do not add a full analytics system.
- Keep existing UI flow intact.

## Verification

- Build with `:app:assembleDebug`.
- Confirm state save/load still uses the same `PrototypeStateStore` API.
- Confirm answer, reflection, help request, and sync actions write learning events.
