# Learning Flow UI Round Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Refine the English+ student task entry and answer flow so the prototype reaches a focused short-learning interaction faster.

**Architecture:** Keep the existing native Android View renderers and add focused task/lesson helper cards in `MainActivity.kt` where they improve the main action hierarchy. Reuse existing `UiKit` cards, pills, metrics, progress bars, and buttons instead of introducing a new UI layer in this round.

**Tech Stack:** Kotlin, Android Views, Gradle.

---

## File Map

- Modify `app/src/main/java/tw/edu/citizenaction/soracompanion/MainActivity.kt`
  - Refine the task queue layout and lesson-screen hierarchy.
  - Add bounded helper cards for task framing and learning-support exits if needed.
- Keep `app/src/main/java/tw/edu/citizenaction/soracompanion/ui/UiKit.kt` unchanged unless the existing primitives cannot express the new hierarchy.
- Verify through the existing debug Android build and reachable flows because this UI prototype does not yet include Android UI test scaffolding.

## Task 1: Strengthen Today's Task Entry

- [ ] Review `renderTaskQueue()`, `currentTaskFocus()`, and `taskCard()` against the approved spec.
- [ ] Make the current task card state the first action, task boundary, and why it is prioritized.
- [ ] Move supporting queue logic and learning contract cues below the first action.
- [ ] Keep later tasks and teacher-added tasks visible with secondary visual weight.

## Task 2: Refine The Lesson Work Surface

- [ ] Review `renderLesson()`, `lessonFocusCard()`, `questionCard()`, and `lessonSupportCard()`.
- [ ] Reorder the lesson content into task goal, active question, immediate state, then support exits.
- [ ] Make the question area feel like the active one-concept work surface with clearer progress and completion framing.
- [ ] Keep help request and recovery routes visible after the state feedback.

## Task 3: Verify The Learning Route

- [ ] Sync changed Kotlin and plan files into `D:\SoraCompanion`.
- [ ] Run `.\gradlew.bat :app:assembleDebug`.
- [ ] Confirm the intended flows remain reachable:
  - home -> task queue -> lesson
  - lesson -> AI support after a wrong answer
  - lesson -> help request
  - lesson -> recovery task
- [ ] Inspect Git status and diff scope.

## Task 4: Publish The Round

- [ ] Commit the verified source and plan updates.
- [ ] Push `main` to GitHub.
