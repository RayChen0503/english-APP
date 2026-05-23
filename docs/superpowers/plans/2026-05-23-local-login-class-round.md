# Local Login And Class Round Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add durable local account selection and class context to the English+ prototype.

**Architecture:** Extend the SQLite storage layer with a local account table while preserving the MainActivity-based prototype flow. Keep account switching as a local sign-in simulation that updates app state, role routing, and class display.

**Tech Stack:** Kotlin, Android Views, Android SQLite, Gradle.

---

## Task 1: Persist Demo Accounts

- [ ] Add a `local_accounts` table to `EnglishPlusDatabase`.
- [ ] Seed the table from existing `PrototypeRepository.localAccounts`.
- [ ] Expose account loading through `PrototypeStateStore`.

## Task 2: Restore Signed-In Role

- [ ] Derive the active role from the persisted selected account.
- [ ] Make role switch and account selection persist the selected account.
- [ ] Record account-switch events.

## Task 3: Improve Class Context UI

- [ ] Update account center copy to show local sign-in and class code.
- [ ] Add class context to student and teacher home.
- [ ] Make teacher class management show class code, managed students, and role context.

## Task 4: Verify And Publish

- [ ] Sync source, docs, and README into `D:\SoraCompanion`.
- [ ] Run `.\gradlew.bat :app:assembleDebug --console=plain`.
- [ ] Inspect Git scope.
- [ ] Commit and push.
