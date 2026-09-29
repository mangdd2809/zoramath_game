package com.example.model

enum class WorldId(
    val title: String,
    val subtitle: String,
    val gradeLevel: String,
    val colorHex: Long,
    val iconDescription: String
) {
    NUMBER_ISLAND(
        title = "Pulau Angka",
        subtitle = "Mengenal angka & berhitung ceria",
        gradeLevel = "PAUD / TK",
        colorHex = 0xFFFF6B6B,
        iconDescription = "Island with numbers"
    ),
    FOREST_ADD_SUB(
        title = "Hutan Tambah & Kurang",
        subtitle = "Petualangan penjumlahan & pengurangan",
        gradeLevel = "SD Kelas 1 - 2",
        colorHex = 0xFF4ECDC4,
        iconDescription = "Lush green math forest"
    ),
    VALLEY_MULT_DIV(
        title = "Lembah Kali & Bagi",
        subtitle = "Trik perkalian & pembagian seru",
        gradeLevel = "SD Kelas 3 - 4",
        colorHex = 0xFFFFBE0B,
        iconDescription = "Golden crystal valley"
    ),
    CASTLE_MATH_MASTER(
        title = "Kastil Master Matematika",
        subtitle = "Operasi campuran & teka-teki logika",
        gradeLevel = "SD Kelas 5 - 6",
        colorHex = 0xFF8338EC,
        iconDescription = "Magical star castle"
    )
}

enum class GameModeType {
    COUNTING,       // Touch items to count them (e.g. apples, stars, cupcakes)
    MULTIPLE_CHOICE,// Standard playful 4-choice math problem
    BALLOON_POP,    // Pop the balloon with the correct answer
    COMPARISON,     // Compare quantities (> , < , =)
    SEQUENCE        // Missing number sequence (e.g. 2, 4, [?], 8)
}

data class LevelInfo(
    val levelNumber: Int,
    val worldId: WorldId,
    val title: String,
    val description: String,
    val gameModeType: GameModeType,
    val totalQuestions: Int = 5,
    val xpReward: Int = 50,
    val coinReward: Int = 20
)

data class Question(
    val id: Int,
    val questionText: String,
    val speechPrompt: String,
    val type: GameModeType,
    val visualItemEmoji: String = "🍎",
    val visualItemCount: Int = 0,
    val visualItemCount2: Int = 0,
    val options: List<String>,
    val correctAnswer: String,
    val explanation: String
)

data class PetCompanion(
    val id: String,
    val name: String,
    val title: String,
    val emoji: String,
    val greeting: String,
    val unlockStars: Int
)

val AVAILABLE_PETS = listOf(
    PetCompanion(
        id = "zorra",
        name = "Zorra",
        title = "Rubah Petualang Jenius",
        emoji = "🦊",
        greeting = "Hai! Aku Zorra! Ayo kita taklukkan semua teka-teki matematika!",
        unlockStars = 0
    ),
    PetCompanion(
        id = "mimi",
        name = "Mimi",
        title = "Kelinci Pelompat Cepat",
        emoji = "🐰",
        greeting = "Hop! Hop! Berhitung itu gampang dan super asyik!",
        unlockStars = 5
    ),
    PetCompanion(
        id = "leo",
        name = "Leo",
        title = "Singa Kecil Pemberani",
        emoji = "🦁",
        greeting = "Aum! Bersamaku, kamu pasti jadi juara matematika!",
        unlockStars = 15
    ),
    PetCompanion(
        id = "piko",
        name = "Piko",
        title = "Penguin Cerdas Es",
        emoji = "🐧",
        greeting = "Kwek! Mari kumpulkan semua bintang kristal matematika!",
        unlockStars = 30
    )
)
