# Navigation Identity UI Round Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Make the prototype feel like a recognizable companion learning app through stronger home identity and clearer bottom navigation.

**Architecture:** Keep the current native Android View renderer and use focused helper changes in `MainActivity.kt` and `UiKit.kt`. Map existing screens into navigation areas instead of redesigning screen routing.

**Tech Stack:** Kotlin, Android Views, Gradle, GitHub `main`.

---

## Task 1: Add Navigation Area Semantics

- [ ] Inspect `bottomNav()`, `shell()`, `Screen`, and navigation target render methods.
- [ ] Add an area-label helper that derives the current app area from `screen`.
- [ ] Surface that area in header or nav presentation without adding duplicate page titles.
- [ ] Keep support pages grouped under student-facing "支持".

## Task 2: Refresh Bottom Navigation

- [ ] Replace plain chip-row nav with destination cells using:
  - short label
  - compact symbolic mark
  - stronger active fill/border/text state
- [ ] Keep five destinations:
  - `首頁`
  - `任務`
  - `支持`
  - `地圖`
  - `檔案`
- [ ] Keep tap areas comfortably large and avoid text overflow.
- [ ] Build after syncing to Android Studio project.

## Task 3: Strengthen Home Identity

- [ ] Add a compact native brand mark treatment to the home hero.
- [ ] Keep current minutes/confidence cues.
- [ ] Make home hero look more distinctive than standard content headers.
- [ ] Avoid external assets and avoid overly playful decoration.
- [ ] Build after syncing.

## Task 4: Verify And Publish

- [ ] Sync changed Kotlin files and these docs to `D:\SoraCompanion`.
- [ ] Run:

```powershell
$env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'
$env:PATH="$env:JAVA_HOME\bin;$env:PATH"
.\gradlew.bat :app:assembleDebug
```

- [ ] Inspect Git status and diff summary.
- [ ] Commit with:

```powershell
& 'C:\Users\ray\AppData\Local\Programs\Git\cmd\git.exe' commit -m 'Shape navigation and home identity'
```

- [ ] Push to GitHub `main`.
