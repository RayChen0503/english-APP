# Navigation Identity UI Design

## Goal

Give the Android prototype a clearer app identity and a more understandable navigation system while keeping the student companion tone dominant.

## Product Direction

Use a blended direction:

- **70% companion learning app**
  - calm
  - supportive
  - clear about where the student is and what to do next
- **30% youthful interaction**
  - memorable brand cues
  - livelier active states
  - stronger sense that the UI is a real app rather than a stack of cards

## Navigation Design

The bottom navigation should become a more recognizable app pattern.

- Keep five destinations.
- Use student-facing words:
  - Home
  - Tasks
  - Support
  - Map
  - Profile
- Rename the current student-facing "breakpoint" destination in navigation language to support.
- Add a stronger active destination treatment.
- Keep click targets large and labels readable.

## Identity Design

The home hero should carry a stronger Sora identity.

- Keep a supportive headline and the current state cues.
- Add a compact brand mark treatment using native shapes/text rather than remote assets.
- Let the hero visually distinguish home from ordinary content pages.
- Use accent color sparingly for youthfulness and emphasis.

## Section Awareness

Pages should make their navigation area clearer.

- Shell/header treatment should show the current app area when possible.
- Support-related pages should feel semantically grouped.
- Task, map, and profile destinations should feel different by purpose without rebuilding navigation architecture.

## Shared UI Approach

- Keep native Android Views.
- Extend `UiKit` only for reusable nav or badge primitives if useful.
- Keep layout responsive to long Chinese labels.
- Do not introduce remote imagery or new backend dependencies.

## Verification

1. Build with `:app:assembleDebug`.
2. Reach student home, task, support, map, and profile through navigation.
3. Ensure mentor paths still render with the shared shell/nav helpers.
4. Sync to `D:\SoraCompanion`, commit, and push to GitHub `main`.

## Out Of Scope

- Compose migration.
- Full icon asset system.
- Full mentor navigation redesign.
- Marketing landing-style hero screens.
