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
        const val Ink = "#14213D"
        const val Muted = "#64748B"
        const val Surface = "#F6F8FB"
        const val Card = "#FFFFFF"
        const val Primary = "#2457D6"
        const val PrimarySoft = "#EAF1FF"
        const val Success = "#0F766E"
        const val SuccessSoft = "#EAF8F5"
        const val Warning = "#B45309"
        const val WarningSoft = "#FFF7ED"
        const val Danger = "#B91C1C"
        const val VioletSoft = "#F6F0FF"
        const val Border = "#DDE5EF"
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
            setLineSpacing(dp(3).toFloat(), 1.0f)
            includeFontPadding = true
        }
    }

    fun eyebrow(text: String): TextView {
        return label(text.uppercase(), 12, ColorToken.Primary, true).apply {
            letterSpacing = 0.08f
        }
    }

    fun container(fill: String = "#FFFFFF", stroke: String = "#E1E7EF"): LinearLayout {
        return LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(18), dp(16), dp(18), dp(16))
            background = rounded(fill, stroke)
        }
    }

    fun sectionBand(fill: String = ColorToken.Card): LinearLayout {
        return LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(18), dp(18), dp(18), dp(18))
            background = rounded(fill, ColorToken.Border)
        }
    }

    fun primaryButton(text: String, action: () -> Unit): Button {
        return Button(activity).apply {
            this.text = text
            textSize = 16f
            setAllCaps(false)
            setTextColor(Color.WHITE)
            background = rounded(ColorToken.Primary, ColorToken.Primary)
            minHeight = dp(48)
            setOnClickListener { action() }
            layoutParams = fullWidthParams()
        }
    }

    fun secondaryButton(text: String, action: () -> Unit): Button {
        return Button(activity).apply {
            this.text = text
            textSize = 16f
            setAllCaps(false)
            setTextColor(Color.parseColor(ColorToken.Primary))
            background = rounded(ColorToken.PrimarySoft, "#B8CCF9")
            minHeight = dp(46)
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
            background = rounded(if (selected) ColorToken.Ink else "#FFFFFF", ColorToken.Border)
            minHeight = dp(42)
            setOnClickListener { action() }
            layoutParams = weightParams()
        }
    }

    fun statusPill(text: String, color: String): TextView {
        return label(text, 12, color, true).apply {
            gravity = Gravity.CENTER
            setPadding(dp(10), dp(4), dp(10), dp(4))
            background = rounded("#FFFFFF", color)
        }
    }

    fun divider(): View {
        return View(activity).apply {
            background = solid(ColorToken.Border)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(1)
            ).apply { setMargins(0, dp(12), 0, dp(12)) }
        }
    }

    fun rounded(fill: String, stroke: String): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = dp(14).toFloat()
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
        ).apply { setMargins(0, dp(7), 0, dp(5)) }
    }

    fun weightParams(): LinearLayout.LayoutParams {
        return LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).apply {
            setMargins(dp(3), dp(3), dp(3), dp(3))
        }
    }

    fun space(height: Int): View {
        return TextView(activity).apply {
            layoutParams = LinearLayout.LayoutParams(1, dp(height))
        }
    }

    fun dp(value: Int): Int = (value * activity.resources.displayMetrics.density).roundToInt()
}
