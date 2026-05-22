# Product Home UI Round Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Turn the student-facing Android prototype into a clearer companion flow by redesigning the home screen first, then extending the same design language into mood check-in and task/lesson surfaces.

**Architecture:** Keep the existing native Android View app and the current screen flow in `MainActivity.kt`. Add only a small number of focused UI helpers where they reduce repeated home/check-in/task layout code, while `UiKit.kt` remains the shared styling layer for spacing, cards, buttons, labels, pills, and emphasis treatments.

**Tech Stack:** Kotlin, native Android Views, Gradle Android app build, GitHub `main` branch.

---

## File Map

- Modify `D:\公民行動\android\SoraCompanion\app\src\main\java\tw\edu\citizenaction\soracompanion\MainActivity.kt`
  - Student home ordering and new student-facing UI sections.
  - Mood check-in layout, mood choice presentation, and plan preview.
  - Task queue, lesson, answer support, and success surface hierarchy.
- Modify `D:\公民行動\android\SoraCompanion\app\src\main\java\tw\edu\citizenaction\soracompanion\ui\UiKit.kt`
  - Add any shared emphasis helpers needed by the new screen blocks.
  - Keep spacing and touch target rules consistent.
- Sync the same changed Kotlin files into:
  - `D:\SoraCompanion\app\src\main\java\tw\edu\citizenaction\soracompanion\MainActivity.kt`
  - `D:\SoraCompanion\app\src\main\java\tw\edu\citizenaction\soracompanion\ui\UiKit.kt`
- Update `D:\SoraCompanion\README.md` only if the prototype summary needs a short note about the redesigned student journey.

## Task 1: Map The Student Home Structure

**Files:**
- Modify: `D:\公民行動\android\SoraCompanion\app\src\main\java\tw\edu\citizenaction\soracompanion\MainActivity.kt`

- [ ] **Step 1: Inspect the current student home code**

Run:

```powershell
rg -n "private fun studentHome|private fun hero|private fun actionGrid|private fun metricRow|private fun messageCard" "D:\公民行動\android\SoraCompanion\app\src\main\java\tw\edu\citizenaction\soracompanion\MainActivity.kt"
```

Expected: the command points to the current student home entry point and the reusable blocks it already depends on.

- [ ] **Step 2: Add a focused home rhythm block**

Implement a helper shaped like:

```kotlin
private fun todayRhythmCard(): View {
    val box = ui.sectionBand(ColorToken.Card)
    box.addView(ui.eyebrow("Today"))
    box.addView(ui.label("今日節奏", 22, ColorToken.Ink, true))
    box.addView(ui.body("依照你現在的狀態，先用低壓任務開始。", ColorToken.Muted))
    box.addView(metricRow(
        Metric("心情", mood.label, mood.color),
        Metric("分鐘", "${minutes} 分", ColorToken.Accent),
        Metric("信心", "$confidence%", ColorToken.Success)
    ))
    box.addView(ui.primaryButton("開始今日任務") { renderTaskQueue() })
    return ui.margins(box, 0, 8, 0, 16)
}
```

Adjust the exact Chinese copy to match existing app wording and fit the available prototype data.

- [ ] **Step 3: Add dual-track entry presentation**

Implement reusable helpers shaped like:

```kotlin
private fun trackEntry(
    label: String,
    title: String,
    detail: String,
    color: String,
    actionText: String,
    action: () -> Unit
): View {
    val box = ui.container(color, ColorToken.Border)
    box.addView(ui.statusPill(label, ColorToken.Primary))
    box.addView(ui.label(title, 18, ColorToken.Ink, true))
    box.addView(ui.body(detail, "#334155"))
    box.addView(ui.secondaryButton(actionText) { action() })
    return ui.margins(box, 0, 8, 0, 8)
}
```

Use one learning track entry and one support track entry in `studentHome()`.

- [ ] **Step 4: Reorder the student home**

Update `studentHome()` so the screen order becomes:

```kotlin
todayRhythmCard()
dualTrack section
contract/progress reminder
next-step task cards
support message
secondary destinations lower in the scroll
```

Expected: the first scroll segment feels like a guided student landing page, not a feature dump.

- [ ] **Step 5: Run a compile check after the home restructuring**

Run after syncing to the buildable Android Studio project:

```powershell
$env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'
$env:PATH="$env:JAVA_HOME\bin;$env:PATH"
.\gradlew.bat :app:assembleDebug
```

Expected: `BUILD SUCCESSFUL`.

## Task 2: Extend The Companion Tone Into Mood Check-In

**Files:**
- Modify: `D:\公民行動\android\SoraCompanion\app\src\main\java\tw\edu\citizenaction\soracompanion\MainActivity.kt`
- Modify: `D:\公民行動\android\SoraCompanion\app\src\main\java\tw\edu\citizenaction\soracompanion\ui\UiKit.kt`

- [ ] **Step 1: Add a stronger selection surface if the existing card helper is insufficient**

If the current `choiceCard()` is too plain, extend it to show:

```kotlin
private fun moodChoiceCard(title: String, subtitle: String, color: String, action: () -> Unit): View {
    val box = ui.container(ColorToken.Card, ColorToken.Border)
    box.addView(ui.statusPill("感受", color))
    box.addView(ui.label(title, 18, color, true))
    box.addView(ui.body(subtitle, ColorToken.Muted))
    box.setOnClickListener { action() }
    return ui.margins(box, 0, 8, 0, 8)
}
```

Keep long labels wrapped and avoid line-constrained horizontal rows.

- [ ] **Step 2: Refresh `renderCheckIn()`**

Update the screen order to:

```kotlin
supportive summary card
mood choice section
duration choice section
generated plan preview
primary CTA into the task flow
```

Expected: choosing a mood still updates `mood`, `minutes`, and `confidence` through the existing state path.

- [ ] **Step 3: Add a compact plan preview**

Use existing state to show:

```kotlin
card(
    "今天先這樣就好",
    "目前建議：${mood.planName}\n任務長度：${minutes} 分鐘\n完成後可以回來記下感受。",
    ColorToken.PrimarySoft
)
```

Expected: the preview is meaningful before and after a duration selection.

- [ ] **Step 4: Build after mood flow changes**

Run:

```powershell
$env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'
$env:PATH="$env:JAVA_HOME\bin;$env:PATH"
.\gradlew.bat :app:assembleDebug
```

Expected: `BUILD SUCCESSFUL`.

## Task 3: Focus The Task Queue And Lesson Surfaces

**Files:**
- Modify: `D:\公民行動\android\SoraCompanion\app\src\main\java\tw\edu\citizenaction\soracompanion\MainActivity.kt`

- [ ] **Step 1: Promote the active task in `renderTaskQueue()`**

Add a prominent current-task card using existing queue data:

```kotlin
private fun currentTaskFocus(): View {
    val task = studyTasks.first()
    val box = ui.sectionBand(ColorToken.PrimarySoft)
    box.addView(ui.statusPill("下一步", ColorToken.Accent))
    box.addView(ui.label(task.title, 22, ColorToken.Ink, true))
    box.addView(ui.body(task.reason, "#334155"))
    box.addView(ui.primaryButton("開始這個任務") { renderLesson() })
    return ui.margins(box, 0, 8, 0, 16)
}
```

Keep the remaining task list below it as supporting content.

- [ ] **Step 2: Tighten lesson support hierarchy**

Update `renderLesson()` so:

- The current question card remains dominant.
- The answer status card becomes a compact helper surface.
- Help and recovery buttons remain available but visually secondary.

Use current data:

```kotlin
wrongAttempts
lastAnswerMessage
currentQuestionIndex
questions.size
```

- [ ] **Step 3: Improve the success ending**

Replace the success screen's first plain card with a dedicated summary card or section band using:

```kotlin
completedTasks
confidence
q.explanation
```

Expected: the completion moment feels like a deliberate end state and still offers reflection, next question, and map navigation.

- [ ] **Step 4: Build after task and lesson changes**

Run:

```powershell
$env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'
$env:PATH="$env:JAVA_HOME\bin;$env:PATH"
.\gradlew.bat :app:assembleDebug
```

Expected: `BUILD SUCCESSFUL`.

## Task 4: Verification And Sync

**Files:**
- Sync: `D:\公民行動\android\SoraCompanion\app\src\main\java\tw\edu\citizenaction\soracompanion\MainActivity.kt`
- Sync: `D:\公民行動\android\SoraCompanion\app\src\main\java\tw\edu\citizenaction\soracompanion\ui\UiKit.kt`
- Modify if needed: `D:\SoraCompanion\README.md`

- [ ] **Step 1: Sync changed Kotlin files into the Android Studio project**

Run:

```powershell
Copy-Item -LiteralPath 'D:\公民行動\android\SoraCompanion\app\src\main\java\tw\edu\citizenaction\soracompanion\MainActivity.kt' -Destination 'D:\SoraCompanion\app\src\main\java\tw\edu\citizenaction\soracompanion\MainActivity.kt'
Copy-Item -LiteralPath 'D:\公民行動\android\SoraCompanion\app\src\main\java\tw\edu\citizenaction\soracompanion\ui\UiKit.kt' -Destination 'D:\SoraCompanion\app\src\main\java\tw\edu\citizenaction\soracompanion\ui\UiKit.kt'
```

- [ ] **Step 2: Run final build verification**

Run:

```powershell
$env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'
$env:PATH="$env:JAVA_HOME\bin;$env:PATH"
.\gradlew.bat :app:assembleDebug
```

Expected: `BUILD SUCCESSFUL`.

- [ ] **Step 3: Inspect the Git scope**

Run:

```powershell
& 'C:\Users\ray\AppData\Local\Programs\Git\cmd\git.exe' status --short
& 'C:\Users\ray\AppData\Local\Programs\Git\cmd\git.exe' diff --stat
```

Expected: changed files are limited to the implemented UI sources and any intentionally updated docs.

- [ ] **Step 4: Commit and push**

Run:

```powershell
& 'C:\Users\ray\AppData\Local\Programs\Git\cmd\git.exe' add app/src/main/java/tw/edu/citizenaction/soracompanion/MainActivity.kt app/src/main/java/tw/edu/citizenaction/soracompanion/ui/UiKit.kt README.md
& 'C:\Users\ray\AppData\Local\Programs\Git\cmd\git.exe' commit -m 'Build student companion UI round'
& 'C:\Users\ray\AppData\Local\Programs\Git\cmd\git.exe' push origin main
```

If `README.md` was not changed, omit it from the `git add` command.

- [ ] **Step 5: Confirm the worktree state**

Run:

```powershell
& 'C:\Users\ray\AppData\Local\Programs\Git\cmd\git.exe' status --short
```

Expected: no uncommitted changes remain after the push.
