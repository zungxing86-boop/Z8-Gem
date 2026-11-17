package com.example.data.curriculum

import com.example.data.model.*
import com.example.ui.theme.*

object Prospect2Units1To4 {

    val lesson1 = CurriculumUnit(
        id = 1,
        type = UnitType.LESSON,
        numberLabel = "درس ۱",
        titleEn = "My Nationality",
        titleFa = "ملیت من",
        themeFa = "معرفی کشورها و ملیت‌ها",
        functionEn = "Talking about Nationalities",
        functionFa = "پرسیدن و گفتن ملیت و کشور مبدأ",
        pageRange = "صفحات ۱۲ تا ۱۷",
        accentColor = Lesson1Accent,
        keyExpressions = listOf(
            ExpressionPair("Where are you from?", "اهل کجا هستید؟"),
            ExpressionPair("I'm from Iran. / I'm Iranian.", "من اهل ایران هستم. / من ایرانی هستم."),
            ExpressionPair("Are you from Iran? — Yes, I am. / No, I'm not.", "آیا اهل ایران هستید؟ — بله، هستم. / خیر، نیستم."),
            ExpressionPair("Are you Iranian?", "آیا ایرانی هستید؟"),
            ExpressionPair("Which is correct?", "کدام درست است؟")
        ),
        conversations = listOf(
            ConversationSection(
                id = "c1_main",
                titleEn = "Conversation: Introducing Sam",
                titleFa = "مکالمه اصلی: معرفی پسرعمو به معلم",
                contextFa = "شایان پسرعمویش سام را به معلم خود آقای چایچی معرفی می‌کند.",
                bookPage = 12,
                lines = listOf(
                    ConversationLine("Shayan", "Mr. Chaychi, this is my cousin Sam. He speaks French, English, and a little Persian.", "آقای چایچی، این پسرعموی من سام است. او فرانسوی، انگلیسی و کمی فارسی صحبت می‌کند."),
                    ConversationLine("Teacher", "Oh, nice to meet you, Sam.", "اوه، از آشنایی با شما خوشوقتم، سام."),
                    ConversationLine("Sam", "Nice to meet you, too.", "من هم از آشنایی با شما خوشوقتم."),
                    ConversationLine("Teacher", "Are you from Iran?", "آیا اهل ایران هستید؟"),
                    ConversationLine("Sam", "Yes, I'm originally Iranian, but I live in France.", "بله، من اصالتاً ایرانی هستم، اما در فرانسه زندگی می‌کنم."),
                    ConversationLine("Teacher", "Welcome to our class. How do you like it in Iran?", "به کلاس ما خوش آمدید. ایران را چطور می‌بینید؟"),
                    ConversationLine("Sam", "Iran is great! I love it. It's a beautiful country.", "ایران عالی است! عاشقش هستم. کشور بسیار زیبایی است.")
                )
            ),
            ConversationSection(
                id = "c1_spelling",
                titleEn = "Spelling & Pronunciation Dialogue",
                titleFa = "مکالمه املا و تلفظ (صفحه ۱۵)",
                contextFa = "دو دانش‌آموز در حال حل جدول کلمات متقاطع هستند و درباره املای درست می‌پرسند.",
                bookPage = 15,
                lines = listOf(
                    ConversationLine("Student 1", "How do you spell \"chair\"?", "کلمه chair را چطور هجی می‌کنی؟"),
                    ConversationLine("Student 2", "That's C-H-A-I-R.", "به صورت C-H-A-I-R."),
                    ConversationLine("Student 1", "And which is correct for کفش in English, S-H-O-O-S or S-H-O-E-S?", "و کدام املا برای کفش در انگلیسی درست است، S-H-O-O-S یا S-H-O-E-S؟"),
                    ConversationLine("Student 2", "S-H-O-E-S.", "S-H-O-E-S درست است."),
                    ConversationLine("Student 1", "Thanks.", "ممنون.")
                )
            )
        ),
        phonicsSpotlights = listOf(
            PhonicsSpotlight(
                id = "p1_ch_sh",
                lessonId = 1,
                pattern = "ch / sh",
                ipaHint = "/tʃ/ و /ʃ/",
                explanationFa = "دو حرف کنار هم (digraphs) در انگلیسی یک صدای واحد می‌سازند: ch صدای «چ» و sh صدای «ش» می‌دهد.",
                mistakeWarningFa = "دقت کنید کلمه shoes با oe نوشته می‌شود نه oo!",
                sampleWords = listOf(
                    PhonicsWord("chair", "صندلی", "/tʃeər/"),
                    PhonicsWord("China", "چین", "/ˈtʃaɪnə/"),
                    PhonicsWord("chick", "جوجه", "/tʃɪk/"),
                    PhonicsWord("peach", "هلو", "/piːtʃ/"),
                    PhonicsWord("shoes", "کفش‌ها", "/ʃuːz/"),
                    PhonicsWord("fish", "ماهی", "/fɪʃ/")
                )
            ),
            PhonicsSpotlight(
                id = "p1_blends",
                lessonId = 1,
                pattern = "fr / sp / br",
                ipaHint = "خوشه‌های همخوان (Blends)",
                explanationFa = "در آغاز کلماتی مانند France، Spain و Brazil دو حرف صامت پشت‌سرهم بدون مصوت میانجی تلفظ می‌شوند.",
                mistakeWarningFa = "اشتباه رایج فارسی‌زبانان اضافه کردن صدای «اِ» اول Spain (اِسپین) است؛ مستقیماً با صدای «س» شروع کنید!",
                sampleWords = listOf(
                    PhonicsWord("France", "فرانسه", "/frɑːns/"),
                    PhonicsWord("French", "فرانسوی", "/frentʃ/"),
                    PhonicsWord("Spain", "اسپانیا", "/speɪn/"),
                    PhonicsWord("Spanish", "اسپانیایی", "/ˈspænɪʃ/"),
                    PhonicsWord("Brazil", "برزیل", "/brəˈzɪl/"),
                    PhonicsWord("British", "بریتانیایی", "/ˈbrɪtɪʃ/")
                )
            )
        ),
        grammarTopics = listOf(
            GrammarTopic(
                id = "g1_country_nationality",
                lessonId = 1,
                titleFa = "تفاوت بیان کشور با from و بیان ملیت (صفت)",
                titleEn = "Countries vs. Nationalities (to be + from)",
                stepByStepExplanationFa = listOf(
                    "گام ۱: اگر می‌خواهید اسم کشور را بگویید، حتماً قبل از نام کشور از کلمه from استفاده کنید (مثال: I am from Iran).",
                    "گام ۲: اگر می‌خواهید ملیت (صفت) را بگویید، دیگر from لازم نیست (مثال: I am Iranian).",
                    "گام ۳: برای پرسیدن کشور مبدأ می‌پرسیم: Where + am/is/are + فاعل + from?",
                    "گام ۴: در پاسخ کوتاه بله/خیر (Yes/No)، با همان فعل to be جواب می‌دهیم: Yes, I am / No, she isn't."
                ),
                formulaEn = "Subject + am/is/are + from + [Country]  |  Subject + am/is/are + [Nationality]",
                bookExamples = listOf(
                    ExpressionPair("Are you from Iran? — Yes, I am. / No, I'm not.", "آیا اهل ایران هستید؟ — بله، هستم. / نه، نیستم."),
                    ExpressionPair("Is she from China? — Yes, she is.", "آیا او اهل چین است؟ — بله، هست."),
                    ExpressionPair("Are they Spanish? — No, they aren't.", "آیا آنها اسپانیایی هستند؟ — نه، نیستند."),
                    ExpressionPair("Where is Mark from? — He's from France.", "مارک اهل کجاست؟ — او اهل فرانسه است.")
                ),
                extraExamples = listOf(
                    ExpressionPair("My uncle is from Japan, so he is Japanese.", "دایی من اهل ژاپن است، پس ژاپنی است."),
                    ExpressionPair("Are we Brazilian? — No, we're not. We're Iranian.", "آیا ما برزیلی هستیم؟ — نه، نیستیم. ما ایرانی هستیم."),
                    ExpressionPair("Where are Ali and Reza from? — They're from Italy.", "علی و رضا اهل کجا هستند؟ — آنها اهل ایتالیا هستند.")
                ),
                miniChecks = listOf(
                    GrammarMiniCheck(
                        id = "mc1_1",
                        questionFa = "جمله صحیح را انتخاب کنید:",
                        promptEn = "Where is Mina from? — ________.",
                        options = listOf("She is from Iranian.", "She is from Iran.", "She from Iran."),
                        correctIndex = 1,
                        explanationRightFa = "آفرین! بعد از from همیشه نام کشور (Iran) می‌آید و فعل is هم سر جایش است.",
                        explanationWrongFa = "دقت کنید: بعد از from باید نام کشور (Iran) بیاید نه ملیت (Iranian)، و جمله حتماً به فعل is نیاز دارد."
                    ),
                    GrammarMiniCheck(
                        id = "mc1_2",
                        questionFa = "پاسخ کوتاه درست چیست؟",
                        promptEn = "Are they from Brazil? — No, ________.",
                        options = listOf("they isn't", "we aren't", "they aren't"),
                        correctIndex = 2,
                        explanationRightFa = "عالی! وقتی سؤال با Are they پرسیده می‌شود، پاسخ منفی با they aren't داده می‌شود.",
                        explanationWrongFa = "چون فاعل سؤال they است، پاسخ هم باید با ضمیر they و فعل منفی aren't باشد."
                    )
                )
            )
        ),
        exercises = listOf(
            BookExercise(
                id = "ex1_crossword_1",
                lessonId = 1,
                category = ExerciseCategory.CROSSWORD,
                titleFa = "حل جدول صفحه ۱۶ — شماره ۱ عمودی (Down)",
                bookPage = 16,
                instructionFa = "نام میوه تصویر صفحه ۱۶ را با حروف درست انگلیسی بنویسید.",
                questionEn = "1 Down: A fruit (۵ حرفی، با p شروع و به ch ختم می‌شود)",
                options = listOf("peach", "peech", "apple", "pear"),
                correctAnswer = "peach",
                stepHintsFa = listOf(
                    "راهنمای ۱: تصویر یک میوه نارنجی-قرمز تابستانی است که در فارسی «هلو» نام دارد.",
                    "راهنمای ۲: این کلمه ۵ حرف دارد و از ترکیب ch که در صفحه ۱۵ خواندید استفاده می‌کند.",
                    "راهنمای ۳: صدای کشیده «ای» وسط آن با دو حرف ea نوشته می‌شود: p-e-a-c-h."
                ),
                ruleTrickFa = "ترفند املا: ترکیب ea در بسیاری از واژگان مثل peach صدای «ای» کشیده می‌سازد و ch در انتها صدای «چ» می‌دهد.",
                fullExplanationFa = "پاسخ صحیح peach است. املای p-e-a-c-h دارای دو حرف آخر ch از بخش تلفظ و املای درس اول است."
            ),
            BookExercise(
                id = "ex1_crossword_2",
                lessonId = 1,
                category = ExerciseCategory.CROSSWORD,
                titleFa = "حل جدول صفحه ۱۶ — شماره ۲ افقی (Across)",
                bookPage = 16,
                instructionFa = "ملیت فردی که اهل چین (From China) است را مشخص کنید.",
                questionEn = "2 Across: From China → Nationality is ________",
                options = listOf("China", "Chinian", "Chinese", "Chineese"),
                correctAnswer = "Chinese",
                stepHintsFa = listOf(
                    "راهنمای ۱: به دنبال «ملیت» فردی از کشور China هستیم، نه نام خود کشور.",
                    "راهنمای ۲: ملیت کشورهایی مثل China و Japan معمولاً با پسوند -ese ساخته می‌شود.",
                    "راهنمای ۳: حروف را در جدول بشمارید: C-H-I-N-E-S-E (۷ حرفی، با یک e بعد از n)."
                ),
                ruleTrickFa = "قاعده ملیت‌ها: کشورهایی که با -a یا -n تمام می‌شوند گاهی پسوند -ese می‌گیرند (China → Chinese, Japan → Japanese).",
                fullExplanationFa = "فرد اهل کشور China دارای ملیت Chinese (چینی) است."
            ),
            BookExercise(
                id = "ex1_pair_card",
                lessonId = 1,
                category = ExerciseCategory.PAIR_CARD,
                titleFa = "تکمیل کارت اطلاعات (صفحات ۱۷، ۶۴ و ۹۲)",
                bookPage = 64,
                instructionFa = "بر اساس کارت دانش‌آموز A (صفحه ۶۴) و کارت B (صفحه ۹۲)، جمله کامل درباره فرانکو چیست؟",
                questionEn = "Name: Franco Sanchez | Age: 27 | Job: Mechanic | Nationality: Spanish. He is from ________.",
                options = listOf("France", "Spain", "Brazil", "Italy"),
                correctAnswer = "Spain",
                stepHintsFa = listOf(
                    "راهنمای ۱: در کارت صفحه ۶۴ ملیت فرانکو Spanish نوشته شده است.",
                    "راهنمای ۲: کدام کشور ملیتش Spanish است؟",
                    "راهنمای ۳: پس از He is from باید نام کشور یعنی Spain بیاید."
                ),
                ruleTrickFa = "همیشه دقت کنید جمله from دارد یا نه! اگر from بود نام کشور (Spain) و اگر نبود صفت ملیت (Spanish) می‌آید.",
                fullExplanationFa = "ملیت Spanish مربوط به کشور اسپانیا (Spain) است؛ پس جمله درست He is from Spain می‌شود."
            )
        ),
        quizQuestions = listOf(
            QuizQuestion("q1_1", 1, "ملیت و کشور", "اگر کسی اهل انگلستان باشد، ملیت او چیست؟", "She is from England. She is ________.", listOf("Englisher", "British", "France", "Spain"), 1, "در کتاب درسی صفحه ۱۳ ملیت کشور England به صورت British آموزش داده شده است."),
            QuizQuestion("q1_2", 1, "ساختار سؤال", "کدام سؤال برای پرسیدن کشور مبدأ درست است؟", "________?", listOf("Where you are from", "Where are you from", "Are where you from", "From where you are"), 1, "در جملات سؤالی با کلمه پرسشی Where، فعل are قبل از فاعل you قرار می‌گیرد."),
            QuizQuestion("q1_3", 1, "املا و تلفظ", "کدام املا برای کلمه «کفش» درست است؟", "Which spelling is correct?", listOf("SHOOS", "SHOES", "SHOSE", "CHOOSE"), 1, "در صفحه ۱۵ کتاب تأکید شده املای صحیح کفش S-H-O-E-S است."),
            QuizQuestion("q1_4", 1, "واژگان", "معنی کلمه «originally» در مکالمه درس اول چیست؟", "Yes, I'm originally Iranian.", listOf("متأسفانه", "اصالتاً", "معمولاً", "احتمالاً"), 1, "کلمه originally در جمله سام به معنی «اصالتاً» است.")
        )
    )

    val lesson2 = CurriculumUnit(
        id = 2,
        type = UnitType.LESSON,
        numberLabel = "درس ۲",
        titleEn = "My Week",
        titleFa = "هفته من",
        themeFa = "فعالیت‌های روزانه و روزهای هفته",
        functionEn = "Talking about Daily Activities",
        functionFa = "گفتگو درباره کارهای روزانه و زمان انجام آن‌ها",
        pageRange = "صفحات ۱۸ تا ۲۳",
        accentColor = Lesson2Accent,
        keyExpressions = listOf(
            ExpressionPair("What do you do in the mornings?", "صبح‌ها چکار می‌کنید؟"),
            ExpressionPair("I go to school.", "من به مدرسه می‌روم."),
            ExpressionPair("When do you study English?", "چه زمانی انگلیسی می‌خوانید؟"),
            ExpressionPair("On Monday mornings. / Every Wednesday afternoon.", "صبح‌های دوشنبه. / هر چهارشنبه بعدازظهر."),
            ExpressionPair("Excuse me, sir / madam / Miss ...", "ببخشید آقا / خانم ...")
        ),
        conversations = listOf(
            ConversationSection(
                id = "c2_main",
                titleEn = "Conversation: Helping a Classmate",
                titleFa = "مکالمه اصلی: کمک به همکلاسی",
                contextFa = "معلم انگلیسی و ریحانه درباره برنامه‌های هفتگی و کمک به همکلاسی‌اش شیوا صحبت می‌کنند.",
                bookPage = 18,
                lines = listOf(
                    ConversationLine("Teacher", "What do you do in the afternoons, Reihaneh?", "ریحانه، بعدازظهرها چکار می‌کنی؟"),
                    ConversationLine("Student", "Well, I go to the gym on Sundays and Tuesdays.", "خب، یک‌شنبه‌ها و سه‌شنبه‌ها به باشگاه می‌روم."),
                    ConversationLine("Teacher", "How about Friday mornings?", "صبح‌های جمعه چطور؟"),
                    ConversationLine("Student", "I stay at home and relax. Why?", "در خانه می‌مانم و استراحت می‌کنم. چطور مگه؟"),
                    ConversationLine("Teacher", "You know, Shiva is not very good at English. Can you help her?", "می‌دانی که شیوا در زبان انگلیسی خیلی خوب نیست. می‌توانی به او کمک کنی؟"),
                    ConversationLine("Student", "Oh, sure.", "اوه، حتماً."),
                    ConversationLine("Teacher", "That sounds great! When can you start?", "عالیه! کی می‌توانی شروع کنی؟"),
                    ConversationLine("Student", "This Wednesday afternoon.", "همین چهارشنبه بعدازظهر."),
                    ConversationLine("Teacher", "That's fine. Thank you. I'll let her know.", "بسیار خب. متشکرم. به او اطلاع می‌دهم.")
                )
            ),
            ConversationSection(
                id = "c2_spelling",
                titleEn = "Spelling & Pronunciation: st in study",
                titleFa = "مکالمه تلفظ: صدای st در study (صفحه ۲۱)",
                contextFa = "دانش‌آموز درباره تلفظ صحیح st در ابتدای کلمه study از معلم خود سؤال می‌پرسد.",
                bookPage = 21,
                lines = listOf(
                    ConversationLine("Student", "Excuse me, sir, what's روزهای هفته in English?", "ببخشید آقا، روزهای هفته به انگلیسی چی می‌شه؟"),
                    ConversationLine("Teacher", "That's 'weekdays'.", "می‌شود weekdays."),
                    ConversationLine("Student", "How do you spell it?", "چطور نوشته می‌شود؟"),
                    ConversationLine("Teacher", "W-E-E-K-D-A-Y-S.", "W-E-E-K-D-A-Y-S."),
                    ConversationLine("Student", "Thanks. And how do you say S-T in 'study'?", "ممنون. و S-T در کلمه study چطور تلفظ می‌شود؟"),
                    ConversationLine("Teacher", "The correct pronunciation is 'study'.", "تلفظ درست «study» است (بدون صدای اِ در ابتدا)."),
                    ConversationLine("Student", "There's no /e/ at the beginning, right?", "صدای /e/ اولش نیست، درسته؟"),
                    ConversationLine("Teacher", "No, there isn't.", "نه، وجود ندارد.")
                )
            )
        ),
        phonicsSpotlights = listOf(
            PhonicsSpotlight(
                id = "p2_st_ee_ay",
                lessonId = 2,
                pattern = "st / ee / ay",
                ipaHint = "خوشه st بدون /e/ اول، واکه بلند /iː/ و /eɪ/",
                explanationFa = "در آغاز کلماتی مثل study و stay مستقیماً صدای «س» را به «ت» بچسبانید. ترکیب ee صدای «ای» کشیده و ay صدای «اِی» می‌دهد.",
                mistakeWarningFa = "هرگز نگویید «اِستادی» یا «اِستی»! صدای مصوت /e/ قبل از st وجود ندارد.",
                sampleWords = listOf(
                    PhonicsWord("study", "درس خواندن", "/ˈstʌdi/"),
                    PhonicsWord("stay", "ماندن", "/steɪ/"),
                    PhonicsWord("week", "هفته", "/wiːk/"),
                    PhonicsWord("weekdays", "روزهای کاری هفته", "/ˈwiːkdeɪz/"),
                    PhonicsWord("day", "روز", "/deɪ/"),
                    PhonicsWord("Friday", "جمعه", "/ˈfraɪdeɪ/")
                )
            )
        ),
        grammarTopics = listOf(
            GrammarTopic(
                id = "g2_time_prepositions",
                lessonId = 2,
                titleFa = "حروف اضافه زمان (in و on) و پرسش درباره برنامه هفتگی",
                titleEn = "Time Prepositions (in / on) & What / When Questions",
                stepByStepExplanationFa = listOf(
                    "گام ۱: برای بخش‌های کلی روز (morning, afternoon, evening) بدون ذکر نام روز، همیشه از in the استفاده می‌کنیم: in the morning.",
                    "گام ۲: به محض اینکه نام یک روز هفته (مثل Monday یا Friday) بیاید—حتی اگر همراه با morning باشد—حرف اضافه به on تبدیل می‌شود: on Friday mornings!",
                    "گام ۳: برای weekdays و the weekend نیز از on استفاده می‌کنیم: on weekdays / on the weekend.",
                    "گام ۴: اگر کلمه every (هر) قبل از زمان بیاید، دیگر از in و on استفاده نمی‌کنیم: Every Wednesday afternoon."
                ),
                formulaEn = "in the morning/afternoon/evening  |  on + [Day] / weekdays / the weekend",
                bookExamples = listOf(
                    ExpressionPair("What do you do in the mornings? — I go to school.", "صبح‌ها چکار می‌کنید؟ — به مدرسه می‌روم."),
                    ExpressionPair("What do you do on Friday mornings?", "صبح‌های جمعه چکار می‌کنید؟"),
                    ExpressionPair("When do you study English? — On Monday mornings.", "کی انگلیسی می‌خوانید؟ — صبح‌های دوشنبه."),
                    ExpressionPair("What days do you go shopping? — Every Wednesday afternoon.", "چه روزهایی خرید می‌روید؟ — هر چهارشنبه بعدازظهر.")
                ),
                extraExamples = listOf(
                    ExpressionPair("I visit my grandparents on the weekend.", "آخر هفته به دیدن پدربزرگ و مادربزرگم می‌روم."),
                    ExpressionPair("We study our lessons in the evening.", "عصرها درس‌هایمان را می‌خوانیم."),
                    ExpressionPair("My brother goes to the library on Tuesday afternoons.", "برادرم بعدازظهرهای سه‌شنبه به کتابخانه می‌رود.")
                ),
                miniChecks = listOf(
                    GrammarMiniCheck(
                        id = "mc2_1",
                        questionFa = "حرف اضافه مناسب را انتخاب کنید:",
                        promptEn = "I play sports ________ Thursday afternoons.",
                        options = listOf("in", "on", "at"),
                        correctIndex = 1,
                        explanationRightFa = "درست است! چون نام روز (Thursday) در عبارت آمده، حتماً باید از on استفاده کنیم.",
                        explanationWrongFa = "نکته طلایی: اگر فقط the afternoon بود از in استفاده می‌کردیم، اما چون اسم روز (Thursday) آمده باید on بگذاریم."
                    ),
                    GrammarMiniCheck(
                        id = "mc2_2",
                        questionFa = "جمله صحیح را برگزینید:",
                        promptEn = "When do you watch TV? — ________.",
                        options = listOf("On the evening", "In the evening", "In Fridays"),
                        correctIndex = 1,
                        explanationRightFa = "آفرین! برای اوقات روز (morning, afternoon, evening) بدون اسم روز از in the استفاده می‌کنیم.",
                        explanationWrongFa = "یادتان باشد: the evening اسم روز ندارد پس حرف اضافه‌اش in است، و برای روزهای هفته (Fridays) از on استفاده می‌شود."
                    )
                )
            )
        ),
        exercises = listOf(
            BookExercise(
                id = "ex2_clock",
                lessonId = 2,
                category = ExerciseCategory.COMPREHENSION,
                titleFa = "چرخه شبانه‌روز و زمان‌ها (صفحه ۲۰)",
                bookPage = 20,
                instructionFa = "با توجه به نمودار ساعت شبانه‌روز در صفحه ۲۰، ساعت ۱۲:۰۰ ظهر به انگلیسی چه نام دارد؟",
                questionEn = "12:00 p.m. is called ________.",
                options = listOf("midnight", "sunrise", "midday / noon", "sunset"),
                correctAnswer = "midday / noon",
                stepHintsFa = listOf(
                    "راهنمای ۱: ساعت 00:00 نیمه‌شب (midnight) است و 6 a.m. طلوع آفتاب (sunrise).",
                    "راهنمای ۲: بالای نمودار در صفحه ۲۰ روبه‌روی خورشید و عدد 12:00 دو کلمه مترادف نوشته شده است.",
                    "راهنمای ۳: کلمه mid + day یعنی میانه روز (ظهر) و noon هم به همین معنی است."
                ),
                ruleTrickFa = "midday = میانه روز (۱۲ ظهر) و midnight = میانه شب (۱۲ شب).",
                fullExplanationFa = "طبق نمودار صفحه ۲۰ کتاب، ساعت ۱۲ ظهر midday یا noon نامیده می‌شود."
            ),
            BookExercise(
                id = "ex2_weekend",
                lessonId = 2,
                category = ExerciseCategory.TABLE_FILL,
                titleFa = "تفکیک روزهای کاری و آخر هفته در ایران (صفحه ۱۹ و ۶۹)",
                bookPage = 19,
                instructionFa = "بر اساس جدول صفحه ۱۹ کتاب، کدام دو روز جزو The Weekend (آخر هفته) محسوب می‌شوند؟",
                questionEn = "In our calendar, The Weekend includes ________.",
                options = listOf("Saturday and Sunday", "Wednesday and Thursday", "Thursday and Friday", "Monday and Tuesday"),
                correctAnswer = "Thursday and Friday",
                stepHintsFa = listOf(
                    "راهنمای ۱: در تقویم ایران هفته از شنبه (Saturday) آغاز می‌شود.",
                    "راهنمای ۲: روزهای کاری (Weekdays) در جدول کتاب ۵ روز از Saturday تا Wednesday هستند.",
                    "راهنمای ۳: بنابراین دو روز پایانی جدول یعنی پنج‌شنبه و جمعه آخر هفته هستند."
                ),
                ruleTrickFa = "هفته با Saturday شروع می‌شود: 5 روز اول Weekdays و دو روز آخر (Thursday, Friday) The Weekend است.",
                fullExplanationFa = "در جدول صفحه ۱۹، Saturday تا Wednesday به عنوان Weekdays و Thursday و Friday به عنوان The Weekend معرفی شده‌اند."
            )
        ),
        quizQuestions = listOf(
            QuizQuestion("q2_1", 2, "حرف اضافه زمان", "جمله درست کدام است؟", "We go to school ________ weekdays.", listOf("in", "on", "at", "every"), 1, "طبق جدول صفحه ۱۹ عبارت درست on weekdays است."),
            QuizQuestion("q2_2", 2, "املا و تلفظ", "کدام حرف در تلفظ کلمه study نباید در ابتدا اضافه شود؟", "There is no /__/ at the beginning of 'study'.", listOf("/a/", "/e/", "/o/", "/u/"), 1, "در مکالمه صفحه ۲۱ آمده: There's no /e/ at the beginning of study."),
            QuizQuestion("q2_3", 2, "واژگان", "معنی عبارت «visit relatives» چیست؟", "I visit my relatives on Fridays.", listOf("به خرید رفتن", "کوهنوردی کردن", "دیدار با بستگان", "تماشای تلویزیون"), 2, "relative به معنی فامیل و بستگان است."),
            QuizQuestion("q2_4", 2, "سؤال‌سازی", "برای پرسیدن «چه روزهایی به کتابخانه می‌روی؟» کدام صحیح است؟", "________ do you go to the library?", listOf("Where", "Who", "What days", "How many"), 2, "در Practice 2 صفحه ۱۹ از When یا What days برای پرسیدن روز انجام کار استفاده می‌شود.")
        )
    )

    val review1 = CurriculumUnit(
        id = 101,
        type = UnitType.REVIEW,
        numberLabel = "مرور ۱",
        titleEn = "Review 1 (Lessons 1–2)",
        titleFa = "مرور جامع درس‌های ۱ و ۲",
        themeFa = "تثبیت ملیت‌ها، روزهای هفته و تلفظ",
        functionEn = "Self-Assessment & Consolidation",
        functionFa = "ارزیابی تسلط بر درس‌های ۱ و ۲ (صفحات ۲۴ و ۲۵)",
        pageRange = "صفحات ۲۴ و ۲۵",
        accentColor = ReviewAccent,
        keyExpressions = listOf(
            ExpressionPair("I'm ... / I am from ...", "من ... هستم / اهل ... هستم"),
            ExpressionPair("Where are ...? / Are you ...?", "اهل کجاست؟ / آیا ... هستید؟"),
            ExpressionPair("I ... in the ... / I ... on ...", "من در ... / در روز ... انجام می‌دهم"),
            ExpressionPair("What do you ...? / When do you ...?", "چه کار می‌کنید؟ / چه زمانی ...؟")
        ),
        conversations = emptyList(),
        phonicsSpotlights = lesson1.phonicsSpotlights + lesson2.phonicsSpotlights,
        grammarTopics = emptyList(),
        exercises = listOf(
            BookExercise(
                id = "rev1_ex1",
                lessonId = 101,
                category = ExerciseCategory.REVIEW_CHECKLIST,
                titleFa = "چک‌لیست مرور ۱ — کشور و ملیت (صفحه ۲۵)",
                bookPage = 25,
                instructionFa = "جفت کشور و ملیت درست را تکمیل کنید:",
                questionEn = "Country: Egypt → Nationality: ________ | Country: Japan → Nationality: ________",
                options = listOf("Egyptish / Japanian", "Egyptian / Japanese", "Egyptese / Japannish", "Egyptian / Japanian"),
                correctAnswer = "Egyptian / Japanese",
                stepHintsFa = listOf(
                    "راهنمای ۱: ملیت مصر با پسوند -ian ساخته می‌شود.",
                    "راهنمای ۲: ملیت ژاپن مانند چین (Chinese) با پسوند -ese است."
                ),
                ruleTrickFa = "Iran → Iranian, Egypt → Egyptian, Brazil → Brazilian | China → Chinese, Japan → Japanese.",
                fullExplanationFa = "جفت درست Egyptian (مصری) و Japanese (ژاپنی) است."
            ),
            BookExercise(
                id = "rev1_ex2",
                lessonId = 101,
                category = ExerciseCategory.REVIEW_CHECKLIST,
                titleFa = "چک‌لیست مرور ۱ — ترتیب روزهای هفته و تلفظ (صفحه ۲۵)",
                bookPage = 25,
                instructionFa = "کدام گروه کلمات به ترتیب نماینده الگوهای ch, sh, st, ee, ay از صفحه ۲۵ است؟",
                questionEn = "Match: ch, sh, st, ee, ay",
                options = listOf(
                    "chair, shoes, study, week, day",
                    "shoes, chair, day, week, study",
                    "France, Spain, Brazil, stay, Friday",
                    "fish, peach, Iran, gym, school"
                ),
                correctAnswer = "chair, shoes, study, week, day",
                stepHintsFa = listOf(
                    "راهنمای ۱: کلمه اول باید ch داشته باشد (chair) و دوم sh (shoes).",
                    "راهنمای ۲: به ترتیب st → study، ee → week و ay → day می‌آید."
                ),
                ruleTrickFa = "هر دو نویسه یا خوشه را در ابتدای کلمه یا بخش اصلی آن بررسی کنید.",
                fullExplanationFa = "ترتیب درست: chair (ch), shoes (sh), study (st), week (ee), day (ay)."
            )
        ),
        quizQuestions = listOf(
            QuizQuestion("qr1_1", 101, "مرور ملیت", "کدام جمله کاملاً درست است؟", "Choose the correct sentence:", listOf("He is from French.", "He is France.", "He is from France.", "He from France."), 2, "با from نام کشور (France) می‌آید و فعل is هم ضروری است."),
            QuizQuestion("qr1_2", 101, "مرور هفته", "بعد از چهارشنبه (Wednesday) کدام روز می‌آید؟", "What day comes after Wednesday?", listOf("Tuesday", "Thursday", "Saturday", "Monday"), 1, "ترتیب روزها: Wednesday و سپس Thursday (پنج‌شنبه)."),
            QuizQuestion("qr1_3", 101, "مرور زمان", "جای خالی را پر کنید:", "I stay at home ________ Fridays.", listOf("in", "at", "on", "to"), 2, "قبل از نام روزهای هفته همیشه حرف اضافه on می‌آید.")
        )
    )

    val lesson3 = CurriculumUnit(
        id = 3,
        type = UnitType.LESSON,
        numberLabel = "درس ۳",
        titleEn = "My Abilities",
        titleFa = "توانایی‌های من",
        themeFa = "بیان توانایی‌ها و مهارت‌ها",
        functionEn = "Talking about Abilities",
        functionFa = "پرسیدن و گفتن درباره کارهایی که بلدیم انجام دهیم",
        pageRange = "صفحات ۲۶ تا ۳۳",
        accentColor = Lesson3Accent,
        keyExpressions = listOf(
            ExpressionPair("Are you good at drawing? — Yes, I am. / No, I'm not.", "آیا در نقاشی مهارت دارید؟ — بله / خیر."),
            ExpressionPair("Can you ride a bicycle? — Yes, I can. / No, I can't.", "آیا می‌توانید دوچرخه‌سواری کنید؟ — بله / خیر."),
            ExpressionPair("Who can play tennis? — Ali can.", "چه کسی می‌تواند تنیس بازی کند؟ — علی می‌تواند."),
            ExpressionPair("Excuse me, I have a question.", "ببخشید، من یک سؤال دارم.")
        ),
        conversations = listOf(
            ConversationSection(
                id = "c3_main",
                titleEn = "Conversation: Talking about Abilities",
                titleFa = "مکالمه اصلی: گفتگو درباره توانایی‌ها",
                contextFa = "الهام و سارا درباره نقاشی کشیدن و عکاسی صحبت می‌کنند.",
                bookPage = 26,
                lines = listOf(
                    ConversationLine("Elham", "Wow! Your drawing is very good.", "وای! نقاشی تو خیلی خوب است."),
                    ConversationLine("Sara", "Thanks. Can you draw?", "ممنون. تو می‌توانی نقاشی بکشی؟"),
                    ConversationLine("Elham", "No, I'm not good at drawing. But I can take good photos.", "نه، در نقاشی مهارت ندارم. اما می‌توانم عکس‌های خوبی بگیرم."),
                    ConversationLine("Sara", "Really? Can I see your photos?", "واقعاً؟ می‌توانم عکس‌هایت را ببینم؟"),
                    ConversationLine("Elham", "Why not? Come to my house this afternoon.", "چرا که نه؟ امروز بعدازظهر به خانه ما بیا."),
                    ConversationLine("Sara", "Oh, I can't make it today. How about Thursday afternoon?", "اوه، امروز نمی‌توانم بیایم. پنج‌شنبه بعدازظهر چطور است؟"),
                    ConversationLine("Elham", "That's fine. You can bring your drawing book, too.", "عالیه. می‌توانی دفتر نقاشی‌ات را هم بیاوری."),
                    ConversationLine("Sara", "Sure.", "حتماً.")
                )
            ),
            ConversationSection(
                id = "c3_spelling",
                titleEn = "Spelling & Pronunciation: oo, pl, sw",
                titleFa = "مکالمه تلفظ: صدای oo و خوشه‌های pl و sw (صفحه ۲۹)",
                contextFa = "دانش‌آموزان درباره تفاوت تلفظ oo در football و afternoon و تلفظ play و swimming می‌پرسند.",
                bookPage = 29,
                lines = listOf(
                    ConversationLine("Student 1", "Excuse me, I have a question.", "ببخشید، یک سؤال دارم."),
                    ConversationLine("Teacher", "Yes?", "بله؟"),
                    ConversationLine("Student 1", "How do you say O-O in the words 'football' and 'afternoon'?", "O-O در کلمات football و afternoon چطور تلفظ می‌شود؟"),
                    ConversationLine("Teacher", "Say double O. Well, the sound is short in 'football' and long in 'afternoon'.", "بگویید double O. صدا در football کوتاه (/ʊ/) و در afternoon کشیده (/uː/) است."),
                    ConversationLine("Student 2", "Excuse me, I have a question, too. We write P-L-A-Y but we say 'play', why?", "ببخشید من هم یک سؤال دارم. می‌نویسیم P-L-A-Y ولی چرا بدون فاصله تلفظ می‌کنیم؟"),
                    ConversationLine("Teacher", "Well, that's 'play'. There's no /e/ between P and L, 'play'!", "خب تلفظ درست play است؛ هیچ صدای /e/ بین P و L وجود ندارد."),
                    ConversationLine("Student 2", "Thanks. And how do you say S-W in 'swimming'?", "ممنون. و S-W در swimming چطور تلفظ می‌شود؟"),
                    ConversationLine("Teacher", "Swimming. Say 'swimming'.", "بگویید swimming (بدون اِ در ابتدا).")
                )
            )
        ),
        phonicsSpotlights = listOf(
            PhonicsSpotlight(
                id = "p3_oo",
                lessonId = 3,
                pattern = "oo / ll / pl / sw",
                ipaHint = "/ʊ/ کوتاه و /uː/ بلند + خوشه‌های pl و sw",
                explanationFa = "دو حرف oo در کلماتی مثل football، book و cook کوتاه است، اما در afternoon و spoon کشیده است. همچنین بین p و l در play و قبل از sw در swimming صدای «اِ» نگذارید.",
                mistakeWarningFa = "نگویید «پِلی» یا «اِسویمینگ»! مستقیماً خوشه همخوان را تلفظ کنید.",
                sampleWords = listOf(
                    PhonicsWord("football", "فوتبال (oo کوتاه)", "/ˈfʊtbɔːl/"),
                    PhonicsWord("cook", "آشپزی (oo کوتاه)", "/kʊk/"),
                    PhonicsWord("afternoon", "بعدازظهر (oo کشیده)", "/ˌɑːftəˈnuːn/"),
                    PhonicsWord("play", "بازی کردن (بدون /e/ میانی)", "/pleɪ/"),
                    PhonicsWord("swimming", "شنا کردن (بدون /e/ اول)", "/ˈswɪmɪŋ/"),
                    PhonicsWord("volleyball", "والیبال (ll)", "/ˈvɒlibɔːl/")
                )
            )
        ),
        grammarTopics = listOf(
            GrammarTopic(
                id = "g3_can_good_at",
                lessonId = 3,
                titleFa = "بیان توانایی با can و good at (نکته بسیار مهم ing!)",
                titleEn = "can + Base Verb vs. good at + Verb-ing",
                stepByStepExplanationFa = listOf(
                    "گام ۱: بعد از فعل کمکی can و can't همیشه شکل ساده فعل (بدون to و بدون ing) می‌آید: I can swim.",
                    "گام ۲: در عبارت be good at (در کاری مهارت داشتن)، چون at حرف اضافه است، فعل بعد از آن حتماً باید ing بگیرد: I am good at swimming.",
                    "گام ۳: برای پرسیدن «چه کسی می‌تواند...؟» از Who can + فعل ساده استفاده می‌کنیم: Who can play tennis? — Ali can.",
                    "گام ۴: دقت کنید در پاسخ به سؤال Who is good at...؟ از فعل am/is/are استفاده می‌کنیم، نه can!"
                ),
                formulaEn = "Can + Subject + [Verb]?  |  am/is/are + good at + [Verb-ing]",
                bookExamples = listOf(
                    ExpressionPair("Are you good at drawing? — Yes, I am. / No, I'm not.", "آیا در نقاشی خوبی؟ — بله هستم. / نه نیستم."),
                    ExpressionPair("Can you ride a bicycle? — Yes, I can. / No, I can't.", "می‌توانی دوچرخه‌سواری کنی؟ — بله / نه."),
                    ExpressionPair("Who can draw? — Parham can, but not very well.", "کی می‌تواند نقاشی بکشد؟ — پرهام می‌تواند، اما نه خیلی خوب."),
                    ExpressionPair("Who is good at telling stories? — Me.", "چه کسی در داستان گفتن مهارت دارد؟ — من.")
                ),
                extraExamples = listOf(
                    ExpressionPair("My sister is good at making cakes, and she can cook well.", "خواهرم در کیک پختن مهارت دارد و می‌تواند خوب آشپزی کند."),
                    ExpressionPair("Can your father work with a computer? — Yes, he can.", "آیا پدرت می‌تواند با رایانه کار کند؟ — بله، می‌تواند."),
                    ExpressionPair("They aren't good at playing chess, but they can play ping-pong.", "آنها در شطرنج مهارت ندارند، اما می‌توانند پینگ‌پنگ بازی کنند.")
                ),
                miniChecks = listOf(
                    GrammarMiniCheck(
                        id = "mc3_1",
                        questionFa = "شکل درست فعل را انتخاب کنید:",
                        promptEn = "Is she good at ________?",
                        options = listOf("cook", "to cook", "cooking"),
                        correctIndex = 2,
                        explanationRightFa = "آفرین! بعد از حرف اضافه at در عبارت good at همیشه فعل + ing می‌آید.",
                        explanationWrongFa = "دقت کنید: بعد از can شکل ساده می‌آید، اما بعد از good at فعل حتماً باید ing بگیرد (cooking)."
                    ),
                    GrammarMiniCheck(
                        id = "mc3_2",
                        questionFa = "گزینه درست برای بعد از can کدام است؟",
                        promptEn = "Can he ________ a puzzle?",
                        options = listOf("do", "doing", "does"),
                        correctIndex = 0,
                        explanationRightFa = "عالی! بعد از can همیشه شکل ساده فعل (do) می‌آید.",
                        explanationWrongFa = "بعد از فعل کمکی can هیچ‌وقت ing یا s نمی‌آید؛ شکل ساده فعل یعنی do درست است."
                    )
                )
            )
        ),
        exercises = listOf(
            BookExercise(
                id = "ex3_oo_words",
                lessonId = 3,
                category = ExerciseCategory.SPELLING_CHECK,
                titleFa = "پیدا کردن ۵ کلمه با oo در درس سوم (صفحه ۲۹)",
                bookPage = 29,
                instructionFa = "تمرین صفحه ۲۹: در درس ۳، کدام گروه کلمات همگی دارای ترکیب oo هستند؟",
                questionEn = "Find 5 words with 'oo' in Lesson 3:",
                options = listOf(
                    "football, afternoon, cook, good, book",
                    "play, swim, draw, cake, tennis",
                    "photos, bicycle, puzzle, chess, story",
                    "good, student, teacher, computer, horse"
                ),
                correctAnswer = "football, afternoon, cook, good, book",
                stepHintsFa = listOf(
                    "راهنمای ۱: به مکالمه صفحه ۲۶ نگاه کنید: کلمات good, afternoon, book در متن آمده‌اند.",
                    "راهنمای ۲: در صفحه ۲۸ (تصاویر توانایی‌ها) کلمات cook و football نیز وجود دارند."
                ),
                ruleTrickFa = "در good، book، cook، football صدای oo کوتاه (/ʊ/) و در afternoon کشیده (/uː/) است.",
                fullExplanationFa = "پنج کلمه اصلی با oo در درس ۳ عبارتند از: football, afternoon, cook, good, book."
            ),
            BookExercise(
                id = "ex3_roleplay_card",
                lessonId = 3,
                category = ExerciseCategory.PAIR_CARD,
                titleFa = "تکمیل کارت مصاحبه صفحه ۳۲ و ۳۳ (Student A & B)",
                bookPage = 33,
                instructionFa = "سؤال ۸ کارت A می‌پرسد: What sports can you do/play? پاسخ درست در کارت B چیست؟",
                questionEn = "8. What sports can you play? → I can ________.",
                options = listOf("playing tennis", "play tennis and swim", "good at tennis", "to play football"),
                correctAnswer = "play tennis and swim",
                stepHintsFa = listOf(
                    "راهنمای ۱: در کارت B جمله با I can شروع شده است.",
                    "راهنمای ۲: بعد از can فعل باید ساده باشد (بدون ing و بدون to)."
                ),
                ruleTrickFa = "همیشه به کلمه قبل از جای خالی دقت کنید: بعد از I can فعل ساده و بعد از I'm good at فعل ingدار می‌آید.",
                fullExplanationFa = "عبارت درست I can play tennis and swim است چون بعد از can مصدر بدون to (شکل ساده فعل) می‌آید."
            )
        ),
        quizQuestions = listOf(
            QuizQuestion("q3_1", 3, "گرامر", "کدام گزینه صحیح است؟", "Ali ________ play basketball well.", listOf("can", "is can", "can to", "good at"), 0, "برای بیان توانایی از ساختار فاعل + can + فعل ساده استفاده می‌کنیم."),
            QuizQuestion("q3_2", 3, "گرامر", "جای خالی را کامل کنید:", "Are you good at ________ the Web?", listOf("search", "searching", "searches", "to search"), 1, "بعد از good at همیشه فعل با ing می‌آید."),
            QuizQuestion("q3_3", 3, "تلفظ", "در کدام کلمه صدای oo کشیده‌تر (طولانی‌تر) است؟", "Long 'oo' sound:", listOf("book", "cook", "football", "afternoon"), 3, "در صفحه ۲۹ توضیح داده شده: صدای oo در football کوتاه و در afternoon بلند است."),
            QuizQuestion("q3_4", 3, "اصطلاح مکالمه", "عبارت «I can't make it today» در مکالمه الهام و سارا یعنی چه؟", "Oh, I can't make it today.", listOf("امروز نمی‌توانم بسازمش", "امروز نمی‌توانم بیایم / برسم", "امروز نقاشی نمی‌کشم", "امروز امتحان دارم"), 1, "عبارت I can't make it برای عذرخواهی از نرسیدن به قرار استفاده می‌شود.")
        )
    )

    val lesson4 = CurriculumUnit(
        id = 4,
        type = UnitType.LESSON,
        numberLabel = "درس ۴",
        titleEn = "My Health",
        titleFa = "سلامتی من",
        themeFa = "بیماری‌ها و توصیه‌های پزشکی",
        functionEn = "Talking about Health Problems & Giving Advice",
        functionFa = "بیان مشکلات سلامتی و دادن توصیه درمانی",
        pageRange = "صفحات ۳۴ تا ۳۹",
        accentColor = Lesson4Accent,
        keyExpressions = listOf(
            ExpressionPair("Are you OK? / What's wrong?", "حالتان خوب است؟ / چه مشکلی پیش آمده؟"),
            ExpressionPair("No, I have a headache.", "نه، من سردرد دارم."),
            ExpressionPair("I have the flu. / I have a toothache.", "من آنفولانزا دارم. / من دندان‌درد دارم."),
            ExpressionPair("Why don't you go to the dentist?", "چرا به دندانپزشک نمی‌روی؟"),
            ExpressionPair("You should get some rest.", "باید کمی استراحت کنی.")
        ),
        conversations = listOf(
            ConversationSection(
                id = "c4_main",
                titleEn = "Conversation: At School",
                titleFa = "مکالمه اصلی: دانش‌آموز بیمار در کلاس",
                contextFa = "معلم متوجه می‌شود که یکی از دانش‌آموزان حال خوبی ندارد و به او توصیه می‌کند.",
                bookPage = 34,
                lines = listOf(
                    ConversationLine("Teacher", "Are you OK?", "حالت خوب است؟"),
                    ConversationLine("Student", "No, I'm not. I have a headache.", "نه، خوب نیستم. سردرد دارم."),
                    ConversationLine("Teacher", "Oh, you have sore eyes, too. You should go home and rest.", "اوه، چشمانت هم درد می‌کند. باید به خانه بروی و استراحت کنی."),
                    ConversationLine("Student", "Yes, but we have one more class.", "بله، اما یک کلاس دیگر داریم."),
                    ConversationLine("Teacher", "Don't worry. I'll talk to your teacher.", "نگران نباش. من با معلمت صحبت می‌کنم."),
                    ConversationLine("Student", "Thanks for your help.", "ممنون از کمکتان."),
                    ConversationLine("Teacher", "Let's go to the office and call your parents first. Class, be quiet! I'll be back in a minute.", "اول برویم دفتر و با والدینت تماس بگیریم. بچه‌ها ساکت باشید! یک دقیقه دیگر برمی‌گردم.")
                )
            ),
            ConversationSection(
                id = "c4_spelling",
                titleEn = "Spelling & Pronunciation: -ache & ea",
                titleFa = "مکالمه تلفظ: پسوند ache و صدای ea (صفحه ۳۶)",
                contextFa = "دانش‌آموز درباره معنای A-C-H-E و تلفظ آن در کلماتی مثل headache و health می‌پرسد.",
                bookPage = 36,
                lines = listOf(
                    ConversationLine("Student", "I see A-C-H-E in some words. What is it?", "من A-C-H-E را در بعضی کلمات می‌بینم. چیست؟"),
                    ConversationLine("Teacher", "It's for pain.", "به معنی «درد» است."),
                    ConversationLine("Student", "Thank you. And how do you say it?", "ممنون. و چطور تلفظ می‌شود؟"),
                    ConversationLine("Teacher", "That's 'ache' (/eɪk/).", "تلفظش «اِیک» (/eɪk/) است (یعنی ch صدای /k/ می‌دهد نه چ!)."),
                    ConversationLine("Student", "How about E-A in 'headache' and 'health'?", "در مورد E-A در کلمات headache و health چطور؟"),
                    ConversationLine("Teacher", "That's /e/ in these words. Say 'headache' and 'health'.", "در این کلمات صدای /e/ کوتاه دارد. بگویید headache و health.")
                )
            )
        ),
        phonicsSpotlights = listOf(
            PhonicsSpotlight(
                id = "p4_ache_ea",
                lessonId = 4,
                pattern = "-ache / ea",
                ipaHint = "ch = /k/ در ache | ea = /e/ کوتاه",
                explanationFa = "در کلمات درد (headache, toothache, earache, backache, stomachache)، ترکیب ache به معنی درد است و چ آن صدای «ک» (/eɪk/) می‌دهد. همچنین ea در head و health کوتاه و شبیه «اِ» تلفظ می‌شود.",
                mistakeWarningFa = "کلمه headache را «هِداچ» نخوانید! تلفظ صحیح «هِد-اِیک» است.",
                sampleWords = listOf(
                    PhonicsWord("headache", "سردرد", "/ˈhedeɪk/"),
                    PhonicsWord("toothache", "دندان‌درد", "/ˈtuːθeɪk/"),
                    PhonicsWord("earache", "گوش‌درد", "/ˈɪəreɪk/"),
                    PhonicsWord("backache", "کمردرد", "/ˈbækeɪk/"),
                    PhonicsWord("stomachache", "دل‌درد", "/ˈstʌməkeɪk/"),
                    PhonicsWord("health", "سلامتی", "/helθ/")
                )
            )
        ),
        grammarTopics = listOf(
            GrammarTopic(
                id = "g4_health_advice",
                lessonId = 4,
                titleFa = "بیان بیماری (have / has) و دادن توصیه (should / Why don't you)",
                titleEn = "Health Problems (have/has) & Giving Advice",
                stepByStepExplanationFa = listOf(
                    "گام ۱: برای بیان مشکل سلامتی از have (برای I, you, we, they) و has (برای he, she) استفاده می‌کنیم: I have a headache / She has a sore throat.",
                    "گام ۲: برای توصیه دادن ۳ روش ساده داریم: ۱) You should + فعل ساده، ۲) Why don't you + فعل ساده؟، ۳) جمله امری مستقیم مثل Go to the dentist.",
                    "گام ۳: دقت کنید بعد از should و Why don't you همیشه فعل به شکل ساده می‌آید (بدون to و ing)."
                ),
                formulaEn = "You should + [Base Verb]  |  Why don't you + [Base Verb]?",
                bookExamples = listOf(
                    ExpressionPair("What's the matter? — He has a running nose.", "موضوع چیه؟ — او آبریزش بینی دارد."),
                    ExpressionPair("I have a headache. — Why don't you get some rest?", "سردرد دارم. — چرا کمی استراحت نمی‌کنی؟"),
                    ExpressionPair("I have a sore throat. — You should see a doctor.", "گلودرد دارم. — باید به پزشک مراجعه کنی."),
                    ExpressionPair("I have a toothache. — Go to the dentist.", "دندان‌درد دارم. — به دندانپزشک برو.")
                ),
                extraExamples = listOf(
                    ExpressionPair("She has a fever. Why doesn't she drink warm water?", "او تب دارد. چرا آب گرم نمی‌نوشد؟"),
                    ExpressionPair("You have a backache. You should not lift heavy things.", "تو کمردرد داری. نباید چیزهای سنگین بلند کنی."),
                    ExpressionPair("Are you all right? — No, I have a stomachache.", "خوبی؟ — نه، دل‌درد دارم.")
                ),
                miniChecks = listOf(
                    GrammarMiniCheck(
                        id = "mc4_1",
                        questionFa = "فعل صحیح را انتخاب کنید:",
                        promptEn = "Reza ________ a sore throat.",
                        options = listOf("have", "has", "is have"),
                        correctIndex = 1,
                        explanationRightFa = "درست است! چون Reza مفرد غایب (he) است، باید از has استفاده کنیم.",
                        explanationWrongFa = "دقت کنید: برای ضمایر مفرد غایب (he, she و اسم یک نفر مثل Reza) از has استفاده می‌شود نه have."
                    ),
                    GrammarMiniCheck(
                        id = "mc4_2",
                        questionFa = "ساختار توصیه درست کدام است؟",
                        promptEn = "Why don't you ________ a doctor?",
                        options = listOf("to see", "seeing", "see"),
                        correctIndex = 2,
                        explanationRightFa = "آفرین! بعد از Why don't you همیشه فعل ساده (see) می‌آید.",
                        explanationWrongFa = "بعد از Why don't you شکل ساده فعل بدون to و ing قرار می‌گیرد."
                    )
                )
            )
        ),
        exercises = listOf(
            BookExercise(
                id = "ex4_match_advice",
                lessonId = 4,
                category = ExerciseCategory.TABLE_FILL,
                titleFa = "تکمیل جدول مشکل سلامتی و توصیه (صفحه ۳۷)",
                bookPage = 37,
                instructionFa = "اگر دوستی بگوید «I have a toothache»، بهترین توصیه درمانی کدام است؟",
                questionEn = "Problem: Toothache → Best Advice: ________",
                options = listOf(
                    "You should go to the library.",
                    "Why don't you go to the dentist?",
                    "You should play football.",
                    "Go to the gym."
                ),
                correctAnswer = "Why don't you go to the dentist?",
                stepHintsFa = listOf(
                    "راهنمای ۱: tooth به معنی دندان و ache به معنی درد است.",
                    "راهنمای ۲: کدام متخصص برای درمان درد دندان مناسب است؟ (dentist = دندانپزشک)."
                ),
                ruleTrickFa = "toothache → dentist | sore throat / flu / fever → doctor | headache → get some rest.",
                fullExplanationFa = "برای دندان‌درد (toothache) طبق صفحه ۳۶ کتاب توصیه می‌کنیم: Go to the dentist / Why don't you go to the dentist?"
            ),
            BookExercise(
                id = "ex4_doctors_page39",
                lessonId = 4,
                category = ExerciseCategory.COMPREHENSION,
                titleFa = "آشنایی با پزشکان و دانشمندان بزرگ (صفحه ۳۹)",
                bookPage = 39,
                instructionFa = "در صفحه ۳۹ کتاب تصویر ۴ پزشک و دانشمند برجسته آمده است. کدام یک جراح مغز و اعصاب مشهور ایرانی معاصر است؟",
                questionEn = "Famous Iranian neurosurgeon shown on page 39:",
                options = listOf("Dr. Louis Pasteur", "Avicenna (Ibn-e-Sina)", "Dr. Majid Samii", "Mr. Chaychi"),
                correctAnswer = "Dr. Majid Samii",
                stepHintsFa = listOf(
                    "راهنمای ۱: ابن‌سینا (Avicenna) پزشک بزرگ ایرانی در دوران کهن و لویی پاستور دانشمند فرانسوی است.",
                    "راهنمای ۲: پروفسور مجید سمیعی و دکتر محمد قریب (پدر طب اطفال ایران) دو پزشک برجسته معاصر در صفحه ۳۹ هستند."
                ),
                ruleTrickFa = "صفحه ۳۹: Avicenna (Ibn-e-Sina)، Dr. Majid Samii، Dr. Louis Pasteur، Dr. Mohammad Gharib.",
                fullExplanationFa = "پاسخ صحیح Dr. Majid Samii (پروفسور مجید سمیعی) است."
            )
        ),
        quizQuestions = listOf(
            QuizQuestion("q4_1", 4, "تلفظ", "تلفظ صحیح بخش ache در پایان headache چیست؟", "How do you pronounce '-ache'?", listOf("/ætʃ/", "/eɪk/", "/ɑːtʃ/", "/iːk/"), 1, "در پاورقی صفحه ۳۶ آمده: /eɪk/."),
            QuizQuestion("q4_2", 4, "واژگان", "عبارت «running nose» به چه معناست؟", "He has a running nose.", listOf("خونریزی بینی", "آبریزش بینی", "گلودرد", "گوش‌درد"), 1, "تصویر صفحه ۳۵ و ۷۸ نشان‌دهنده آبریزش بینی است."),
            QuizQuestion("q4_3", 4, "گرامر", "معنی جمله «What's the matter?» چیست؟", "What's the matter?", listOf("ساعت چند است؟", "اهل کجایی؟", "چه مشکلی پیش آمده؟ / چی شده؟", "چند سالت است؟"), 2, "برای پرسیدن حال بیمار از What's wrong? / What's the matter? / What's the problem? استفاده می‌شود."),
            QuizQuestion("q4_4", 4, "توصیه", "کدام گزینه از نظر دستوری غلط است؟", "Which sentence is INCORRECT?", listOf("You should rest.", "You should to rest.", "Why don't you rest?", "Go to the doctor."), 1, "بعد از should هرگز حرف اضافه to نمی‌آید.")
        )
    )

    val review2 = CurriculumUnit(
        id = 102,
        type = UnitType.REVIEW,
        numberLabel = "مرور ۲",
        titleEn = "Review 2 (Lessons 3–4)",
        titleFa = "مرور جامع درس‌های ۳ و ۴",
        themeFa = "تثبیت توانایی‌ها، مشکلات سلامتی و تلفظ",
        functionEn = "Self-Assessment & Consolidation",
        functionFa = "ارزیابی تسلط بر درس‌های ۳ و ۴ (صفحات ۴۰ و ۴۱)",
        pageRange = "صفحات ۴۰ و ۴۱",
        accentColor = ReviewAccent,
        keyExpressions = listOf(
            ExpressionPair("I'm good at ... / I can ...", "من در ... مهارت دارم / می‌توانم ..."),
            ExpressionPair("Are you good ...? / Can you ...? / Who can ...?", "آیا در ... خوبی؟ / می‌توانی ...؟ / چه کسی می‌تواند ...؟"),
            ExpressionPair("I have ... / What's ...?", "من ... دارم / چه مشکلی هست؟"),
            ExpressionPair("Why don't you ...? / You should ...", "چرا ... نمی‌کنی؟ / باید ...")
        ),
        conversations = emptyList(),
        phonicsSpotlights = lesson3.phonicsSpotlights + lesson4.phonicsSpotlights,
        grammarTopics = emptyList(),
        exercises = listOf(
            BookExercise(
                id = "rev2_ex1",
                lessonId = 102,
                category = ExerciseCategory.REVIEW_CHECKLIST,
                titleFa = "چک‌لیست مرور ۲ — نام ۵ مشکل سلامتی (صفحه ۴۱)",
                bookPage = 41,
                instructionFa = "کدام فهرست شامل ۵ مشکل سلامتی درست از درس ۴ است؟",
                questionEn = "Write the names of 5 health problems:",
                options = listOf(
                    "headache, backache, toothache, sore throat, running nose",
                    "cough, sneeze, dentist, hospital, drugstore",
                    "draw, cook, swim, headache, doctor",
                    "fever, nurse, patient, flu, museum"
                ),
                correctAnswer = "headache, backache, toothache, sore throat, running nose",
                stepHintsFa = listOf(
                    "راهنمای ۱: کلماتی مثل dentist, hospital, nurse نام شغل یا مکان هستند نه مشکل سلامتی.",
                    "راهنمای ۲: مشکلات سلامتی با کلمه درد (-ache) یا sore یا flu/cold بیان می‌شوند."
                ),
                ruleTrickFa = "مشکل سلامتی = چیزی که بیمار به آن دچار می‌شود (headache, earache, stomachache, sore eyes, the flu).",
                fullExplanationFa = "پاسخ درست شامل ۵ مشکل سلامتی از صفحه ۳۵ است."
            ),
            BookExercise(
                id = "rev2_ex2",
                lessonId = 102,
                category = ExerciseCategory.REVIEW_CHECKLIST,
                titleFa = "چک‌لیست مرور ۲ — تطبیق الگوهای تلفظ صفحه ۴۱",
                bookPage = 41,
                instructionFa = "برای الگوهای oo, ll, pl, sw, ch, ea کلمات متناظر را پیدا کنید:",
                questionEn = "Match: oo, ll, pl, sw, ch, ea",
                options = listOf(
                    "football, all, play, swim, stomachache, health",
                    "swim, play, health, all, football, toothache",
                    "chair, shoes, Spain, week, stay, Iran",
                    "cook, draw, dentist, nose, flu, cake"
                ),
                correctAnswer = "football, all, play, swim, stomachache, health",
                stepHintsFa = listOf(
                    "راهنمای ۱: برای ch در این مرور می‌توان از stomachache (با صدای /k/) یا chair استفاده کرد.",
                    "راهنمای ۲: برای ea کلمه health یا headache صدای /e/ دارد."
                ),
                ruleTrickFa = "به ترتیب قرارگیری الگوها در سؤال دقت کنید.",
                fullExplanationFa = "تطبیق دقیق: oo→football, ll→all, pl→play, sw→swim, ch→stomachache, ea→health."
            )
        ),
        quizQuestions = listOf(
            QuizQuestion("qr2_1", 102, "مرور توانایی", "کدام گزینه از نظر گرامری درست است؟", "Choose the correct option:", listOf("He is good at play chess.", "He can playing chess.", "He is good at playing chess.", "He good at chess."), 2, "بعد از good at فعل حتماً ing می‌گیرد و فعل to be (is) لازم است."),
            QuizQuestion("qr2_2", 102, "مرور سلامتی", "اگر دوستی گلودرد داشته باشد چه می‌گوییم؟", "I have a sore throat.", listOf("You should see a doctor.", "Can you draw?", "Where are you from?", "Every Wednesday."), 0, "برای گلودرد توصیه مناسب You should see a doctor است.")
        )
    )
}
