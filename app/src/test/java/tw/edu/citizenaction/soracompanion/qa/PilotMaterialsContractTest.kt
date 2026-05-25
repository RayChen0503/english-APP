package tw.edu.citizenaction.soracompanion.qa

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PilotMaterialsContractTest {
    @Test
    fun teacherBriefContainsCorePilotMessage() {
        val brief = PilotMaterialsContract.teacherBrief()

        assertEquals(11, PilotMaterialsContract.MATERIALS_SCHEMA_VERSION)
        assertTrue(brief.title.contains("English+"))
        assertTrue(brief.talkingPoints.any { it.contains("低壓") || it.contains("短任務") })
        assertTrue(brief.talkingPoints.any { it.contains("情緒斷點") || it.contains("錯題") })
        assertTrue(brief.talkingPoints.any { it.contains("志工") || it.contains("老師") })
        assertFalse(brief.talkingPoints.any { it.contains("公開排名") && it.contains("做") })
    }

    @Test
    fun consentNoticeExplainsDataUseAndOptOut() {
        val notice = PilotMaterialsContract.consentNotice()

        assertTrue(notice.sections.containsKey("資料會用在哪裡"))
        assertTrue(notice.sections.containsKey("退出方式"))
        assertTrue(notice.sections.containsKey("不公開排名"))
        assertFalse(notice.allowPublicRanking)
        assertTrue(notice.sections.getValue("資料會用在哪裡").contains("學生"))
    }

    @Test
    fun feedbackFormAsksStudentAndTeacherUsefulnessQuestions() {
        val form = PilotMaterialsContract.feedbackForm()
        val audienceSet = form.questions.map { it.audience }.toSet()

        assertTrue(audienceSet.contains("student"))
        assertTrue(audienceSet.contains("teacher"))
        assertTrue(audienceSet.contains("volunteer"))
        assertTrue(form.questions.any { it.question.contains("今天先做什麼") || it.question.contains("下一步") })
        assertFalse(form.questions.any { it.question.contains("公開排名") })
    }

    @Test
    fun observationSheetTracksFlowEvidenceWithoutGrades() {
        val sheet = PilotMaterialsContract.observationSheet()

        assertTrue(sheet.fields.contains("student returned after support"))
        assertTrue(sheet.fields.contains("teacher next action identified"))
        assertTrue(sheet.fields.contains("handoff summary understandable"))
        assertFalse(sheet.fields.contains("student rank"))
    }
}
