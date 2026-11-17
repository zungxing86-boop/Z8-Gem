package com.example.data.curriculum

import com.example.data.model.*
import com.example.ui.theme.*

object Prospect2Units5To7 {

    val lesson5 = CurriculumUnit(
        id = 5,
        type = UnitType.LESSON,
        numberLabel = "درس ۵",
        titleEn = "My City",
        titleFa = "شهر من",
        themeFa = "موقعیت جغرافیایی و توصیف شهر",
        functionEn = "Talking about a Place",
        functionFa = "پرسیدن و توصیف موقعیت و امکانات یک شهر",
        pageRange = "صفحات ۴۲ تا ۴۷",
        accentColor = Lesson5Accent,
        keyExpressions = listOf(
            ExpressionPair("Where is Isfahan? — It's in the center of Iran.", "اصفهان کجاست؟ — در مرکز ایران است."),
            ExpressionPair("What's Isfahan like? — It's a big and clean city.", "اصفهان چطور شهری است؟ — شهری بزرگ و تمیز است."),
            ExpressionPair("It's very famous for its mosques and palaces.", "به مساجد و کاخ‌هایش بسیار مشهور است."),
            ExpressionPair("Is there a metro system in the city? — Yes, there is.", "آیا مترو در شهر وجود دارد؟ — بله، هست."),
            ExpressionPair("Are there any museums? — Yes, some great ones.", "آیا موزه‌ای وجود دارد؟ — بله، چند موزه عالی.")
        ),
        conversations = listOf(
            ConversationSection(
                id = "c5_main",
                titleEn = "Conversation: Talking about Isfahan",
                titleFa = "مکالمه اصلی: گفتگو درباره اصفهان",
                contextFa = "فانیندرا (گردشگر هندی) درباره شهر اصفهان از مرتضی سؤال می‌پرسد.",
                bookPage = 42,
                lines = listOf(
                    ConversationLine("Phanindra", "Morteza, tell me about Isfahan. Where is it?", "مرتضی، درباره اصفهان بگو. کجا قرار دارد؟"),
                    ConversationLine("Morteza", "Well, Isfahan's an old city in the center of Iran.", "خب، اصفهان یک شهر قدیمی در مرکز ایران است."),
                    ConversationLine("Phanindra", "What's it like?", "چطور شهری است؟"),
                    ConversationLine("Morteza", "It's a big and clean city.", "شهری بزرگ و تمیز است."),
                    ConversationLine("Phanindra", "Any famous buildings?", "ساختمان‌های مشهوری دارد؟"),
                    ConversationLine("Morteza", "Yes, many. Actually, Isfahan is very famous for its mosques and palaces.", "بله، خیلی. در واقع اصفهان به مساجد و کاخ‌هایش بسیار مشهور است."),
                    ConversationLine("Phanindra", "Are there any museums?", "آیا موزه‌ای وجود دارد؟"),
                    ConversationLine("Morteza", "Yes, some great ones.", "بله، چند موزه عالی دارد."),
                    ConversationLine("Phanindra", "I should see the city soon.", "باید به زودی این شهر را ببینم."),
                    ConversationLine("Morteza", "Sure, and we can have special food downtown.", "حتماً، و می‌توانیم در مرکز شهر غذای مخصوص بخوریم.")
                )
            ),
            ConversationSection(
                id = "c5_spelling",
                titleEn = "Spelling & Pronunciation: South-west",
                titleFa = "مکالمه تلفظ: جهت جنوب‌غربی (صفحه ۴۵)",
                contextFa = "دانش‌آموز درباره تلفظ درست th در کلمه South-west می‌پرسد.",
                bookPage = 45,
                lines = listOf(
                    ConversationLine("Student", "Excuse me, what's جنوب غربی in English?", "ببخشید، جنوب غربی به انگلیسی چه می‌شود؟"),
                    ConversationLine("Teacher", "'South-west'.", "می‌شود South-west."),
                    ConversationLine("Student", "Please say it again.", "لطفاً دوباره بگویید."),
                    ConversationLine("Teacher", "South-west.", "South-west."),
                    ConversationLine("Student", "But my friends say 'sout-west'. Is it correct?", "اما دوستانم می‌گویند «ساوت». آیا درست است؟"),
                    ConversationLine("Teacher", "No, that's not correct. Say 'south' (/saʊθ/).", "نه، درست نیست. بگویید south (نوک زبان بین دندان‌ها).")
                )
            )
        ),
        phonicsSpotlights = listOf(
            PhonicsSpotlight(
                id = "p5_th_ph_que_wh",
                lessonId = 5,
                pattern = "th / ph / que / wh",
                ipaHint = "/θ/ و /f/ و /k/ و /w/",
                explanationFa = "ترکیب th در south صدای /θ/ (نوک زبان بین دندان)، ترکیب ph در Phanindra صدای «ف»، ترکیب que در انتهای mosque صدای «ک» و wh در where/what صدای «و» می‌دهد.",
                mistakeWarningFa = "کلمه mosque را «موسکیو» نخوانید؛ که در انتها ساکن و به صورت /mɒsk/ تلفظ می‌شود.",
                sampleWords = listOf(
                    PhonicsWord("south", "جنوب", "/saʊθ/"),
                    PhonicsWord("north", "شمال", "/nɔːθ/"),
                    PhonicsWord("mosque", "مسجد", "/mɒsk/"),
                    PhonicsWord("where", "کجا", "/weər/"),
                    PhonicsWord("what", "چه / چطور", "/wɒt/"),
                    PhonicsWord("photo", "عکس (ph=/f/)", "/ˈfəʊtəʊ/")
                )
            )
        ),
        grammarTopics = listOf(
            GrammarTopic(
                id = "g5_there_is_are_what_like",
                lessonId = 5,
                titleFa = "ساختار وجود داشتن (Is there / Are there) و توصیف شهر (What's ... like?)",
                titleEn = "There is / There are & What's ... like?",
                stepByStepExplanationFa = listOf(
                    "گام ۱: برای پرسیدن ویژگی‌ها و ظاهر یک مکان می‌پرسیم: What's [City] like? (اینجا like به معنی «دوست داشتن» نیست، به معنی «چطور است» می‌باشد!).",
                    "گام ۲: برای پرسیدن وجود یک مکان مفرد از Is there a + اسم مفرد؟ استفاده می‌کنیم: Is there a stadium?",
                    "گام ۳: برای پرسیدن وجود مکان‌های جمع از Are there any + اسم جمع؟ استفاده می‌کنیم: Are there any museums?",
                    "گام ۴: در پاسخ کوتاه مفرد: Yes, there is / No, there isn't و در جمع: Yes, there are / No, there aren't."
                ),
                formulaEn = "Is there a [Singular]?  |  Are there any [Plural]?  |  What's [Place] like?",
                bookExamples = listOf(
                    ExpressionPair("Where is Maku? — It's in the north-west.", "ماکو کجاست؟ — در شمال‌غربی است."),
                    ExpressionPair("What's Isfahan like? — It's very clean.", "اصفهان چطور شهری است؟ — خیلی تمیز است."),
                    ExpressionPair("Is there a metro system? — Yes, there is.", "آیا مترو وجود دارد؟ — بله، هست."),
                    ExpressionPair("Are there any restaurants? — Yes, many.", "آیا رستوران وجود دارد؟ — بله، زیاد.")
                ),
                extraExamples = listOf(
                    ExpressionPair("Mashhad is in the north-east of Iran. It is famous for its Holy Shrine.", "مشهد در شمال‌شرقی ایران است و به حرم مطهرش مشهور است."),
                    ExpressionPair("Are there any parks near your house? — Yes, there are two big parks.", "آیا پارکی نزدیک خانه‌تان هست؟ — بله، دو پارک بزرگ هست."),
                    ExpressionPair("What's Shiraz like? — It's a beautiful historical city.", "شیراز چطور شهری است؟ — شهری زیبا و تاریخی است.")
                ),
                miniChecks = listOf(
                    GrammarMiniCheck(
                        id = "mc5_1",
                        questionFa = "جمله پرسشی صحیح را انتخاب کنید:",
                        promptEn = "________ any zoos in your city?",
                        options = listOf("Is there", "Are there", "There are"),
                        correctIndex = 1,
                        explanationRightFa = "آفرین! چون zoos اسم جمع است و کلمه any هم آمده، باید با Are there سؤال بسازیم.",
                        explanationWrongFa = "به s جمع در zoos و کلمه any دقت کنید؛ برای اسم‌های جمع حتماً Are there می‌آید."
                    ),
                    GrammarMiniCheck(
                        id = "mc5_2",
                        questionFa = "معنی صحیح سؤال «What's Tehran like?» کدام است؟",
                        promptEn = "What's Tehran like?",
                        options = listOf("تهران چه چیزی را دوست دارد؟", "تهران چطور شهری است؟", "تهران کجاست؟"),
                        correctIndex = 1,
                        explanationRightFa = "عالی! ساختار What + is + مکان + like؟ برای پرسیدن ویژگی‌ها و وصف آن مکان به کار می‌رود.",
                        explanationWrongFa = "در این سؤال، like فعل «دوست داشتن» نیست بلکه به معنی «شبیه / چطور» است؛ پس می‌پرسد: تهران چطور شهری است؟"
                    )
                )
            )
        ),
        exercises = listOf(
            BookExercise(
                id = "ex5_compass",
                lessonId = 5,
                category = ExerciseCategory.COMPREHENSION,
                titleFa = "موقعیت شهرهای مشهور ایران روی نقشه (صفحه ۴۵ و ۷۹)",
                bookPage = 45,
                instructionFa = "بر اساس قطب‌نمای صفحه ۷۹، شهر بندرعباس در جنوب و شهر مشهد در شمال‌شرقی ایران به ترتیب چه نام دارند؟",
                questionEn = "Bandar Abbas is in the ________, and Mashhad is in the ________.",
                options = listOf(
                    "south / north-east",
                    "north / south-west",
                    "east / north-west",
                    "center / south-east"
                ),
                correctAnswer = "south / north-east",
                stepHintsFa = listOf(
                    "راهنمای ۱: جنوب به انگلیسی south و شمال north است.",
                    "راهنمای ۲: شرق east است؛ پس شمال‌شرقی می‌شود north-east."
                ),
                ruleTrickFa = "همیشه اول جهت اصلی عمودی (north / south) و سپس جهت افقی (east / west) می‌آید: north-east, south-west.",
                fullExplanationFa = "جنوب = south و شمال‌شرقی = north-east است."
            ),
            BookExercise(
                id = "ex5_najaf_card",
                lessonId = 5,
                category = ExerciseCategory.PAIR_CARD,
                titleFa = "کارت کار دونفره شهرها (صفحات ۴۶، ۶۴ و ۹۲)",
                bookPage = 92,
                instructionFa = "با ترکیب اطلاعات کارت A صفحه ۶۴ و کارت B صفحه ۹۲، کدام توصیف درباره شهر نجف درست است؟",
                questionEn = "City: Najaf | Country: Iraq | Capital: No | Famous for: Holy Shrine | Airport: Yes | Metro: No",
                options = listOf(
                    "Najaf is the capital of Iraq and has a metro.",
                    "Najaf is in Iraq, famous for its Holy Shrine, and has an airport.",
                    "Najaf has no airport and no museum.",
                    "Najaf is in Iran near Isfahan."
                ),
                correctAnswer = "Najaf is in Iraq, famous for its Holy Shrine, and has an airport.",
                stepHintsFa = listOf(
                    "راهنمای ۱: در کارت A کشور Iraq، مشهور به Holy Shrine و Museum: Yes نوشته شده است.",
                    "راهنمای ۲: در کارت B نام شهر Najaf، Capital: No، Airport: Yes و Metro: No است."
                ),
                ruleTrickFa = "در تمرین‌های Pair Work کتاب، اطلاعات ناقص کارت A در کارت B تکمیل می‌شود و برعکس.",
                fullExplanationFa = "نجف در کشور عراق است، پایتخت نیست، به حرم مطهر مشهور است، فرودگاه و موزه دارد اما مترو ندارد."
            )
        ),
        quizQuestions = listOf(
            QuizQuestion("q5_1", 5, "جهت‌ها", "مخفف NW روی قطب‌نما صفحه ۷۹ چیست؟", "NW stands for:", listOf("North-East", "North-West", "South-West", "New-West"), 1, "N = North و W = West؛ پس NW یعنی North-West (شمال‌غربی)."),
            QuizQuestion("q5_2", 5, "گرامر", "پاسخ منفی به سؤال «Is there a stadium?» چیست؟", "Is there a stadium?", listOf("No, there aren't.", "No, it isn't.", "No, there isn't.", "No, isn't there."), 2, "سؤال با Is there پرسیده شده، پس پاسخ منفی No, there isn't است."),
            QuizQuestion("q5_3", 5, "واژگان", "کلمه «shrine» در صفحه ۴۴ چه معنایی دارد؟", "Mashhad is famous for its shrine.", listOf("پل", "حرم / زیارتگاه", "ورزشگاه", "فرودگاه"), 1, "در Photo Dictionary صفحه ۸۲ نیز shrine با تصویر حرم مطهر نشان داده شده است."),
            QuizQuestion("q5_4", 5, "توصیف شهر", "جای خالی را پر کنید:", "Shiraz is famous ________ its gardens.", listOf("to", "in", "for", "at"), 2, "صفت famous همیشه با حرف اضافه for (به معنی «مشهور به») می‌آید.")
        )
    )

    val lesson6 = CurriculumUnit(
        id = 6,
        type = UnitType.LESSON,
        numberLabel = "درس ۶",
        titleEn = "My Village",
        titleFa = "روستای من",
        themeFa = "توصیف روستا، فصل‌ها و آب و هوا",
        functionEn = "Talking about a Place & Weather",
        functionFa = "توصیف روستا، طبیعت، فصل‌ها و وضعیت آب و هوا",
        pageRange = "صفحات ۴۸ تا ۵۳",
        accentColor = Lesson6Accent,
        keyExpressions = listOf(
            ExpressionPair("Where is Ghez-ghal'eh? — It's in West Azarbaijan.", "قزقلعه کجاست؟ — در آذربایجان غربی است."),
            ExpressionPair("What is it like? — It's a mountain village.", "چطور جایی است؟ — یک روستای کوهستانی است."),
            ExpressionPair("It is famous for its sunflower fields.", "به مزارع آفتابگردانش مشهور است."),
            ExpressionPair("What's the weather like in ...? — It's sunny / windy.", "آب و هوای ... چطور است؟ — آفتابی / بادی است."),
            ExpressionPair("Is it cold in winter? — Yes, it is. / No, it isn't.", "آیا در زمستان سرد است؟ — بله / خیر.")
        ),
        conversations = listOf(
            ConversationSection(
                id = "c6_main",
                titleEn = "Conversation: Ghez-ghal'eh Village",
                titleFa = "مکالمه اصلی: روستای قزقلعه",
                contextFa = "سام درباره روستای زادگاه حمید (قزقلعه در نزدیکی خوی) سؤال می‌پرسد.",
                bookPage = 48,
                lines = listOf(
                    ConversationLine("Sam", "Where are you from, Hamid?", "حمید، اهل کجایی؟"),
                    ConversationLine("Hamid", "Ghez-ghal'eh.", "قزقلعه."),
                    ConversationLine("Sam", "Where is it?", "کجا قرار دارد؟"),
                    ConversationLine("Hamid", "It's a village in West Azarbaijan, near the city of Khoy.", "روستایی در آذربایجان غربی، نزدیک شهر خوی است."),
                    ConversationLine("Sam", "What's it like?", "چطور جایی است؟"),
                    ConversationLine("Hamid", "It's a mountain village with many trees and flowers. It's famous for its sunflower fields.", "یک روستای کوهستانی با درختان و گل‌های زیاد است. به مزارع آفتابگردانش مشهور است."),
                    ConversationLine("Sam", "What's the people's job?", "شغل مردم آنجا چیست؟"),
                    ConversationLine("Hamid", "They work on farms and raise animals.", "آنها در مزرعه کار می‌کنند و حیوانات پرورش می‌دهند."),
                    ConversationLine("Sam", "What about the weather?", "آب و هوایش چطور است؟"),
                    ConversationLine("Hamid", "There's a lot of wind in summer, fall and winter. It's very cold from Aban to Farvardin.", "در تابستان، پاییز و زمستان باد زیادی می‌وزد. از آبان تا فروردین خیلی سرد است."),
                    ConversationLine("Sam", "It sounds to be a very interesting place.", "به نظر می‌رسد جای بسیار جالبی باشد.")
                )
            ),
            ConversationSection(
                id = "c6_spelling",
                titleEn = "Spelling & Pronunciation: Two Sounds of TH",
                titleFa = "مکالمه تلفظ: دو صدای متفاوت T-H (صفحه ۵۱)",
                contextFa = "دانش‌آموز می‌پرسد آیا ترکیب T-H در همه کلمات یکسان تلفظ می‌شود؟",
                bookPage = 51,
                lines = listOf(
                    ConversationLine("Student", "Can you help me, madam?", "خانم، می‌توانید کمکم کنید؟"),
                    ConversationLine("Teacher", "Yes, what's the problem?", "بله، مشکل چیست؟"),
                    ConversationLine("Student", "How do you say T-H? Is it the same in all words?", "T-H چطور تلفظ می‌شود؟ آیا در همه کلمات یکسان است؟"),
                    ConversationLine("Teacher", "No, it's sometimes different.", "نه، گاهی متفاوت است."),
                    ConversationLine("Teacher", "In 'there', 'this', and 'they', it's /ð/. In 'north', 'south', 'thank', and 'three', it's /θ/.", "در there، this و they صدای واک‌دار /ð/ (مثل ذال نرم با لرزش گلو) و در north، south، thank و three صدای بی‌واک /θ/ (مثل ث) دارد.")
                )
            )
        ),
        phonicsSpotlights = listOf(
            PhonicsSpotlight(
                id = "p6_th_fl_tr",
                lessonId = 6,
                pattern = "th (/ð/ vs /θ/) / fl / tr",
                ipaHint = "دو صدای T-H + خوشه‌های fl و tr",
                explanationFa = "برای تست تفاوت th دستتان را روی گلو بگذارید: در there, this, they حنجره می‌لرزد (/ð/)؛ در north, thank, three لرزش وجود ندارد (/θ/). خوشه‌های fl (flower) و tr (tree, tractor) نیز بدون مصوت میانجی خوانده می‌شوند.",
                mistakeWarningFa = "کلمه three (سه) را شبیه tree (درخت) نخوانید! اول three صدای /θ/ دارد.",
                sampleWords = listOf(
                    PhonicsWord("there / this / they", "صدای /ð/ واک‌دار", "/ðeər/"),
                    PhonicsWord("north / thank / three", "صدای /θ/ بی‌واک", "/θriː/"),
                    PhonicsWord("flower", "گل (fl)", "/ˈflaʊər/"),
                    PhonicsWord("field", "مزرعه", "/fiːld/"),
                    PhonicsWord("tree", "درخت (tr)", "/triː/"),
                    PhonicsWord("tractor", "تراکتور (tr)", "/ˈtræktər/")
                )
            )
        ),
        grammarTopics = listOf(
            GrammarTopic(
                id = "g6_weather_seasons",
                lessonId = 6,
                titleFa = "پرسش درباره آب و هوا و تفاوت Is it با Is there",
                titleEn = "Weather Questions & Is it vs. Is there",
                stepByStepExplanationFa = listOf(
                    "گام ۱: برای پرسیدن وضعیت هوا می‌پرسیم: What's the weather like in [Place]? و در جواب از It's + صفت آب و هوا استفاده می‌کنیم: It's sunny / windy / cold.",
                    "گام ۲: به نوع کلمه دقت کنید: صفت‌ها مثل cold, hot, rainy, snowy با It is می‌آیند: Is it cold in winter? — Yes, it is.",
                    "گام ۳: اما اسم‌های غیرقابل شمارش مثل rain (باران)، snow (برف) و wind (باد) با there is و کلمه much می‌آیند: Is there much rain in spring? — Yes, there is.",
                    "گام ۴: قبل از نام فصل‌ها (spring, summer, fall, winter) حرف اضافه in می‌آید: in winter."
                ),
                formulaEn = "It is + [Adjective: cold/rainy]  |  There is (much) + [Noun: rain/wind/snow]",
                bookExamples = listOf(
                    ExpressionPair("What's the weather like in Ghez-ghal'eh? — It's windy in summer.", "هوای قزقلعه چطور است؟ — در تابستان بادی است."),
                    ExpressionPair("Is it cold in winter? — Yes, it is. / No, it isn't.", "آیا در زمستان سرد است؟ — بله / نه."),
                    ExpressionPair("Is there much rain in spring? — Yes, there is.", "آیا در بهار باران زیادی می‌بارد؟ — بله."),
                    ExpressionPair("Are there any fields? — Yes, there are.", "آیا مزرعه‌ای وجود دارد؟ — بله.")
                ),
                extraExamples = listOf(
                    ExpressionPair("It's hot and dry in Yazd in summer.", "هوای یزد در تابستان گرم و خشک است."),
                    ExpressionPair("Is it snowy in Ardabil? — Yes, there is a lot of snow in winter.", "آیا در اردبیل برفی است؟ — بله، در زمستان برف زیادی هست."),
                    ExpressionPair("What's the weather like today? — It's warm and cloudy.", "امروز هوا چطور است؟ — گرم و ابری است.")
                ),
                miniChecks = listOf(
                    GrammarMiniCheck(
                        id = "mc6_1",
                        questionFa = "ساختار درست را انتخاب کنید:",
                        promptEn = "________ much snow in winter?",
                        options = listOf("Is it", "Is there", "Are there"),
                        correctIndex = 1,
                        explanationRightFa = "آفرین! snow (برف) یک اسم است و با much آمده، پس حتماً از Is there استفاده می‌کنیم.",
                        explanationWrongFa = "تفاوت کلیدی: snowy صفت است (Is it snowy?) اما snow اسم است و با Is there می‌آید (Is there much snow?)."
                    ),
                    GrammarMiniCheck(
                        id = "mc6_2",
                        questionFa = "حرف اضافه درست قبل از فصل‌ها چیست؟",
                        promptEn = "It is very warm ________ spring.",
                        options = listOf("on", "in", "at"),
                        correctIndex = 1,
                        explanationRightFa = "درست است! قبل از نام فصل‌ها (in spring, in summer) همیشه in می‌آید.",
                        explanationWrongFa = "یادتان باشد برای فصل‌ها و ماه‌ها حرف اضافه in به کار می‌رود."
                    )
                )
            )
        ),
        exercises = listOf(
            BookExercise(
                id = "ex6_card_natanz",
                lessonId = 6,
                category = ExerciseCategory.PAIR_CARD,
                titleFa = "حدس نام مکان از کارت A و B (صفحات ۵۳، ۶۵ و ۹۳)",
                bookPage = 93,
                instructionFa = "بر اساس کارت صفحه ۶۵ (Village, Mountain: Yes) و صفحه ۹۳ (Center of Iran near Natanz, Hot and Dry)، این روستای کوهستانی مشهور چیست؟",
                questionEn = "Village near Natanz in the center of Iran, with mountains:",
                options = listOf("Ghez-ghal'eh", "Abyaneh", "Masouleh", "Kandovan"),
                correctAnswer = "Abyaneh",
                stepHintsFa = listOf(
                    "راهنمای ۱: موقعیت آن در مرکز ایران نزدیک شهر نطنز (استان اصفهان) است.",
                    "راهنمای ۲: روستای تاریخی ابیانه (Abyaneh) در نزدیکی نطنز قرار دارد."
                ),
                ruleTrickFa = "سؤال صفحه ۵۳ کتاب می‌پرسد: Can you guess the name of the place?",
                fullExplanationFa = "روستای کوهستانی نزدیک نطنز در مرکز ایران، روستای مشهور ابیانه (Abyaneh) است."
            ),
            BookExercise(
                id = "ex6_animals",
                lessonId = 6,
                category = ExerciseCategory.COMPREHENSION,
                titleFa = "تمایز واژگان مزرعه و حیوانات (صفحات ۵۰ و ۸۹)",
                bookPage = 50,
                instructionFa = "کلمه «cattle» در صفحه ۵۰ و ۸۹ کتاب به چه معناست؟",
                questionEn = "Farmers raise cattle on farms. 'Cattle' means:",
                options = listOf("تراکتورها", "گله گاو و گاوداری", "گل‌های آفتابگردان", "گاوآهن"),
                correctAnswer = "گله گاو و گاوداری",
                stepHintsFa = listOf(
                    "راهنمای ۱: تصویر پایین سمت چپ صفحه ۵۰ چند گاو سیاه و سفید در حال چرا را نشان می‌دهد.",
                    "راهنمای ۲: کلمه cow برای یک گاو و cattle برای گله گاوها استفاده می‌شود."
                ),
                ruleTrickFa = "کلمه cattle همیشه معنای جمع دارد و s جمع نمی‌گیرد.",
                fullExplanationFa = "کلمه cattle به معنی گله گاو است."
            )
        ),
        quizQuestions = listOf(
            QuizQuestion("q6_1", 6, "تلفظ", "در کدام کلمه صدای T-H مانند /ð/ (واک‌دار مثل they) است؟", "Same 'th' sound as 'they':", listOf("north", "south", "three", "this"), 3, "در صفحه ۵۱ کتاب آمده: there, this, they صدای /ð/ و north, south, thank, three صدای /θ/ دارند."),
            QuizQuestion("q6_2", 6, "گرامر", "جمله درست را انتخاب کنید:", "What's the weather like?", listOf("It's wind.", "It's windy.", "It has windy.", "There is windy."), 1, "بعد از It's باید صفت (windy) بیاید نه اسم (wind)."),
            QuizQuestion("q6_3", 6, "واژگان", "معنی کلمه «plow» در صفحه ۵۰ چیست؟", "plow", listOf("تراکتور", "مزرعه", "گاوآهن / شخم زدن", "تپه"), 2, "در تصویر صفحه ۵۰ و ۸۸، plow با تصویر شخم زدن با گاو نمایش داده شده است."),
            QuizQuestion("q6_4", 6, "مکالمه", "مردم قزقلعه چه شغلی دارند؟", "What's the people's job in Ghez-ghal'eh?", listOf("Work in offices", "Work on farms and raise animals", "Work in museums", "Play tennis"), 1, "در مکالمه صفحه ۴۸ حمید می‌گوید: They work on farms and raise animals.")
        )
    )

    val lesson7 = CurriculumUnit(
        id = 7,
        type = UnitType.LESSON,
        numberLabel = "درس ۷",
        titleEn = "My Hobbies",
        titleFa = "سرگرمی‌های من",
        themeFa = "سرگرمی‌ها و فعالیت‌های اوقات فراغت",
        functionEn = "Talking about Hobbies & Free Time",
        functionFa = "گفتگو درباره علاقه‌مندی‌ها، سرگرمی‌ها و اوقات فراغت",
        pageRange = "صفحات ۵۴ تا ۵۹",
        accentColor = Lesson7Accent,
        keyExpressions = listOf(
            ExpressionPair("Do you have any hobbies? — Yes, listening to stories on the radio.", "آیا سرگرمی‌ای دارید؟ — بله، گوش دادن به داستان از رادیو."),
            ExpressionPair("What's your hobby? — Playing tennis.", "سرگرمی شما چیست؟ — تنیس بازی کردن."),
            ExpressionPair("What do you do in your free time? — I usually go shopping.", "در اوقات فراغت چکار می‌کنید؟ — معمولاً به خرید می‌روم."),
            ExpressionPair("How about ...?", "در مورد ... چطور؟")
        ),
        conversations = listOf(
            ConversationSection(
                id = "c7_main",
                titleEn = "Conversation: Talking about Hobbies",
                titleFa = "مکالمه اصلی: سرگرمی‌های زهرا، سمیرا و خانم امامی",
                contextFa = "خانم امامی (معلم زبان) با دو دانش‌آموزش زهرا و سمیرا درباره سرگرمی‌ها و اوقات فراغت گفتگو می‌کند.",
                bookPage = 54,
                lines = listOf(
                    ConversationLine("Teacher", "Do you have any hobbies, Zahra?", "زهرا، آیا سرگرمی‌ای داری؟"),
                    ConversationLine("Zahra", "Yes, I do. I watch movies as a hobby.", "بله، دارم. به عنوان سرگرمی فیلم تماشا می‌کنم."),
                    ConversationLine("Teacher", "Interesting! How about you, Samira?", "جالبه! تو چطور، سمیرا؟"),
                    ConversationLine("Samira", "Well, I love reading.", "خب، من عاشق مطالعه کردن هستم."),
                    ConversationLine("Zahra", "Really? What sort of things do you read?", "واقعاً؟ چه جور چیزهایی می‌خوانی؟"),
                    ConversationLine("Samira", "Books, magazines, sports news on the Net, and sometimes poems.", "کتاب، مجله، اخبار ورزشی در اینترنت و گاهی هم شعر."),
                    ConversationLine("Zahra", "And how about you, Mrs. Emami?", "و شما چطور، خانم امامی؟"),
                    ConversationLine("Teacher", "Actually, I don't have any hobbies. But I usually go to the gym in my free time.", "راستش من سرگرمی خاصی ندارم. اما در اوقات فراغتم معمولاً به باشگاه می‌روم.")
                )
            ),
            ConversationSection(
                id = "c7_spelling",
                titleEn = "Spelling & Pronunciation: -ing Ending",
                titleFa = "مکالمه تلفظ: پایان -ing در کلمات (صفحه ۵۷)",
                contextFa = "دانش‌آموز درباره تلفظ حرف g در پایان کلماتی مثل reading و browsing سؤال می‌پرسد.",
                bookPage = 57,
                lines = listOf(
                    ConversationLine("Student", "Excuse me. How do you say I-N-G in 'reading', 'playing', and 'searching'?", "ببخشید. I-N-G در کلمات reading، playing و searching چطور تلفظ می‌شود؟"),
                    ConversationLine("Teacher", "Oh, there's no /g/ at the end. Say 'reading', 'playing', and 'searching'.", "اوه، هیچ صدای /g/ (گ) در انتها نیست! بگویید reading، playing و searching."),
                    ConversationLine("Student", "Thanks. How about B-R-O-W-S-I-N-G? How do you say it?", "ممنون. در مورد B-R-O-W-S-I-N-G چطور؟"),
                    ConversationLine("Teacher", "That's 'browsing'.", "تلفظش «browsing» (/ˈbraʊzɪŋ/) است.")
                )
            )
        ),
        phonicsSpotlights = listOf(
            PhonicsSpotlight(
                id = "p7_ing_ow_ou",
                lessonId = 7,
                pattern = "-ing / ow / ou",
                ipaHint = "صدای خیشومی /ŋ/ بدون گ پایانی | /aʊ/",
                explanationFa = "در پایان کلمات ingدار، صدای «گ» تلفظ نمی‌شود و صدا از بینی خارج می‌شود (/ŋ/). ترکیب ow در browsing و ou در about صدای «اَو» (/aʊ/) می‌دهند.",
                mistakeWarningFa = "کلماتی مثل reading و playing را با صدای «گ» محکم در انتها (ریدینگْگ) تلفظ نکنید!",
                sampleWords = listOf(
                    PhonicsWord("reading", "خواندن", "/ˈriːdɪŋ/"),
                    PhonicsWord("playing", "بازی کردن", "/ˈpleɪɪŋ/"),
                    PhonicsWord("searching", "جستجو کردن", "/ˈsɜːtʃɪŋ/"),
                    PhonicsWord("browsing", "مرور کردن (ow=/aʊ/)", "/ˈbraʊzɪŋ/"),
                    PhonicsWord("about", "درباره (ou=/aʊ/)", "/əˈbaʊt/"),
                    PhonicsWord("fishing", "ماهیگیری", "/ˈfɪʃɪŋ/")
                )
            )
        ),
        grammarTopics = listOf(
            GrammarTopic(
                id = "g7_hobbies_gerund",
                lessonId = 7,
                titleFa = "بیان سرگرمی‌ها با اسم مصدر (Verb-ing) و افعال like / love / enjoy",
                titleEn = "Hobbies & Free Time (like / love / enjoy + Verb-ing)",
                stepByStepExplanationFa = listOf(
                    "گام ۱: برای نام بردن سرگرمی به عنوان «اسم کار»، به فعل ing اضافه می‌کنیم: Reading is my hobby / Listening to stories.",
                    "گام ۲: بعد از افعال علاقه مثل enjoy (لذت بردن)، like (دوست داشتن) و love (عاشق بودن)، معمولاً فعل دوم به صورت ingدار می‌آید: I enjoy searching the Web.",
                    "گام ۳: اما اگر عبارت like to باشد، بعد از to شکل ساده فعل می‌آید: What do you like to do?",
                    "گام ۴: با فعالیت‌هایی مثل shopping، horse riding، fishing، skiing معمولاً از فعل go استفاده می‌کنیم: I go horse riding."
                ),
                formulaEn = "I enjoy/love + [Verb-ing]  |  I go + [Activity-ing]  |  I like to + [Base Verb]",
                bookExamples = listOf(
                    ExpressionPair("What's your hobby? — Listening to stories on the radio.", "سرگرمی‌ات چیست؟ — گوش دادن به داستان از رادیو."),
                    ExpressionPair("I enjoy searching the Web.", "از جستجو در اینترنت لذت می‌برم."),
                    ExpressionPair("I like playing computer games.", "بازی کردن با رایانه را دوست دارم."),
                    ExpressionPair("I go horse riding. / I usually go shopping.", "اسب‌سواری می‌روم. / معمولاً به خرید می‌روم.")
                ),
                extraExamples = listOf(
                    ExpressionPair("My father enjoys working in the garden on Fridays.", "پدرم جمعه‌ها از باغبانی لذت می‌برد."),
                    ExpressionPair("What do you like to do in your free time? — I like to walk in the park.", "در اوقات فراغت چکار دوست داری بکنی؟ — دوست دارم در پارک قدم بزنم."),
                    ExpressionPair("We usually go skiing in winter and go fishing in summer.", "معمولاً در زمستان اسکی و در تابستان ماهیگیری می‌رویم.")
                ),
                miniChecks = listOf(
                    GrammarMiniCheck(
                        id = "mc7_1",
                        questionFa = "شکل درست فعل بعد از enjoy را انتخاب کنید:",
                        promptEn = "I enjoy ________ the Internet.",
                        options = listOf("browse", "browsing", "browses"),
                        correctIndex = 1,
                        explanationRightFa = "آفرین! بعد از فعل enjoy همیشه فعل با ing می‌آید (browsing).",
                        explanationWrongFa = "دقت کنید: فعل enjoy همیشه همراه با اسم مصدر (فعل + ing) به کار می‌رود."
                    ),
                    GrammarMiniCheck(
                        id = "mc7_2",
                        questionFa = "فعل مناسب برای فعالیت‌هایی مانند shopping و fishing کدام است؟",
                        promptEn = "In my free time, I usually ________ horse riding.",
                        options = listOf("play", "do", "go"),
                        correctIndex = 2,
                        explanationRightFa = "عالی! برای فعالیت‌های بیرونی که به -ing ختم می‌شوند مثل shopping, fishing, skiing, horse riding از فعل go استفاده می‌کنیم.",
                        explanationWrongFa = "برای ورزش‌های توپی و فکری مثل tennis یا chess از play، اما برای فعالیت‌هایی مثل shopping و horse riding از go استفاده می‌شود."
                    )
                )
            )
        ),
        exercises = listOf(
            BookExercise(
                id = "ex7_group_table",
                lessonId = 7,
                category = ExerciseCategory.TABLE_FILL,
                titleFa = "تفکیک سرگرمی (Hobby) و فعالیت فراغت (Free Time) — صفحه ۵۸",
                bookPage = 58,
                instructionFa = "بر اساس مکالمه صفحه ۵۴، خانم امامی درباره سرگرمی و اوقات فراغتش چه می‌گوید؟",
                questionEn = "Mrs. Emami says: 'I don't have any hobbies, but I usually ________ in my free time.'",
                options = listOf("watch movies", "go to the gym", "read poems", "go fishing"),
                correctAnswer = "go to the gym",
                stepHintsFa = listOf(
                    "راهنمای ۱: به خط آخر مکالمه صفحه ۵۴ نگاه کنید.",
                    "راهنمای ۲: خانم امامی می‌گوید در اوقات فراغتش معمولاً به باشگاه (gym) می‌رود."
                ),
                ruleTrickFa = "usually (معمولاً) یک قید تکرار است که قبل از فعل اصلی می‌آید.",
                fullExplanationFa = "خانم امامی در مکالمه صفحه ۵۴ می‌گوید: I usually go to the gym in my free time."
            ),
            BookExercise(
                id = "ex7_word_form",
                lessonId = 7,
                category = ExerciseCategory.SPELLING_CHECK,
                titleFa = "املای درست فعل‌ها هنگام گرفتن ing",
                bookPage = 57,
                instructionFa = "کدام گروه از نظر املای -ing کاملاً درست نوشته شده است؟",
                questionEn = "Choose the correctly spelled -ing words:",
                options = listOf(
                    "browsing, riding, reading, swimming",
                    "browseing, rideing, readding, swiming",
                    "browsing, rideing, reading, swimming",
                    "brousing, riding, reading, swiming"
                ),
                correctAnswer = "browsing, riding, reading, swimming",
                stepHintsFa = listOf(
                    "راهنمای ۱: اگر فعلی به e ساکن ختم شود (مثل browse, ride)، هنگام اضافه کردن ing حرف e حذف می‌شود.",
                    "راهنمای ۲: در swim تک‌هجایی قبل از m یک مصوت کوتاه است، پس m دوتا می‌شود (swimming)."
                ),
                ruleTrickFa = "حذف e آخر (ride → riding) + دو برابر شدن حرف آخر در افعال کوتاه (swim → swimming).",
                fullExplanationFa = "املای صحیح: browsing، riding، reading، swimming."
            )
        ),
        quizQuestions = listOf(
            QuizQuestion("q7_1", 7, "تلفظ", "در پایان کلماتی مانند playing و searching چه صدایی نباید شنیده شود؟", "There is no /__/ at the end of 'playing'.", listOf("/n/", "/g/", "/ɪ/", "/ŋ/"), 1, "طبق مکالمه صفحه ۵۷، صدای /g/ در پایان -ing تلفظ نمی‌شود."),
            QuizQuestion("q7_2", 7, "واژگان", "عبارت «working in the garden» در صفحه ۹۱ یعنی چه؟", "working in the garden", listOf("قدم زدن در پارک", "کار در باغچه / باغبانی", "گشت‌وگذار در اینترنت", "ماهیگیری"), 1, "در Photo Dictionary صفحه ۹۱ با تصویر باغبانی نشان داده شده است."),
            QuizQuestion("q7_3", 7, "گرامر", "جمله درست را انتخاب کنید:", "What do you ________ in your free time?", listOf("do", "doing", "does", "to do"), 0, "بعد از What do you فعل اصلی به صورت ساده (do) می‌آید."),
            QuizQuestion("q7_4", 7, "مکالمه", "سمیرا چه چیزهایی می‌خواند؟", "What sort of things does Samira read?", listOf("Only books", "Books, magazines, sports news on the Net, and poems", "Only newspapers", "Letters"), 1, "در صفحه ۵۴ سمیرا دقیقاً این چهار مورد را نام می‌برد.")
        )
    )

    val review3 = CurriculumUnit(
        id = 103,
        type = UnitType.REVIEW,
        numberLabel = "مرور ۳",
        titleEn = "Review 3 (Lessons 5–7)",
        titleFa = "مرور جامع درس‌های ۵، ۶ و ۷",
        themeFa = "تثبیت شهر، روستا، آب و هوا و سرگرمی‌ها",
        functionEn = "Final Book Consolidation",
        functionFa = "ارزیابی پایانی درس‌های ۵ تا ۷ (صفحات ۶۰ تا ۶۳)",
        pageRange = "صفحات ۶۰ تا ۶۳",
        accentColor = ReviewAccent,
        keyExpressions = listOf(
            ExpressionPair("It is ... / It is famous for ...", "اینجا ... است / به ... مشهور است"),
            ExpressionPair("There is ... / There are ...", "وجود دارد (مفرد) / وجود دارند (جمع)"),
            ExpressionPair("What is the weather like? — It is ...", "آب و هوا چطور است؟ — ... است"),
            ExpressionPair("What do you do in your free time? — I ...", "در اوقات فراغت چکار می‌کنی؟ — من ...")
        ),
        conversations = emptyList(),
        phonicsSpotlights = lesson5.phonicsSpotlights + lesson6.phonicsSpotlights + lesson7.phonicsSpotlights,
        grammarTopics = emptyList(),
        exercises = listOf(
            BookExercise(
                id = "rev3_ex1",
                lessonId = 103,
                category = ExerciseCategory.REVIEW_CHECKLIST,
                titleFa = "چک‌لیست مرور ۳ — موقعیت جغرافیایی ۵ شهر ایران (صفحه ۶۲)",
                bookPage = 62,
                instructionFa = "کدام گزینه موقعیت جغرافیایی شهرهای ایران را درست بیان کرده است؟",
                questionEn = "Select the accurate geographical statement:",
                options = listOf(
                    "Isfahan is in the north-west; Maku is in the south.",
                    "Isfahan is in the center; Maku is in the north-west; Karaj is near the capital.",
                    "Tabriz is in the south-east; Shiraz is in the north.",
                    "Mashhad is in the south-west of Iran."
                ),
                correctAnswer = "Isfahan is in the center; Maku is in the north-west; Karaj is near the capital.",
                stepHintsFa = listOf(
                    "راهنمای ۱: در درس ۵ خواندیم اصفهان در مرکز (center)، ماکو در شمال‌غرب (north-west) و کرج نزدیک پایتخت (near the capital) است."
                ),
                ruleTrickFa = "به Practice 1 صفحه ۴۳ مراجعه کنید.",
                fullExplanationFa = "موقعیت دقیق طبق درس ۵: اصفهان در مرکز، ماکو در شمال‌غربی و کرج نزدیک پایتخت است."
            ),
            BookExercise(
                id = "rev3_ex2",
                lessonId = 103,
                category = ExerciseCategory.REVIEW_CHECKLIST,
                titleFa = "چک‌لیست مرور ۳ — تطبیق الگوهای تلفظ صفحه ۶۳",
                bookPage = 63,
                instructionFa = "برای الگوهای th, ph, que, wh, fl, tr, ing, ow, ou کلمات درست کدامند؟",
                questionEn = "Match: th, ph, que, wh, fl, tr, ing, ow, ou",
                options = listOf(
                    "south, photo, mosque, where, flower, tree, reading, browsing, about",
                    "tree, flower, where, mosque, photo, south, about, browsing, reading",
                    "chair, shoes, week, stay, football, cook, health, toothache, flu",
                    "there, France, Spain, Brazil, study, play, swim, gym, park"
                ),
                correctAnswer = "south, photo, mosque, where, flower, tree, reading, browsing, about",
                stepHintsFa = listOf(
                    "راهنمای ۱: الگوهای صفحه‌ ۶۳ به ترتیب درس ۵ (th, ph, que, wh)، درس ۶ (fl, tr) و درس ۷ (ing, ow, ou) هستند."
                ),
                ruleTrickFa = "مرور ۳ شامل هر ۹ ترکیب حروف از سه درس پایانی کتاب است.",
                fullExplanationFa = "تطبیق کامل صفحه ۶۳: th→south, ph→photo, que→mosque, wh→where, fl→flower, tr→tree, ing→reading, ow→browsing, ou→about."
            )
        ),
        quizQuestions = listOf(
            QuizQuestion("qr3_1", 103, "مرور شهر و روستا", "کدام سؤال برای پرسیدن وضعیت آب و هوا درست است؟", "Choose the correct question:", listOf("How is the weather like?", "What's the weather like?", "Where is the weather?", "Is the weather like?"), 1, "ساختار استاندارد در درس ۵ و ۶: What's ... like? است."),
            QuizQuestion("qr3_2", 103, "مرور سرگرمی", "جمله درست را انتخاب کنید:", "Do you have any hobbies?", listOf("Yes, listen to the radio.", "Yes, listening to the radio.", "Yes, I am listen.", "Yes, I listening."), 1, "در پاسخ کوتاه به جای اسم سرگرمی از شکل -ing (listening) استفاده می‌کنیم."),
            QuizQuestion("qr3_3", 103, "مرور تلفظ", "کدام کلمه دارای ترکیب que با صدای /k/ در انتهاست؟", "Word ending in 'que':", listOf("question", "mosque", "quick", "square"), 1, "کلمه mosque به معنی مسجد با que تمام می‌شود و /mɒsk/ تلفظ می‌شود.")
        )
    )
}

object Prospect2Catalog {
    val units: List<CurriculumUnit> = listOf(
        Prospect2Units1To4.lesson1,
        Prospect2Units1To4.lesson2,
        Prospect2Units1To4.review1,
        Prospect2Units1To4.lesson3,
        Prospect2Units1To4.lesson4,
        Prospect2Units1To4.review2,
        Prospect2Units5To7.lesson5,
        Prospect2Units5To7.lesson6,
        Prospect2Units5To7.lesson7,
        Prospect2Units5To7.review3
    )

    val allWords: List<VocabWord> = Prospect2VocabData.allWords

    fun unitById(id: Int): CurriculumUnit = units.firstOrNull { it.id == id } ?: units.first()

    fun wordsForUnit(unitId: Int): List<VocabWord> = when (unitId) {
        101 -> allWords.filter { it.lessonId in listOf(1, 2) }
        102 -> allWords.filter { it.lessonId in listOf(3, 4) }
        103 -> allWords.filter { it.lessonId in listOf(5, 6, 7) }
        else -> allWords.filter { it.lessonId == unitId }
    }

    fun nextUnitId(currentId: Int): Int? {
        val idx = units.indexOfFirst { it.id == currentId }
        return if (idx in 0 until units.lastIndex) units[idx + 1].id else null
    }
}
