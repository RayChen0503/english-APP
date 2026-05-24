package tw.edu.citizenaction.soracompanion.ui

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DesignSystemContractTest {
    @Test
    fun colorTokensMatchEnglishPlusBrandPalette() {
        val tokens = DesignSystemContract.colorTokens()

        assertEquals("#17324D", tokens["Ink"])
        assertEquals("#1C6E74", tokens["Primary"])
        assertEquals("#F08A3C", tokens["Accent"])
        assertEquals("#D7E3DF", tokens["Border"])
        assertTrue(tokens.values.all { it.matches(Regex("#[0-9A-Fa-f]{6}")) })
    }

    @Test
    fun componentSizesRespectTouchAndSpacingRules() {
        val rules = DesignSystemContract.componentRules()

        assertEquals(52, rules.primaryButtonMinHeightDp)
        assertEquals(48, rules.secondaryButtonMinHeightDp)
        assertEquals(20, rules.cardPaddingDp)
        assertEquals(8, rules.cardRadiusDp)
        assertTrue(DesignSystemContract.isEightPointSpacing(24))
        assertFalse(DesignSystemContract.isEightPointSpacing(22))
    }

    @Test
    fun screenWidthPolicyCoversSmallAndroidPhones() {
        val policy = DesignSystemContract.screenWidthPolicy()

        assertEquals(listOf(360, 412), policy.requiredCheckWidthsDp)
        assertTrue(policy.supportsWidth(360))
        assertTrue(policy.supportsWidth(412))
        assertFalse(policy.supportsWidth(320))
    }

    @Test
    fun densityPolicySeparatesStudentAndTeacherScreens() {
        val student = DesignSystemContract.screenDensityPolicy(DesignAudience.Student)
        val teacher = DesignSystemContract.screenDensityPolicy(DesignAudience.Teacher)

        assertEquals("one primary action", student.primaryActionRule)
        assertEquals("evidence, owner, next action", teacher.primaryActionRule)
        assertTrue(student.maxCardsPerScreen < teacher.maxCardsPerScreen)
    }
}
