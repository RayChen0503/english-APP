# Round 7 - English+ Design System

This round turns the existing UI direction into a reusable design system for the Android prototype.

## Product Direction

English+ should feel like a low-pressure learning companion, not a test-first question bank. The interface should first answer:

1. What should the student do today?
2. Is the task emotionally safe enough to start?
3. What evidence can teachers or volunteers act on?

## Core Tokens

| Token | Value | Role |
| --- | --- | --- |
| Ink | `#17324D` | Main titles and high-priority information |
| Muted | `#5F7287` | Supporting text and low-pressure explanations |
| Surface | `#F4F7F5` | App background |
| Card | `#FFFFFF` | Content cards |
| Primary | `#1C6E74` | Main actions and current state |
| PrimarySoft | `#E4F4F1` | Calm highlighted sections |
| Accent | `#F08A3C` | Time, pace, and short-task cues |
| Success | `#26734D` | Progress and repaired learning moments |
| Warning | `#A15A14` | Follow-up or risk without panic |
| Danger | `#B63F4C` | Needs human handoff |
| Border | `#D7E3DF` | Card and component boundaries |

## Type Scale

| Element | Size | Usage |
| --- | --- | --- |
| Eyebrow | 12sp bold | Navigation area and app context |
| Body | 15sp | Student-facing explanations and card text |
| Card title | 17-20sp bold | Scan-friendly content blocks |
| Screen title | 28sp bold | One clear title per screen |

## Components

| Component | Spec | Rule |
| --- | --- | --- |
| Primary button | 52dp minimum height | Only the recommended next action |
| Secondary button | 48dp minimum height | Alternative route or back path |
| Status pill | 12sp, soft fill | Short state labels only |
| Card | 8dp radius, 20dp padding | One decision per card |
| Section band | 24dp padding | Larger grouped context |

## Layout Rules

- Use mobile-first width checks at 360dp and 412dp.
- Avoid nested cards.
- Keep cards focused on one decision: mood, task, breakpoint, handoff, or report evidence.
- Student screens should use short sentences and reduce pressure.
- Teacher and volunteer screens should emphasize evidence, owner, next action, and follow-up status.
- Buttons must not truncate Chinese labels on common Android screen widths.

## App Entry

The prototype now includes an in-app `English+ 設計系統` screen from the product design principles area and the learning map area.
