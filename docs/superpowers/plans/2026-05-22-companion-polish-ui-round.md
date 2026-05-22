# Companion Polish UI Round Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add product polish to the student companion UI through home progress cues, stronger mood selection feedback, and gentler achievement rhythm in task and lesson screens.

**Architecture:** Keep the app in native Android Views with `MainActivity.kt` as the current screen renderer and `UiKit.kt` as the shared style layer. Derive selected/progress states from existing prototype variables instead of introducing new persistence.

**Tech Stack:** Kotlin, native Android Views, Gradle Android app build, GitHub `main`.

---

## File Map

- Modify `D:\公民行動\android\SoraCompanion\app\src\main\java\tw\edu\citizenaction\soracompanion\MainActivity.kt`
  - Home session progress and track-state polish.
  - Mood/duration selected states and support response copy.
  - Task checkpoint, lesson micro-goal, and success next-step framing.
- Modify `D:\公民行動\android\SoraCompanion\app\src\main\java\tw\edu\citizenaction\soracompanion\ui\UiKit.kt` only if shared segmented or selected helpers reduce repeated code.

## Task 1: Polish The Home Progress Rhythm

- [ ] Inspect the existing home helpers:

```powershell
rg -n "todayRhythmCard|trackEntry|studentHome|flowStrip" "D:\公民行動\android\SoraCompanion\app\src\main\java\tw\edu\citizenaction\soracompanion\MainActivity.kt"
```

- [ ] Add a compact progress block using existing state:

```kotlin
private fun progressRhythmCard(): View {
    val box = ui.container(ColorToken.Card, ColorToken.Border)
    box.addView(ui.statusPill("今日節奏", ColorToken.Primary))
    box.addView(ProgressBar(...))
    box.addView(ui.body("你已完成 ... 下一步 ..."))
    return ui.margins(box, 0, 8, 0, 12)
}
```

- [ ] Place the progress block early on student home below today's rhythm.
- [ ] Improve the learning and support track copy so each track shows what it offers right now.
- [ ] Build the Android project after syncing changed files.

## Task 2: Add Mood And Duration Selected States

- [ ] Update `moodChoiceCard()` to accept whether its mood is selected.
- [ ] Render the selected mood with a stronger fill, pill, or border derived from `mood`.
- [ ] Replace raw duration buttons with duration cards or chip-like controls that show `minutes == value`.
- [ ] Extend the plan preview with a low-mood support route when `mood == Mood.Low`.
- [ ] Build the Android project after syncing changed files.

## Task 3: Add Gentle Achievement Rhythm To Learning

- [ ] Add a current checkpoint label to the focused task card.
- [ ] Add a compact micro-goal to the lesson focus card.
- [ ] Add the next unlock or next-step hint to the success summary.
- [ ] Keep help and recovery options available below the focused lesson content.
- [ ] Build the Android project after syncing changed files.

## Task 4: Verify And Publish

- [ ] Sync final Kotlin files into `D:\SoraCompanion`.
- [ ] Run:

```powershell
$env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'
$env:PATH="$env:JAVA_HOME\bin;$env:PATH"
.\gradlew.bat :app:assembleDebug
```

- [ ] Inspect:

```powershell
& 'C:\Users\ray\AppData\Local\Programs\Git\cmd\git.exe' status --short
& 'C:\Users\ray\AppData\Local\Programs\Git\cmd\git.exe' diff --stat
```

- [ ] Commit and push verified UI changes:

```powershell
& 'C:\Users\ray\AppData\Local\Programs\Git\cmd\git.exe' add app/src/main/java/tw/edu/citizenaction/soracompanion/MainActivity.kt app/src/main/java/tw/edu/citizenaction/soracompanion/ui/UiKit.kt docs/superpowers/specs/2026-05-22-companion-polish-ui-design.md docs/superpowers/plans/2026-05-22-companion-polish-ui-round.md
& 'C:\Users\ray\AppData\Local\Programs\Git\cmd\git.exe' commit -m 'Polish student companion feedback'
& 'C:\Users\ray\AppData\Local\Programs\Git\cmd\git.exe' push origin main
```

- [ ] Confirm clean Git status.
