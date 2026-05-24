# Round 6 - Report Formats

This round improves the teacher report export flow.

## What Changed

- The existing text report export remains available.
- Export now also creates `english_plus_teacher_report.html`.
- The HTML report is styled for teacher reading and can be printed to PDF from a browser.
- The weekly report screen now includes a share action that sends the latest report content through Android's native share sheet.

## Current Scope

This is a prototype-level reporting flow. It avoids adding a heavy PDF/Word generation library to the Android client. The production direction is:

- Android app exports and shares lightweight report content.
- Backend or teacher dashboard generates official PDF and Word reports.
- Report upload can reuse the existing Smart Sync payload and cloud backend URL.

## Generated Files

- `english_plus_demo_report.txt`
- `english_plus_teacher_report.html`
