# Round 2 - Formal Login And Account System

This round moves English+ from pure demo accounts toward a formal account architecture. It does not yet add a real Firebase project, Google OAuth client, or school SSO server because those require external credentials and console setup.

## What Changed

- Added `AuthContract` as the shared contract for login providers.
- Normalized account roles into:
  - `學生`
  - `老師`
  - `志工`
- Added provider labels for:
  - Demo Mode
  - Firebase Auth
  - Google Sign-In
  - School SSO
- Added endpoint validation:
  - Production auth endpoints must use `https://`.
  - Local development endpoints can use `http://localhost`, `http://127.0.0.1`, or `http://10.0.2.2`.
- Added login input validation before network requests.
- Updated the remote auth request payload to schema version `2`.
- Updated the account center to show account-system readiness.
- Added unit tests for auth role normalization, endpoint validation, missing fields, and payload shape.

## Backend Login Contract

The mobile app posts:

```json
{
  "schemaVersion": 2,
  "app": "English+",
  "username": "student@example.com",
  "password": "secret",
  "classCode": "CLASS-8A",
  "provider": "school"
}
```

Expected backend response:

```json
{
  "displayName": "Ray",
  "roleLabel": "學生",
  "classCode": "CLASS-8A",
  "token": "server-issued-token",
  "provider": "firebase"
}
```

`roleLabel` can be returned as Chinese or English. The app normalizes it to `學生`, `老師`, or `志工`.

## What Still Requires External Setup

- Create Firebase project.
- Add Android app to Firebase project.
- Download `google-services.json`.
- Add Firebase Auth and Google Sign-In dependencies.
- Configure OAuth client IDs.
- Deploy auth proxy or school SSO adapter.
- Define real user records and class membership rules.

## Verification

Run:

```powershell
$env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'
$env:PATH="$env:JAVA_HOME\bin;$env:PATH"
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:assembleRelease --console=plain
```
