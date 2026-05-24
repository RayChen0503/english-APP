# Round 9 - Store Release Readiness

This round adds a formal contract for preparing English+ for a classroom pilot or eventual Play Store release.

## Added

- `StoreReleaseContract` with schema version `9`.
- Play Store listing draft:
  - app name
  - short description
  - full description
  - contact email placeholder
- Privacy declaration covering:
  - student learning records
  - class collaboration notes
  - AI support context
  - local account role and class code
  - offline sync status
- Store-readiness gate for:
  - signed release
  - privacy policy URL
  - backend deployment
  - classroom consent
- Classroom pilot gate for:
  - debug APK
  - teacher brief
  - test device

## Manifest Hardening

- Disabled Android backup with `android:allowBackup="false"`.
- Disabled cleartext traffic with `android:usesCleartextTraffic="false"`.

## Current Status

The app is closer to a classroom pilot than a public store launch. A public launch still requires a signed release setup, real privacy policy page, deployed backend, and consent flow for student data.
