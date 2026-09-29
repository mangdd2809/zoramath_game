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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AVAILABLE_PETS
import com.example.ui.components.CreatorCreditFooter
import com.example.ui.theme.GameBgCream
import com.example.ui.theme.ZorraMintGreen
import com.example.ui.theme.ZorraOrange
import com.example.ui.theme.ZorraOrangeDark
import com.example.ui.theme.ZorraSunYellow
import com.example.viewmodel.GameViewModel
import com.example.viewmodel.Screen

@Composable
fun TrophyScreen(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.navigateTo(Screen.HOME)
    }

    val profile by viewModel.playerProfile.collectAsState()
    val badges by viewModel.allBadges.collectAsState()
    val allProgress by viewModel.allProgress.collectAsState()

    val totalStars = allProgress.sumOf { it.stars }

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
                        text = "Galeri Piala & Sahabat",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Koleksi lencana dan pilih teman berhitungmu",
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
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Pilih Sahabat Pendamping:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Mascot selector
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AVAILABLE_PETS.forEach { pet ->
                        val isUnlocked = totalStars >= pet.unlockStars
                        val isSelected = profile.selectedPetId == pet.id

                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .clickable(enabled = isUnlocked) {
                                    viewModel.selectCompanion(pet.id)
                                    viewModel.soundManager.speak(pet.greeting)
                                },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = when {
                                    isSelected -> ZorraOrange.copy(alpha = 0.2f)
                                    !isUnlocked -> Color(0xFFF1F1F4)
                                    else -> Color.White
                                }
                            ),
                            border = if (isSelected) {
                                androidx.compose.foundation.BorderStroke(2.dp, ZorraOrange)
                            } else null
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp, horizontal = 4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(46.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) ZorraOrange.copy(alpha = 0.3f) else Color(0xFFF7F7F9)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isUnlocked) {
                                        Text(text = pet.emoji, fontSize = 24.sp)
                                    } else {
                                        Icon(
                                            imageVector = Icons.Filled.Lock,
                                            contentDescription = "Terkunci",
                                            tint = Color.Gray,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = pet.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = if (isSelected) ZorraOrangeDark else MaterialTheme.colorScheme.onSurface
                                )

                                Text(
                                    text = if (isUnlocked) (if (isSelected) "Dipilih" else "Aktif") else "⭐ ${pet.unlockStars}",
                                    fontSize = 10.sp,
                                    color = if (isSelected) ZorraOrangeDark else Color.Gray,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }

            // Current companion quote box
            item {
                val currentPet = AVAILABLE_PETS.find { it.id == profile.selectedPetId } ?: AVAILABLE_PETS.first()
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = ZorraSunYellow.copy(alpha = 0.18f)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = currentPet.emoji, fontSize = 28.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "${currentPet.name} - ${currentPet.title}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color(0xFF6B4800)
                            )
                            Text(
                                text = "\"${currentPet.greeting}\"",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            // Badges Section
            item {
                Text(
                    text = "Lencana Prestasi (${badges.count { it.isUnlocked }} / ${badges.size}):",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            items(badges) { badge ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (badge.isUnlocked) Color.White else Color(0xFFF7F7F9)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = if (badge.isUnlocked) 2.dp else 0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(
                                    if (badge.isUnlocked) ZorraSunYellow.copy(alpha = 0.25f)
                                    else Color.LightGray.copy(alpha = 0.3f)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (badge.isUnlocked) badge.iconEmoji else "🔒",
                                fontSize = 24.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = badge.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = if (badge.isUnlocked) MaterialTheme.colorScheme.onSurface else Color.Gray
                            )
                            Text(
                                text = badge.description,
                                fontSize = 12.sp,
                                color = if (badge.isUnlocked) MaterialTheme.colorScheme.onSurfaceVariant else Color.LightGray
                            )
                            Text(
                                text = "Syarat: ${badge.requirementText}",
                                fontSize = 10.sp,
                                color = if (badge.isUnlocked) ZorraMintGreen else Color.Gray,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        if (badge.isUnlocked) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = ZorraMintGreen.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = "Terbuka",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF00695C),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }
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
