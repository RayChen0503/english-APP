# Final Showcase Polish Design

## Goal

Polish the English+ prototype into a clearer showcase build that explains the student journey and the proposal value without adding another large feature round.

## Showcase Priority

The final pass should favor:

1. A student journey that is easy to demonstrate.
2. Strong proposal signals that are visible inside the product.
3. Consistent naming, copy, and documentation for the English+ prototype.

## Student Showcase Path

The intended demonstration path is:

1. Home shows the student's next action and two-track choice.
2. Check-in shows that emotion and available time change the task.
3. Task and lesson screens show a bounded short-learning interaction.
4. Support screens show AI-first help and prepared human handoff.
5. Map screens show progress, repair evidence, and offline continuity.

## Product Signals

The final polish should keep three ideas visible across the prototype:

- Emotion is handled before learning pressure increases.
- AI and human support form a dual-track handoff, not a replacement.
- Rural constraints such as fragmented time and unstable internet are product requirements.

## UI And Copy Direction

- Prefer student-facing product language on student screens.
- Keep report terms such as breakpoint evidence where teacher, volunteer, or evaluation screens need them.
- Make home, support, task, and map screens feel like parts of one prototype.
- Reduce duplicated explanatory cards where the same idea already appears in the active flow.
- Keep primary actions obvious and supporting exploration secondary.

## Documentation Direction

- Update README language to match the current English+ brand and visible navigation labels.
- Describe the polished prototype around the current demonstration route instead of only listing accumulated features.
- Keep technical limitations honest: local state, fake data, no real AI API, and no backend sync.

## Scope Boundaries

- Do not add a backend, real AI integration, or a new curriculum system.
- Do not redesign teacher tooling broadly in this final student-showcase pass.
- Do not introduce a new UI framework or large file-structure refactor.

## Verification

- Keep the showcase route reachable from the app.
- Keep the Android debug build passing with `:app:assembleDebug`.
- Sync source and README into `D:\SoraCompanion`.
- Commit and push the final polish after verification.
