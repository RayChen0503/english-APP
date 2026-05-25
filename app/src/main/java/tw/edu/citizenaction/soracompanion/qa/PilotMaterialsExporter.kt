package tw.edu.citizenaction.soracompanion.qa

import java.io.File

data class ExportedPilotMaterial(
    val fileName: String,
    val label: String,
    val mimeType: String,
    val file: File,
    val byteSize: Long
)

object PilotMaterialsExporter {
    private const val MARKDOWN_MIME_TYPE = "text/markdown; charset=utf-8"

    fun exportAll(targetDir: File): List<ExportedPilotMaterial> {
        targetDir.mkdirs()
        require(targetDir.exists() && targetDir.isDirectory) {
            "Pilot material export target must be a directory."
        }

        return listOf(
            writeMarkdown(
                targetDir,
                fileName = "english_plus_pilot_material_pack.md",
                label = "Complete pilot material pack",
                content = PilotMaterialsRenderer.renderMaterialPack()
            ),
            writeMarkdown(
                targetDir,
                fileName = "english_plus_teacher_brief.md",
                label = "Teacher brief",
                content = PilotMaterialsRenderer.renderTeacherBrief(PilotMaterialsContract.teacherBrief())
            ),
            writeMarkdown(
                targetDir,
                fileName = "english_plus_consent_notice.md",
                label = "Consent notice",
                content = PilotMaterialsRenderer.renderConsentNotice(PilotMaterialsContract.consentNotice())
            ),
            writeMarkdown(
                targetDir,
                fileName = "english_plus_feedback_form.md",
                label = "Feedback form",
                content = PilotMaterialsRenderer.renderFeedbackForm(PilotMaterialsContract.feedbackForm())
            ),
            writeMarkdown(
                targetDir,
                fileName = "english_plus_observation_sheet.md",
                label = "Observation sheet",
                content = PilotMaterialsRenderer.renderObservationSheet(PilotMaterialsContract.observationSheet())
            )
        )
    }

    private fun writeMarkdown(
        targetDir: File,
        fileName: String,
        label: String,
        content: String
    ): ExportedPilotMaterial {
        val file = File(targetDir, fileName)
        file.writeText(content, Charsets.UTF_8)
        return ExportedPilotMaterial(
            fileName = fileName,
            label = label,
            mimeType = MARKDOWN_MIME_TYPE,
            file = file,
            byteSize = file.length()
        )
    }
}
