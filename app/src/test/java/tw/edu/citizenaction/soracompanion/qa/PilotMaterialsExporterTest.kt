package tw.edu.citizenaction.soracompanion.qa

import java.nio.file.Files
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PilotMaterialsExporterTest {
    @Test
    fun exportsCompletePilotMaterialSet() {
        val targetDir = Files.createTempDirectory("english-plus-pilot-materials").toFile()
        try {
            val exported = PilotMaterialsExporter.exportAll(targetDir)

            assertEquals(5, exported.size)
            assertEquals(
                listOf(
                    "english_plus_pilot_material_pack.md",
                    "english_plus_teacher_brief.md",
                    "english_plus_consent_notice.md",
                    "english_plus_feedback_form.md",
                    "english_plus_observation_sheet.md"
                ),
                exported.map { it.fileName }
            )
            exported.forEach { material ->
                assertEquals("text/markdown; charset=utf-8", material.mimeType)
                assertTrue(material.file.exists())
                assertTrue(material.byteSize > 100)
                assertTrue(material.file.readText(Charsets.UTF_8).contains("English+"))
            }
        } finally {
            targetDir.deleteRecursively()
        }
    }

    @Test
    fun exportedConsentNoticeKeepsNoPublicRankingGuardrail() {
        val targetDir = Files.createTempDirectory("english-plus-consent").toFile()
        try {
            val exported = PilotMaterialsExporter.exportAll(targetDir)
            val consent = exported.first { it.fileName == "english_plus_consent_notice.md" }
                .file
                .readText(Charsets.UTF_8)

            assertTrue(consent.contains("no public ranking"))
            assertFalse(consent.contains("public ranking: allowed"))
        } finally {
            targetDir.deleteRecursively()
        }
    }
}
