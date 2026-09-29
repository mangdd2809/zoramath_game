package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CreatorCreditFooter
import com.example.ui.theme.GameBgCream
import com.example.ui.theme.ZorraBerryPink
import com.example.ui.theme.ZorraMintGreen
import com.example.ui.theme.ZorraOrange
import com.example.ui.theme.ZorraOrangeDark
import com.example.ui.theme.ZorraSkyBlue
import com.example.ui.theme.ZorraSunYellow
import com.example.viewmodel.GameViewModel
import com.example.viewmodel.Screen

@Composable
fun PracticeScreen(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.navigateTo(Screen.HOME)
    }

    var selectedOp by remember { mutableStateOf("+") }
    var selectedDifficulty by remember { mutableStateOf("Mudah") }

    val operations = listOf(
        Triple("COUNT", "Hitung Benda", "🍎"),
        Triple("+", "Penjumlahan", "➕"),
        Triple("-", "Pengurangan", "➖"),
        Triple("×", "Perkalian", "✖️"),
        Triple("÷", "Pembagian", "➗")
    )

    val difficulties = listOf(
        "Mudah" to "Untuk Pemula (Angka 1-10)",
        "Sedang" to "Tingkat Menengah (Angka 10-50)",
        "Sulit" to "Tantangan Master (Angka 50+)"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(GameBgCream)
    ) {
        // Top Bar
        Surface(
            color = Color.White,
            shadowElevation = 3.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateTo(Screen.HOME) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali ke Beranda",
                        tint = ZorraOrangeDark
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                Column {
                    Text(
                        text = "Mode Latihan Bebas",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Pilih operasi dan tingkat kesulitan sesukamu",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "1. Pilih Operasi Matematika:",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Operations grid
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    operations.forEach { (opKey, opLabel, opEmoji) ->
                        val isSelected = selectedOp == opKey
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedOp = opKey },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) ZorraOrange.copy(alpha = 0.15f) else Color.White
                            ),
                            border = if (isSelected) {
                                androidx.compose.foundation.BorderStroke(2.dp, ZorraOrange)
                            } else null
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) ZorraOrange else Color(0xFFF0F0F3)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = opEmoji, fontSize = 20.sp)
                                }
                                Spacer(modifier = Modifier.width(14.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = opLabel,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = if (isSelected) ZorraOrangeDark else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                if (isSelected) {
                                    Text(text = "✓ Terpilih", color = ZorraOrangeDark, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "2. Pilih Tingkat Kesulitan:",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Difficulty Cards
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    difficulties.forEach { (diff, subtitle) ->
                        val isSelected = selectedDifficulty == diff
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedDifficulty = diff },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) ZorraMintGreen.copy(alpha = 0.2f) else Color.White
                            ),
                            border = if (isSelected) {
                                androidx.compose.foundation.BorderStroke(2.dp, ZorraMintGreen)
                            } else null
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = when (diff) {
                                        "Mudah" -> "🌱"
                                        "Sedang" -> "⭐"
                                        else -> "🔥"
                                    },
                                    fontSize = 24.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = diff,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = if (isSelected) Color(0xFF00695C) else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }

            // Start Practice CTA
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        viewModel.startPractice(selectedOp, selectedDifficulty)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .testTag("start_practice_cta"),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ZorraOrange)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Mulai", tint = Color.White)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Mulai 5 Soal Latihan! 🚀",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }
            }

            item {
                CreatorCreditFooter()
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
