package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class PlayerRepository(private val playerDao: PlayerDao) {

    val playerProfile: Flow<PlayerProfileEntity?> = playerDao.getPlayerProfile()
    val allProgress: Flow<List<LevelProgressEntity>> = playerDao.getAllProgress()
    val allBadges: Flow<List<BadgeEntity>> = playerDao.getAllBadges()

    suspend fun updateName(newName: String) {
        val current = playerDao.getPlayerProfile().firstOrNull() ?: PlayerProfileEntity()
        playerDao.insertOrUpdateProfile(current.copy(playerName = newName.trim()))
    }

    suspend fun updateSelectedPet(petId: String) {
        val current = playerDao.getPlayerProfile().firstOrNull() ?: PlayerProfileEntity()
        playerDao.insertOrUpdateProfile(current.copy(selectedPetId = petId))
    }

    suspend fun updateSettings(sound: Boolean, music: Boolean, tts: Boolean) {
        val current = playerDao.getPlayerProfile().firstOrNull() ?: PlayerProfileEntity()
        playerDao.insertOrUpdateProfile(
            current.copy(
                soundEnabled = sound,
                musicEnabled = music,
                ttsEnabled = tts
            )
        )
    }

    suspend fun completeLevel(
        worldId: String,
        levelNumber: Int,
        starsEarned: Int,
        scoreEarned: Int,
        coinsEarned: Int,
        xpEarned: Int
    ) {
        // Save level progress
        playerDao.saveLevelProgress(
            LevelProgressEntity(
                worldId = worldId,
                levelNumber = levelNumber,
                stars = starsEarned,
                highScore = scoreEarned,
                isCompleted = true
            )
        )

        // Update profile stats
        val current = playerDao.getPlayerProfile().firstOrNull() ?: PlayerProfileEntity()
        val allProg = playerDao.getAllProgress().firstOrNull() ?: emptyList()

        // Calculate total stars from distinct completed levels
        val totalStars = allProg.sumOf { it.stars } + starsEarned
        val newXp = current.currentXp + xpEarned
        val newLevel = 1 + (newXp / 150)
        val newCoins = current.totalCoins + coinsEarned

        playerDao.insertOrUpdateProfile(
            current.copy(
                totalStars = totalStars,
                totalCoins = newCoins,
                currentXp = newXp,
                playerLevel = newLevel
            )
        )

        // Check badge unlocks
        playerDao.unlockBadge("badge_first_step")
        if (totalStars >= 15) {
            playerDao.unlockBadge("badge_star_collector")
        }
        if (worldId == "NUMBER_ISLAND" && levelNumber == 5) {
            playerDao.unlockBadge("badge_counting_expert")
        }
        if (worldId == "FOREST_ADD_SUB" && levelNumber == 5) {
            playerDao.unlockBadge("badge_math_wizard")
        }
        if (worldId == "VALLEY_MULT_DIV" && levelNumber == 5) {
            playerDao.unlockBadge("badge_times_hero")
        }
        if (worldId == "CASTLE_MATH_MASTER" && levelNumber == 5) {
            playerDao.unlockBadge("badge_zorra_champion")
        }
    }
}
