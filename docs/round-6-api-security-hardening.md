# Round 6 - API Security Hardening

This round makes the AI security rule executable in app code.

## Added

- `AiSecurityContract` with schema version `2`.
- AI routing decisions:
  - `Proxy`: production-safe remote AI through HTTPS backend proxy.
  - `DirectOpenAiDevelopment`: development-only direct OpenAI fallback.
  - `LocalSimulation`: no remote AI call.
- Production mode blocks direct mobile OpenAI key usage.
- HTTP proxy endpoints are rejected for remote AI calls.
- AI Proxy payload now includes security metadata and never sends a mobile OpenAI key.
- AI Lab now shows the Round 6 security rule.

## Production Rule

The Android app must not hold or send a production OpenAI key. A formal launch should deploy a backend proxy, store the OpenAI key on the server, and let Android send only learning context over HTTPS.

## Current App Behavior

When the user taps live AI feedback, the app evaluates the route first. In production mode, only a valid `https://` AI Proxy can call remote AI. Otherwise, English+ falls back to local simulated support text.

## Still Needed

- Deploy the real AI proxy service.
- Add backend authentication and rate limiting.
- Add server logs that avoid storing sensitive student text longer than necessary.
