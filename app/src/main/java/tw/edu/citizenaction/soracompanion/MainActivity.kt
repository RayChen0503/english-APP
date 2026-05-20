package tw.edu.citizenaction.soracompanion

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import tw.edu.citizenaction.soracompanion.data.PrototypeRepository
import tw.edu.citizenaction.soracompanion.model.ActionItem
import tw.edu.citizenaction.soracompanion.model.AiScenario
import tw.edu.citizenaction.soracompanion.model.AppState
import tw.edu.citizenaction.soracompanion.model.Breakpoint
import tw.edu.citizenaction.soracompanion.model.DesignPrinciple
import tw.edu.citizenaction.soracompanion.model.InterventionStep
import tw.edu.citizenaction.soracompanion.model.JourneyStep
import tw.edu.citizenaction.soracompanion.model.LearningContract
import tw.edu.citizenaction.soracompanion.model.LearningModule
import tw.edu.citizenaction.soracompanion.model.LocalAccount
import tw.edu.citizenaction.soracompanion.model.Metric
import tw.edu.citizenaction.soracompanion.model.MistakeRecord
import tw.edu.citizenaction.soracompanion.model.MentorCheck
import tw.edu.citizenaction.soracompanion.model.Mood
import tw.edu.citizenaction.soracompanion.model.OfflinePack
import tw.edu.citizenaction.soracompanion.model.HandoffPriority
import tw.edu.citizenaction.soracompanion.model.HelpRequestOption
import tw.edu.citizenaction.soracompanion.model.Question
import tw.edu.citizenaction.soracompanion.model.ReflectionPrompt
import tw.edu.citizenaction.soracompanion.model.Role
import tw.edu.citizenaction.soracompanion.model.Screen
import tw.edu.citizenaction.soracompanion.model.StudyTask
import tw.edu.citizenaction.soracompanion.model.StudentRow
import tw.edu.citizenaction.soracompanion.model.SyncRecord
import tw.edu.citizenaction.soracompanion.model.SupportMessage
import tw.edu.citizenaction.soracompanion.model.TeacherAction
import tw.edu.citizenaction.soracompanion.model.WeeklySignal
import tw.edu.citizenaction.soracompanion.state.PrototypeStateStore
import tw.edu.citizenaction.soracompanion.ui.UiKit
import tw.edu.citizenaction.soracompanion.ui.UiKit.ColorToken

class MainActivity : Activity() {
    private lateinit var root: LinearLayout
    private lateinit var ui: UiKit
    private lateinit var stateStore: PrototypeStateStore

    private var role = Role.Student
    private var screen = Screen.Home
    private var mood = Mood.Okay
    private var minutes = 5
    private var wrongAttempts = 0
    private var currentQuestionIndex = 0
    private var completedTasks = 3
    private var confidence = 46
    private var actionDoneCount = 0
    private var managedStudentCount = 5
    private var offlinePendingCount = 1
    private var selectedAccountName = "林家豪"
    private var lastAnswerMessage = "還沒有開始今日任務。"

    private val student = PrototypeRepository.student
    private val modules = PrototypeRepository.modules
    private val questions = PrototypeRepository.questions
    private val roster = PrototypeRepository.roster
    private val studyTasks = PrototypeRepository.studyTasks
    private val supportMessages = PrototypeRepository.supportMessages
    private val weeklySignals = PrototypeRepository.weeklySignals
    private val mistakeRecords = PrototypeRepository.mistakeRecords
    private val offlinePacks = PrototypeRepository.offlinePacks
    private val mentorChecks = PrototypeRepository.mentorChecks
    private val handoffPriorities = PrototypeRepository.handoffPriorities
    private val journeySteps = PrototypeRepository.journeySteps
    private val interventionSteps = PrototypeRepository.interventionSteps
    private val designPrinciples = PrototypeRepository.designPrinciples
    private val helpRequestOptions = PrototypeRepository.helpRequestOptions
    private val learningContracts = PrototypeRepository.learningContracts
    private val reflectionPrompts = PrototypeRepository.reflectionPrompts
    private val teacherActions = PrototypeRepository.teacherActions
    private val syncRecords = PrototypeRepository.syncRecords
    private val localAccounts = PrototypeRepository.localAccounts
    private val aiScenarios = PrototypeRepository.aiScenarios
    private val breakpoints: MutableList<Breakpoint> = PrototypeRepository.initialBreakpoints()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = UiKit(this)
        stateStore = PrototypeStateStore(this)
        restoreState()
        renderHome()
    }

    private fun restoreState() {
        val saved = stateStore.load()
        mood = saved.mood
        minutes = saved.minutes
        confidence = saved.confidence
        completedTasks = saved.completedTasks
        currentQuestionIndex = saved.currentQuestionIndex.coerceIn(0, questions.lastIndex)
        actionDoneCount = saved.actionDoneCount
        managedStudentCount = saved.managedStudentCount
        offlinePendingCount = saved.offlinePendingCount
        selectedAccountName = saved.selectedAccountName
    }

    private fun persistState() {
        stateStore.save(AppState(mood, minutes, confidence, completedTasks, currentQuestionIndex, actionDoneCount, managedStudentCount, offlinePendingCount, selectedAccountName))
    }

    private fun renderHome() {
        screen = Screen.Home
        shell("Sora Companion", "偏鄉學生雙軌學習平台")
        hero("先接住情緒，再修復英文斷點", "學生卡關時由 AI 即時拆小任務；真正需要人的地方，再把斷點摘要交給雲端志工或老師。")
        root.addView(roleSwitch())
        if (role == Role.Student) studentHome() else mentorHome()
        bottomNav()
    }

    private fun studentHome() {
        section("學生今日總覽")
        root.addView(metricRow(
            Metric("心情", mood.label, mood.color),
            Metric("任務時間", "${minutes} 分鐘", ColorToken.Primary),
            Metric("信心值", "$confidence%", ColorToken.Success)
        ))
        root.addView(flowStrip("心情檢測", "短任務", "AI 拆解", "志工接力"))
        root.addView(card("今日建議", "先做「${modules[1].title}」中的一題一概念任務。今天不是補完整章，而是把一個斷點修回來。", ColorToken.PrimarySoft))
        root.addView(contractPreviewCard(learningContracts.first()))
        root.addView(actionGrid(
            ActionItem("心情檢測", "調整任務難度") { renderCheckIn() },
            ActionItem("今日任務", "3-5 分鐘微練習") { renderLesson() },
            ActionItem("學習檔案", "看目標與偏好") { renderProfile() },
            ActionItem("離線任務包", "碎片時間也能學") { renderOfflinePacks() }
        ))
        root.addView(ui.secondaryButton("帳號與班級資料") { renderAccountCenter() })
        root.addView(ui.secondaryButton("查看完整使用旅程") { renderJourney() })
        section("任務佇列")
        studyTasks.take(2).forEach { root.addView(taskCard(it)) }
        root.addView(ui.secondaryButton("查看所有任務安排") { renderTaskQueue() })
        section("目前進度")
        modules.take(3).forEach { root.addView(moduleCard(it)) }
        root.addView(ui.secondaryButton("查看完整學習地圖") { renderMap() })
        section("最近陪伴")
        supportMessages.take(2).forEach { root.addView(messageCard(it)) }
    }

    private fun mentorHome() {
        section("老師/志工工作台")
        root.addView(metricRow(
            Metric("待關懷", "2 位", ColorToken.Warning),
            Metric("高風險", "1 個", ColorToken.Danger),
            Metric("可處理", "15 分鐘", ColorToken.Success)
        ))
        root.addView(flowStrip("AI 低風險", "摘要整理", "真人接力", "週報回饋"))
        root.addView(card("今日優先處理", "${student.name}｜${student.grade}｜${student.goal}\n最新訊號：${breakpoints[0].evidence}", ColorToken.WarningSoft))
        root.addView(ui.primaryButton("查看待辦處理佇列") { renderActionQueue() })
        root.addView(actionGrid(
            ActionItem("學生列表", "看誰需要接力") { renderRoster() },
            ActionItem("斷點摘要", "看 AI 已處理什麼") { renderBreakpoints() },
            ActionItem("接力優先序", "安排誰先處理") { renderHandoffBoard() },
            ActionItem("檢核指標", "對齊 OPPM 品質") { renderMentorChecks() }
        ))
        root.addView(ui.secondaryButton("學生資料管理") { renderStudentManager() })
        root.addView(ui.secondaryButton("AI 提示實驗室") { renderAiLab() })
        root.addView(ui.secondaryButton("查看產品設計原則") { renderDesignPrinciples() })
        section("本週訊號")
        weeklySignals.forEach { root.addView(signalCard(it)) }
        root.addView(ui.secondaryButton("查看週報與提案摘要") { renderWeeklyReport() })
    }

    private fun renderProfile() {
        screen = Screen.Profile
        shell("學生學習檔案", "讓老師更快理解學生，而不是只看分數")
        root.addView(card("基本資料", "${student.name}｜${student.age} 歲｜${student.location}｜${student.grade}\n目標：${student.goal}", ColorToken.PrimarySoft))
        root.addView(card("限制條件", student.constraint, ColorToken.WarningSoft))
        root.addView(card("學習偏好", student.learningStyle, ColorToken.SuccessSoft))
        root.addView(card("支持需求", student.supportNeed, ColorToken.VioletSoft))
        root.addView(ui.primaryButton("依照檔案產生今日任務") { renderTaskQueue() })
        bottomNav()
    }

    private fun renderAccountCenter() {
        screen = Screen.Account
        shell("帳號與班級資料", "本機原型版先模擬登入、角色與班級代碼")
        root.addView(card("目前使用者", "$selectedAccountName｜${roleLabel()}\n這是本機展示帳號，未連接正式登入系統。", ColorToken.PrimarySoft))
        localAccounts.forEach { root.addView(accountCard(it)) }
        root.addView(ui.secondaryButton("切換到老師/志工端") {
            role = Role.Mentor
            selectedAccountName = "王老師"
            persistState()
            renderHome()
        })
        bottomNav()
    }

    private fun roleLabel(): String = if (role == Role.Student) "學生端" else "老師/志工端"

    private fun renderLearningContract() {
        screen = Screen.Contract
        shell("今日學習契約", "先約定任務邊界，再開始學習")
        root.addView(card("為什麼需要契約？", "偏鄉學生常遇到時間零碎、挫折累積、求助成本高。學習契約讓學生知道今天只要做到哪裡，也讓老師知道平台不會用排行榜或大量題目壓迫學生。", ColorToken.PrimarySoft))
        learningContracts.forEach { root.addView(contractCard(it)) }
        root.addView(ui.primaryButton("我接受今天的低壓任務") { renderTaskQueue() })
        root.addView(ui.secondaryButton("先做心情檢測") { renderCheckIn() })
        bottomNav()
    }

    private fun renderJourney() {
        screen = Screen.Journey
        shell("完整使用旅程", "從不敢開始，到完成修復，再回到學習")
        root.addView(card("這不是一般題庫流程", "Sora Companion 的主流程不是「打開就考試」，而是先判斷學生能不能承受今天的任務，再決定 AI 或真人要在哪裡介入。", ColorToken.PrimarySoft))
        journeySteps.forEachIndexed { index, item ->
            root.addView(journeyCard(index + 1, item))
        }
        root.addView(ui.primaryButton("查看情緒斷點處理流程") { renderInterventionFlow() })
        root.addView(ui.secondaryButton("回今日任務") { renderTaskQueue() })
        bottomNav()
    }

    private fun renderInterventionFlow() {
        screen = Screen.Intervention
        shell("情緒斷點處理流程", "把挫折訊號轉成可以被修復的設計動作")
        root.addView(card("斷點不是失敗", "平台要避免學生因為一次錯題就離開，所以每個斷點都要有觸發條件、介入動作、學生看得懂的語句，以及可交給老師的證據。", ColorToken.WarningSoft))
        interventionSteps.forEach { root.addView(interventionCard(it)) }
        root.addView(ui.primaryButton("生成志工接力摘要") { renderHandoff() })
        bottomNav()
    }

    private fun renderDesignPrinciples() {
        screen = Screen.Mentor
        shell("產品設計原則", "把課程提案翻成可被檢核的功能")
        root.addView(card("v0.6 檢核方向", "這頁用來確認我們做的不是單純英文練習 app，而是面向偏鄉學生、情緒斷點與雙軌接力的學習平台。", ColorToken.PrimarySoft))
        designPrinciples.forEach { root.addView(principleCard(it)) }
        root.addView(ui.primaryButton("查看 OPPM 品質檢核") { renderMentorChecks() })
        bottomNav()
    }

    private fun renderTaskQueue() {
        screen = Screen.Lesson
        shell("今日任務佇列", "依心情、時間與斷點自動排序")
        root.addView(card("排程邏輯", "可用時間：${minutes} 分鐘｜模式：${mood.planName}\n先排低壓修復，再排挑戰題。", ColorToken.PrimarySoft))
        root.addView(ui.secondaryButton("查看今日學習契約") { renderLearningContract() })
        studyTasks.forEach { root.addView(taskCard(it)) }
        root.addView(ui.primaryButton("開始第一個任務") { renderLesson() })
        bottomNav()
    }

    private fun renderCheckIn() {
        screen = Screen.CheckIn
        shell("心情與時間檢測", "從狀態開始，而不是一進來就考試")
        section("今天的狀態")
        Mood.values().forEach { item ->
            root.addView(choiceCard(item.label, item.description, item.color) {
                mood = item
                minutes = item.defaultMinutes
                confidence = (confidence + item.confidenceDelta).coerceIn(0, 100)
                persistState()
                renderCheckIn()
            })
        }
        section("今天能用多久？")
        listOf(3, 5, 8, 12).forEach { value ->
            root.addView(ui.secondaryButton("${value} 分鐘") {
                minutes = value
                persistState()
                renderCheckIn()
            })
        }
        root.addView(card("平台調整", "模式：${mood.planName}\n任務時間：${minutes} 分鐘\n回饋方式：先提示、再修復，不用排行榜刺激。", ColorToken.PrimarySoft))
        root.addView(ui.primaryButton("產生今日任務") { renderLesson() })
        bottomNav()
    }

    private fun renderLesson() {
        screen = Screen.Lesson
        val q = questions[currentQuestionIndex]
        shell("今日短任務", "一題一概念，避免二度挫折")
        root.addView(card("任務規則", "答錯不會扣分。連續卡住時，系統會標記斷點並改派修復任務。", ColorToken.PrimarySoft))
        root.addView(questionCard(q))
        root.addView(card("即時狀態", "錯誤次數：$wrongAttempts / 3\n最近回饋：$lastAnswerMessage", ColorToken.Card))
        root.addView(ui.secondaryButton("我想直接求助") { renderHelpRequest() })
        root.addView(ui.secondaryButton("改做復原任務") { renderRecoveryMode() })
        bottomNav()
    }

    private fun renderRecoveryMode() {
        screen = Screen.Lesson
        shell("復原任務", "狀態不好時也能完成的一小步")
        root.addView(card("為什麼切換？", "當心情低落或連續答錯時，平台會把原本任務縮成更小的復原任務，避免直接退出。", ColorToken.WarningSoft))
        root.addView(card("復原任務內容", "只判斷一件事：He 要搭配 is。\n完成後就可以休息，也會算進學習地圖。", ColorToken.PrimarySoft))
        root.addView(ui.primaryButton("完成復原任務") {
            completedTasks += 1
            confidence = (confidence + 2).coerceAtMost(100)
            persistState()
            renderSuccess(questions.first())
        })
        root.addView(ui.secondaryButton("交給志工接力") { renderHelpRequest() })
        bottomNav()
    }

    private fun answer(option: String) {
        val q = questions[currentQuestionIndex]
        if (option == q.answer) {
            completedTasks += 1
            confidence = (confidence + 4).coerceAtMost(100)
            wrongAttempts = 0
            lastAnswerMessage = "答對了：${q.explanation}"
            currentQuestionIndex = (currentQuestionIndex + 1) % questions.size
            persistState()
            renderSuccess(q)
        } else {
            wrongAttempts += 1
            confidence = (confidence - 1).coerceAtLeast(0)
            lastAnswerMessage = "你選了 $option。${q.explanation}"
            persistState()
            if (wrongAttempts >= 3) {
                breakpoints.add(0, Breakpoint("今日新斷點：${q.concept}", "高", "同一題連續答錯 3 次", "AI 已停止加題並改派修復任務。", "請志工用同一概念帶 2 題，不追加作業。"))
                wrongAttempts = 0
                renderBreakpoints()
            } else {
                renderAiCoach()
            }
        }
    }

    private fun renderSuccess(q: Question) {
        screen = Screen.Lesson
        shell("完成一個微任務", "把成功經驗存回學習地圖")
        root.addView(card("做到了", "你完成第 $completedTasks 個微任務。\n${q.explanation}", ColorToken.SuccessSoft))
        root.addView(card("下一步", "系統會把這次成功記錄為「句型修復」進度，不需要一次補完整章。", ColorToken.PrimarySoft))
        root.addView(ui.primaryButton("做一個 20 秒反思") { renderReflection() })
        root.addView(ui.primaryButton("繼續下一題") { renderLesson() })
        root.addView(ui.secondaryButton("回學習地圖") { renderMap() })
        bottomNav()
    }

    private fun renderReflection() {
        screen = Screen.Reflection
        shell("課後 20 秒反思", "把完成感留下來，而不是只留下分數")
        root.addView(card("反思目的", "這不是檢討，而是讓學生把今天完成的那一小步說清楚。老師端之後看到的也會是修復證據，而不只是錯題。", ColorToken.SuccessSoft))
        reflectionPrompts.forEach { root.addView(reflectionCard(it)) }
        root.addView(ui.secondaryButton("略過，回學習地圖") { renderMap() })
        bottomNav()
    }

    private fun handleReflection(prompt: ReflectionPrompt) {
        confidence = (confidence + prompt.confidenceDelta).coerceIn(0, 100)
        lastAnswerMessage = prompt.platformResponse
        persistState()
        renderReflectionSaved(prompt)
    }

    private fun renderReflectionSaved(prompt: ReflectionPrompt) {
        screen = Screen.Reflection
        shell("反思已保存", "把今天的小進步放回週報")
        root.addView(card("學生選擇", prompt.studentChoice, ColorToken.PrimarySoft))
        root.addView(card("平台回應", prompt.platformResponse, ColorToken.SuccessSoft))
        root.addView(card("目前信心值", "$confidence%｜下次會從同一個斷點繼續，而不是直接加難度。", ColorToken.Card))
        root.addView(ui.primaryButton("回學習地圖") { renderMap() })
        bottomNav()
    }

    private fun renderAiCoach() {
        screen = Screen.AiCoach
        val q = questions[currentQuestionIndex]
        shell("AI 即時陪伴", "錯題先變小，再重新嘗試")
        root.addView(card("目前卡住的點", q.prompt, ColorToken.WarningSoft))
        root.addView(card("AI 拆解", "${q.explanation}\n\n現在只要先記住這一個規則，不需要一次背完 am / is / are。", ColorToken.VioletSoft))
        root.addView(card("低壓下一步", "回到同一題再試一次。如果第三次仍卡住，系統會自動整理給志工。", ColorToken.PrimarySoft))
        root.addView(ui.primaryButton("回題目再試一次") { renderLesson() })
        root.addView(ui.secondaryButton("交給志工接力") { renderHelpRequest() })
        bottomNav()
    }

    private fun renderHelpRequest() {
        screen = Screen.HelpRequest
        shell("主動求助", "讓學生用自己的話說出卡住的原因")
        root.addView(card("求助不是失敗", "這一步的目標是降低學生開口成本。平台會先分流：能由 AI 處理的就立即拆小，需要真人陪伴的才交給志工。", ColorToken.SuccessSoft))
        helpRequestOptions.forEach { root.addView(helpOptionCard(it)) }
        root.addView(ui.secondaryButton("我還是想先自己試一題") { renderLesson() })
        bottomNav()
    }

    private fun handleHelpRequest(option: HelpRequestOption) {
        lastAnswerMessage = option.studentText
        when (option.route) {
            "AI 先處理" -> renderAiCoach()
            "復原模式" -> {
                mood = Mood.Low
                minutes = 3
                persistState()
                renderRecoveryMode()
            }
            "離線任務" -> renderOfflinePacks()
            else -> {
                breakpoints.add(0, Breakpoint(option.reason, "高", option.studentText, option.platformAction, "志工先肯定狀態，再用同一概念做低壓陪練。"))
                renderHandoff()
            }
        }
    }

    private fun renderBreakpoints() {
        screen = Screen.Breakpoints
        shell("斷點中心", "把卡關變成可以處理的紀錄")
        root.addView(card("處理原則", "連續錯題、停留過久、重複退出都不是懲罰理由，而是需要被接住的訊號。", ColorToken.WarningSoft))
        breakpoints.forEach { root.addView(breakpointCard(it)) }
        root.addView(ui.secondaryButton("查看平台如何介入情緒斷點") { renderInterventionFlow() })
        root.addView(ui.primaryButton("生成志工接力摘要") { renderHandoff() })
        bottomNav()
    }

    private fun renderHandoff() {
        screen = Screen.Handoff
        shell("雲端志工接力", "把真人時間用在最值得的地方")
        root.addView(card("學生摘要", "${student.name}｜${student.location}｜${student.goal}\n目前心情：${mood.label}\n今日任務時間：${minutes} 分鐘", ColorToken.PrimarySoft))
        root.addView(card("斷點摘要", "${breakpoints.first().title}\n證據：${breakpoints.first().evidence}\nAI 已做：${breakpoints.first().aiAction}", ColorToken.WarningSoft))
        root.addView(card("建議陪伴語", "你願意回來做修復任務已經很好。今天我們只看一個規則，先不追完整進度。", ColorToken.SuccessSoft))
        root.addView(ui.primaryButton("查看陪伴腳本") { renderMentorScript() })
        bottomNav()
    }

    private fun renderMap() {
        screen = Screen.Map
        shell("個人化學習地圖", "固定節奏比一次衝刺更重要")
        root.addView(card("本週總覽", "完成微任務：$completedTasks\n信心值：$confidence%\n目前重點：${modules[1].title}", ColorToken.PrimarySoft))
        modules.forEach { root.addView(moduleCard(it)) }
        section("錯題修復紀錄")
        mistakeRecords.forEach { root.addView(mistakeCard(it)) }
        root.addView(ui.secondaryButton("查看離線任務包") { renderOfflinePacks() })
        section("陪伴時間線")
        supportMessages.forEach { root.addView(messageCard(it)) }
        root.addView(ui.secondaryButton("看週報") { renderWeeklyReport() })
        bottomNav()
    }

    private fun renderOfflinePacks() {
        screen = Screen.Map
        shell("離線任務包", "降低家庭網路與碎片時間限制")
        root.addView(card("設計原因", "偏鄉學生不一定有穩定網路或完整一小時。任務包以 3-8 分鐘為單位，先下載、可離線練習。", ColorToken.PrimarySoft))
        offlinePacks.forEach { root.addView(offlinePackCard(it)) }
        section("同步狀態")
        syncRecords.forEach { root.addView(syncCard(it)) }
        root.addView(ui.primaryButton("模擬同步待上傳紀錄") {
            offlinePendingCount = (offlinePendingCount - 1).coerceAtLeast(0)
            persistState()
            renderSyncCenter()
        })
        root.addView(ui.secondaryButton("查看同步中心") { renderSyncCenter() })
        root.addView(ui.primaryButton("回今日任務") { renderTaskQueue() })
        bottomNav()
    }

    private fun renderSyncCenter() {
        screen = Screen.SyncCenter
        shell("離線同步中心", "模擬網路不穩時的資料保存與補傳")
        root.addView(metricRow(
            Metric("待上傳", "${offlinePendingCount} 件", if (offlinePendingCount > 0) ColorToken.Warning else ColorToken.Success),
            Metric("任務包", "${offlinePacks.size} 組", ColorToken.Primary),
            Metric("摘要", "1 份", ColorToken.Success)
        ))
        root.addView(card("同步策略", "學生離線時仍可完成短任務；網路恢復後，微任務、反思、志工接力摘要會補傳。正式版可接 Room/Firebase，目前先用本機狀態模擬。", ColorToken.PrimarySoft))
        syncRecords.forEach { root.addView(syncCard(it)) }
        root.addView(ui.primaryButton("全部標記為已同步") {
            offlinePendingCount = 0
            persistState()
            renderSyncCenter()
        })
        bottomNav()
    }

    private fun renderRoster() {
        screen = Screen.Roster
        shell("學生列表", "讓老師先看見誰需要接力")
        roster.forEach { row -> root.addView(studentRowCard(row)) }
        root.addView(ui.primaryButton("查看 ${student.name} 的斷點") { renderBreakpoints() })
        root.addView(ui.secondaryButton("查看接力優先序") { renderHandoffBoard() })
        bottomNav()
    }

    private fun renderStudentDetail(row: StudentRow) {
        screen = Screen.StudentDetail
        shell("${row.name} 的接力資料", "用一頁讓老師知道現在要不要介入")
        root.addView(metricRow(
            Metric("風險", row.risk, if (row.risk == "高") ColorToken.Danger else ColorToken.Warning),
            Metric("狀態", if (row.risk == "低") "可自學" else "需追蹤", ColorToken.Primary),
            Metric("接力", if (row.risk == "高") "今日" else "本週", ColorToken.Success)
        ))
        root.addView(card("目前斷點", row.issue, if (row.risk == "高") ColorToken.WarningSoft else ColorToken.PrimarySoft))
        root.addView(card("最新狀態", row.status, ColorToken.Card))
        root.addView(card("老師下一步", if (row.risk == "高") "先用陪伴腳本降低挫折，再只帶同一概念兩題。" else "保留低壓任務，觀察是否願意回來完成。", ColorToken.SuccessSoft))
        root.addView(ui.primaryButton("查看接力腳本") { renderMentorScript() })
        root.addView(ui.secondaryButton("回學生列表") { renderRoster() })
        bottomNav()
    }

    private fun renderHandoffBoard() {
        screen = Screen.Mentor
        shell("接力優先序", "把有限的真人時間安排到最需要的地方")
        root.addView(card("排序規則", "高風險情緒斷點 > 連續錯題 > 重複退出 > 一般複習。AI 可處理低風險，真人處理高價值斷點。", ColorToken.PrimarySoft))
        handoffPriorities.forEach { root.addView(priorityCard(it)) }
        root.addView(ui.secondaryButton("查看待辦處理佇列") { renderActionQueue() })
        root.addView(ui.primaryButton("查看陪伴腳本") { renderMentorScript() })
        bottomNav()
    }

    private fun renderActionQueue() {
        screen = Screen.ActionQueue
        shell("待辦處理佇列", "把志工、老師、小組各自要做的事排清楚")
        root.addView(metricRow(
            Metric("待辦", "${teacherActions.size} 件", ColorToken.Warning),
            Metric("已處理", "${actionDoneCount} 件", ColorToken.Success),
            Metric("待同步", "${offlinePendingCount} 件", ColorToken.Primary)
        ))
        root.addView(card("設計目的", "老師端不只要看到學生問題，也要知道誰負責、多久內處理、下一步做什麼。這可以降低真人接力的溝通成本。", ColorToken.PrimarySoft))
        teacherActions.forEach { root.addView(teacherActionCard(it)) }
        root.addView(ui.primaryButton("標記一件待辦已處理") {
            actionDoneCount = (actionDoneCount + 1).coerceAtMost(teacherActions.size)
            persistState()
            renderActionQueue()
        })
        root.addView(ui.primaryButton("查看接力優先序") { renderHandoffBoard() })
        bottomNav()
    }

    private fun renderStudentManager() {
        screen = Screen.StudentManager
        shell("學生資料管理", "本機原型先模擬新增學生、分組與追蹤狀態")
        root.addView(metricRow(
            Metric("管理學生", "${managedStudentCount} 位", ColorToken.Primary),
            Metric("高風險", "1 位", ColorToken.Danger),
            Metric("已處理待辦", "${actionDoneCount} 件", ColorToken.Success)
        ))
        root.addView(card("目前限制", "這裡還沒有正式資料庫，但已把老師端需要的管理概念做成可操作原型：新增展示學生、切換追蹤狀態、查看接力工作。", ColorToken.PrimarySoft))
        roster.forEach { root.addView(studentRowCard(it)) }
        root.addView(ui.primaryButton("新增 1 位展示學生") {
            managedStudentCount += 1
            persistState()
            renderStudentManager()
        })
        root.addView(ui.secondaryButton("查看待辦處理佇列") { renderActionQueue() })
        bottomNav()
    }

    private fun renderAiLab() {
        screen = Screen.AiLab
        shell("AI 提示實驗室", "用本機規則模擬 AI 診斷、回饋與接力摘要")
        root.addView(card("目前狀態", "這不是正式串接 AI API，而是把未來 AI 應該輸出的三件事先做成可展示原型：診斷、學生語氣回饋、志工接力摘要。", ColorToken.WarningSoft))
        aiScenarios.forEach { root.addView(aiScenarioCard(it)) }
        root.addView(ui.primaryButton("用目前錯題生成 AI 回饋") { renderGeneratedAiFeedback() })
        bottomNav()
    }

    private fun renderGeneratedAiFeedback() {
        screen = Screen.AiLab
        val q = questions[currentQuestionIndex]
        shell("AI 生成結果模擬", "依目前題型與錯題狀態產生個人化回饋")
        root.addView(card("輸入資料", "${q.prompt}\n題型：${q.type}\n目前錯誤次數：$wrongAttempts", ColorToken.PrimarySoft))
        root.addView(card("診斷", "學生可能卡在「${q.concept}」。先不要加題，先給一個可重試的小提示。", ColorToken.WarningSoft))
        root.addView(card("給學生的話", q.repairHint, ColorToken.SuccessSoft))
        root.addView(card("給志工的摘要", "學生在「${q.concept}」出現不穩，建議只用同題型陪練 2 題，避免一次混入新規則。", ColorToken.Card))
        root.addView(ui.primaryButton("回今日任務") { renderLesson() })
        bottomNav()
    }

    private fun renderMentorScript() {
        screen = Screen.Mentor
        shell("志工陪伴腳本", "降低志工準備成本，也避免學生被再次打擊")
        root.addView(card("開場 30 秒", "先肯定：你願意回來做修復任務已經很好。今天我們只看一個規則，不看整章。", ColorToken.SuccessSoft))
        root.addView(card("引導問題", "1. He 是一個人還是很多人？\n2. 一個人通常搭配 is 還是 are？\n3. 你可以自己造一句 He is 嗎？", ColorToken.PrimarySoft))
        root.addView(card("結束紀錄", "能完成 2 題再加 They are；如果仍卡住，記錄為高優先斷點，不追加作業。", ColorToken.WarningSoft))
        root.addView(ui.primaryButton("回老師工作台") {
            role = Role.Mentor
            renderHome()
        })
        bottomNav()
    }

    private fun renderWeeklyReport() {
        screen = Screen.Report
        shell("本週學習週報", "用進步證據取代排名壓力")
        root.addView(metricRow(
            Metric("微任務", "${completedTasks} 個", ColorToken.Primary),
            Metric("斷點修復", "2 個", ColorToken.Success),
            Metric("求助", "1 次", ColorToken.Warning)
        ))
        weeklySignals.forEach { root.addView(signalCard(it)) }
        root.addView(card("給學生看的話", "你這週不是沒有進步，而是把問題縮小了。能說出 He is，就是修復英文斷層的一步。", ColorToken.SuccessSoft))
        root.addView(card("給老師/mentor 的摘要", "學生對完整測驗仍焦慮，但願意完成 3-5 分鐘任務。建議下週維持低壓短任務與志工接力。", ColorToken.PrimarySoft))
        root.addView(ui.secondaryButton("查看 OPPM 檢核指標") { renderMentorChecks() })
        bottomNav()
    }

    private fun renderMentorChecks() {
        screen = Screen.Report
        shell("OPPM 品質檢核", "把原型對齊課程與 mentor 評估")
        root.addView(card("檢核目的", "這頁不是給學生看的，而是給小組、mentor、老師確認產品方向是否符合提案目標。", ColorToken.PrimarySoft))
        mentorChecks.forEach { root.addView(mentorCheckCard(it)) }
        root.addView(card("下一步", "最需要驗證的是可執行性：志工是否願意使用摘要接力？老師是否覺得斷點紀錄有用？", ColorToken.WarningSoft))
        bottomNav()
    }

    private fun shell(title: String, subtitle: String) {
        val scroll = ScrollView(this)
        root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(ui.dp(18), ui.dp(22), ui.dp(18), ui.dp(24))
            background = ui.solid(ColorToken.Surface)
        }
        scroll.addView(root)
        setContentView(scroll)
        root.addView(ui.eyebrow("Sora Companion"))
        root.addView(ui.label(title, 28, ColorToken.Ink, true).apply { setPadding(0, ui.dp(6), 0, ui.dp(4)) })
        root.addView(ui.body(subtitle, ColorToken.Muted))
        root.addView(ui.space(12))
    }

    private fun hero(title: String, text: String) {
        val box = ui.container(ColorToken.Ink, ColorToken.Ink)
        box.addView(ui.label(title, 22, "#FFFFFF", true))
        box.addView(ui.body(text, "#D8E4FF").apply { setPadding(0, ui.dp(8), 0, 0) })
        root.addView(ui.margins(box, 0, 10, 0, 14))
    }

    private fun roleSwitch(): View {
        val row = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        row.addView(ui.chipButton("學生端", role == Role.Student) {
            role = Role.Student
            renderHome()
        })
        row.addView(ui.chipButton("老師/志工端", role == Role.Mentor) {
            role = Role.Mentor
            renderHome()
        })
        return ui.margins(row, 0, 4, 0, 12)
    }

    private fun bottomNav() {
        root.addView(ui.space(12))
        val nav = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        nav.addView(ui.chipButton("首頁", screen == Screen.Home || screen == Screen.Account) { renderHome() })
        nav.addView(ui.chipButton("任務", screen == Screen.Lesson || screen == Screen.AiCoach || screen == Screen.Contract || screen == Screen.Reflection) { renderLesson() })
        nav.addView(ui.chipButton("斷點", screen == Screen.Breakpoints || screen == Screen.Handoff || screen == Screen.Intervention || screen == Screen.HelpRequest) { renderBreakpoints() })
        nav.addView(ui.chipButton("地圖", screen == Screen.Map || screen == Screen.Report || screen == Screen.Journey || screen == Screen.StudentDetail || screen == Screen.ActionQueue || screen == Screen.StudentManager || screen == Screen.AiLab || screen == Screen.SyncCenter) { renderMap() })
        nav.addView(ui.chipButton("檔案", screen == Screen.Profile) { renderProfile() })
        root.addView(nav)
    }

    private fun actionGrid(a: ActionItem, b: ActionItem, c: ActionItem, d: ActionItem): View {
        val outer = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        val row1 = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        val row2 = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        row1.addView(actionCard(a), ui.weightParams())
        row1.addView(actionCard(b), ui.weightParams())
        row2.addView(actionCard(c), ui.weightParams())
        row2.addView(actionCard(d), ui.weightParams())
        outer.addView(row1)
        outer.addView(row2)
        return outer
    }

    private fun actionCard(item: ActionItem): View {
        val box = ui.container(ColorToken.Card, ColorToken.Border)
        box.addView(ui.label(item.title, 16, ColorToken.Ink, true))
        box.addView(ui.body(item.subtitle, ColorToken.Muted))
        box.setOnClickListener { item.action() }
        return ui.margins(box, 4, 4, 4, 4)
    }

    private fun contractPreviewCard(contract: LearningContract): View {
        val box = ui.container(ColorToken.SuccessSoft, ColorToken.Border)
        box.addView(ui.label("今日學習契約", 14, ColorToken.Success, true))
        box.addView(ui.label(contract.title, 18, ColorToken.Ink, true).apply { setPadding(0, ui.dp(6), 0, ui.dp(3)) })
        box.addView(ui.body(contract.studentPromise, "#334155"))
        box.setOnClickListener { renderLearningContract() }
        return ui.margins(box, 0, 8, 0, 10)
    }

    private fun accountCard(account: LocalAccount): View {
        val box = ui.container(if (account.displayName == selectedAccountName) ColorToken.PrimarySoft else ColorToken.Card, ColorToken.Border)
        val top = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        top.addView(ui.label(account.displayName, 17, ColorToken.Ink, true), LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))
        top.addView(ui.statusPill(account.roleLabel, if (account.roleLabel == "學生") ColorToken.Primary else ColorToken.Success))
        box.addView(top)
        box.addView(ui.body("班級/群組代碼：${account.classCode}", "#334155").apply { setPadding(0, ui.dp(7), 0, 0) })
        box.addView(ui.body(account.loginState, ColorToken.Muted))
        box.setOnClickListener {
            selectedAccountName = account.displayName
            role = if (account.roleLabel == "學生") Role.Student else Role.Mentor
            persistState()
            renderAccountCenter()
        }
        return ui.margins(box, 0, 8, 0, 8)
    }

    private fun aiScenarioCard(scenario: AiScenario): View {
        val box = ui.container(ColorToken.Card, ColorToken.Border)
        box.addView(ui.label(scenario.title, 17, ColorToken.Ink, true))
        box.addView(ui.body("輸入：${scenario.input}", ColorToken.Muted).apply { setPadding(0, ui.dp(7), 0, 0) })
        box.addView(ui.body("診斷：${scenario.diagnosis}", "#334155"))
        box.addView(ui.body("學生回饋：${scenario.feedback}", ColorToken.Primary))
        box.addView(ui.body("接力摘要：${scenario.handoffSummary}", ColorToken.Success))
        return ui.margins(box, 0, 8, 0, 8)
    }

    private fun contractCard(contract: LearningContract): View {
        val box = ui.container(ColorToken.Card, ColorToken.Border)
        box.addView(ui.label(contract.title, 18, ColorToken.Ink, true))
        box.addView(ui.divider())
        box.addView(ui.body("學生承諾：${contract.studentPromise}", "#334155"))
        box.addView(ui.body("平台承諾：${contract.platformPromise}", ColorToken.Primary).apply { setPadding(0, ui.dp(6), 0, 0) })
        box.addView(ui.body("真人接力：${contract.mentorPromise}", ColorToken.Success).apply { setPadding(0, ui.dp(6), 0, 0) })
        return ui.margins(box, 0, 8, 0, 8)
    }

    private fun reflectionCard(prompt: ReflectionPrompt): View {
        val box = ui.container(ColorToken.Card, ColorToken.Border)
        box.addView(ui.label(prompt.title, 17, ColorToken.Ink, true))
        box.addView(ui.body(prompt.studentChoice, ColorToken.Muted).apply { setPadding(0, ui.dp(6), 0, 0) })
        box.addView(ui.body("平台回應：${prompt.platformResponse}", "#334155").apply { setPadding(0, ui.dp(6), 0, 0) })
        box.setOnClickListener { handleReflection(prompt) }
        return ui.margins(box, 0, 8, 0, 8)
    }

    private fun teacherActionCard(action: TeacherAction): View {
        val color = when (action.status) {
            "今日待處理" -> ColorToken.Danger
            "本週追蹤" -> ColorToken.Warning
            else -> ColorToken.Primary
        }
        val box = ui.container(if (action.status == "今日待處理") ColorToken.WarningSoft else ColorToken.Card, ColorToken.Border)
        val top = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        top.addView(ui.label(action.title, 17, ColorToken.Ink, true), LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))
        top.addView(ui.statusPill(action.status, color))
        box.addView(top)
        box.addView(ui.body("負責：${action.owner}｜期限：${action.due}", ColorToken.Muted).apply { setPadding(0, ui.dp(7), 0, 0) })
        box.addView(ui.body("證據：${action.evidence}", "#334155"))
        box.addView(ui.body("下一步：${action.nextStep}", ColorToken.Success))
        return ui.margins(box, 0, 8, 0, 8)
    }

    private fun syncCard(record: SyncRecord): View {
        val color = when (record.status) {
            "已同步" -> ColorToken.Success
            "待回覆" -> ColorToken.Warning
            else -> ColorToken.Primary
        }
        val box = ui.container(ColorToken.Card, ColorToken.Border)
        val top = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        top.addView(ui.label(record.title, 16, ColorToken.Ink, true), LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))
        top.addView(ui.statusPill(record.status, color))
        box.addView(top)
        box.addView(ui.body(record.detail, "#334155").apply { setPadding(0, ui.dp(7), 0, 0) })
        return ui.margins(box, 0, 8, 0, 8)
    }

    private fun flowStrip(vararg steps: String): View {
        val box = ui.container(ColorToken.Card, ColorToken.Border)
        box.addView(ui.label("今日服務路徑", 14, ColorToken.Muted, true))
        val row = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        steps.forEachIndexed { index, step ->
            val node = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                setPadding(ui.dp(5), ui.dp(8), ui.dp(5), ui.dp(6))
            }
            node.addView(ui.statusPill("${index + 1}", if (index <= 1) ColorToken.Primary else ColorToken.Success))
            node.addView(ui.label(step, 12, ColorToken.Ink, true).apply {
                gravity = Gravity.CENTER
                setPadding(0, ui.dp(5), 0, 0)
            })
            row.addView(node, ui.weightParams())
        }
        box.addView(row)
        return ui.margins(box, 0, 8, 0, 10)
    }

    private fun questionCard(question: Question): View {
        val box = ui.container(ColorToken.Card, ColorToken.Border)
        box.addView(ui.label("題目 ${currentQuestionIndex + 1} / ${questions.size}", 14, ColorToken.Muted, true))
        box.addView(ui.label(question.prompt, 30, ColorToken.Ink, true).apply {
            gravity = Gravity.CENTER
            setPadding(0, ui.dp(14), 0, ui.dp(14))
        })
        question.options.forEach { option ->
            box.addView(ui.secondaryButton(option) { answer(option) })
        }
        return ui.margins(box, 0, 8, 0, 12)
    }

    private fun moduleCard(module: LearningModule): View {
        val box = ui.container(ColorToken.Card, ColorToken.Border)
        val top = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        top.addView(ui.label(module.title, 17, ColorToken.Ink, true), LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))
        top.addView(ui.statusPill(module.status, ColorToken.Primary))
        box.addView(top)
        box.addView(ui.body(module.subtitle, ColorToken.Muted))
        box.addView(ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal).apply {
            max = 100
            progress = module.progress
            setPadding(0, ui.dp(8), 0, ui.dp(6))
        })
        box.addView(ui.body("${module.progress}%｜${module.nextStep}", "#334155"))
        return ui.margins(box, 0, 8, 0, 8)
    }

    private fun taskCard(task: StudyTask): View {
        val fill = when (task.status) {
            "今日優先" -> ColorToken.PrimarySoft
            "待解鎖" -> ColorToken.VioletSoft
            else -> ColorToken.Card
        }
        val box = ui.container(fill, ColorToken.Border)
        val top = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        top.addView(ui.label(task.title, 17, ColorToken.Ink, true), LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))
        top.addView(ui.statusPill("${task.minutes} 分鐘", ColorToken.Primary))
        box.addView(top)
        box.addView(ui.body("難度：${task.difficulty}｜${task.status}", ColorToken.Muted))
        box.addView(ui.body(task.reason, "#334155"))
        return ui.margins(box, 0, 8, 0, 8)
    }

    private fun journeyCard(index: Int, item: JourneyStep): View {
        val box = ui.container(ColorToken.Card, ColorToken.Border)
        val top = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        top.addView(ui.statusPill("0$index", ColorToken.Primary))
        top.addView(ui.label(item.stage, 18, ColorToken.Ink, true).apply {
            setPadding(ui.dp(10), 0, 0, 0)
        }, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))
        box.addView(top)
        box.addView(ui.divider())
        box.addView(ui.body("學生感受：${item.studentFeeling}", ColorToken.Muted))
        box.addView(ui.body("平台回應：${item.platformResponse}", "#334155").apply { setPadding(0, ui.dp(6), 0, 0) })
        box.addView(ui.body("接力規則：${item.handoffRule}", ColorToken.Success).apply { setPadding(0, ui.dp(6), 0, 0) })
        return ui.margins(box, 0, 8, 0, 8)
    }

    private fun interventionCard(item: InterventionStep): View {
        val box = ui.container(ColorToken.Card, ColorToken.Border)
        box.addView(ui.statusPill(item.trigger, ColorToken.Warning))
        box.addView(ui.label(item.designAction, 17, ColorToken.Ink, true).apply { setPadding(0, ui.dp(10), 0, ui.dp(4)) })
        box.addView(ui.body("學生看到：${item.studentCopy}", ColorToken.Primary))
        box.addView(ui.body("設計證據：${item.evidence}", ColorToken.Muted).apply { setPadding(0, ui.dp(6), 0, 0) })
        return ui.margins(box, 0, 8, 0, 8)
    }

    private fun principleCard(item: DesignPrinciple): View {
        val box = ui.container(ColorToken.Card, ColorToken.Border)
        box.addView(ui.label(item.title, 18, ColorToken.Ink, true))
        box.addView(ui.body(item.detail, "#334155").apply { setPadding(0, ui.dp(6), 0, 0) })
        box.addView(ui.body("原型證據：${item.productProof}", ColorToken.Success).apply { setPadding(0, ui.dp(6), 0, 0) })
        return ui.margins(box, 0, 8, 0, 8)
    }

    private fun helpOptionCard(option: HelpRequestOption): View {
        val box = ui.container(ColorToken.Card, ColorToken.Border)
        val top = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        top.addView(ui.label(option.reason, 17, ColorToken.Ink, true), LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))
        top.addView(ui.statusPill(option.route, if (option.route == "志工接力") ColorToken.Danger else ColorToken.Primary))
        box.addView(top)
        box.addView(ui.body(option.studentText, ColorToken.Muted).apply { setPadding(0, ui.dp(8), 0, 0) })
        box.addView(ui.body("平台動作：${option.platformAction}", "#334155").apply { setPadding(0, ui.dp(6), 0, 0) })
        box.setOnClickListener { handleHelpRequest(option) }
        return ui.margins(box, 0, 8, 0, 8)
    }

    private fun studentRowCard(row: StudentRow): View {
        val fill = if (row.risk == "高") ColorToken.WarningSoft else ColorToken.Card
        val riskColor = when (row.risk) {
            "高" -> ColorToken.Danger
            "中" -> ColorToken.Warning
            else -> ColorToken.Success
        }
        val box = ui.container(fill, ColorToken.Border)
        val top = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        top.addView(ui.label(row.name, 17, ColorToken.Ink, true), LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))
        top.addView(ui.statusPill("風險 ${row.risk}", riskColor))
        box.addView(top)
        box.addView(ui.body("斷點：${row.issue}", "#334155").apply { setPadding(0, ui.dp(7), 0, 0) })
        box.addView(ui.body("狀態：${row.status}", ColorToken.Muted))
        box.setOnClickListener { renderStudentDetail(row) }
        return ui.margins(box, 0, 8, 0, 8)
    }

    private fun mistakeCard(record: MistakeRecord): View {
        val box = ui.container(ColorToken.Card, ColorToken.Border)
        val top = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        top.addView(ui.label(record.concept, 16, ColorToken.Ink, true), LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))
        top.addView(ui.statusPill(record.status, if (record.status == "待接力") ColorToken.Warning else ColorToken.Primary))
        box.addView(top)
        box.addView(ui.body("錯誤型態：${record.wrongPattern}", "#334155"))
        box.addView(ui.body("修復步驟：${record.repairStep}", ColorToken.Success))
        return ui.margins(box, 0, 8, 0, 8)
    }

    private fun offlinePackCard(pack: OfflinePack): View {
        val box = ui.container(ColorToken.Card, ColorToken.Border)
        val top = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        top.addView(ui.label(pack.title, 16, ColorToken.Ink, true), LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))
        top.addView(ui.statusPill(pack.size, ColorToken.Muted))
        box.addView(top)
        box.addView(ui.body("預估時間：${pack.duration}", ColorToken.Primary))
        box.addView(ui.body(pack.content, "#334155"))
        return ui.margins(box, 0, 8, 0, 8)
    }

    private fun messageCard(message: SupportMessage): View {
        val box = ui.container(ColorToken.Card, ColorToken.Border)
        val top = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        top.addView(ui.label(message.sender, 16, ColorToken.Ink, true), LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))
        top.addView(ui.statusPill(message.time, ColorToken.Muted))
        box.addView(top)
        box.addView(ui.body(message.content, "#334155"))
        box.addView(ui.body("類型：${message.tone}", ColorToken.Success))
        return ui.margins(box, 0, 8, 0, 8)
    }

    private fun signalCard(signal: WeeklySignal): View {
        val box = ui.container(ColorToken.Card, ColorToken.Border)
        val top = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        top.addView(ui.label(signal.label, 16, ColorToken.Ink, true), LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))
        top.addView(ui.label(signal.value, 16, signal.color, true))
        box.addView(top)
        box.addView(ui.body(signal.note, ColorToken.Muted))
        return ui.margins(box, 0, 6, 0, 6)
    }

    private fun priorityCard(item: HandoffPriority): View {
        val fill = if (item.urgency == "高") ColorToken.WarningSoft else ColorToken.Card
        val box = ui.container(fill, ColorToken.Border)
        val top = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        top.addView(ui.label(item.title, 16, ColorToken.Ink, true), LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))
        top.addView(ui.statusPill(item.urgency, if (item.urgency == "高") ColorToken.Danger else ColorToken.Warning))
        box.addView(top)
        box.addView(ui.body("負責：${item.owner}", ColorToken.Muted))
        box.addView(ui.body("下一步：${item.nextAction}", ColorToken.Success))
        return ui.margins(box, 0, 8, 0, 8)
    }

    private fun mentorCheckCard(item: MentorCheck): View {
        val box = ui.container(ColorToken.Card, ColorToken.Border)
        val top = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        top.addView(ui.label(item.label, 16, ColorToken.Ink, true), LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))
        top.addView(ui.label(item.status, 14, item.color, true))
        box.addView(top)
        box.addView(ui.body(item.note, "#334155"))
        return ui.margins(box, 0, 8, 0, 8)
    }

    private fun breakpointCard(item: Breakpoint): View {
        val fill = if (item.severity == "高") ColorToken.WarningSoft else ColorToken.Card
        val box = ui.container(fill, ColorToken.Border)
        val top = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        top.addView(ui.label(item.title, 17, ColorToken.Ink, true), LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))
        top.addView(ui.statusPill(item.severity, if (item.severity == "高") ColorToken.Danger else ColorToken.Warning))
        box.addView(top)
        box.addView(ui.body("證據：${item.evidence}", "#334155"))
        box.addView(ui.body("AI：${item.aiAction}", "#475569"))
        box.addView(ui.body("真人接力：${item.mentorAction}", ColorToken.Success))
        return ui.margins(box, 0, 8, 0, 8)
    }

    private fun metricRow(a: Metric, b: Metric, c: Metric): View {
        val row = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        listOf(a, b, c).forEach { row.addView(metricCard(it), ui.weightParams()) }
        return row
    }

    private fun metricCard(metric: Metric): View {
        val box = ui.container(ColorToken.Card, ColorToken.Border)
        box.addView(ui.label(metric.label, 12, ColorToken.Muted, true))
        box.addView(ui.label(metric.value, 18, metric.color, true))
        return ui.margins(box, 3, 3, 3, 3)
    }

    private fun choiceCard(title: String, subtitle: String, color: String, action: () -> Unit): View {
        val box = ui.container(ColorToken.Card, ColorToken.Border)
        box.addView(ui.label(title, 17, color, true))
        box.addView(ui.body(subtitle, ColorToken.Muted))
        box.setOnClickListener { action() }
        return ui.margins(box, 0, 6, 0, 6)
    }

    private fun card(title: String, text: String, fill: String): View {
        val box = ui.container(fill, ColorToken.Border)
        box.addView(ui.label(title, 17, ColorToken.Ink, true))
        box.addView(ui.body(text, "#334155").apply { setPadding(0, ui.dp(7), 0, 0) })
        return ui.margins(box, 0, 8, 0, 10)
    }

    private fun section(text: String) {
        root.addView(ui.label(text, 19, ColorToken.Ink, true).apply {
            setPadding(0, ui.dp(22), 0, ui.dp(8))
        })
    }
}
