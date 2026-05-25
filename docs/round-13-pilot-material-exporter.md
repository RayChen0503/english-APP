# Round 13 - Pilot Material Exporter

This round completes the missing step after Round 12: rendered pilot materials can now be exported as Markdown files.

## Added

- `PilotMaterialsExporter`
- `ExportedPilotMaterial`
- Unit tests for file names, MIME type, UTF-8 Markdown content, and no-public-ranking guardrail

## Exported Files

- `english_plus_pilot_material_pack.md`
- `english_plus_teacher_brief.md`
- `english_plus_consent_notice.md`
- `english_plus_feedback_form.md`
- `english_plus_observation_sheet.md`

## Completion Check

Earlier rounds were reviewed before this work. The main gap found was that Round 12 could render material text but did not yet define a real export boundary. Round 13 closes that gap with a tested exporter.

## Next Product Step

The exporter is now ready to be connected to a visible app button or to a future teacher dashboard/share flow.
