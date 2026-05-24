package tw.edu.citizenaction.soracompanion.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PrototypeRepositoryTest {
    @Test
    fun questionBankItemsHaveValidQuestionPayloads() {
        val items = PrototypeRepository.questionBankItems

        assertTrue("question bank should contain enough demo items", items.size >= 10)
        assertEquals("question bank ids should be unique", items.size, items.map { it.id }.distinct().size)

        items.forEach { item ->
            assertFalse("question prompt should not be blank for ${item.id}", item.question.prompt.isBlank())
            assertTrue("answer must be one of the options for ${item.id}", item.question.answer in item.question.options)
            assertFalse("concept should not be blank for ${item.id}", item.question.concept.isBlank())
            assertFalse("repair hint should not be blank for ${item.id}", item.question.repairHint.isBlank())
        }
    }

    @Test
    fun productPrototypeKeepsBothStudentAndMentorTracks() {
        assertTrue("student task track should have several short tasks", PrototypeRepository.studyTasks.size >= 4)
        assertTrue("mentor roster should contain multiple students", PrototypeRepository.roster.size >= 5)
        assertTrue("handoff priorities should exist for human relay", PrototypeRepository.handoffPriorities.isNotEmpty())
        assertTrue("collaboration messages should exist", PrototypeRepository.supportMessages.isNotEmpty())
    }

    @Test
    fun designPrinciplesCoverTheCoreProposal() {
        val principles = PrototypeRepository.designPrinciples

        assertTrue("design principles should cover at least four product rules", principles.size >= 4)
        principles.forEach { principle ->
            assertFalse("principle title should not be blank", principle.title.isBlank())
            assertFalse("principle detail should not be blank", principle.detail.isBlank())
            assertFalse("principle product proof should not be blank", principle.productProof.isBlank())
        }
    }
}
