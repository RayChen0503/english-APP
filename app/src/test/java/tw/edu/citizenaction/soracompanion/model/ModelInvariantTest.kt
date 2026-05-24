package tw.edu.citizenaction.soracompanion.model

import org.junit.Assert.assertTrue
import org.junit.Test

class ModelInvariantTest {
    @Test
    fun moodDefaultsStayWithinSafePrototypeBounds() {
        Mood.values().forEach { mood ->
            assertTrue("${mood.name} default minutes should be short", mood.defaultMinutes in 3..10)
            assertTrue("${mood.name} confidence delta should be non-negative", mood.confidenceDelta >= 0)
            assertTrue("${mood.name} color should be a hex color", mood.color.matches(Regex("^#[0-9A-Fa-f]{6}$")))
        }
    }

    @Test
    fun screenRegistryIncludesCurrentPrototypeAreas() {
        val screens = Screen.values().map { it.name }.toSet()

        assertTrue("home exists", "Home" in screens)
        assertTrue("lesson exists", "Lesson" in screens)
        assertTrue("handoff exists", "Handoff" in screens)
        assertTrue("sync center exists", "SyncCenter" in screens)
        assertTrue("question bank exists", "QuestionBank" in screens)
        assertTrue("design system exists", "DesignSystem" in screens)
    }
}
