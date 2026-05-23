# Round 4: Teacher and Mentor Collaboration

Goal: move teacher/mentor collaboration from static prototype cards into a saved local workflow that can support the later sync round.

Implemented scope:

- Add `CollaborationNote` model for actor, role, target, note, status, and timestamp.
- Add SQLite `collaboration_notes` table and database upgrade to version 4.
- Expose collaboration note read/write methods through `PrototypeStateStore`.
- Record notes from mentor handoff, mentor script usage, and teacher action completion.
- Surface recent collaboration notes in handoff, priority board, weekly report, and storage status.
- Keep all collaboration local for this round; cloud sync remains next-round scope.

Verification:

- Run `:app:assembleDebug` after implementation.
