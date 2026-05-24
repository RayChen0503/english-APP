package tw.edu.citizenaction.soracompanion.ui

enum class DesignAudience {
    Student,
    Teacher
}

data class ComponentRules(
    val primaryButtonMinHeightDp: Int,
    val secondaryButtonMinHeightDp: Int,
    val cardPaddingDp: Int,
    val cardRadiusDp: Int,
    val statusPillTextSp: Int
)

data class ScreenWidthPolicy(
    val requiredCheckWidthsDp: List<Int>,
    val minimumSupportedWidthDp: Int
) {
    fun supportsWidth(widthDp: Int): Boolean = widthDp >= minimumSupportedWidthDp
}

data class ScreenDensityPolicy(
    val audience: DesignAudience,
    val maxCardsPerScreen: Int,
    val primaryActionRule: String,
    val copyRule: String
)

object DesignSystemContract {
    const val DESIGN_SYSTEM_VERSION = 7

    fun colorTokens(): Map<String, String> {
        return linkedMapOf(
            "Ink" to UiKit.ColorToken.Ink,
            "Muted" to UiKit.ColorToken.Muted,
            "Surface" to UiKit.ColorToken.Surface,
            "Card" to UiKit.ColorToken.Card,
            "Primary" to UiKit.ColorToken.Primary,
            "PrimarySoft" to UiKit.ColorToken.PrimarySoft,
            "Accent" to UiKit.ColorToken.Accent,
            "AccentSoft" to UiKit.ColorToken.AccentSoft,
            "Success" to UiKit.ColorToken.Success,
            "SuccessSoft" to UiKit.ColorToken.SuccessSoft,
            "Warning" to UiKit.ColorToken.Warning,
            "WarningSoft" to UiKit.ColorToken.WarningSoft,
            "Danger" to UiKit.ColorToken.Danger,
            "VioletSoft" to UiKit.ColorToken.VioletSoft,
            "Border" to UiKit.ColorToken.Border
        )
    }

    fun componentRules(): ComponentRules {
        return ComponentRules(
            primaryButtonMinHeightDp = 52,
            secondaryButtonMinHeightDp = 48,
            cardPaddingDp = 20,
            cardRadiusDp = 8,
            statusPillTextSp = 12
        )
    }

    fun screenWidthPolicy(): ScreenWidthPolicy {
        return ScreenWidthPolicy(
            requiredCheckWidthsDp = listOf(360, 412),
            minimumSupportedWidthDp = 360
        )
    }

    fun screenDensityPolicy(audience: DesignAudience): ScreenDensityPolicy {
        return when (audience) {
            DesignAudience.Student -> ScreenDensityPolicy(
                audience = audience,
                maxCardsPerScreen = 5,
                primaryActionRule = "one primary action",
                copyRule = "short, low-pressure, task-first"
            )
            DesignAudience.Teacher -> ScreenDensityPolicy(
                audience = audience,
                maxCardsPerScreen = 8,
                primaryActionRule = "evidence, owner, next action",
                copyRule = "scan-friendly evidence and follow-up status"
            )
        }
    }

    fun isEightPointSpacing(valueDp: Int): Boolean {
        return valueDp > 0 && (valueDp % 8 == 0 || valueDp % 4 == 0)
    }
}
