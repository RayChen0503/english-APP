# Round 1 - Release Readiness Cleanup

This round prepares the current English+ Android prototype for future internal testing and store-release work. It does not connect Firebase, create Play Console assets, or sign a production bundle yet.

## Decisions

| Item | Decision |
| --- | --- |
| Product name | English+ |
| Application id | `tw.edu.citizenaction.soracompanion` |
| Version name | `0.7.0` |
| Version code | `7` |
| Build status | Classroom demo / internal testing prototype |
| Store status | Not ready for public Play Store submission |

## Changes In This Round

- Updated Android version metadata from `0.1.0` to `0.7.0`.
- Added an explicit `debug` version suffix.
- Added a `release` build type with a stable ProGuard rules file.
- Rewrote the README so it separates current working prototype features from production gaps.
- Added this release-readiness checklist document.

## Ready For Internal Testing

The project can produce a debug APK for local sharing:

```text
app/build/outputs/apk/debug/app-debug.apk
```

This is appropriate for classmates, group members, and teachers who need to preview the prototype on Android devices.

## Not Yet Ready For Store Release

Before Google Play submission, the project still needs:

- Release keystore and signed `.aab`.
- Firebase Auth or another formal account system.
- Cloud database and authorization rules.
- Production AI proxy.
- Privacy policy and Google Play Data Safety answers.
- Store listing assets.
- Closed testing and real-device QA.

## Verification Commands

```powershell
$env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'
$env:PATH="$env:JAVA_HOME\bin;$env:PATH"
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug --console=plain
```
