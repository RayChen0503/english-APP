# Product Home UI Design

## Goal

Refresh the next Android prototype round around three connected surfaces:

1. A student-first home screen that feels like a low-pressure companion instead of a feature directory.
2. A mood check-in flow that extends the same calm, supportive language into a more deliberate emotional entry point.
3. A focused task and lesson flow that keeps learning actions clear while preserving escape hatches for support.

The design should make the app's dual-track promise visible: learning progress and emotional support are both present, but the home screen should lead with reassurance and the next useful action.

## Product Direction

The next UI round will use a **student companion** direction with a restrained **dual-track** structure.

- The student should first notice that the platform recognizes their current state.
- The primary home action should be a concrete next step for today.
- Support actions should be visible without turning the screen into a crisis dashboard.
- The learning task flow should become quieter and more focused after the home screen.
- The mentor view should keep working, but this round prioritizes the student journey and shared UI pieces touched by it.

## Screen Architecture

### Home Screen

The home screen becomes the product tone-setter.

#### Header

- Keep the app identity and role context.
- Add a warmer daily summary that combines mood, recommended task duration, and confidence state.
- Use one clear primary CTA for the next learning step.

#### Today Rhythm Block

This is the top information block after the hero treatment.

- Show current mood.
- Show the suggested task duration.
- Show a lightweight confidence or support signal.
- Use visual hierarchy that emphasizes the recommended next action more than the labels.

#### Dual-Track Entry

Expose two paths with clear labels and different emotional weight:

- **Learning track**
  - Today's task queue
  - Learning map
  - Mistake repair or current module
- **Support track**
  - Mood check-in
  - AI support or help request
  - Mentor/volunteer handoff path

These should be more intentional than the current generic action grid. The student should understand that both tracks are legitimate ways to continue.

#### Next Step Content

Only keep the most useful follow-up content on the first viewport and early scroll:

- Today's recommended task
- One contract or progress reminder
- One support message or emotional reassurance cue

Longer module lists and secondary tools should move lower in the scroll order or behind their existing destination screens.

### Mood Check-In

The mood page extends the home tone instead of reading like a settings panel.

- Open with a supportive summary of why the check-in matters.
- Present mood choices as emotionally distinct cards with stronger feedback states.
- Keep time selection simple and tappable.
- Show a resulting plan preview after selection.
- Make the CTA clearly lead back into today's task flow.

### Task And Lesson Flow

The task and lesson pages should feel more focused than the home screen.

- Highlight one current task or question at a time.
- Keep progress visible with compact progress indicators.
- Keep help, recovery, and reflection paths available but visually secondary.
- Make answer feedback and success states feel intentional, not merely appended text cards.

## Shared UI System

The current native Android View architecture remains in place.

- Continue using `UiKit` as the shared style and component layer.
- Reuse existing `MainActivity` flow functions for this round rather than changing navigation architecture.
- Introduce focused reusable pieces when they reduce repetition:
  - a track entry card or band
  - a home rhythm summary card
  - a stronger mood choice presentation
  - a focused lesson status row
- Keep spacing on the existing 4/8-point rhythm.
- Keep tap targets comfortably large.
- Preserve the calm palette from the previous UI refresh and use accent color only for the actions and state highlights that deserve attention.

## Data Flow

This round should use existing local prototype state:

- Mood
- Suggested minutes
- Confidence
- Current question index
- Task progress and action counts
- Existing repository data for tasks, support messages, modules, and contracts

No remote data, authentication redesign, AI API integration, or persistence redesign is required for this UI round.

## Error And Edge Handling

- Home should still render if the optional support content is short or existing counters are low.
- Mood check-in should remain operable before any new selection is made.
- Task and lesson cards should not hide support exits during wrong-answer or recovery states.
- UI changes should avoid layouts that depend on long text fitting into a single line.

## Verification

The implementation round should verify:

1. The Android project builds with `:app:assembleDebug`.
2. The student home, mood check-in, task queue, lesson, and success surfaces remain reachable.
3. Shared component changes do not break mentor pages that reuse the same UI helpers.
4. The changed UI source files are synced into `D:\SoraCompanion`.
5. The verified change is committed and pushed to GitHub on `main`.

## Out Of Scope

- Replacing native Android Views with Compose.
- Adding backend services, real AI calls, Firebase, or Room.
- Redesigning every mentor screen in the same round.
- Building a full illustration asset system.
- Changing the core learning content model.
