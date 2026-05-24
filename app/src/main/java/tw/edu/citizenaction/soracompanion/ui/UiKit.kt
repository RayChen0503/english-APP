package tw.edu.citizenaction.soracompanion.ui

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import kotlin.math.roundToInt

class UiKit(private val activity: Activity) {
    object ColorToken {
        const val Ink = "#17324D"
        const val Muted = "#5F7287"
        const val Surface = "#F4F7F5"
        const val Card = "#FFFFFF"
        const val Primary = "#1C6E74"
        const val PrimarySoft = "#E4F4F1"
        const val Accent = "#F08A3C"
        const val AccentSoft = "#FFF0E5"
        const val Success = "#26734D"
        const val SuccessSoft = "#E7F6EC"
        const val Warning = "#A15A14"
        const val WarningSoft = "#FFF3DC"
        const val Danger = "#B63F4C"
        const val VioletSoft = "#F1ECFF"
        const val Border = "#D7E3DF"
    }

    fun label(text: String, size: Int, color: String, bold: Boolean = false): TextView {
        return TextView(activity).apply {
            this.text = text
            textSize = size.toFloat()
            setTextColor(Color.parseColor(color))
            if (bold) typeface = Typeface.DEFAULT_BOLD
            includeFontPadding = true
        }
    }

    fun body(text: String, color: String = "#334155"): TextView {
        return TextView(activity).apply {
            this.text = text
            textSize = 15f
            setTextColor(Color.parseColor(color))
            setLineSpacing(dp(4).toFloat(), 1.0f)
            includeFontPadding = true
        }
    }

    fun eyebrow(text: String): TextView {
        return label(text.uppercase(), 12, ColorToken.Primary, true).apply {
            letterSpacing = 0.04f
        }
    }

    fun container(fill: String = "#FFFFFF", stroke: String = "#E1E7EF"): LinearLayout {
        val rules = DesignSystemContract.componentRules()
        return LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(rules.cardPaddingDp), dp(rules.cardPaddingDp), dp(rules.cardPaddingDp), dp(rules.cardPaddingDp))
            background = rounded(fill, stroke)
            elevation = dp(2).toFloat()
        }
    }

    fun sectionBand(fill: String = ColorToken.Card): LinearLayout {
        return LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(24), dp(24), dp(24), dp(24))
            background = rounded(fill, ColorToken.Border)
            elevation = dp(1).toFloat()
        }
    }

    fun primaryButton(text: String, action: () -> Unit): Button {
        val rules = DesignSystemContract.componentRules()
        return Button(activity).apply {
            this.text = text
            textSize = 16f
            setAllCaps(false)
            setTextColor(Color.WHITE)
            background = rounded(ColorToken.Primary, ColorToken.Primary)
            minHeight = dp(rules.primaryButtonMinHeightDp)
            elevation = dp(2).toFloat()
            setOnClickListener { action() }
            layoutParams = fullWidthParams()
        }
    }

    fun secondaryButton(text: String, action: () -> Unit): Button {
        val rules = DesignSystemContract.componentRules()
        return Button(activity).apply {
            this.text = text
            textSize = 16f
            setAllCaps(false)
            setTextColor(Color.parseColor(ColorToken.Primary))
            background = rounded(ColorToken.PrimarySoft, ColorToken.Border)
            minHeight = dp(rules.secondaryButtonMinHeightDp)
            setOnClickListener { action() }
            layoutParams = fullWidthParams()
        }
    }

    fun chipButton(text: String, selected: Boolean, action: () -> Unit): Button {
        return Button(activity).apply {
            this.text = text
            textSize = 14f
            setAllCaps(false)
            setTextColor(Color.parseColor(if (selected) "#FFFFFF" else ColorToken.Muted))
            background = rounded(if (selected) ColorToken.Ink else ColorToken.Card, if (selected) ColorToken.Ink else ColorToken.Border)
            minHeight = dp(48)
            setOnClickListener { action() }
            layoutParams = weightParams()
        }
    }

    fun statusPill(text: String, color: String): TextView {
        return label(text, DesignSystemContract.componentRules().statusPillTextSp, color, true).apply {
            gravity = Gravity.CENTER
            setPadding(dp(12), dp(4), dp(12), dp(4))
            background = rounded(pillFill(color), color)
        }
    }

    fun divider(): View {
        return View(activity).apply {
            background = solid(ColorToken.Border)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(1)
            ).apply { setMargins(0, dp(16), 0, dp(16)) }
        }
    }

    fun rounded(fill: String, stroke: String): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = dp(DesignSystemContract.componentRules().cardRadiusDp).toFloat()
            setColor(Color.parseColor(fill))
            setStroke(dp(1), Color.parseColor(stroke))
        }
    }

    fun solid(fill: String): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(Color.parseColor(fill))
        }
    }

    fun margins(view: View, left: Int, top: Int, right: Int, bottom: Int): View {
        view.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { setMargins(dp(left), dp(top), dp(right), dp(bottom)) }
        return view
    }

    fun fullWidthParams(): LinearLayout.LayoutParams {
        return LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { setMargins(0, dp(8), 0, dp(8)) }
    }

    fun weightParams(): LinearLayout.LayoutParams {
        return LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).apply {
            setMargins(dp(4), dp(4), dp(4), dp(4))
        }
    }

    fun space(height: Int): View {
        return TextView(activity).apply {
            layoutParams = LinearLayout.LayoutParams(1, dp(height))
        }
    }

    fun dp(value: Int): Int = (value * activity.resources.displayMetrics.density).roundToInt()

    private fun pillFill(color: String): String = when (color) {
        ColorToken.Primary -> ColorToken.PrimarySoft
        ColorToken.Success -> ColorToken.SuccessSoft
        ColorToken.Warning -> ColorToken.WarningSoft
        ColorToken.Danger -> "#FDEBED"
        ColorToken.Accent -> ColorToken.AccentSoft
        else -> ColorToken.Card
    }
}
