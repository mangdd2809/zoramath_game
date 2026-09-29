package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.CreatorCreditFooter
import com.example.ui.theme.GameBgCream
import com.example.ui.theme.ZorraMintGreen
import com.example.ui.theme.ZorraOrange
import com.example.ui.theme.ZorraOrangeDark
import com.example.ui.theme.ZorraSunYellow
import com.example.viewmodel.GameViewModel
import com.example.viewmodel.Screen

@Composable
fun SettingsAboutScreen(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.navigateTo(Screen.HOME)
    }

    val profile by viewModel.playerProfile.collectAsState()
    var nameInput by remember(profile.playerName) { mutableStateOf(profile.playerName) }
    var isSavedPrompt by remember { mutableStateOf(false) }

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
                        text = "Pengaturan & Info",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Atur suara, nama pemain, dan informasi aplikasi",
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
                    text = "Profil Petualang Cilik:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Name editing card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.img_zorra_icon),
                                contentDescription = "Zorra Avatar",
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Nama Petualang:",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                                Text(
                                    text = profile.playerName,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        OutlinedTextField(
                            value = nameInput,
                            onValueChange = {
                                nameInput = it
                                isSavedPrompt = false
                            },
                            label = { Text("Ganti Nama") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("player_name_input"),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {
                                if (nameInput.isNotBlank()) {
                                    viewModel.updatePlayerName(nameInput)
                                    isSavedPrompt = true
                                    viewModel.soundManager.speak("Nama berhasil disimpan!")
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .testTag("save_name_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = ZorraOrange),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = if (isSavedPrompt) "Tersimpan! ✓" else "Simpan Nama Baru",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Audio & Voice Switches
            item {
                Text(
                    text = "Suara & Musik:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Sound FX
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Filled.VolumeUp, contentDescription = null, tint = ZorraOrange)
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(text = "Efek Suara Permainan", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(text = "Suara klik, lonceng, dan marimba", fontSize = 11.sp, color = Color.Gray)
                                }
                            }
                            Switch(
                                checked = profile.soundEnabled,
                                onCheckedChange = { viewModel.toggleSound() },
                                colors = SwitchDefaults.colors(checkedThumbColor = ZorraOrange, checkedTrackColor = ZorraOrange.copy(alpha = 0.4f))
                            )
                        }

                        // Background Music
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Filled.MusicNote, contentDescription = null, tint = ZorraSunYellow)
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(text = "Musik Latar Ceria", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(text = "Melodi lembut menyenangkan untuk anak", fontSize = 11.sp, color = Color.Gray)
                                }
                            }
                            Switch(
                                checked = profile.musicEnabled,
                                onCheckedChange = { viewModel.toggleMusic() },
                                colors = SwitchDefaults.colors(checkedThumbColor = ZorraOrange, checkedTrackColor = ZorraOrange.copy(alpha = 0.4f))
                            )
                        }

                        // Narration Voice (TTS)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Filled.RecordVoiceOver, contentDescription = null, tint = ZorraMintGreen)
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(text = "Suara Narator Zorra", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(text = "Membacakan pertanyaan dalam bahasa Indonesia", fontSize = 11.sp, color = Color.Gray)
                                }
                            }
                            Switch(
                                checked = profile.ttsEnabled,
                                onCheckedChange = { viewModel.toggleTts() },
                                colors = SwitchDefaults.colors(checkedThumbColor = ZorraMintGreen, checkedTrackColor = ZorraMintGreen.copy(alpha = 0.4f))
                            )
                        }
                    }
                }
            }

            // About ZorraMath Card
            item {
                Text(
                    text = "Tentang ZorraMath:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.img_zorra_icon),
                                contentDescription = "Zorra Logo",
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(RoundedCornerShape(14.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "ZorraMath",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = ZorraOrangeDark
                                )
                                Text(
                                    text = "Versi 1.0 • Edisi Petualangan Ceria",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "ZorraMath dirancang khusus untuk memikat hati anak-anak usia dini (PAUD/TK) hingga Sekolah Dasar (SD) agar senang belajar berhitung sambil berpetualang secara interaktif dan penuh warna.",
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = ZorraOrange.copy(alpha = 0.1f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, ZorraOrange.copy(alpha = 0.3f))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "🎨", fontSize = 20.sp)
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "Kreator & Pengembang:",
                                        fontSize = 11.sp,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = "Created with ❤️ by @lmerGtamam",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = ZorraOrangeDark
                                    )
                                }
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
