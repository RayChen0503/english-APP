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
        LearningModule("會考克漏字", "用上下文判斷語意與文法", 54, "完成一篇 4 題短文", "可挑戰"),
        LearningModule("閱讀理解", "從公告、訊息、短文找線索", 47, "先練主旨與細節題", "進行中"),
        LearningModule("翻譯/句子重組", "把中文意思轉成自然英文", 39, "先掌握主詞、動詞、時間", "待修復"),
        LearningModule("錯題復原", "把常錯規則整理成下一步", 22, "只做一個 3 分鐘修復任務", "低壓")
    )

    val questions = buildQuestions()
    val questionBankItems = buildQuestionBankItems(questions)

    private fun buildQuestions(): List<Question> {
        return listOf(
            Question("He ___ a student.", listOf("am", "is", "are"), "is", "He 是第三人稱單數，要搭配 is。", "be 動詞：He/She/It + is", "選擇題", "先看主詞 He，再選第三人稱單數的 is。"),
            Question("They ___ my friends.", listOf("is", "are", "am"), "are", "They 是複數主詞，要搭配 are。", "be 動詞：複數 + are", "選擇題", "They 表示很多人，先排除 is 和 am。"),
            Question("I ___ ready.", listOf("am", "is", "are"), "am", "I 固定搭配 am。", "be 動詞：I + am", "選擇題", "看到 I，先想 I am。"),
            Question("She ___ happy today.", listOf("are", "am", "is"), "is", "She 和 He 一樣，要搭配 is。", "be 動詞：She + is", "選擇題", "She 是一個人，第三人稱單數用 is。"),
            Question("We ___ in the classroom.", listOf("is", "are", "am"), "are", "We 是複數主詞，要搭配 are。", "be 動詞：We + are", "選擇題", "We 表示我們，通常搭配 are。"),
            Question("The boy is reading a book. 這句的主詞是？", listOf("The boy", "reading", "a book"), "The boy", "主詞是做動作的人，這裡是 The boy。", "閱讀：找主詞", "選擇題", "先問：誰正在 reading？答案是 The boy。"),
            Question("Thank you. 最自然的回應是？", listOf("You're welcome.", "Good night.", "I'm sorry."), "You're welcome.", "別人說謝謝時，可以回 You're welcome。", "口語：感謝回應", "選擇題", "看到 Thank you，就想 You're welcome。"),
            Question("usually 的意思比較接近？", listOf("通常", "從不", "明天"), "通常", "usually 表示通常、經常。", "單字：頻率副詞", "選擇題", "usually 是頻率字，表示事情常常發生。"),

            Question("If it ___ tomorrow, the baseball game will be canceled.", listOf("rains", "rained", "will rain", "is raining"), "rains", "if 條件句談未來時，if 子句通常用現在式。", "會考文法：條件句", "填空題", "看到 If + tomorrow，主句有 will，空格用現在式 rains。"),
            Question("Ms. Lin asked us ___ our reports before Friday.", listOf("finish", "to finish", "finished", "finishing"), "to finish", "ask + 人 + to V 表示要求某人做某事。", "會考文法：不定詞", "填空題", "先看 asked us，後面常接 to finish。"),
            Question("This is the camera ___ my uncle bought in Japan.", listOf("who", "where", "which", "when"), "which", "先行詞 camera 是物品，關係代名詞用 which。", "會考文法：關係代名詞", "填空題", "先判斷先行詞是人還是物，camera 是物。"),
            Question("The soup tastes too ___. Could I have some water?", listOf("salty", "quiet", "heavy", "early"), "salty", "需要水通常表示湯太鹹，salty 最符合語意。", "會考字彙：語意判斷", "填空題", "不要只看單字，後句 some water 是線索。"),

            Question("克漏字：A young man found a wallet on the bus. He did not keep it. He took it to the driver because he thought it was the ___ thing to do.", listOf("right", "cheap", "noisy", "empty"), "right", "撿到錢包交給司機是正確的事，right 表示正確的。", "會考克漏字：上下文語意", "克漏字", "前兩句說他沒有私留，後面應該是正確的事。"),
            Question("克漏字：Mia wanted to join the race, ___ she hurt her foot the day before it.", listOf("but", "so", "or", "because"), "but", "想參加比賽與腳受傷形成轉折，要用 but。", "會考克漏字：連接詞", "克漏字", "前後意思相反，找轉折連接詞。"),
            Question("克漏字：The notice says students should bring their own cups. This helps the school use ___ paper cups.", listOf("fewer", "much", "little", "more than"), "fewer", "cups 是可數複數，減少紙杯要用 fewer。", "會考克漏字：數量詞", "克漏字", "先看 paper cups 是可數複數。"),
            Question("克漏字：Kevin studied hard for the test. When he saw his score, he smiled because his work had finally ___.", listOf("paid off", "put on", "looked up", "turned down"), "paid off", "努力有成果可用 paid off。", "會考克漏字：片語", "克漏字", "hard work + score + smiled 表示努力有回報。"),

            Question("閱讀理解：A sign at a library says, 'Please return books on time. If a book is late, you cannot borrow another one until it is returned.' What happens if a student returns a book late?", listOf("The student must wait before borrowing again.", "The student can keep all books longer.", "The library will give a free book.", "The library closes for one day."), "The student must wait before borrowing again.", "公告說逾期未還前不能再借書。", "會考閱讀：公告細節", "閱讀理解", "先找 If a book is late 後面的規則。"),
            Question("閱讀理解：Tina texted Ben, 'I will be ten minutes late. Please order noodles for me first.' What does Tina want Ben to do?", listOf("Order food before she arrives.", "Cancel the meeting.", "Wait outside the station.", "Buy a train ticket."), "Order food before she arrives.", "她會晚到，請 Ben 先幫她點麵。", "會考閱讀：訊息理解", "閱讀理解", "text message 題先找請求動作。"),
            Question("閱讀理解：A short article says many students sleep with their phones near their beds. It suggests turning off notifications at night. What is the article mainly about?", listOf("A way to sleep better", "A history of phones", "A new school rule", "A popular game"), "A way to sleep better", "文章提到手機通知影響夜間睡眠，主旨是改善睡眠。", "會考閱讀：主旨", "閱讀理解", "主旨題要抓整段重複的核心：phone, night, sleep。"),
            Question("閱讀理解：The weather report says, 'It will be cloudy in the morning, but heavy rain is expected after 3 p.m.' When should people carry an umbrella?", listOf("In the afternoon", "Only at midnight", "Before sunrise", "Never"), "In the afternoon", "heavy rain is expected after 3 p.m. 表示下午需要傘。", "會考閱讀：時間線索", "閱讀理解", "抓 after 3 p.m.，不要被 morning 迷惑。"),

            Question("翻譯/句子重組：『我每天放學後練習英文。』最自然的英文是？", listOf("I practice English after school every day.", "I after school every day practice English.", "Every day English practice I after school.", "I am practice English after school every day."), "I practice English after school every day.", "英文基本順序是主詞 + 動詞 + 受詞 + 時間。", "會考翻譯：語序", "翻譯/句子重組", "先排 I practice English，再放 after school every day。"),
            Question("翻譯/句子重組：『這本書太難了，我看不懂。』最自然的英文是？", listOf("This book is too difficult for me to understand.", "This book too difficult I cannot understand it.", "I am too difficult to understand this book.", "This book is difficult too understand me."), "This book is too difficult for me to understand.", "too + 形容詞 + for 人 + to V 可表達太難而無法理解。", "會考翻譯：too...to", "翻譯/句子重組", "看到『太...而不能』，想 too difficult for me to understand。"),
            Question("翻譯/句子重組：『如果明天下雨，我們就待在家。』最自然的英文是？", listOf("If it rains tomorrow, we will stay home.", "If it will rain tomorrow, we stay home.", "Tomorrow rains if we will home stay.", "If tomorrow rain, we are stay home."), "If it rains tomorrow, we will stay home.", "if 條件句談未來，if 子句用現在式 rains，主句用 will。", "會考翻譯：條件句", "翻譯/句子重組", "if 子句不要用 will rain。"),
            Question("翻譯/句子重組：『我想知道公車什麼時候會到。』最自然的英文是？", listOf("I want to know when the bus will arrive.", "I want know when will the bus arrive.", "I want to know when will arrive the bus.", "I want knowing the bus when arrive."), "I want to know when the bus will arrive.", "間接問句用直述句語序：when the bus will arrive。", "會考翻譯：間接問句", "翻譯/句子重組", "間接問句不是 when will the bus arrive。")
        )
    }

    private fun buildQuestionBankItems(sourceQuestions: List<Question>): List<QuestionBankItem> {
        val levels = listOf("A1", "A1", "A1", "A1", "A1", "A1", "A1", "A1", "A2", "A2", "A2", "A2", "A2", "A2", "A2", "B1", "A2", "A2", "B1", "A2", "A2", "B1", "B1", "B1")
        val units = listOf(
            "be 動詞暖身", "be 動詞暖身", "be 動詞暖身", "be 動詞暖身", "be 動詞暖身", "短句閱讀", "口語回應", "頻率副詞",
            "會考文法填空", "會考文法填空", "會考文法填空", "會考字彙填空",
            "會考克漏字", "會考克漏字", "會考克漏字", "會考克漏字",
            "會考閱讀理解", "會考閱讀理解", "會考閱讀理解", "會考閱讀理解",
            "翻譯與句子重組", "翻譯與句子重組", "翻譯與句子重組", "翻譯與句子重組"
        )
        val skills = listOf(
            "文法", "文法", "文法", "文法", "文法", "閱讀", "口說", "單字",
            "文法", "文法", "文法", "單字",
            "克漏字", "克漏字", "克漏字", "克漏字",
            "閱讀", "閱讀", "閱讀", "閱讀",
            "翻譯", "翻譯", "翻譯", "翻譯"
        )
        return sourceQuestions.mapIndexed { index, question ->
            QuestionBankItem(
                id = "cap-style-${(index + 1).toString().padStart(3, '0')}",
                level = levels[index],
                unit = units[index],
                skill = skills[index],
                source = if (index < 8) "English+ seed" else "English+ CAP-style original",
                question = question,
                reviewState = if (index < 16) "approved" else "draft"
            )
        }
    }

    fun initialBreakpoints(): MutableList<Breakpoint> = mutableListOf(
        Breakpoint("be 動詞反覆錯", "高", "He am / He are 連續錯 3 次。", "AI 先把規則拆成 He is、They are 兩張小卡。", "志工只追問一件事：學生能不能說出 He 為什麼用 is。"),
        Breakpoint("看到長篇就停住", "中", "閱讀題停留超過 18 秒且沒有作答。", "平台先標出題型與線索，降低整篇閱讀壓力。", "老師下次補一張『先看題目，再回文章找線索』練習單。")
    )

    val roster = listOf(
        StudentRow("小安", "高", "be 動詞與填空題卡關", "需要志工接力 1 次"),
        StudentRow("阿柔", "中", "克漏字上下文判斷猶豫", "先給連接詞提示"),
        StudentRow("志豪", "低", "單字量不足", "可用離線單字包"),
        StudentRow("小晴", "中", "翻譯語序不穩", "適合句子重組練習"),
        StudentRow("宇翔", "低", "閱讀細節題穩定", "可安排主旨題挑戰")
    )

    val studyTasks = listOf(
        StudyTask("He is / She is 快速判斷", 3, "低", "先用一題建立成功感。", "推薦"),
        StudyTask("會考填空 3 題", 5, "中", "練 if 條件句、to V、關係代名詞。", "可開始"),
        StudyTask("克漏字短文一組", 6, "中", "練連接詞、上下文與片語。", "挑戰"),
        StudyTask("閱讀理解主旨題", 8, "高", "從文章整體判斷主旨，不只找單字。", "延伸")
    )

    val supportMessages = listOf(
        SupportMessage("AI 陪伴", "剛剛", "你不是不會英文，只是現在題型變難了。我們先把題目分成填空、克漏字、閱讀，再找線索。", "低壓"),
        SupportMessage("Emily 志工", "20:12", "小安今天願意挑戰會考填空，先肯定嘗試，再修一個規則。", "接力"),
        SupportMessage("老師", "昨天", "本週不公開排名，改看學生是否能從入門題走到會考挑戰題。", "教學提醒")
    )

    val weeklySignals = listOf(
        WeeklySignal("完成短任務", "4 次", "比上週多 1 次", "#0F766E"),
        WeeklySignal("會考挑戰題", "5 題", "已開始練克漏字與閱讀理解", "#246BFD"),
        WeeklySignal("高壓斷點", "1 次", "出現在長篇閱讀", "#B45309"),
        WeeklySignal("需要真人接力", "1 人", "已安排志工追蹤", "#B91C1C")
    )

    val mistakeRecords = listOf(
        MistakeRecord("He / She / It + is", "把 He 搭配成 am 或 are", "先念 He is，再做 2 題對照", "已修復一次"),
        MistakeRecord("if 條件句", "if 子句誤用 will rain", "記住 if + 現在式，主句 + will", "待複習"),
        MistakeRecord("克漏字連接詞", "轉折語意誤選 so", "先判斷前後句是因果還是轉折", "接力追蹤")
    )

    val offlinePacks = listOf(
        OfflinePack("3 分鐘 be 動詞包", "1.2 MB", "3-5 分鐘", "5 題判斷、2 張規則小卡、1 個反思問題"),
        OfflinePack("會考填空包", "1.4 MB", "5-8 分鐘", "條件句、to V、關係代名詞與語意填空"),
        OfflinePack("會考閱讀包", "1.8 MB", "8-10 分鐘", "公告、簡訊、短文主旨與細節題")
    )

    val mentorChecks = listOf(
        MentorCheck("低壓任務入口", "通過", "學生打開首頁後能很快看到今天先做什麼。", "#0F766E"),
        MentorCheck("題型難度梯度", "已加強", "題庫已從入門選擇題延伸到會考填空、克漏字、閱讀與翻譯。", "#0F766E"),
        MentorCheck("真人接力", "待實測", "需要用實際老師/志工確認摘要是否夠清楚。", "#B45309"),
        MentorCheck("離線同步", "展示完成", "本機保存與待同步狀態可被看見。", "#0F766E"),
        MentorCheck("正式後端", "未部署", "Firebase 或校內後端仍屬內測後任務。", "#B45309")
    )

    val handoffPriorities = listOf(
        HandoffPriority("小安 if 條件句高壓斷點", "Emily 志工", "陪他說出 if 子句為什麼不用 will rain", "高"),
        HandoffPriority("阿柔 克漏字轉折判斷", "英文老師", "示範先判斷前後句關係", "中"),
        HandoffPriority("志豪 單字量不足", "AI 陪伴", "安排 3 分鐘校園單字包", "低")
    )

    val journeySteps = listOf(
        JourneyStep("打開 App", "怕一打開就是考試", "首頁先問今天狀態，並給出一個小任務", "狀態低時先進復原任務"),
        JourneyStep("開始短任務", "擔心又答錯", "一題一概念，錯了也給下一步提示", "錯 2 次後生成接力摘要"),
        JourneyStep("挑戰會考題", "題目變長後容易慌", "先標示題型與解題線索，再逐步增加難度", "高壓斷點交給老師/志工"),
        JourneyStep("老師查看", "老師時間有限", "用待辦與優先序呈現誰需要先處理", "高風險斷點優先"),
        JourneyStep("回到學習", "怕再次失敗", "用錯題修復和小成就把信心接回來", "週報只呈現支持證據")
    )

    val interventionSteps = listOf(
        InterventionStep("心情低落", "改成 3 分鐘復原任務", "今天只完成一小步就好。", "學生仍願意回到任務"),
        InterventionStep("會考題太長", "先拆題型與線索", "這題不是要你一次讀懂全部，先看題目在問什麼。", "長題變成可操作步驟"),
        InterventionStep("主動求助", "把學生文字轉成志工摘要", "我卡在 if 句，不知道為什麼不能用 will rain。", "志工不需要重讀全部歷程"),
        InterventionStep("老師介入", "顯示下一步而非總分排名", "下一步只要確認 if + 現在式。", "避免公開比較造成壓力")
    )

    val designPrinciples = listOf(
        DesignPrinciple("主行動優先", "首頁第一眼要看到今天先做什麼。", "主按鈕導向心情檢測或短任務。"),
        DesignPrinciple("難度有梯度", "保留簡單題作下限，但讓進步的學生能挑戰會考題型。", "題庫包含 A1、A2、B1 與五種題型。"),
        DesignPrinciple("AI 不取代真人", "AI 處理可拆小的錯題，真人處理需要陪伴的斷點。", "handoff 摘要保留給老師/志工。"),
        DesignPrinciple("不公開排名", "偏鄉學生先需要安全感，不是再次被分數定義。", "週報呈現完成、修復與接力證據。")
    )

    val helpRequestOptions = listOf(
        HelpRequestOption("我看不懂題目", "我不知道這題在問什麼。", "平台先抓題型和關鍵線索，再生成接力摘要。", "AI 陪伴"),
        HelpRequestOption("我一直選錯", "我知道答案好像不對，但不知道差在哪裡。", "平台整理錯誤規則，交給志工追蹤。", "志工接力"),
        HelpRequestOption("題目太長", "我看到閱讀題就停住。", "切成先看題目、再找關鍵句。", "閱讀拆解"),
        HelpRequestOption("我想問老師", "我需要老師告訴我下一步。", "整理成老師可讀的一句話。", "老師接力")
    )

    val learningContracts = listOf(
        LearningContract("今天只做一小步", "我願意先做 3-5 分鐘。", "平台不會公開排名，只提供下一步。", "老師/志工只看需要支持的地方。"),
        LearningContract("錯題可以被修復", "我可以答錯，但要知道下一步。", "平台會把錯題拆成小規則。", "真人接力會看修復線索，不責備。"),
        LearningContract("會考題可以拆小", "我可以先找題型和線索。", "平台會先說明這是填空、克漏字、閱讀或翻譯。", "老師會看學生卡在哪一種題型。")
    )

    val reflectionPrompts = listOf(
        ReflectionPrompt("我完成了一小步", "我今天至少完成一題。", "已把這次完成放進學習地圖。", 3),
        ReflectionPrompt("我知道自己卡在哪裡", "我卡在 if 條件句。", "這會變成下一次修復任務。", 1),
        ReflectionPrompt("我需要人幫忙", "我想請志工看一下。", "平台會把摘要交給志工。", 0)
    )

    val teacherActions = listOf(
        TeacherAction("追蹤小安 if 條件句", "Emily 志工", "待處理", "今晚 21:00", "if 子句誤用 will rain。", "陪他完成 if + 現在式對照 2 題。"),
        TeacherAction("協助阿柔克漏字", "英文老師", "本週追蹤", "明天", "轉折連接詞判斷不穩。", "示範先判斷前後句關係。"),
        TeacherAction("確認離線包使用", "導師", "已安排", "本週五", "家中網路不穩。", "先下載會考填空包。")
    )

    val syncRecords = listOf(
        SyncRecord("短任務完成紀錄", "已保存", "本機已記錄 1 次 be 動詞練習。"),
        SyncRecord("會考題型進度", "待同步", "學生已開始克漏字與閱讀理解。"),
        SyncRecord("志工接力摘要", "已排隊", "等待雲端後端正式設定。")
    )

    val localAccounts = listOf(
        LocalAccount("小安", AuthContract.ROLE_STUDENT, "YILAN-CHENGZHI-8A", "本機展示帳號"),
        LocalAccount("Emily", AuthContract.ROLE_VOLUNTEER, "MENTOR-GROUP-A", "本機展示帳號"),
        LocalAccount("林老師", AuthContract.ROLE_TEACHER, "CLASS-ENGLISH-02", "本機展示帳號")
    )

    val aiScenarios = listOf(
        AiScenario("會考填空修復", "學生回答 If it will rain tomorrow...", "卡在 if 條件句未來語意。", "先記：if 子句用現在式，主句才用 will。我們只重做一題。", "小安卡在 if 條件句，建議志工用 if it rains / we will stay home 對照。"),
        AiScenario("克漏字降壓", "學生看到短文題組就停住。", "不是整篇都不會，而是不知道先看哪裡。", "先看空格前後一句，判斷是轉折、因果、時間還是例子。", "學生需要先練連接詞和上下文線索。"),
        AiScenario("閱讀理解拆解", "學生說文章太長。", "情緒壓力高，應降低閱讀負擔。", "先讀題目問什麼，再回文章找同義線索，不用逐字翻譯。", "建議老師先示範一題主旨題。")
    )
}
