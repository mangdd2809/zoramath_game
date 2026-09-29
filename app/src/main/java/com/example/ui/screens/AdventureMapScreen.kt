package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.WorldId
import com.example.ui.theme.GameBgCream
import com.example.ui.theme.ZorraMintGreen
import com.example.ui.theme.ZorraOrange
import com.example.ui.theme.ZorraOrangeDark
import com.example.ui.theme.ZorraSunYellow
import com.example.viewmodel.GameViewModel
import com.example.viewmodel.Screen

@Composable
fun AdventureMapScreen(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.navigateTo(Screen.HOME)
    }

    val selectedWorld by viewModel.selectedWorldForMap.collectAsState()
    val allProgress by viewModel.allProgress.collectAsState()

    val levelNames = when (selectedWorld) {
        WorldId.NUMBER_ISLAND -> listOf(
            "Mengenal Angka 1-5" to "Sentuh & hitung buah segar",
            "Hitung Benda 6-10" to "Bantu Zorra menghitung semangka & donat",
            "Banyak vs Sedikit" to "Bandingkan jumlah benda dengan simbol > < =",
            "Urutan Angka Hilang" to "Tebak angka berikutnya dalam pola",
            "Pesta Balon Angka" to "Pecahkan balon angka yang tepat!"
        )
        WorldId.FOREST_ADD_SUB -> listOf(
            "Penjumlahan Apel Segar" to "Belajar tambah apel & bintang",
            "Pengurangan Bunga Mekar" to "Belajar kurang dengan balon terbang",
            "Tebak Angka Rahasia" to "Temukan angka rahasia di dalam kotak",
            "Balapan Hitung Puluhan" to "Penjumlahan & pengurangan angka puluhan",
            "Bos Jamur Ceria 🍄" to "Kalahkan Bos Jamur dengan 5 jurus hitung!"
        )
        WorldId.VALLEY_MULT_DIV -> listOf(
            "Taman Keranjang Buah" to "Memahami konsep perkalian bergrup",
            "Tabel Perkalian Bintang" to "Latihan perkalian 2 sampai 9",
            "Bagi Roti Bersama Teman" to "Belajar konsep adil pembagian",
            "Tantangan Pembagian Cepat" to "Asah ketelitian membagi angka",
            "Bos Kristal Emas 💎" to "Kalahkan monster kristal dengan kombo kali-bagi!"
        )
        WorldId.CASTLE_MATH_MASTER -> listOf(
            "Operasi Campuran (KABATAKU)" to "Aturan urutan operasi matematika",
            "Pecahan Pizza Lezat" to "Memahami pecahan & persentase",
            "Misteri Pola & Deret" to "Tebak deret kuadrat & prima",
            "Tantangan Waktu Cepat" to "Pangkat & geometri bangun datar",
            "Raja Angka Tertinggi 👑" to "Pertarungan agung merebut mahkota ZorraMath!"
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(GameBgCream)
    ) {
        // Header
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
                IconButton(
                    onClick = { viewModel.navigateTo(Screen.HOME) },
                    modifier = Modifier.testTag("back_to_home")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali ke Beranda",
                        tint = ZorraOrangeDark
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                Column {
                    Text(
                        text = "Peta Petualangan",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Pilih pulau & selesaikan tantangan",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        // Horizontal World Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WorldId.entries.forEach { world ->
                val isSelected = world == selectedWorld
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (isSelected) Color(world.colorHex) else Color.White,
                    shadowElevation = if (isSelected) 4.dp else 1.dp,
                    modifier = Modifier.clickable { viewModel.selectWorldForMap(world) }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = when (world) {
                                WorldId.NUMBER_ISLAND -> "🏝️"
                                WorldId.FOREST_ADD_SUB -> "🌲"
                                WorldId.VALLEY_MULT_DIV -> "💎"
                                WorldId.CASTLE_MATH_MASTER -> "🏰"
                            },
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Column {
                            Text(
                                text = world.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = world.gradeLevel,
                                fontSize = 10.sp,
                                color = if (isSelected) Color.White.copy(alpha = 0.85f) else Color.Gray
                            )
                        }
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // World Banner / Map illustration
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(20.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.img_world_map),
                            contentDescription = "Peta Petualangan Zorra",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.6f)
                                        )
                                    )
                                )
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = selectedWorld.title,
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 18.sp
                            )
                            Text(
                                text = "${selectedWorld.gradeLevel} • ${selectedWorld.subtitle}",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Daftar Level:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // 5 Levels in this world
            items(5) { index ->
                val levelNum = index + 1
                val (title, desc) = levelNames[index]

                // Find progress
                val progress = allProgress.find { it.worldId == selectedWorld.name && it.levelNumber == levelNum }
                val isCompleted = progress?.isCompleted ?: false
                val starsEarned = progress?.stars ?: 0

                // Unlock rule: Level 1 always unlocked. Level N unlocked if Level N-1 is completed
                val prevCompleted = if (levelNum == 1) true else {
                    allProgress.any { it.worldId == selectedWorld.name && it.levelNumber == levelNum - 1 && it.isCompleted }
                }
                val isUnlocked = prevCompleted

                val isBoss = levelNum == 5

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(if (isUnlocked) 4.dp else 1.dp, RoundedCornerShape(18.dp))
                        .clickable(enabled = isUnlocked) {
                            viewModel.startLevel(selectedWorld, levelNum)
                        }
                        .testTag("level_${selectedWorld.name}_$levelNum"),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = when {
                            !isUnlocked -> Color(0xFFF0F0F3)
                            isBoss -> Color(0xFFFFF7ED)
                            else -> Color.White
                        }
                    ),
                    border = if (isBoss && isUnlocked) {
                        androidx.compose.foundation.BorderStroke(2.dp, ZorraSunYellow)
                    } else null
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Level number circle badge
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(
                                    when {
                                        !isUnlocked -> Color.LightGray
                                        isBoss -> ZorraOrange
                                        isCompleted -> ZorraMintGreen
                                        else -> Color(selectedWorld.colorHex)
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (!isUnlocked) {
                                Icon(
                                    imageVector = Icons.Filled.Lock,
                                    contentDescription = "Terkunci",
                                    tint = Color.White,
                                    modifier = Modifier.size(22.dp)
                                )
                            } else if (isBoss) {
                                Text(text = "👑", fontSize = 24.sp)
                            } else {
                                Text(
                                    text = "$levelNum",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Level $levelNum: $title",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = if (isUnlocked) MaterialTheme.colorScheme.onSurface else Color.Gray
                                )
                            }
                            Text(
                                text = desc,
                                fontSize = 12.sp,
                                color = if (isUnlocked) MaterialTheme.colorScheme.onSurfaceVariant else Color.LightGray
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            // Star indicator
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                for (s in 1..3) {
                                    Text(
                                        text = if (s <= starsEarned) "⭐" else "☆",
                                        fontSize = 16.sp,
                                        color = if (s <= starsEarned) Color.Unspecified else Color.Gray
                                    )
                                    Spacer(modifier = Modifier.width(2.dp))
                                }

                                if (isCompleted) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = ZorraMintGreen.copy(alpha = 0.2f)
                                    ) {
                                        Text(
                                            text = "Selesai!",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF00695C),
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }

                        // Action arrow / play icon
                        if (isUnlocked) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(ZorraOrange.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.PlayArrow,
                                    contentDescription = "Mulai Level",
                                    tint = ZorraOrange,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
