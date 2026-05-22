# Emotional Support UI Design

## Goal

Polish the English+ emotional support path so a student can understand how the app responds when learning pressure rises.

## Support Path

The student-facing route should read as:

1. Check in with current state.
2. Receive a smaller plan or AI support when stuck.
3. Ask for help in student language.
4. Move into a prepared handoff only when human support is valuable.

## Screen Direction

### Mood Check-In

- Make the current selected state and support response more explicit.
- Give low-energy paths visible reassurance.
- Explain what English+ changes after the check-in.

### AI Companion

- Present AI support as an ordered response:
  - noticed the stuck point
  - made the concept smaller
  - offered a next path
- Keep trying again and handing off as clear student choices.

### Help Request

- Make options feel like reasons a student can say out loud.
- Make each route visible before the student chooses it.
- Lower the emotional cost of asking.

### Support Center And Handoff

- Use student-facing support language on student paths.
- Preserve breakpoint evidence where it matters for teacher or volunteer handoff.
- Show that English+ prepares the handoff instead of abandoning the student.

## Verification

- Build with `:app:assembleDebug`.
- Keep mood check-in, AI support, help request, support center, and handoff reachable.
- Sync source to `D:\SoraCompanion`, commit, and push to GitHub.
