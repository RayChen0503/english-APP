package tw.edu.citizenaction.soracompanion.data

import tw.edu.citizenaction.soracompanion.auth.AuthContract
import tw.edu.citizenaction.soracompanion.model.AiScenario
import tw.edu.citizenaction.soracompanion.model.Breakpoint
import tw.edu.citizenaction.soracompanion.model.DesignPrinciple
import tw.edu.citizenaction.soracompanion.model.HandoffPriority
import tw.edu.citizenaction.soracompanion.model.HelpRequestOption
import tw.edu.citizenaction.soracompanion.model.InterventionStep
import tw.edu.citizenaction.soracompanion.model.JourneyStep
import tw.edu.citizenaction.soracompanion.model.LearningContract
import tw.edu.citizenaction.soracompanion.model.LearningModule
import tw.edu.citizenaction.soracompanion.model.LocalAccount
import tw.edu.citizenaction.soracompanion.model.MentorCheck
import tw.edu.citizenaction.soracompanion.model.MistakeRecord
import tw.edu.citizenaction.soracompanion.model.OfflinePack
import tw.edu.citizenaction.soracompanion.model.Question
import tw.edu.citizenaction.soracompanion.model.QuestionBankItem
import tw.edu.citizenaction.soracompanion.model.ReflectionPrompt
import tw.edu.citizenaction.soracompanion.model.StudentProfile
import tw.edu.citizenaction.soracompanion.model.StudentRow
import tw.edu.citizenaction.soracompanion.model.StudyTask
import tw.edu.citizenaction.soracompanion.model.SupportMessage
import tw.edu.citizenaction.soracompanion.model.SyncRecord
import tw.edu.citizenaction.soracompanion.model.TeacherAction
import tw.edu.citizenaction.soracompanion.model.WeeklySignal

object PrototypeRepository {
    val student = StudentProfile(
        name = "小安",
        age = 14,
        location = "宜蘭偏鄉",
        grade = "八年級",
        goal = "英文從 C 慢慢拉回 B",
        constraint = "家裡網路不穩，完整測驗容易焦慮",
        mentor = "志工 Emily",
        learningStyle = "需要短任務、明確提示和可被接住的錯誤回饋",
        supportNeed = "先降低挫折，再把真正需要人的地方交給老師或志工"
    )

    val modules = listOf(
        LearningModule("be 動詞暖身", "am / is / are 的基本判斷", 68, "再練 2 題主詞搭配", "進行中"),
        LearningModule("短句閱讀", "從一句英文找出主詞與動作", 42, "先看關鍵字，不急著翻整句", "待修復"),
        LearningModule("生活單字", "學校、家庭、時間相關字彙", 35, "用圖片或例句記一個詞", "暖身"),
        LearningModule("口語回應", "Thank you / Sorry / I am fine", 31, "先選情境，再選回應", "可挑戰"),
        LearningModule("錯題復原", "把常錯規則整理成下一步", 18, "只做一個 3 分鐘修復任務", "低壓")
    )

    val questions = listOf(
        Question("He ___ a student.", listOf("am", "is", "are"), "is", "He 是第三人稱單數，要搭配 is。", "be 動詞：He/She/It + is", repairHint = "先看主詞 He，再選第三人稱單數的 is。"),
        Question("They ___ my friends.", listOf("is", "are", "am"), "are", "They 是複數主詞，要搭配 are。", "be 動詞：複數 + are", repairHint = "They 表示很多人，先排除 is 和 am。"),
        Question("I ___ ready.", listOf("am", "is", "are"), "am", "I 固定搭配 am。", "be 動詞：I + am", repairHint = "看到 I，先想 I am。"),
        Question("She ___ happy today.", listOf("are", "am", "is"), "is", "She 和 He 一樣，要搭配 is。", "be 動詞：She + is", repairHint = "She 是一個人，第三人稱單數用 is。"),
        Question("We ___ in the classroom.", listOf("is", "are", "am"), "are", "We 是複數主詞，要搭配 are。", "be 動詞：We + are", repairHint = "We 表示我們，通常搭配 are。"),
        Question("The boy is reading a book. 這句的主詞是？", listOf("The boy", "reading", "a book"), "The boy", "主詞是做動作的人，這裡是 The boy。", "閱讀：找主詞", repairHint = "先問：誰正在 reading？答案是 The boy。"),
        Question("Thank you. 最自然的回應是？", listOf("You're welcome.", "Good night.", "I'm sorry."), "You're welcome.", "別人說謝謝時，可以回 You're welcome。", "口語：感謝回應", repairHint = "看到 Thank you，就想 You're welcome。"),
        Question("usually 的意思比較接近？", listOf("通常", "從不", "明天"), "通常", "usually 表示通常、經常。", "單字：頻率副詞", repairHint = "usually 是頻率字，表示事情常常發生。")
    )

    val questionBankItems = listOf(
        QuestionBankItem("b1-u1-001", "A1", "be 動詞暖身", "文法", "English+ seed", questions[0], reviewState = "approved"),
        QuestionBankItem("b1-u1-002", "A1", "be 動詞暖身", "文法", "English+ seed", questions[1], reviewState = "approved"),
        QuestionBankItem("b1-u1-003", "A1", "be 動詞暖身", "文法", "English+ seed", questions[2], reviewState = "approved"),
        QuestionBankItem("b1-u1-004", "A1", "be 動詞暖身", "文法", "English+ seed", questions[3], reviewState = "approved"),
        QuestionBankItem("b1-u1-005", "A1", "be 動詞暖身", "文法", "English+ seed", questions[4], reviewState = "approved"),
        QuestionBankItem("r1-u1-001", "A1", "短句閱讀", "閱讀", "English+ seed", questions[5]),
        QuestionBankItem("s1-u1-001", "A1", "口語回應", "口說", "English+ seed", questions[6]),
        QuestionBankItem("v1-u1-001", "A1", "頻率副詞", "單字", "English+ seed", questions[7]),
        QuestionBankItem("b1-u2-001", "A1", "be 動詞延伸", "文法", "English+ seed", Question("My brother ___ tall.", listOf("is", "are", "am"), "is", "My brother 是單數主詞，要搭配 is。", "be 動詞：單數 + is", repairHint = "先把 My brother 看成 he，再選 is。")),
        QuestionBankItem("r1-u2-001", "A1", "短句閱讀", "閱讀", "English+ seed", Question("Tom plays basketball after school. Tom 做什麼？", listOf("打籃球", "看書", "睡覺"), "打籃球", "plays basketball 表示打籃球。", "閱讀：抓動作", repairHint = "找動詞 plays，再看後面的 basketball。")),
        QuestionBankItem("v1-u2-001", "A1", "校園單字", "單字", "English+ seed", Question("library 的意思是？", listOf("圖書館", "操場", "餐廳"), "圖書館", "library 是圖書館。", "單字：校園地點", repairHint = "library 和 book、read 常一起出現。")),
        QuestionBankItem("s1-u2-001", "A1", "日常口語", "口說", "English+ seed", Question("I'm sorry. 最適合的情境是？", listOf("道歉", "道早安", "問路"), "道歉", "I'm sorry 用在道歉或表達抱歉。", "口語：道歉", repairHint = "sorry 的核心意思是抱歉。"))
    )

    fun initialBreakpoints(): MutableList<Breakpoint> = mutableListOf(
        Breakpoint("be 動詞反覆錯", "高", "He am / He are 連續錯 3 次。", "AI 先把規則拆成 He is、They are 兩張小卡。", "志工只追問一件事：學生能不能說出 He 為什麼用 is。"),
        Breakpoint("看到長句就停住", "中", "閱讀題停留超過 18 秒且沒有作答。", "平台先標出主詞與動詞，降低整句翻譯壓力。", "老師下次補一張『先找誰做什麼』練習單。")
    )

    val roster = listOf(
        StudentRow("小安", "高", "be 動詞卡關", "需要志工接力 1 次"),
        StudentRow("阿柔", "中", "長句閱讀猶豫", "先給拆句提示"),
        StudentRow("志豪", "低", "單字量不足", "可用離線單字包"),
        StudentRow("小晴", "中", "開口回應不確定", "適合口語情境練習"),
        StudentRow("宇翔", "低", "完成率穩定", "可安排小挑戰")
    )

    val studyTasks = listOf(
        StudyTask("He is / She is 快速判斷", 3, "低", "先用一題建立成功感。", "推薦"),
        StudyTask("They are / We are 對照", 5, "中", "把單數和複數分開練。", "可開始"),
        StudyTask("找出句子的主詞", 5, "低", "閱讀前先找到誰在做事。", "暖身"),
        StudyTask("Thank you 情境回應", 8, "中", "用生活句降低開口壓力。", "延伸")
    )

    val supportMessages = listOf(
        SupportMessage("AI 陪伴", "剛剛", "你不是不會英文，只是現在卡在主詞和 be 動詞的搭配。我們先練 He is。", "低壓"),
        SupportMessage("Emily 志工", "20:12", "小安今天願意回來做 3 分鐘任務，先肯定開始，再修一個規則。", "接力"),
        SupportMessage("老師", "昨天", "本週不要公開排名，先看誰願意完成短任務與回到練習。", "教學提醒")
    )

    val weeklySignals = listOf(
        WeeklySignal("完成短任務", "4 次", "比上週多 1 次", "#0F766E"),
        WeeklySignal("接受提示後作答", "9 題", "大多能在第二次修正", "#246BFD"),
        WeeklySignal("高壓斷點", "1 次", "出現在長句閱讀", "#B45309"),
        WeeklySignal("需要真人接力", "1 人", "已安排志工追蹤", "#B91C1C")
    )

    val mistakeRecords = listOf(
        MistakeRecord("He / She / It + is", "把 He 搭配成 am 或 are", "先念 He is，再做 2 題對照", "已修復一次"),
        MistakeRecord("複數主詞 + are", "They 被誤選成 is", "用 They are my friends 當固定句", "待複習"),
        MistakeRecord("長句閱讀", "看到整句就想放棄", "先圈主詞，再找動詞", "接力追蹤")
    )

    val offlinePacks = listOf(
        OfflinePack("3 分鐘 be 動詞包", "1.2 MB", "3-5 分鐘", "5 題判斷、2 張規則小卡、1 個反思問題"),
        OfflinePack("校園單字包", "900 KB", "5 分鐘", "library、classroom、teacher 等生活單字"),
        OfflinePack("短句閱讀包", "1.6 MB", "5-8 分鐘", "先找主詞與動詞，不要求一次翻完整句")
    )

    val mentorChecks = listOf(
        MentorCheck("低壓任務入口", "通過", "學生打開首頁後能很快看到今天先做什麼。", "#0F766E"),
        MentorCheck("情緒斷點處理", "通過", "錯題後有 AI 或本機模擬提示，不會直接責備。", "#0F766E"),
        MentorCheck("真人接力", "待實測", "需要用實際老師/志工確認摘要是否夠清楚。", "#B45309"),
        MentorCheck("離線同步", "展示完成", "本機保存與待同步狀態可被看見。", "#0F766E"),
        MentorCheck("正式後端", "未部署", "Firebase 或校內後端仍屬內測後任務。", "#B45309")
    )

    val handoffPriorities = listOf(
        HandoffPriority("小安 be 動詞高壓斷點", "Emily 志工", "陪他說出 He is / They are 的差別", "高"),
        HandoffPriority("阿柔 長句閱讀停住", "英文老師", "示範先找主詞與動詞", "中"),
        HandoffPriority("志豪 單字量不足", "AI 陪伴", "安排 3 分鐘校園單字包", "低")
    )

    val journeySteps = listOf(
        JourneyStep("打開 App", "怕一打開就是考試", "首頁先問今天狀態，並給出一個小任務", "狀態低時先進復原任務"),
        JourneyStep("開始短任務", "擔心又答錯", "一題一概念，錯了也給下一步提示", "錯 2 次後生成接力摘要"),
        JourneyStep("卡關求助", "不知道怎麼說自己不會", "提供可選原因，幫學生整理成求助訊息", "只把必要脈絡交給志工"),
        JourneyStep("老師查看", "老師時間有限", "用待辦與優先序呈現誰需要先處理", "高風險斷點優先"),
        JourneyStep("回到學習", "怕再次失敗", "用錯題修復和小成就把信心接回來", "週報只呈現支持證據")
    )

    val interventionSteps = listOf(
        InterventionStep("心情低落", "改成 3 分鐘復原任務", "今天只完成一小步就好。", "學生仍願意回到任務"),
        InterventionStep("連續錯題", "AI 拆成一個規則與一題練習", "不是你不會，是這個規則需要被拆小。", "錯題變成可修復紀錄"),
        InterventionStep("主動求助", "把學生文字轉成志工摘要", "我卡在主詞，不知道為什麼要用 is。", "志工不需要重讀全部歷程"),
        InterventionStep("老師介入", "顯示下一步而非總分排名", "下一步只要確認 He is。", "避免公開比較造成壓力")
    )

    val designPrinciples = listOf(
        DesignPrinciple("主行動優先", "首頁第一眼要看到今天先做什麼。", "主按鈕導向心情檢測或短任務。"),
        DesignPrinciple("AI 不取代真人", "AI 處理可拆小的錯題，真人處理需要陪伴的斷點。", "handoff 摘要保留給老師/志工。"),
        DesignPrinciple("不公開排名", "偏鄉學生先需要安全感，不是再次被分數定義。", "週報呈現完成、修復與接力證據。"),
        DesignPrinciple("離線可用", "網路不穩時仍能保存學習紀錄。", "同步中心顯示本機待同步狀態。")
    )

    val helpRequestOptions = listOf(
        HelpRequestOption("我看不懂題目", "我不知道這題在問什麼。", "平台先抓主詞和關鍵字，再生成接力摘要。", "AI 陪伴"),
        HelpRequestOption("我一直選錯", "我知道答案好像不對，但不知道差在哪裡。", "平台整理錯誤規則，交給志工追蹤。", "志工接力"),
        HelpRequestOption("我現在不想考", "我想先做簡單一點的任務。", "切換成 3 分鐘復原任務。", "復原任務"),
        HelpRequestOption("我想問老師", "我需要老師告訴我下一步。", "整理成老師可讀的一句話。", "老師接力")
    )

    val learningContracts = listOf(
        LearningContract("今天只做一小步", "我願意先做 3-5 分鐘。", "平台不會公開排名，只提供下一步。", "老師/志工只看需要支持的地方。"),
        LearningContract("錯題可以被修復", "我可以答錯，但要知道下一步。", "平台會把錯題拆成小規則。", "真人接力會看修復線索，不責備。"),
        LearningContract("可以主動求助", "我可以說自己卡住。", "平台會幫我整理求助訊息。", "志工會用摘要回覆，而不是重新考我。")
    )

    val reflectionPrompts = listOf(
        ReflectionPrompt("我完成了一小步", "我今天至少完成一題。", "已把這次完成放進學習地圖。", 3),
        ReflectionPrompt("我知道自己卡在哪裡", "我卡在 He 要不要用 is。", "這會變成下一次修復任務。", 1),
        ReflectionPrompt("我需要人幫忙", "我想請志工看一下。", "平台會把摘要交給志工。", 0)
    )

    val teacherActions = listOf(
        TeacherAction("追蹤小安 be 動詞", "Emily 志工", "待處理", "今晚 21:00", "He am / He are 連續錯 3 次。", "陪他完成 He is / They are 對照 2 題。"),
        TeacherAction("協助阿柔拆長句", "英文老師", "本週追蹤", "明天", "閱讀題停留時間偏長。", "示範找主詞和動詞。"),
        TeacherAction("確認離線包使用", "導師", "已安排", "本週五", "家中網路不穩。", "先下載 3 分鐘任務包。")
    )

    val syncRecords = listOf(
        SyncRecord("短任務完成紀錄", "已保存", "本機已記錄 1 次 be 動詞練習。"),
        SyncRecord("離線任務包", "待同步", "學生下載任務包後尚未補傳。"),
        SyncRecord("志工接力摘要", "已排隊", "等待雲端後端正式設定。")
    )

    val localAccounts = listOf(
        LocalAccount("小安", AuthContract.ROLE_STUDENT, "YILAN-CHENGZHI-8A", "本機展示帳號"),
        LocalAccount("Emily", AuthContract.ROLE_VOLUNTEER, "MENTOR-GROUP-A", "本機展示帳號"),
        LocalAccount("林老師", AuthContract.ROLE_TEACHER, "CLASS-ENGLISH-02", "本機展示帳號")
    )

    val aiScenarios = listOf(
        AiScenario("be 動詞修復", "學生回答 He am a student.", "卡在主詞與 be 動詞搭配。", "先記：He/She/It 後面通常用 is。我們只重做一題。", "小安卡在 be 動詞，建議志工用 He is / They are 對照。"),
        AiScenario("長句閱讀降壓", "學生看到 Tom plays basketball after school 就停住。", "不是單字全不會，而是不知道從哪裡開始。", "先找誰做什麼：Tom plays basketball。後面的 after school 是時間。", "學生需要先練找主詞和動詞。"),
        AiScenario("復原任務", "學生說現在不想考。", "情緒壓力高，應降低任務難度。", "今天不用完成測驗，只做一題你能掌握的題目。", "建議老師先肯定回到任務，再安排短題。")
    )
}
