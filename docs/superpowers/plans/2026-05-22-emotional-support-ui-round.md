# Emotional Support UI Round Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Refine the English+ student support path across mood check-in, AI companion, help request, support center, and handoff screens.

**Architecture:** Keep current native View screen renderers and add focused support helpers in `MainActivity.kt` where they clarify student-facing flow order.

**Tech Stack:** Kotlin, Android Views, Gradle.

---

## Task 1: Improve Mood Support Feedback

- [ ] Add a compact selected-state response after mood choices.
- [ ] Strengthen low-mood route language in the plan preview.
- [ ] Keep task generation and home fallback visible.

## Task 2: Make AI Companion A Support Sequence

- [ ] Replace plain AI cards with ordered support steps.
- [ ] Keep retry and human-handoff actions visible.
- [ ] Build after syncing.

## Task 3: Clarify Help Request And Handoff

- [ ] Show help request options with visible route labels and response copy.
- [ ] Rename student-facing support center wording where appropriate.
- [ ] Add a prepared-handoff summary block before teacher/volunteer details.
- [ ] Build after syncing.

## Task 4: Verify And Publish

- [ ] Sync changed Kotlin and docs files into `D:\SoraCompanion`.
- [ ] Run `.\gradlew.bat :app:assembleDebug`.
- [ ] Inspect Git scope.
- [ ] Commit and push.
