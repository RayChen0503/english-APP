package tw.edu.citizenaction.soracompanion.data

import org.junit.Assert.assertFalse
import org.junit.Test
import tw.edu.citizenaction.soracompanion.auth.AuthContract
import tw.edu.citizenaction.soracompanion.model.Mood
import tw.edu.citizenaction.soracompanion.qa.PilotMaterialsContract
import tw.edu.citizenaction.soracompanion.qa.StoreReleaseContract

class PrototypeCopyQualityTest {
    @Test
    fun visiblePrototypeCopyDoesNotContainEncodingArtifacts() {
        visibleCopy().forEach { value ->
            assertFalse("copy contains private-use artifact: $value", hasPrivateUseCharacter(value))
            assertFalse("copy contains replacement artifact: $value", value.contains('\uFFFD'))
        }
    }

    @Test
    fun roleLabelsAreReadableForInnerPilot() {
        assertFalse(hasPrivateUseCharacter(AuthContract.ROLE_STUDENT))
        assertFalse(hasPrivateUseCharacter(AuthContract.ROLE_TEACHER))
        assertFalse(hasPrivateUseCharacter(AuthContract.ROLE_VOLUNTEER))
    }

    private fun visibleCopy(): List<String> {
        val repository = PrototypeRepository
        val pilotMaterials = PilotMaterialsContract
        val storeListing = StoreReleaseContract.playStoreListing()
        return buildList {
            Mood.values().forEach {
                add(it.label)
                add(it.description)
                add(it.planName)
            }
            add(repository.student.name)
            add(repository.student.location)
            add(repository.student.goal)
            add(repository.student.constraint)
            repository.modules.forEach {
                add(it.title)
                add(it.subtitle)
                add(it.nextStep)
                add(it.status)
            }
            repository.questions.forEach {
                add(it.prompt)
                add(it.explanation)
                add(it.concept)
                add(it.repairHint)
                addAll(it.options)
            }
            repository.roster.forEach {
                add(it.name)
                add(it.issue)
                add(it.status)
            }
            repository.studyTasks.forEach {
                add(it.title)
                add(it.reason)
                add(it.status)
            }
            repository.designPrinciples.forEach {
                add(it.title)
                add(it.detail)
                add(it.productProof)
            }
            add(pilotMaterials.teacherBrief().title)
            addAll(pilotMaterials.teacherBrief().talkingPoints)
            addAll(pilotMaterials.consentNotice().sections.keys)
            addAll(pilotMaterials.consentNotice().sections.values)
            add(pilotMaterials.feedbackForm().title)
            pilotMaterials.feedbackForm().questions.forEach {
                add(it.question)
                add(it.responseType)
            }
            add(storeListing.shortDescription)
            add(storeListing.fullDescription)
        }
    }

    private fun hasPrivateUseCharacter(value: String): Boolean {
        return value.any { it in '\uE000'..'\uF8FF' }
    }
}
