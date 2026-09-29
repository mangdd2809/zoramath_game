package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_profile")
data class PlayerProfileEntity(
    @PrimaryKey val id: Int = 1,
    val playerName: String = "Petualang Cilik",
    val selectedPetId: String = "zorra",
    val totalStars: Int = 0,
    val totalCoins: Int = 50,
    val currentXp: Int = 0,
    val playerLevel: Int = 1,
    val soundEnabled: Boolean = true,
    val musicEnabled: Boolean = true,
    val ttsEnabled: Boolean = true
)

@Entity(tableName = "level_progress", primaryKeys = ["worldId", "levelNumber"])
data class LevelProgressEntity(
    val worldId: String,
    val levelNumber: Int,
    val stars: Int = 0,
    val highScore: Int = 0,
    val isCompleted: Boolean = false
)

@Entity(tableName = "badges")
data class BadgeEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val iconEmoji: String,
    val isUnlocked: Boolean = false,
    val requirementText: String = ""
)
