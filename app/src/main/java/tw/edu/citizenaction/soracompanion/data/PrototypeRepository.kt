package tw.edu.citizenaction.soracompanion.data

import tw.edu.citizenaction.soracompanion.model.Breakpoint
import tw.edu.citizenaction.soracompanion.model.AiScenario
import tw.edu.citizenaction.soracompanion.model.DesignPrinciple
import tw.edu.citizenaction.soracompanion.model.InterventionStep
import tw.edu.citizenaction.soracompanion.model.JourneyStep
import tw.edu.citizenaction.soracompanion.model.LearningModule
import tw.edu.citizenaction.soracompanion.model.LearningContract
import tw.edu.citizenaction.soracompanion.model.LocalAccount
import tw.edu.citizenaction.soracompanion.model.MistakeRecord
import tw.edu.citizenaction.soracompanion.model.OfflinePack
import tw.edu.citizenaction.soracompanion.model.HandoffPriority
import tw.edu.citizenaction.soracompanion.model.HelpRequestOption
import tw.edu.citizenaction.soracompanion.model.MentorCheck
import tw.edu.citizenaction.soracompanion.model.Question
import tw.edu.citizenaction.soracompanion.model.ReflectionPrompt
import tw.edu.citizenaction.soracompanion.model.SyncRecord
import tw.edu.citizenaction.soracompanion.model.StudyTask
import tw.edu.citizenaction.soracompanion.model.SupportMessage
import tw.edu.citizenaction.soracompanion.model.StudentProfile
import tw.edu.citizenaction.soracompanion.model.StudentRow
import tw.edu.citizenaction.soracompanion.model.TeacherAction
import tw.edu.citizenaction.soracompanion.model.WeeklySignal

object PrototypeRepository {
    val student = StudentProfile(
        name = "林家豪",
        age = 14,
        location = "宜蘭頭城",
        grade = "國二",
        goal = "會考英文從 C 往 B 前進",
        constraint = "家庭與同儕支援有限，學習時間常被切碎。",
        mentor = "雲端志工 Emily",
        learningStyle = "需要短任務、即時鼓勵與明確下一步。",
        supportNeed = "在情緒低落或連續答錯時，需要先被接住，再重新練習。"
    )

    val modules = listOf(
        LearningModule("字彙地基", "會考常見 120 字", 68, "今天複習 6 個高頻字", "穩定"),
        LearningModule("句型修復", "be 動詞 / 現在式", 42, "目前最需要陪伴", "修復中"),
        LearningModule("閱讀短任務", "3 分鐘生活短文", 24, "先找主詞與動詞", "低壓"),
        LearningModule("聽力暖身", "慢速句子辨識", 31, "狀態好時練 2 題", "可選"),
        LearningModule("會考小測", "單題診斷", 18, "暫時不做連續測驗", "保留")
    )

    val questions = listOf(
        Question("He ___ a student.", listOf("am", "is", "are"), "is", "He 是一個人，通常搭配 is。", "be 動詞與主詞搭配", "句型修復", "先看主詞 He，再只判斷一件事：He 搭配 is。"),
        Question("They ___ my friends.", listOf("is", "are", "am"), "are", "They 是很多人，通常搭配 are。", "be 動詞與複數主詞", "句型修復", "把 They 想成很多人，所以要選 are。"),
        Question("I ___ ready.", listOf("am", "is", "are"), "am", "I 要搭配 am，這是最常見固定組合。", "I am 固定組合", "句型修復", "I am 先當成固定片語背起來。"),
        Question("She ___ happy today.", listOf("are", "am", "is"), "is", "She 是一個人，和 He 一樣通常搭配 is。", "第三人稱單數", "句型修復", "He 和 She 都是一個人，先選 is。"),
        Question("We ___ in the classroom.", listOf("is", "are", "am"), "are", "We 是我們，表示很多人，通常搭配 are。", "複數主詞", "句型修復", "We 代表我們，通常不只一個人，所以用 are。"),
        Question("選出主詞：The boy is reading a book.", listOf("The boy", "reading", "a book"), "The boy", "主詞通常是句子裡正在做動作的人或東西。", "閱讀拆句", "閱讀題", "先不要翻整句，只找誰在做事。"),
        Question("聽力暖身：I am fine. 這句最接近哪個意思？", listOf("我很好", "我很晚", "我在找東西"), "我很好", "I am fine 是最常見的狀態表達。", "聽力暖身", "聽力題", "先抓 fine 這個關鍵字，不需要每個字都懂。"),
        Question("字彙：usually 最接近哪個意思？", listOf("通常", "突然", "最後"), "通常", "usually 表示通常、平常。", "會考高頻字", "字彙題", "把 usually 和平常做的事連在一起。")
    )

    fun initialBreakpoints(): MutableList<Breakpoint> = mutableListOf(
        Breakpoint(
            "be 動詞與主詞搭配",
            "高",
            "He am / He are 連續答錯 3 次",
            "AI 已改用一題一概念提示。",
            "請志工用 He is、They are 各帶 2 題口頭練習。"
        ),
        Breakpoint(
            "看到長句就想退出",
            "中",
            "閱讀題停留 18 秒後返回首頁",
            "AI 已拆成找主詞、找動詞、看選項三步。",
            "先問學生看懂哪三個字，不急著講整段翻譯。"
        )
    )

    val roster = listOf(
        StudentRow("林家豪", "高", "be 動詞連錯", "今天已回來做 1 次修復任務"),
        StudentRow("陳以晴", "中", "閱讀題停留過久", "需要短文拆句"),
        StudentRow("吳承恩", "低", "字彙複習中斷", "適合推 3 分鐘復原任務"),
        StudentRow("黃品妤", "中", "聽力任務跳出", "適合改用慢速句子"),
        StudentRow("張宇翔", "低", "完成字彙複習", "可給小挑戰題")
    )

    val studyTasks = listOf(
        StudyTask("He is 單題修復", 3, "低", "連續錯題後先建立可完成感", "今日優先"),
        StudyTask("They are 對照練習", 5, "中", "接在 He is 之後，避免一次混太多", "待解鎖"),
        StudyTask("生活短句閱讀", 5, "低", "用短句找主詞與動詞", "可選"),
        StudyTask("會考單題診斷", 8, "中", "狀態穩定時才做", "保留")
    )

    val supportMessages = listOf(
        SupportMessage("AI 小幫手", "剛剛", "你不是完全不會，是卡在 He 要搭配哪個 be 動詞。先把問題縮小就好。", "即時鼓勵"),
        SupportMessage("Emily 志工", "昨天 20:12", "你昨天願意回來做 3 分鐘任務，這比一次寫很多題更重要。", "真人陪伴"),
        SupportMessage("系統", "週一", "本週目標已調整為低壓修復，不啟用排行榜。", "學習節奏")
    )

    val weeklySignals = listOf(
        WeeklySignal("回來學習", "4 天", "比上週多 1 天", "#0F766E"),
        WeeklySignal("微任務", "9 題", "其中 6 題在 5 分鐘內完成", "#246BFD"),
        WeeklySignal("主動求助", "1 次", "願意求助是正向訊號", "#B45309"),
        WeeklySignal("高風險斷點", "1 個", "需要志工接力", "#B91C1C")
    )

    val mistakeRecords = listOf(
        MistakeRecord("He / She / It + is", "把 He 搭配 am 或 are", "先只練 He is，再對照 They are", "修復中"),
        MistakeRecord("複數主詞 + are", "看到 They 仍選 is", "用圖片或人數提示建立複數概念", "待接力"),
        MistakeRecord("長句閱讀", "看到 10 字以上句子就退出", "改成找主詞、找動詞、看選項三步", "觀察中")
    )

    val offlinePacks = listOf(
        OfflinePack("3 分鐘 be 動詞修復包", "1.2 MB", "3-5 分鐘", "5 題單概念練習、2 句 AI 鼓勵、1 個求助按鈕"),
        OfflinePack("會考高頻字暖身包", "900 KB", "5 分鐘", "6 個單字、6 張例句卡、1 次低壓複習"),
        OfflinePack("短文拆句包", "1.6 MB", "5-8 分鐘", "2 篇短文、主詞提示、動詞提示、逐步解題")
    )

    val mentorChecks = listOf(
        MentorCheck("差異化", "良好", "有避開與 Cool English、均一大量內容平台正面競爭。", "#0F766E"),
        MentorCheck("動機導向", "良好", "首頁心情檢測、低壓任務、非排行榜成就系統都有對應。", "#0F766E"),
        MentorCheck("可執行性", "待確認", "目前是原型與假資料，下一步需確認學校/志工合作流程。", "#B45309"),
        MentorCheck("低負擔", "良好", "志工端以摘要和腳本接力，降低備課成本。", "#0F766E"),
        MentorCheck("完整度", "待補強", "尚未接後端、帳號與真 AI。", "#B45309")
    )

    val handoffPriorities = listOf(
        HandoffPriority("林家豪 be 動詞高風險斷點", "Emily 志工", "今天只帶 He is / They are 各 2 題", "高"),
        HandoffPriority("陳以晴 閱讀停留過久", "英文老師", "下次課堂用短句拆主詞動詞", "中"),
        HandoffPriority("吳承恩 字彙中斷", "AI 小幫手", "推送 3 分鐘高頻字暖身包", "低")
    )

    val journeySteps = listOf(
        JourneyStep(
            "進入 app",
            "不知道今天能不能學，擔心又被考倒。",
            "先問心情與可用時間，不直接進入測驗。",
            "心情低落或時間少於 5 分鐘時，只派復原任務。"
        ),
        JourneyStep(
            "開始短任務",
            "願意嘗試，但只承受得住很小的壓力。",
            "一題一概念，答錯先提示，不顯示排名。",
            "連續答錯 2 次時啟動 AI 拆解。"
        ),
        JourneyStep(
            "出現斷點",
            "覺得自己英文很爛，想關掉 app。",
            "把任務縮小成可完成步驟，並保存斷點證據。",
            "連續答錯 3 次或重複退出時，生成志工接力摘要。"
        ),
        JourneyStep(
            "真人接力",
            "需要被肯定，也需要有人用同一概念陪練。",
            "志工只看到必要脈絡、錯題型態與建議陪伴語。",
            "高風險情緒訊號優先給真人，低風險交給 AI。"
        ),
        JourneyStep(
            "回到學習",
            "希望知道自己有進步，不只是被糾錯。",
            "把復原任務和短任務都計入學習地圖。",
            "週報呈現修復證據，不做同儕排名。"
        )
    )

    val interventionSteps = listOf(
        InterventionStep(
            "心情低落",
            "自動縮短任務長度，預設 3 分鐘復原任務。",
            "今天只做一小步也算完成。",
            "避免學生一進入平台就面對完整測驗。"
        ),
        InterventionStep(
            "連續答錯",
            "停止加題，改成 AI 拆解規則與同題重試。",
            "不是你不會，是這個規則還沒有被拆小。",
            "將錯誤從能力評價轉成可修復概念。"
        ),
        InterventionStep(
            "需要真人",
            "產生斷點摘要、證據、AI 已做處理與志工建議。",
            "我已經幫你整理好，老師會知道你卡在哪。",
            "讓志工把時間用在陪伴，而不是重問脈絡。"
        ),
        InterventionStep(
            "完成修復",
            "將復原任務納入進度與週報，建立可見成就。",
            "你完成的是修復，不是補考。",
            "讓學生看見自己有往前，不被排行榜壓垮。"
        )
    )

    val designPrinciples = listOf(
        DesignPrinciple(
            "低壓先行",
            "所有學習任務都先被時間、心情與斷點重新排序。",
            "心情檢測、3-5 分鐘任務、復原模式。"
        ),
        DesignPrinciple(
            "AI 不取代真人",
            "AI 處理即時拆解，真人處理高價值陪伴和情緒接住。",
            "斷點中心、志工接力摘要、陪伴腳本。"
        ),
        DesignPrinciple(
            "進步不靠排名",
            "用修復紀錄、完成微任務和週報呈現成長。",
            "學習地圖、錯題修復紀錄、本週週報。"
        ),
        DesignPrinciple(
            "偏鄉限制內建",
            "網路不穩、時間零碎、師資有限都被視為設計條件。",
            "離線任務包、短任務、老師端優先序。"
        )
    )

    val helpRequestOptions = listOf(
        HelpRequestOption(
            "我看不懂題目",
            "我不是不想做，是題目一長就不知道從哪裡開始。",
            "先改派拆句提示，並把卡住的題型記到錯題修復紀錄。",
            "AI 先處理"
        ),
        HelpRequestOption(
            "我一直答錯",
            "我已經試了幾次，但越做越沒信心。",
            "停止加題，生成斷點摘要，志工只帶同一概念兩題。",
            "志工接力"
        ),
        HelpRequestOption(
            "我今天很累",
            "我想學，但今天狀態真的不好。",
            "切換成 3 分鐘復原任務，完成後仍記入進度。",
            "復原模式"
        ),
        HelpRequestOption(
            "我沒有穩定網路",
            "我可能等等就沒網路，想先下載可以做的內容。",
            "推薦離線任務包，保留本週進度與待同步紀錄。",
            "離線任務"
        )
    )

    val learningContracts = listOf(
        LearningContract(
            "今天只修一個斷點",
            "我只需要完成 1 個 3-5 分鐘任務。",
            "平台不啟用排行榜，不用完整測驗追進度。",
            "志工只看斷點摘要，不重新檢討學生。"
        ),
        LearningContract(
            "先回到可完成感",
            "如果我很累，可以選復原任務。",
            "平台會把復原任務也算進學習紀錄。",
            "老師看到的是修復證據，不是偷懶紀錄。"
        ),
        LearningContract(
            "需要人時再接力",
            "我可以主動求助，不必等到完全放棄。",
            "平台會先分流 AI 或真人，避免學生一直重講狀況。",
            "志工接力時只帶同一概念，不追加作業。"
        )
    )

    val reflectionPrompts = listOf(
        ReflectionPrompt(
            "我比剛開始更懂一點",
            "我可以說出 He 要搭配 is。",
            "系統會把這次記為句型斷點修復。",
            3
        ),
        ReflectionPrompt(
            "我還是有點卡",
            "我需要再看一次規則，不想馬上加題。",
            "系統會保留低壓任務，並把斷點放進明天優先序。",
            1
        ),
        ReflectionPrompt(
            "我今天先到這裡",
            "我完成一小步，但現在不想繼續。",
            "系統會保存進度，不要求你連續做完整章節。",
            0
        )
    )

    val teacherActions = listOf(
        TeacherAction(
            "確認林家豪 be 動詞斷點",
            "Emily 志工",
            "今日待處理",
            "今天 21:00 前",
            "He am / He are 連續答錯 3 次，且主動選擇「我一直答錯」。",
            "用陪伴腳本開場，只帶 He is 與 They are 各 2 題。"
        ),
        TeacherAction(
            "調整陳以晴閱讀任務",
            "英文老師",
            "本週追蹤",
            "週五前",
            "閱讀題停留過久，返回首頁次數偏高。",
            "把閱讀題改成圈主詞、圈動詞、再看選項。"
        ),
        TeacherAction(
            "確認離線任務包是否可用",
            "小組成員",
            "待驗證",
            "下次訪談前",
            "學生家庭網路不穩，碎片時間學習常中斷。",
            "用 3 分鐘任務包做一次可用性測試。"
        )
    )

    val syncRecords = listOf(
        SyncRecord("今日微任務", "已同步", "完成 1 題 be 動詞修復，已寫入本週週報。"),
        SyncRecord("離線任務包", "可下載", "3 分鐘 be 動詞修復包可離線使用。"),
        SyncRecord("志工接力摘要", "待回覆", "已建立摘要，等待 Emily 志工確認。")
    )

    val localAccounts = listOf(
        LocalAccount("林家豪", "學生", "YILAN-CHENGZHI-8A", "本機展示帳號"),
        LocalAccount("Emily", "雲端志工", "MENTOR-GROUP-A", "本機展示帳號"),
        LocalAccount("王老師", "老師", "CLASS-ENGLISH-02", "本機展示帳號")
    )

    val aiScenarios = listOf(
        AiScenario(
            "be 動詞錯題拆解",
            "學生連續把 He ___ a student 選成 am / are。",
            "不是整章不會，而是主詞與 be 動詞搭配不穩。",
            "先看 He 是一個人，所以選 is。今天只練 He is，不加新規則。",
            "林家豪在 be 動詞搭配連續錯 3 次，AI 已改派單概念修復，建議志工只帶 He is / They are 各 2 題。"
        ),
        AiScenario(
            "長句閱讀退出",
            "學生看到 10 字以上句子停留 18 秒後返回首頁。",
            "可能不是單字量完全不足，而是長句切分壓力太高。",
            "先圈出主詞，再找動詞，最後才看選項，不需要翻完整句。",
            "學生遇到長句閱讀有退出訊號，建議老師下次用圈主詞/動詞策略，不直接要求翻譯。"
        ),
        AiScenario(
            "情緒低落求助",
            "學生選擇「我今天很累」。",
            "目前最重要的是保住回來學習的意願，不是提高題量。",
            "今天只做一小步也算完成。你可以先做 3 分鐘復原任務。",
            "學生主動表示疲憊，平台已降為 3 分鐘復原任務，暫不建議追加作業。"
        )
    )
}
