# Round 8 - Testing Validation

This round adds repeatable validation for the English+ Android prototype.

## Executable Validation Contract

The app now includes `ValidationContract` with `VALIDATION_SCHEMA_VERSION = 8`.

It defines:

- required automated Gradle checks
- manual smoke-test flows
- release-readiness blockers
- the rule that a classroom/store candidate is blocked until automated checks, manual smoke test, and physical-device testing all pass

## Automated Checks

Run from the project root:

```powershell
$env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'
$env:PATH="$env:JAVA_HOME\bin;$env:PATH"
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:assembleRelease --console=plain
```

### Unit Test Coverage

`PrototypeRepositoryTest`

- Question bank has enough seed items.
- Question bank IDs are unique.
- Every question has a prompt, answer, options, concept, and repair hint.
- Student task track and mentor/volunteer track both exist.
- Handoff, collaboration, and design-principle data are not empty.

`ModelInvariantTest`

- Mood defaults stay short and low-pressure.
- Mood confidence changes are non-negative.
- Mood colors remain valid hex colors.
- Screen registry includes the core prototype areas, including sync, question bank, and design system.

## Manual Smoke Test

Use Android Studio Run on a virtual or physical device:

1. Open English+ and confirm the home screen shows today's next action early.
2. Switch between student and teacher/volunteer mode.
3. Start a short task and answer at least one question.
4. Trigger AI support and confirm fallback/local result still works if no API proxy is configured.
5. Open handoff and confirm collaboration notes are visible.
6. Open sync center and confirm pending/synced states are understandable.
7. Open question bank and confirm levels, skills, and items render.
8. Open report export and confirm the preview includes learning, support, sync, and question-bank evidence.
9. Open product design principles and the new design system screen.
10. Confirm the app can be closed and reopened without losing local progress.

## Known Limits

- UI instrumentation tests are not yet added because the user's emulator setup has been unstable.
- Firebase/Auth/backend integration still needs live service credentials before end-to-end cloud tests can be meaningful.
- Physical-device testing is recommended before any classroom demo.
