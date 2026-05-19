package tw.edu.citizenaction.soracompanion

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import kotlin.math.roundToInt

class MainActivity : Activity() {
    private lateinit var root: LinearLayout
    private var mood: Mood = Mood.Okay
    private var wrongAttempts = 0
    private val student = StudentSnapshot(
        name = "林家豪",
        grade = "國二｜會考英文減 C",
        streak = 4,
        progress = 0.42,
        currentBreakpoint = "be 動詞與現在式句型混淆",
        mentorNote = "連續三次在 am/is/are 選擇上卡住，AI 已嘗試用中文例句拆解。"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showHome()
    }

    private fun setScreen(title: String, subtitle: String? = null) {
        val scroll = ScrollView(this)
        root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(28), dp(20), dp(28))
            setBackgroundColor(Color.rgb(250, 251, 252))
        }
        scroll.addView(root)
        setContentView(scroll)
        root.addView(label("Sora Companion", 13, "#246BFD", true))
        root.addView(title(title))
        subtitle?.let { root.addView(body(it)) }
        root.addView(space(12))
    }

    private fun showHome() {
        setScreen(
            "偏鄉學生雙軌學習平台",
            "用 AI 即時接住卡關，再把真正需要人的部分交給雲端志工。"
        )
        root.addView(card("今天先從狀態開始", "不是一打開就考試。先確認心情，再安排今天可以完成的 3-5 分鐘任務。"))
        root.addView(primaryButton("學生入口：開始今日學習") { showMoodCheck() })
        root.addView(secondaryButton("志工/老師入口：查看斷點摘要") { showMentorDashboard() })
        root.addView(section("第一版展示重點"))
        bullet("心情檢測會影響今日任務難度。")
        bullet("連續答錯會觸發 AI 鼓勵與斷點標記。")
        bullet("志工端會看到整理後的卡關原因，而不是一堆零散紀錄。")
    }

    private fun showMoodCheck() {
        setScreen("今天的狀態如何？", "選一個最接近的狀態，平台會調整任務量與回饋語氣。")
        Mood.entries.forEach { option ->
            root.addView(secondaryButton("${option.icon}  ${option.label}") {
                mood = option
                showStudentDashboard()
            })
        }
        root.addView(backButton())
    }

    private fun showStudentDashboard() {
        setScreen("嗨，${student.name}", "${student.grade}｜今天模式：${mood.label}")
        root.addView(progressCard())
        root.addView(card("今日 3 分鐘任務", mood.taskDescription))
        root.addView(primaryButton("開始任務") { showLesson() })
        root.addView(secondaryButton("查看我的學習地圖") { showLearningMap() })
        root.addView(backButton("回首頁") { showHome() })
    }

    private fun showLesson() {
        setScreen("短任務：選出正確句子", "目標：用很小的步驟修復 be 動詞斷點。")
        root.addView(card("題目", "「他是一位學生」的英文句子應該是？"))
        root.addView(secondaryButton("He am a student.") { answer(false) })
        root.addView(secondaryButton("He is a student.") { answer(true) })
        root.addView(secondaryButton("He are a student.") { answer(false) })
        root.addView(backButton("回今日首頁") { showStudentDashboard() })
    }

    private fun answer(correct: Boolean) {
        if (correct) {
            wrongAttempts = 0
            showSuccess()
        } else {
            wrongAttempts += 1
            if (wrongAttempts >= 3) showBreakpoint() else showTryAgain()
        }
    }

    private fun showTryAgain() {
        setScreen("先別急，我們把題目拆小一點", "這不是你不會英文，而是 be 動詞還沒有被接穩。")
        root.addView(card("AI 提示", "看到 He / She / It 的時候，先想成「一個人或一個東西」，通常會搭配 is。"))
        root.addView(primaryButton("再試一次") { showLesson() })
    }

    private fun showBreakpoint() {
        setScreen("已標記一個學習斷點", "系統會先幫你降低難度，也把紀錄整理給志工老師。")
        root.addView(card("AI 鼓勵", "你已經願意試三次了，這本身就是進步。現在我們先做更小的任務：只判斷 He 要搭配哪一個 be 動詞。"))
        root.addView(card("斷點紀錄", "概念：${student.currentBreakpoint}\n狀態：連續錯 3 次\n下一步：改派一題一概念練習"))
        root.addView(primaryButton("進入修復任務") { showRecoveryTask() })
        root.addView(secondaryButton("看看老師會收到什麼") { showMentorDashboard() })
    }

    private fun showRecoveryTask() {
        setScreen("修復任務", "這次只做一件事：He 搭配哪一個 be 動詞？")
        root.addView(card("小題目", "He ___ happy."))
        root.addView(secondaryButton("is") { showSuccess() })
        root.addView(secondaryButton("are") { showTryAgain() })
        root.addView(secondaryButton("am") { showTryAgain() })
    }

    private fun showSuccess() {
        setScreen("完成一個小步驟", "今天先拿到一個可完成感，這比硬撐更重要。")
        root.addView(card("成就回饋", "你修復了 be 動詞的一個斷點。系統會把這次成功記到學習地圖。"))
        root.addView(primaryButton("看學習地圖") { showLearningMap() })
        root.addView(secondaryButton("回首頁") { showHome() })
    }

    private fun showLearningMap() {
        setScreen("我的學習地圖", "只和昨天的自己比，不用跟排行榜比。")
        root.addView(progressCard())
        root.addView(card("已穩定", "26 個基礎單字｜3 個生活句型｜2 次勇敢求助"))
        root.addView(card("正在修復", student.currentBreakpoint))
        root.addView(card("下一站", "用 3 分鐘任務練習 I / You / He 的 be 動詞搭配。"))
        root.addView(backButton("回今日首頁") { showStudentDashboard() })
    }

    private fun showMentorDashboard() {
        setScreen("志工/老師斷點摘要", "把 AI 已經接住的紀錄整理好，讓真人陪伴更精準、負擔更低。")
        root.addView(card("學生", "${student.name}｜${student.grade}\n今日心情：${mood.label}\n本週連續學習：${student.streak} 天"))
        root.addView(card("高優先斷點", "${student.currentBreakpoint}\n${student.mentorNote}"))
        root.addView(card("建議陪伴語", "先肯定他願意回來做修復任務，再用一題一概念陪他練 He is / They are。"))
        root.addView(primaryButton("模擬送出鼓勵訊息") { showMentorMessageSent() })
        root.addView(backButton("回首頁") { showHome() })
    }

    private fun showMentorMessageSent() {
        setScreen("訊息已送出", "學生端會收到一則低壓力鼓勵，而不是新的考試壓力。")
        root.addView(card("送出的訊息", "家豪，你今天已經把問題縮小到一個句型了。下一題我們只看 He is，慢慢來就好。"))
        root.addView(secondaryButton("返回志工摘要") { showMentorDashboard() })
    }

    private fun progressCard(): View {
        val percent = (student.progress * 100).roundToInt()
        return card("個人進度", "本週穩定度 $percent%｜連續學習 ${student.streak} 天\n目前不是比誰最快，而是把斷點一個一個修回來。")
    }

    private fun title(text: String) = TextView(this).apply {
        this.text = text
        textSize = 28f
        setTextColor(Color.rgb(21, 35, 58))
        typeface = Typeface.DEFAULT_BOLD
        setPadding(0, dp(6), 0, dp(8))
    }

    private fun section(text: String) = label(text, 18, "#15233A", true).apply {
        setPadding(0, dp(20), 0, dp(6))
    }

    private fun body(text: String) = TextView(this).apply {
        this.text = text
        textSize = 16f
        setTextColor(Color.rgb(71, 85, 105))
        setLineSpacing(dp(2).toFloat(), 1.0f)
    }

    private fun label(text: String, size: Int, color: String, bold: Boolean = false) = TextView(this).apply {
        this.text = text
        textSize = size.toFloat()
        setTextColor(Color.parseColor(color))
        if (bold) typeface = Typeface.DEFAULT_BOLD
    }

    private fun card(heading: String, content: String): View {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(16), dp(14), dp(16), dp(14))
            setBackgroundResource(R.drawable.card_background)
        }
        layout.addView(label(heading, 17, "#15233A", true))
        layout.addView(body(content).apply { setPadding(0, dp(6), 0, 0) })
        layout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { setMargins(0, dp(8), 0, dp(10)) }
        return layout
    }

    private fun primaryButton(text: String, onClick: () -> Unit) = Button(this).apply {
        this.text = text
        textSize = 16f
        setTextColor(Color.WHITE)
        setBackgroundResource(R.drawable.primary_button)
        setOnClickListener { onClick() }
        layoutParams = buttonParams()
    }

    private fun secondaryButton(text: String, onClick: () -> Unit) = Button(this).apply {
        this.text = text
        textSize = 16f
        setTextColor(Color.rgb(23, 70, 162))
        setBackgroundResource(R.drawable.secondary_button)
        setOnClickListener { onClick() }
        layoutParams = buttonParams()
    }

    private fun backButton(text: String = "返回", onClick: () -> Unit = { showHome() }) = secondaryButton(text, onClick)

    private fun buttonParams() = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply { setMargins(0, dp(8), 0, dp(4)) }

    private fun bullet(text: String) {
        root.addView(body("• $text").apply { setPadding(dp(4), dp(2), 0, dp(2)) })
    }

    private fun space(height: Int) = TextView(this).apply {
        layoutParams = LinearLayout.LayoutParams(1, dp(height))
        gravity = Gravity.CENTER
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).roundToInt()
}

data class StudentSnapshot(
    val name: String,
    val grade: String,
    val streak: Int,
    val progress: Double,
    val currentBreakpoint: String,
    val mentorNote: String
)

enum class Mood(val label: String, val icon: String, val taskDescription: String) {
    Good("狀態不錯", "☀", "今天可以完成 2 題基礎句型與 1 題挑戰題。"),
    Okay("普通，可以試試", "○", "今天先做 1 個短任務，答對後再決定要不要加題。"),
    Low("有點累，需要簡單一點", "·", "今天只做 1 題修復任務，先拿回可完成感。")
}
