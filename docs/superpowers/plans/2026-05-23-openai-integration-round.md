# OpenAI Integration Round Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add optional real OpenAI Responses API generation to English+ while preserving local fallback behavior.

**Architecture:** Introduce a small HTTP client around `HttpURLConnection` so the prototype does not need new SDK dependencies. Store the API key in private app preferences through `PrototypeStateStore`, expose configuration in AI Lab, and keep existing local simulation as fallback.

**Tech Stack:** Kotlin, Android Views, Android HTTP/JSON, OpenAI Responses API, Gradle.

---

## Task 1: Add API Client

- [ ] Create `ai/OpenAiClient.kt`.
- [ ] POST to `https://api.openai.com/v1/responses`.
- [ ] Send `model`, `instructions`, `input`, and `max_output_tokens`.
- [ ] Parse JSON output text into diagnosis, student feedback, and handoff summary.

## Task 2: Configure Key And Permission

- [ ] Add Android internet permission.
- [ ] Add API key save/load methods to `PrototypeStateStore`.
- [ ] Add AI Lab UI for key status and key entry.
- [ ] Ensure no key is hard-coded.

## Task 3: Wire AI Lab

- [ ] Add a real API generation button.
- [ ] Show loading state during the call.
- [ ] Show live API output if the call succeeds.
- [ ] Fall back to existing local simulation on missing key or failure.
- [ ] Record live and fallback AI events.

## Task 4: Verify And Publish

- [ ] Sync source, docs, manifest, and README into `D:\SoraCompanion`.
- [ ] Run `.\gradlew.bat :app:assembleDebug --console=plain`.
- [ ] Inspect Git diff and scan for accidental secrets.
- [ ] Commit and push.
