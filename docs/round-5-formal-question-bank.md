# Round 5 - Formal Question Bank Contract

This round turns the existing demo question list into a production-shaped question bank contract.

## Added

- `QuestionBankContract` with schema version `5`.
- Stable class-scoped `importId` for cloud import and conflict resolution.
- Review states: `Draft`, `Approved`, `Archived`.
- Teacher-only publish rule; students remain read-only.
- Question bank metadata for level counts, skill counts, collection path, publisher identity, permission rule, and conflict rule.
- SQLite question bank columns for `review_state` and `import_batch_id`.
- Cloud sync payload now exports `questionBankSchemaVersion`, `questionBankMetadata`, per-question `importId`, review state, and import batch.
- Backend client now has `fetchQuestionBank` and `pushQuestionBank` request wrappers.

## Current behavior

The app can still run fully offline with the local SQLite question bank. When a backend endpoint is configured, the exported payload now carries enough metadata for a Firebase Function or school backend to validate and merge the question bank safely.

## Still needed for a formal production launch

- A real teacher admin page for importing CSV/Excel/Google Sheet question sets.
- Human review workflow for approving, archiving, and versioning questions.
- A complete leveled English question dataset.
- Backend-side validation to enforce the teacher-only publish rule.
