# Round 5 - API Security

This round moves English+ toward a safer AI architecture.

## What Changed

- Added `AiProxyClient`.
- Added an AI Proxy endpoint setting in the AI Lab.
- AI generation now prefers the backend proxy when a proxy URL is configured.
- Local OpenAI API Key mode remains only as a prototype fallback.
- The app stores the proxy URL, not the production OpenAI key.

## Proxy Request Contract

The app sends:

```json
{
  "action": "generateSupport",
  "type": "ai_support_proxy",
  "schemaVersion": 1,
  "app": "English+",
  "classCode": "YILAN-CHENGZHI-8A",
  "question": "He ___ a student.",
  "concept": "be 動詞與主詞搭配",
  "answerContext": "先看主詞 He，再判斷 He 搭配 is。",
  "moodLabel": "普通",
  "wrongAttempts": 1
}
```

Expected response:

```json
{
  "diagnosis": "學生卡在主詞和 be 動詞搭配。",
  "studentFeedback": "先看 He，He 通常搭配 is。",
  "handoffSummary": "請志工用 He is / They are 做兩題低壓練習。",
  "source": "school-ai-proxy"
}
```

The backend should hold the official OpenAI key and call OpenAI from a trusted server environment. The Android app should not ship with a production key.
