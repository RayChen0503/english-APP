# Local Login And Class Design

## Goal

Add a functional local login and class layer for English+ so the prototype moves beyond a role toggle while staying ready for a later cloud-auth round.

## First Slice

This round stores and uses:

1. Local demo accounts for student, volunteer, and teacher roles.
2. Class or group codes attached to each account.
3. The current signed-in account as part of persistent app state.
4. A teacher-facing class management proof screen.

## Approach

Use the existing SQLite layer from the local storage round.

The app will still avoid real passwords and cloud identity in this round. Instead, selecting an account becomes a durable local sign-in action. The selected account determines the visible role and class context.

## User-Facing Behavior

- Account center should say "currently signed in" instead of only "current user".
- Tapping a demo account should persist the selected account and role.
- Student and teacher home should reflect the current account.
- Teacher/class screens should show class code and managed student count.
- Account switching should record a local learning/system event for traceability.

## Scope Boundaries

- Do not add password fields, OAuth, Firebase Auth, or server calls.
- Do not build full multi-class administration yet.
- Do not remove demo accounts; they remain useful for classroom presentation.

## Verification

- Build with `:app:assembleDebug`.
- Confirm account selection, role routing, class code display, and persisted selected account still compile through the same app state path.
