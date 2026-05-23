# Cloud Backend Sync Round

Goal: move beyond local-only SQLite by adding a cloud backend handoff point that does not require Firebase project credentials during class prototyping.

Implemented scope:

- Add `CloudBackendClient` to POST JSON payloads to a configurable backend URL.
- Add cloud backend URL storage in `PrototypeStateStore`.
- Add a structured cloud sync payload containing app state, storage snapshot, learning events, collaboration notes, and offline sync items.
- Add Sync Center UI for backend status, endpoint entry, cloud sync progress, success, and failure states.
- Mark local offline sync queue items as synced after a successful backend response.
- Keep the app fully usable without a backend URL.

Future production work:

- Replace the configurable URL with Firebase Cloud Functions or a school backend API.
- Add authentication tokens.
- Add retry scheduling and conflict handling.
- Move sensitive service credentials to server-side code.

Verification:

- Run `:app:assembleDebug` after implementation.
