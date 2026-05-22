# Learning Flow UI Design

## Goal

Polish the English+ student learning flow so the app moves from "today's next action" into a focused short task without making the student scan a dashboard first.

## Product Direction

This round prioritizes the main action path:

1. Show the student what to do first.
2. Make the first task feel bounded and doable.
3. Turn the answer screen into a clear one-concept interaction.
4. Keep support and recovery exits visible when the task becomes stressful.

## Task Entry

### Today's Task Page

- Lead with a stronger current-task focus card.
- Explain why this task is first using current mood, time, and breakpoint logic.
- Keep the remaining task queue visible but visually secondary.
- Keep teacher-added low-pressure tasks visible without letting them compete with the first action.

### Task Framing

- Make the student's commitment small and explicit:
  - task duration
  - one concept
  - what completion means
- Keep the learning contract available as supporting context rather than a blocking explanation.

## Question Interaction

### Lesson Screen

- Order content as:
  1. task goal
  2. active question
  3. immediate learning state
  4. support exits
- Make the question card feel like the active work surface rather than one more generic card.
- Preserve the existing "one question, one concept" principle.

### Feedback And Support

- Keep the current confidence and stuck-state feedback readable.
- Show students that a wrong answer changes the support route, not their worth.
- Preserve direct routes to AI support, recovery task, and help request.

## Scope Boundaries

- Do not redesign the emotional support round again.
- Do not add a full curriculum or formal question bank in this round.
- Do not turn the student learning path into a metrics dashboard.

## Verification

- Keep home -> task queue -> lesson -> answer feedback reachable.
- Keep lesson -> AI support and lesson -> recovery/help exits reachable.
- Build with `:app:assembleDebug`.
- Sync the source into `D:\SoraCompanion`, commit, and push after verification.
