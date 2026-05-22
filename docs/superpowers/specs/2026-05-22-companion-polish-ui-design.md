# Companion Polish UI Design

## Goal

Push the Android prototype from a clearer companion flow into a more responsive product experience by combining three design qualities:

1. A low-pressure companion tone across student screens.
2. Achievement rhythm inside task and lesson flows.
3. Stronger emotional response inside mood and support surfaces.

## Product Direction

The app should feel calm first, motivating second, and supportive whenever a student slows down.

- Calm surfaces reduce pressure and make the student journey readable.
- Progress cues show that small tasks count.
- Mood selection and support routes should visibly respond to the student instead of reading like a static form.

## Home Polish

The student home remains the main companion landing page.

- Add a compact progress rhythm that communicates the current session or today step.
- Make learning and support tracks show their current role more clearly.
- Keep the primary start action visible.
- Give the support track more explicit reassurance that checking in first is a valid path.
- Avoid adding a full dashboard or dense analytics strip.

## Mood Polish

Mood check-in becomes more responsive.

- The selected mood card should have a stronger visual state.
- Duration choices should show which task length is currently selected.
- The resulting plan preview should read as an answer to the student's choice.
- Support routing language should remain visible when the current mood is low or the student wants a slower pace.

## Task And Lesson Polish

Learning surfaces should gain a small sense of stage progression without becoming high-pressure gamification.

- Task queue should show stage or checkpoint framing for the current task.
- Lesson should show question progress and the current micro-goal.
- Success should celebrate completion, show what changed, and hint at what opens next.
- Recovery and help options stay visible and secondary.

## Shared UI Approach

Keep the existing native Android View architecture.

- Reuse `UiKit` and current screen functions.
- Prefer compact new helpers over new navigation architecture.
- Use existing state where possible:
  - `mood`
  - `minutes`
  - `confidence`
  - `completedTasks`
  - `currentQuestionIndex`
  - `wrongAttempts`
- If a selected state needs visual difference, derive it from current state rather than adding new storage.

## Verification

The implementation round should:

1. Build with `:app:assembleDebug`.
2. Keep the home, mood check-in, task queue, lesson, success, support, and mentor paths reachable.
3. Keep UI changes scoped to the present native Android prototype.
4. Sync verified source into `D:\SoraCompanion`.
5. Commit and push the round to GitHub `main`.

## Out Of Scope

- Backend changes.
- Compose migration.
- Full game mechanics, scores, streak pressure, or rankings.
- Redesigning all mentor views.
- New remote assets.
