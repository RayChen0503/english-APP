# OpenAI Integration Design

## Goal

Replace the purely local AI simulation with an optional real OpenAI Responses API call while keeping the prototype safe to run without a key.

## API Choice

Use the OpenAI Responses API with text input and text output.

Default model: `gpt-5.4-mini`.

Reasoning:

- It is suitable for lower-latency and lower-cost interactive feedback.
- The app only needs short text: diagnosis, student-facing encouragement, and volunteer handoff summary.
- The existing AI Lab can keep local simulation as a fallback.

## User-Facing Behavior

- AI Lab shows whether a local OpenAI API key is configured.
- Teacher or presenter can paste an API key into the app for local testing.
- If a key exists, AI Lab can call the real API.
- If no key exists or the network/API call fails, the prototype falls back to the existing local AI simulation.
- Generated AI output is recorded as a learning event.

## Safety And Scope

- Do not hard-code any API key.
- Do not commit secrets.
- Store the key only in local private app preferences for prototype use.
- Keep prompts short and focused on education support.
- Do not add streaming, tool calling, backend proxy, or cloud auth in this round.

## Verification

- Build with `:app:assembleDebug`.
- Confirm the app compiles with Android internet permission.
- Confirm no API key appears in source files.
- Confirm AI Lab still works without an API key through local fallback.
