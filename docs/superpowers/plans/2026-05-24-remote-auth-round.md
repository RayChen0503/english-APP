# Round 1 of Remaining Work: Remote Auth Skeleton

Goal: move the account center beyond local demo account switching, while keeping the prototype runnable without Firebase credentials.

Implemented scope:

- Add `AuthClient` that posts username, password, and class code to a configurable auth endpoint.
- Support Firebase Auth wrapper APIs, school APIs, or test auth webhooks.
- Store the auth endpoint locally.
- Save successful auth sessions with display name, role, class code, token preview, and provider.
- Write the authenticated account back into the local account list.
- Keep local demo accounts available as fallback.
- Add account center UI for endpoint, username, password, class/group code, login progress, success, and failure states.

Future production work:

- Replace password POST form with Firebase Auth SDK or Google Sign-In when the real project credentials are available.
- Add refresh token handling.
- Add logout and account revocation.
- Add secure backend token exchange.

Verification:

- Run `:app:assembleDebug` after implementation.
