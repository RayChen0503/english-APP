# Local Storage Round Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add real local persistence for English+ app state and learning events.

**Architecture:** Introduce a small SQLite-backed storage layer while preserving the existing `PrototypeStateStore` API. MainActivity continues to own the prototype flow, but learning actions now also record durable event rows.

**Tech Stack:** Kotlin, Android Views, Android SQLite, Gradle.

---

## Task 1: Add Local Database

- [ ] Create `storage/EnglishPlusDatabase.kt`.
- [ ] Add `app_state` and `learning_events` tables.
- [ ] Add load/save methods for `AppState`.
- [ ] Add methods to write learning events and read a storage snapshot.

## Task 2: Wire Existing State Store

- [ ] Update `PrototypeStateStore` to read and write through SQLite.
- [ ] Keep old SharedPreferences as a one-time migration source.
- [ ] Preserve the existing `load()` and `save()` call sites.
- [ ] Add `recordEvent()` and `storageSnapshot()` for feature screens.

## Task 3: Record User Actions

- [ ] Record correct and wrong answers.
- [ ] Record reflections.
- [ ] Record help requests.
- [ ] Record sync simulation actions.

## Task 4: Show Storage Proof

- [ ] Add a storage status card in learning map.
- [ ] Add the same card to sync center.
- [ ] Update README to describe local SQLite persistence.

## Task 5: Verify And Publish

- [ ] Sync source, docs, and README into `D:\SoraCompanion`.
- [ ] Run `.\gradlew.bat :app:assembleDebug`.
- [ ] Inspect Git diff scope.
- [ ] Commit and push.
