package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.data.curriculum.Prospect2Catalog
import com.example.firebase.configureAppCheck
import com.example.ui.components.TvFocusableCard
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.TutorViewModel

private object AppRoutes {
    const val HOME = "home"
    const val LESSON = "lesson/{unitId}/{stageIndex}"
    const val DICTIONARY = "dictionary?weakOnly={weakOnly}"
    const val PRONUNCIATION = "pronunciation"
    const val AI_TUTOR = "tutor?unitId={unitId}&prompt={prompt}"
    const val PROGRESS = "progress"

    fun lessonRoute(unitId: Int, stageIndex: Int = 0) = "lesson/$unitId/$stageIndex"
    fun dictionaryRoute(weakOnly: Boolean = false) = "dictionary?weakOnly=$weakOnly"
    fun tutorRoute(unitId: Int = 1, prompt: String = "") =
        "tutor?unitId=$unitId&prompt=${android.net.Uri.encode(prompt)}"
}

private data class NavDestinationItem(
    val routeBase: String,
    val titleFa: String,
    val icon: ImageVector,
    val testTag: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mandatory Step 1: Configure Firebase App Check with launch Intent before UI/ViewModels
        configureAppCheck(this, intent)

        enableEdgeToEdge()
        setContent {
            ProspectTutorTheme {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    ProspectTutorApp()
                }
            }
        }
    }
}

@Composable
fun ProspectTutorApp(
    viewModel: TutorViewModel = viewModel()
) {
    val navController = rememberNavController()
    val dashboardState by viewModel.dashboardState.collectAsStateWithLifecycle()
    val playbackState by viewModel.playbackState.collectAsStateWithLifecycle()
    val pronunciationResult by viewModel.pronunciationResult.collectAsStateWithLifecycle()
    val chatMessages by viewModel.chatMessages.collectAsStateWithLifecycle()
    val tutorStreamState by viewModel.tutorStreamState.collectAsStateWithLifecycle()

    var tutorSelectedUnitId by remember { mutableIntStateOf(1) }

    val navItems = remember {
        listOf(
            NavDestinationItem(AppRoutes.HOME, "خانه و دروس", Icons.Default.Home, "nav_home"),
            NavDestinationItem("dictionary", "واژگان هوشمند", Icons.Default.MenuBook, "nav_dictionary"),
            NavDestinationItem(AppRoutes.PRONUNCIATION, "تلفظ و خواندن", Icons.Default.RecordVoiceOver, "nav_pronunciation"),
            NavDestinationItem("tutor", "معلم هوشمند", Icons.Default.Psychology, "nav_tutor"),
            NavDestinationItem(AppRoutes.PROGRESS, "پیشرفت و راهنما", Icons.Default.Assessment, "nav_progress")
        )
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: AppRoutes.HOME

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(MidnightBg)
    ) {
        val isWideScreen = maxWidth >= 600.dp

        if (isWideScreen) {
            // TV / Tablet Landscape Layout: Persistent D-pad friendly Navigation Rail + Overscan-safe Content
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.safeDrawing)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                TvNavigationRail(
                    items = navItems,
                    currentRoute = currentRoute,
                    xp = dashboardState.preferences.totalXp,
                    onSelect = { item ->
                        viewModel.stopSpeaking()
                        val target = when (item.routeBase) {
                            "dictionary" -> AppRoutes.dictionaryRoute(false)
                            "tutor" -> AppRoutes.tutorRoute(tutorSelectedUnitId, "")
                            else -> item.routeBase
                        }
                        navController.navigate(target) {
                            popUpTo(AppRoutes.HOME) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

                Spacer(Modifier.width(12.dp))

                Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                    ProspectNavHost(
                        navController = navController,
                        viewModel = viewModel,
                        dashboardState = dashboardState,
                        playbackState = playbackState,
                        pronunciationResult = pronunciationResult,
                        chatMessages = chatMessages,
                        tutorStreamState = tutorStreamState,
                        tutorSelectedUnitId = tutorSelectedUnitId,
                        onSelectTutorUnit = { tutorSelectedUnitId = it },
                        isWideScreen = true
                    )
                }
            }
        } else {
            // Mobile Portrait Layout: Scaffold with Bottom NavigationBar respecting WindowInsets
            Scaffold(
                containerColor = MidnightBg,
                contentWindowInsets = WindowInsets.safeDrawing,
                bottomBar = {
                    NavigationBar(
                        containerColor = DeepSlateSurface,
                        windowInsets = WindowInsets.navigationBars
                    ) {
                        navItems.forEach { item ->
                            val selected = currentRoute.startsWith(item.routeBase)
                            NavigationBarItem(
                                selected = selected,
                                onClick = {
                                    viewModel.stopSpeaking()
                                    val target = when (item.routeBase) {
                                        "dictionary" -> AppRoutes.dictionaryRoute(false)
                                        "tutor" -> AppRoutes.tutorRoute(tutorSelectedUnitId, "")
                                        else -> item.routeBase
                                    }
                                    navController.navigate(target) {
                                        popUpTo(AppRoutes.HOME) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(imageVector = item.icon, contentDescription = item.titleFa)
                                },
                                label = {
                                    Text(text = item.titleFa, maxLines = 1)
                                },
                                modifier = Modifier.testTag(item.testTag),
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = SkyTealPrimary,
                                    selectedTextColor = SkyTealPrimary,
                                    indicatorColor = SkyTealContainer,
                                    unselectedIconColor = TextTertiarySubtle,
                                    unselectedTextColor = TextTertiarySubtle
                                )
                            )
                        }
                    }
                }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                    ProspectNavHost(
                        navController = navController,
                        viewModel = viewModel,
                        dashboardState = dashboardState,
                        playbackState = playbackState,
                        pronunciationResult = pronunciationResult,
                        chatMessages = chatMessages,
                        tutorStreamState = tutorStreamState,
                        tutorSelectedUnitId = tutorSelectedUnitId,
                        onSelectTutorUnit = { tutorSelectedUnitId = it },
                        isWideScreen = false
                    )
                }
            }
        }
    }
}

@Composable
private fun TvNavigationRail(
    items: List<NavDestinationItem>,
    currentRoute: String,
    xp: Int,
    onSelect: (NavDestinationItem) -> Unit
) {
    Surface(
        color = DeepSlateSurface,
        shape = RoundedCornerShape(22.dp),
        modifier = Modifier
            .width(196.dp)
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = SkyTealContainer.copy(alpha = 0.65f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "معلم پراسپکت ۲",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimaryLight
                    )
                    Text(
                        text = "پایه هشتم • $xp XP",
                        style = MaterialTheme.typography.labelMedium,
                        color = AmberGoldSecondary
                    )
                }
            }

            Spacer(Modifier.height(4.dp))

            items.forEach { item ->
                val selected = currentRoute.startsWith(item.routeBase)
                TvFocusableCard(
                    onClick = { onSelect(item) },
                    accentColor = SkyTealPrimary,
                    containerColor = if (selected) SkyTealContainer else ElevatedCardSurface.copy(alpha = 0.5f),
                    focusedContainerColor = SkyTealContainer.copy(alpha = 0.85f),
                    cornerRadius = 14.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(item.testTag)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.titleFa,
                            tint = if (selected) SkyTealPrimary else TextSecondaryMuted,
                            modifier = Modifier.size(22.dp)
                        )
                        Text(
                            text = item.titleFa,
                            style = MaterialTheme.typography.labelLarge,
                            color = if (selected) TextPrimaryLight else TextSecondaryMuted
                        )
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MidnightBg.copy(alpha = 0.65f))
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "کنترل TV: جهت‌نما + OK",
                    style = MaterialTheme.typography.labelMedium,
                    color = TextTertiarySubtle
                )
            }
        }
    }
}

@Composable
private fun ProspectNavHost(
    navController: androidx.navigation.NavHostController,
    viewModel: TutorViewModel,
    dashboardState: com.example.ui.viewmodel.TutorDashboardState,
    playbackState: com.example.audio.PlaybackState,
    pronunciationResult: com.example.audio.PronunciationResult,
    chatMessages: List<com.example.ui.viewmodel.ChatMessage>,
    tutorStreamState: com.example.ai.TutorStreamState,
    tutorSelectedUnitId: Int,
    onSelectTutorUnit: (Int) -> Unit,
    isWideScreen: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.HOME,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(AppRoutes.HOME) {
            HomeScreen(
                dashboardState = dashboardState,
                isWideScreen = isWideScreen,
                onOpenUnit = { unitId, stage ->
                    navController.navigate(AppRoutes.lessonRoute(unitId, stage))
                },
                onOpenWeakWordsReview = {
                    navController.navigate(AppRoutes.dictionaryRoute(weakOnly = true))
                },
                onOpenAiTutor = {
                    navController.navigate(AppRoutes.tutorRoute(dashboardState.preferences.lastUnitId, ""))
                }
            )
        }

        composable(
            route = AppRoutes.LESSON,
            arguments = listOf(
                navArgument("unitId") { type = NavType.IntType },
                navArgument("stageIndex") { type = NavType.IntType }
            )
        ) { backStack ->
            val unitId = backStack.arguments?.getInt("unitId") ?: 1
            val stageIndex = backStack.arguments?.getInt("stageIndex") ?: 0
            val unit = remember(unitId) { Prospect2Catalog.unitById(unitId) }

            LessonDetailScreen(
                unit = unit,
                initialStage = stageIndex,
                dashboardState = dashboardState,
                playbackState = playbackState,
                isWideScreen = isWideScreen,
                onBack = { navController.popBackStack() },
                onStageChanged = { st -> viewModel.saveLastLocation(unitId, st) },
                onMarkStageDone = { st -> viewModel.markStageDone(unitId, st) },
                onPlayText = { txt, tag, lineIdx -> viewModel.speakText(txt, tag, lineIdx) },
                onPlayConversation = { lines, start -> viewModel.playConversation(lines, start) },
                onStopSpeech = { viewModel.stopSpeaking() },
                onSetSpeechRate = { viewModel.setSpeechRate(it) },
                onTogglePersian = { viewModel.setShowPersianTranslation(it) },
                onRecordGrammarMistake = { title, userAns, correct ->
                    viewModel.recordGrammarMistake(unitId, title, userAns, correct)
                },
                onRecordExercise = { ex, solved, hints, userAns ->
                    viewModel.recordExerciseResult(
                        exerciseId = ex.id,
                        lessonId = unitId,
                        solved = solved,
                        hintsUsed = hints,
                        userAnswer = userAns,
                        correctAnswer = ex.correctAnswer,
                        titleFa = ex.titleFa
                    )
                },
                onRecordQuiz = { score, total, mistakes ->
                    viewModel.recordQuizCompletion(unitId, score, total, mistakes)
                },
                onAskTutorAboutLesson = { prompt ->
                    onSelectTutorUnit(if (unitId > 100) 1 else unitId)
                    navController.navigate(AppRoutes.tutorRoute(if (unitId > 100) 1 else unitId, prompt))
                },
                onOpenNextUnit = { nextId ->
                    navController.navigate(AppRoutes.lessonRoute(nextId, 0)) {
                        popUpTo(AppRoutes.HOME)
                    }
                }
            )
        }

        composable(
            route = AppRoutes.DICTIONARY,
            arguments = listOf(
                navArgument("weakOnly") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { backStack ->
            val weakOnly = backStack.arguments?.getBoolean("weakOnly") ?: false
            DictionaryScreen(
                dashboardState = dashboardState,
                initialWeakOnly = weakOnly,
                isWideScreen = isWideScreen,
                buildReviewQueue = { filter -> viewModel.buildSmartReviewQueue(filter) },
                onPlayWord = { txt -> viewModel.speakText(txt, "dict_$txt") },
                onRecordWordReview = { word, isCorrect -> viewModel.recordWordReview(word, isCorrect) }
            )
        }

        composable(AppRoutes.PRONUNCIATION) {
            PronunciationLabScreen(
                playbackState = playbackState,
                pronunciationResult = pronunciationResult,
                isWideScreen = isWideScreen,
                isSpeechRecAvailable = viewModel.isSpeechRecognitionAvailable(),
                onPlayText = { txt, tag, idx -> viewModel.speakText(txt, tag, idx) },
                onPlayConversation = { lines, start -> viewModel.playConversation(lines, start) },
                onStopSpeech = { viewModel.stopSpeaking() },
                onSetSpeechRate = { viewModel.setSpeechRate(it) },
                onStartSpeakingPractice = { target -> viewModel.startPronunciationPractice(target) },
                onClearSpeakingResult = { viewModel.clearPronunciationResult() }
            )
        }

        composable(
            route = AppRoutes.AI_TUTOR,
            arguments = listOf(
                navArgument("unitId") {
                    type = NavType.IntType
                    defaultValue = 1
                },
                navArgument("prompt") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStack ->
            val argUnitId = backStack.arguments?.getInt("unitId") ?: tutorSelectedUnitId
            val argPrompt = backStack.arguments?.getString("prompt")?.takeIf { it.isNotBlank() }
            val unit = Prospect2Catalog.unitById(if (argUnitId in 1..7) argUnitId else tutorSelectedUnitId)

            AiTutorScreen(
                messages = chatMessages,
                streamState = tutorStreamState,
                selectedUnit = unit,
                initialPrompt = argPrompt,
                isWideScreen = isWideScreen,
                onSelectUnit = { onSelectTutorUnit(it) },
                onSendPrompt = { q, u, offline -> viewModel.askTutor(q, u, offline) },
                onClearChat = { viewModel.clearChat() }
            )
        }

        composable(AppRoutes.PROGRESS) {
            ProgressReportScreen(
                dashboardState = dashboardState,
                isWideScreen = isWideScreen,
                onUpdateStudentName = { viewModel.setStudentName(it) },
                onOpenLesson = { unitId -> navController.navigate(AppRoutes.lessonRoute(unitId, 0)) },
                onPracticeWeakWords = { navController.navigate(AppRoutes.dictionaryRoute(weakOnly = true)) },
                onAskTutorWeakness = {
                    navController.navigate(
                        AppRoutes.tutorRoute(
                            dashboardState.preferences.lastUnitId,
                            "نقاط ضعف من را تحلیل کن و تمرین جبرانی بده"
                        )
                    )
                },
                onResetAllProgress = { viewModel.clearAllProgress() }
            )
        }
    }
}
