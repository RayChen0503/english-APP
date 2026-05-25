package tw.edu.citizenaction.soracompanion.qa

object PilotMaterialsRenderer {
    fun renderTeacherBrief(brief: TeacherBrief): String {
        return buildString {
            appendLine("# ${brief.title}")
            appendLine()
            appendLine("## Talking Points")
            brief.talkingPoints.forEach { point ->
                appendLine("- $point")
            }
            appendLine()
            appendLine("## Demo Reminder")
            appendLine(brief.demoReminder)
        }.trimEnd()
    }

    fun renderConsentNotice(notice: ConsentNotice): String {
        return buildString {
            appendLine("# English+ Pilot Consent Notice")
            appendLine()
            notice.sections.forEach { (title, body) ->
                appendLine("## $title")
                appendLine(body)
                appendLine()
            }
            appendLine("## Public Ranking")
            if (notice.allowPublicRanking) {
                appendLine("public ranking: allowed")
            } else {
                appendLine("no public ranking; this pilot focuses on support evidence, not score comparison.")
            }
        }.trimEnd()
    }

    fun renderFeedbackForm(form: FeedbackForm): String {
        return buildString {
            appendLine("# ${form.title}")
            appendLine()
            form.questions.forEachIndexed { index, item ->
                appendLine("${index + 1}. [${item.audience}] ${item.question}")
                appendLine("   - Response type: ${item.responseType}")
            }
        }.trimEnd()
    }

    fun renderObservationSheet(sheet: ObservationSheet): String {
        return buildString {
            appendLine("# ${sheet.title}")
            appendLine()
            sheet.fields.forEach { field ->
                appendLine("- [ ] $field")
            }
        }.trimEnd()
    }

    fun renderMaterialPack(): String {
        return buildString {
            appendLine("# English+ Classroom Pilot Material Pack")
            appendLine()
            appendLine("## 1. Teacher Brief")
            appendLine(renderTeacherBrief(PilotMaterialsContract.teacherBrief()))
            appendLine()
            appendLine("## 2. Consent Notice")
            appendLine(renderConsentNotice(PilotMaterialsContract.consentNotice()))
            appendLine()
            appendLine("## 3. Feedback Form")
            appendLine(renderFeedbackForm(PilotMaterialsContract.feedbackForm()))
            appendLine()
            appendLine("## 4. Observation Sheet")
            appendLine(renderObservationSheet(PilotMaterialsContract.observationSheet()))
        }.trimEnd()
    }
}
