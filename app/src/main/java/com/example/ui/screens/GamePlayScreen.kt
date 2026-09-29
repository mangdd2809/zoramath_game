package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.GameModeType
import com.example.model.Question
import com.example.ui.components.ConfettiExplosion
import com.example.ui.theme.GameBgCream
import com.example.ui.theme.ZorraBerryPink
import com.example.ui.theme.ZorraMintGreen
import com.example.ui.theme.ZorraOrange
import com.example.ui.theme.ZorraOrangeDark
import com.example.ui.theme.ZorraSkyBlue
import com.example.ui.theme.ZorraSunYellow
import com.example.viewmodel.ActiveGameState
import com.example.viewmodel.GameViewModel
import com.example.viewmodel.Screen

@Composable
fun GamePlayScreen(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.gameState.collectAsState()

    BackHandler {
        if (state.isPractice) {
            viewModel.navigateTo(Screen.PRACTICE)
        } else {
            viewModel.navigateTo(Screen.ADVENTURE_MAP)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(GameBgCream)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Game Top HUD
            GameHudBar(
                state = state,
                onBack = {
                    if (state.isPractice) {
                        viewModel.navigateTo(Screen.PRACTICE)
                    } else {
                        viewModel.navigateTo(Screen.ADVENTURE_MAP)
                    }
                },
                onSpeak = { viewModel.speakPromptForCurrentQuestion() }
            )

            val currentQuestion = state.currentQuestion
            if (currentQuestion != null && !state.isLevelCompleted) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(4.dp))
                        // Question Banner Card
                        QuestionHeaderCard(
                            question = currentQuestion,
                            onSpeak = { viewModel.speakPromptForCurrentQuestion() }
                        )
                    }

                    // Mode Specific Interactive Area
                    item {
                        when (currentQuestion.type) {
                            GameModeType.COUNTING -> {
                                InteractiveCountingBoard(
                                    question = currentQuestion,
                                    countedIndices = state.countedItems,
                                    onItemTap = { idx -> viewModel.tapCountItem(idx) }
                                )
                            }
                            GameModeType.BALLOON_POP -> {
                                BalloonPopBoard(
                                    question = currentQuestion,
                                    poppedBalloons = state.poppedBalloons,
                                    onPop = { opt -> viewModel.popBalloon(opt) }
                                )
                            }
                            GameModeType.COMPARISON -> {
                                VisualComparisonBoard(question = currentQuestion)
                            }
                            else -> {
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }

                    // Answer Options (If not balloon pop mode, show big friendly choice buttons)
                    if (currentQuestion.type != GameModeType.BALLOON_POP) {
                        item {
                            AnswerChoicesSection(
                                question = currentQuestion,
                                selectedOption = state.selectedOption,
                                isAnswerChecked = state.isAnswerChecked,
                                isCorrect = state.isCorrect,
                                onSelect = { opt -> viewModel.submitAnswer(opt) }
                            )
                        }
                    }

                    // Answer Feedback & Next Button
                    if (state.isAnswerChecked) {
                        item {
                            AnswerFeedbackCard(
                                isCorrect = state.isCorrect,
                                explanation = currentQuestion.explanation,
                                isLastQuestion = state.currentIndex == state.questions.size - 1,
                                onNext = { viewModel.nextQuestion() }
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(30.dp))
                    }
                }
            }
        }

        // Level Complete Dialog / Confetti Overlay
        if (state.isLevelCompleted) {
            LevelCompleteOverlay(
                state = state,
                onRetry = { viewModel.retryLevel() },
                onContinue = {
                    if (state.isPractice) {
                        viewModel.navigateTo(Screen.PRACTICE)
                    } else {
                        viewModel.navigateTo(Screen.ADVENTURE_MAP)
                    }
                }
            )
        }
    }
}

@Composable
fun GameHudBar(
    state: ActiveGameState,
    onBack: () -> Unit,
    onSpeak: () -> Unit
) {
    Surface(
        color = Color.White,
        shadowElevation = 3.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("game_back_button")) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = ZorraOrangeDark
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Column {
                        Text(
                            text = if (state.isPractice) "Mode Latihan" else "${state.worldId?.title ?: "Petualangan"} - Level ${state.levelNumber}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Soal ${state.currentIndex + 1} dari ${state.questions.size}",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Audio voice prompt button
                    IconButton(
                        onClick = onSpeak,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(ZorraSkyBlue.copy(alpha = 0.2f))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.VolumeUp,
                            contentDescription = "Dengarkan Pertanyaan",
                            tint = Color(0xFF0077B6),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    // Score chip
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = ZorraSunYellow.copy(alpha = 0.2f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "⭐", fontSize = 13.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${state.currentScore}",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 13.sp,
                                color = Color(0xFF8A5A00)
                            )
                        }
                    }
                }
            }

            // Linear question progress bar
            val progress = if (state.questions.isNotEmpty()) {
                (state.currentIndex + 1).toFloat() / state.questions.size.toFloat()
            } else 0f

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = ZorraOrange,
                trackColor = Color(0xFFFFE0D2)
            )
        }
    }
}

@Composable
fun QuestionHeaderCard(
    question: Question,
    onSpeak: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = ZorraOrange.copy(alpha = 0.12f)
            ) {
                Text(
                    text = when (question.type) {
                        GameModeType.COUNTING -> "🔢 Sentuh & Hitung Benda!"
                        GameModeType.BALLOON_POP -> "🎈 Pecahkan Balon yang Benar!"
                        GameModeType.COMPARISON -> "⚖️ Bandingkan Jumlahnya!"
                        GameModeType.SEQUENCE -> "🧩 Lengkapi Urutan Angka!"
                        GameModeType.MULTIPLE_CHOICE -> "💡 Temukan Jawaban yang Tepat!"
                    },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = ZorraOrangeDark,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = question.questionText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InteractiveCountingBoard(
    question: Question,
    countedIndices: Set<Int>,
    onItemTap: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FBFD)),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, ZorraSkyBlue.copy(alpha = 0.4f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sentuh setiap ${question.visualItemEmoji} untuk berhitung:",
                fontSize = 13.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Display grid of items to count
            FlowRow(
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                for (i in 1..question.visualItemCount) {
                    val isCounted = countedIndices.contains(i)
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(62.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                if (isCounted) ZorraMintGreen.copy(alpha = 0.25f)
                                else Color.White
                            )
                            .border(
                                width = if (isCounted) 2.dp else 1.dp,
                                color = if (isCounted) ZorraMintGreen else Color.LightGray.copy(alpha = 0.6f),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable { onItemTap(i) },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = question.visualItemEmoji, fontSize = 28.sp)
                            if (isCounted) {
                                Text(
                                    text = "$i",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF00695C)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (countedIndices.isNotEmpty()) ZorraMintGreen.copy(alpha = 0.2f) else Color.LightGray.copy(alpha = 0.2f)
            ) {
                Text(
                    text = "Benda yang disentuh: ${countedIndices.size} / ${question.visualItemCount}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (countedIndices.isNotEmpty()) Color(0xFF00695C) else Color.Gray,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun BalloonPopBoard(
    question: Question,
    poppedBalloons: Set<String>,
    onPop: (String) -> Unit
) {
    val balloonColors = listOf(
        ZorraOrange,
        ZorraSkyBlue,
        ZorraMintGreen,
        ZorraBerryPink
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sentuh balon pilihanmu untuk meletuskannya!",
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            question.options.forEachIndexed { index, option ->
                val isPopped = poppedBalloons.contains(option)
                val balloonColor = balloonColors[index % balloonColors.size]

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable(enabled = !isPopped) { onPop(option) }
                ) {
                    if (isPopped) {
                        Box(
                            modifier = Modifier
                                .size(72.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "💥", fontSize = 36.sp)
                        }
                    } else {
                        // Floating bouncy balloon
                        Box(
                            modifier = Modifier
                                .size(74.dp)
                                .shadow(6.dp, CircleShape)
                                .clip(CircleShape)
                                .background(balloonColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = option,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        }
                        // Balloon string
                        Text(text = "🧶", fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun VisualComparisonBoard(question: Question) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Group
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = ZorraOrange.copy(alpha = 0.12f),
                    modifier = Modifier.padding(bottom = 6.dp)
                ) {
                    Text(
                        text = "Kelompok Kiri",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = ZorraOrangeDark,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFFFF7ED)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = question.visualItemEmoji, fontSize = 26.sp)
                        Text(
                            text = "${question.visualItemCount}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = ZorraOrangeDark
                        )
                    }
                }
            }

            Text(text = "VS", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Color.Gray)

            // Right Group
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = ZorraSkyBlue.copy(alpha = 0.12f),
                    modifier = Modifier.padding(bottom = 6.dp)
                ) {
                    Text(
                        text = "Kelompok Kanan",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0077B6),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFE8F8FC)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = question.visualItemEmoji, fontSize = 26.sp)
                        Text(
                            text = "${question.visualItemCount2}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF0077B6)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AnswerChoicesSection(
    question: Question,
    selectedOption: String?,
    isAnswerChecked: Boolean,
    isCorrect: Boolean,
    onSelect: (String) -> Unit
) {
    val optionColors = listOf(
        ZorraOrange,
        ZorraSkyBlue,
        ZorraMintGreen,
        ZorraBerryPink
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        question.options.chunked(2).forEachIndexed { rowIndex, rowOptions ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                rowOptions.forEachIndexed { colIndex, option ->
                    val isThisSelected = selectedOption == option
                    val isTarget = isAnswerChecked && option.trim() == question.correctAnswer.trim()
                    val buttonBaseColor = optionColors[(rowIndex * 2 + colIndex) % optionColors.size]

                    val (containerColor, contentColor) = when {
                        isTarget -> ZorraMintGreen to Color.White
                        isThisSelected && !isCorrect -> ZorraBerryPink to Color.White
                        isThisSelected -> buttonBaseColor to Color.White
                        else -> Color.White to MaterialTheme.colorScheme.onSurface
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(64.dp)
                            .shadow(3.dp, RoundedCornerShape(16.dp))
                            .clickable(enabled = !isAnswerChecked) { onSelect(option) }
                            .testTag("option_$option"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = containerColor),
                        border = if (!isAnswerChecked) {
                            androidx.compose.foundation.BorderStroke(2.dp, buttonBaseColor.copy(alpha = 0.4f))
                        } else null
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                Text(
                                    text = option,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = contentColor,
                                    textAlign = TextAlign.Center
                                )
                                if (isTarget) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(
                                        imageVector = Icons.Filled.CheckCircle,
                                        contentDescription = "Benar",
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                } else if (isThisSelected && !isCorrect) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = "Salah",
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnswerFeedbackCard(
    isCorrect: Boolean,
    explanation: String,
    isLastQuestion: Boolean,
    onNext: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCorrect) Color(0xFFE8F8F5) else Color(0xFFFFF0F2)
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.5.dp,
            if (isCorrect) ZorraMintGreen else ZorraBerryPink
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (isCorrect) "🎉 Luar Biasa! Benar!" else "💡 Coba Perhatikan:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (isCorrect) Color(0xFF00695C) else ZorraBerryPink
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = explanation,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("next_question_button"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isCorrect) ZorraMintGreen else ZorraOrange
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (isLastQuestion) "Lihat Hasil Level! 🏆" else "Soal Berikutnya",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Lanjut",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun LevelCompleteOverlay(
    state: ActiveGameState,
    onRetry: () -> Unit,
    onContinue: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f)),
        contentAlignment = Alignment.Center
    ) {
        ConfettiExplosion()

        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .shadow(12.dp, RoundedCornerShape(28.dp)),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_zorra_mascot),
                    contentDescription = "Zorra Merayakan Kemenangan",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Level Selesai!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = ZorraOrangeDark
                )

                Text(
                    text = "Kamu hebat sekali!",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Stars display (1-3 stars)
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in 1..3) {
                        val earned = i <= state.starsEarned
                        Text(
                            text = if (earned) "⭐" else "☆",
                            fontSize = 38.sp,
                            color = if (earned) Color.Unspecified else Color.LightGray,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Stats row (Score, Coins, Correct count)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(GameBgCream)
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Skor", fontSize = 11.sp, color = Color.Gray)
                        Text(
                            text = "${state.currentScore}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = ZorraOrangeDark
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Benar", fontSize = 11.sp, color = Color.Gray)
                        Text(
                            text = "${state.correctCount}/${state.questions.size}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF00695C)
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Koin", fontSize = 11.sp, color = Color.Gray)
                        Text(
                            text = "+${state.coinsEarned} 🪙",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF7A4E00)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = onRetry,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFECEFF1),
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(imageVector = Icons.Filled.Refresh, contentDescription = "Ulangi")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Ulangi", fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = onContinue,
                        modifier = Modifier
                            .weight(1.3f)
                            .height(48.dp)
                            .testTag("continue_adventure_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = ZorraOrange),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(text = "Lanjut! 🚀", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}
