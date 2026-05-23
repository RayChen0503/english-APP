# Round 3 - Question Bank

This round moves English+ from demo-only hardcoded questions toward a formal question bank.

## What Changed

- Added `QuestionBankItem` with id, level, unit, skill, source, question, and updatedAt.
- Added SQLite table `question_bank`.
- Seeded the local database with the current demo questions plus additional A1 starter questions.
- The lesson flow now loads questions from SQLite after startup.
- Added a Question Bank Center screen with summary metrics, skill grouping, and question preview cards.
- Cloud sync payload now includes `questionBank`, so a future backend can receive/export the local bank.

## Current Scope

This is still a prototype bank, not the final school-wide content system. It creates the data structure and UI route needed for:

- graded question sets,
- teacher/backend imports,
- skill-based assignment,
- offline question packs,
- future Firebase or school backend sync.

## Next Backend Contract Direction

A future backend import can map rows into:

```json
{
  "id": "b1-u1-001",
  "level": "A1",
  "unit": "be 動詞暖身",
  "skill": "句型修復",
  "source": "school-backend",
  "prompt": "He ___ a student.",
  "options": ["am", "is", "are"],
  "answer": "is",
  "explanation": "He 是一個人，通常搭配 is。",
  "concept": "be 動詞與主詞搭配",
  "type": "句型修復",
  "repairHint": "先看主詞 He，再判斷 He 搭配 is。"
}
```
