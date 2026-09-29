package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        PlayerProfileEntity::class,
        LevelProgressEntity::class,
        BadgeEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun playerDao(): PlayerDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "zorramath_database"
                )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            val database = getDatabase(context)
                            val dao = database.playerDao()
                            // Seed initial profile
                            dao.insertOrUpdateProfile(
                                PlayerProfileEntity(
                                    id = 1,
                                    playerName = "Bintang Cilik",
                                    selectedPetId = "zorra",
                                    totalStars = 0,
                                    totalCoins = 100,
                                    currentXp = 0,
                                    playerLevel = 1,
                                    soundEnabled = true,
                                    musicEnabled = true,
                                    ttsEnabled = true
                                )
                            )
                            // Seed initial badges
                            val defaultBadges = listOf(
                                BadgeEntity(
                                    id = "badge_first_step",
                                    title = "Langkah Pertama",
                                    description = "Selesaikan level pertamamu!",
                                    iconEmoji = "🌱",
                                    isUnlocked = false,
                                    requirementText = "Selesaikan 1 Level"
                                ),
                                BadgeEntity(
                                    id = "badge_star_collector",
                                    title = "Kolektor Bintang",
                                    description = "Kumpulkan 15 bintang di petualangan!",
                                    iconEmoji = "⭐",
                                    isUnlocked = false,
                                    requirementText = "Kumpulkan 15 Bintang"
                                ),
                                BadgeEntity(
                                    id = "badge_counting_expert",
                                    title = "Ahli Berhitung",
                                    description = "Selesaikan semua level di Pulau Angka!",
                                    iconEmoji = "🔢",
                                    isUnlocked = false,
                                    requirementText = "Tamatkan Pulau Angka"
                                ),
                                BadgeEntity(
                                    id = "badge_math_wizard",
                                    title = "Penyihir Tambah Kurang",
                                    description = "Kuasai Hutan Tambah & Kurang dengan 3 bintang!",
                                    iconEmoji = "🧙‍♂️",
                                    isUnlocked = false,
                                    requirementText = "Tamatkan Hutan Tambah Kurang"
                                ),
                                BadgeEntity(
                                    id = "badge_times_hero",
                                    title = "Pahlawan Perkalian",
                                    description = "Taklukkan monster Lembah Kali & Bagi!",
                                    iconEmoji = "⚔️",
                                    isUnlocked = false,
                                    requirementText = "Tamatkan Lembah Kali & Bagi"
                                ),
                                BadgeEntity(
                                    id = "badge_zorra_champion",
                                    title = "Juara Sejati ZorraMath",
                                    description = "Capai Kastil Master dan raih mahkota tertinggi!",
                                    iconEmoji = "👑",
                                    isUnlocked = false,
                                    requirementText = "Selesaikan Kastil Master"
                                )
                            )
                            dao.insertBadges(defaultBadges)
                        }
                    }
                })
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
