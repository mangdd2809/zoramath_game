package com.example.model

import kotlin.random.Random

object QuestionBank {

    fun getQuestionsForLevel(worldId: WorldId, levelNumber: Int): List<Question> {
        return when (worldId) {
            WorldId.NUMBER_ISLAND -> getNumberIslandQuestions(levelNumber)
            WorldId.FOREST_ADD_SUB -> getForestQuestions(levelNumber)
            WorldId.VALLEY_MULT_DIV -> getValleyQuestions(levelNumber)
            WorldId.CASTLE_MATH_MASTER -> getCastleQuestions(levelNumber)
        }
    }

    private fun getNumberIslandQuestions(level: Int): List<Question> {
        return when (level) {
            1 -> listOf(
                Question(
                    id = 101,
                    questionText = "Berapa banyak buah apel merah yang ada?",
                    speechPrompt = "Sentuh dan hitung berapa buah apel merah ini!",
                    type = GameModeType.COUNTING,
                    visualItemEmoji = "🍎",
                    visualItemCount = 3,
                    options = listOf("2", "3", "4", "5"),
                    correctAnswer = "3",
                    explanation = "Tepat sekali! Ada 3 buah apel merah segar!"
                ),
                Question(
                    id = 102,
                    questionText = "Hitung berapa banyak bintang berkilau!",
                    speechPrompt = "Hitung berapa bintang yang bersinar cerah!",
                    type = GameModeType.COUNTING,
                    visualItemEmoji = "⭐",
                    visualItemCount = 5,
                    options = listOf("4", "5", "6", "3"),
                    correctAnswer = "5",
                    explanation = "Luar biasa! Ada 5 bintang berkilau!"
                ),
                Question(
                    id = 103,
                    questionText = "Berapa buah pisang manis di pohon?",
                    speechPrompt = "Ayo hitung ada berapa pisang manis ini!",
                    type = GameModeType.COUNTING,
                    visualItemEmoji = "🍌",
                    visualItemCount = 4,
                    options = listOf("3", "4", "5", "2"),
                    correctAnswer = "4",
                    explanation = "Pintar! Ada 4 buah pisang lezat!"
                ),
                Question(
                    id = 104,
                    questionText = "Berapa kue stroberi lezat yang dibuat Zorra?",
                    speechPrompt = "Berapa kue stroberi yang ada?",
                    type = GameModeType.COUNTING,
                    visualItemEmoji = "🧁",
                    visualItemCount = 2,
                    options = listOf("1", "2", "3", "4"),
                    correctAnswer = "2",
                    explanation = "Hebat! Ada 2 kue stroberi!"
                ),
                Question(
                    id = 105,
                    questionText = "Berapa permen lolipop warna-warni ini?",
                    speechPrompt = "Hitung permen lolipopnya bersama Zorra!",
                    type = GameModeType.COUNTING,
                    visualItemEmoji = "🍭",
                    visualItemCount = 6,
                    options = listOf("5", "6", "7", "8"),
                    correctAnswer = "6",
                    explanation = "Bagus sekali! Ada 6 permen lolipop!"
                )
            )
            2 -> listOf(
                Question(
                    id = 106,
                    questionText = "Ayo hitung semangka segar ini!",
                    speechPrompt = "Berapa banyak buah semangka segar ini?",
                    type = GameModeType.COUNTING,
                    visualItemEmoji = "🍉",
                    visualItemCount = 7,
                    options = listOf("6", "7", "8", "9"),
                    correctAnswer = "7",
                    explanation = "Wah hebat! Totalnya ada 7 semangka!"
                ),
                Question(
                    id = 107,
                    questionText = "Hitung donat cokelat manis!",
                    speechPrompt = "Ada berapa donat manis di sini?",
                    type = GameModeType.COUNTING,
                    visualItemEmoji = "🍩",
                    visualItemCount = 8,
                    options = listOf("7", "8", "9", "10"),
                    correctAnswer = "8",
                    explanation = "Mantap! Ada 8 donat cokelat!"
                ),
                Question(
                    id = 108,
                    questionText = "Berapa buah jeruk oranye yang dipetik Zorra?",
                    speechPrompt = "Hitung buah jeruk yang dipetik Zorra!",
                    type = GameModeType.COUNTING,
                    visualItemEmoji = "🍊",
                    visualItemCount = 9,
                    options = listOf("8", "9", "10", "7"),
                    correctAnswer = "9",
                    explanation = "Benar sekali! Ada 9 buah jeruk!"
                ),
                Question(
                    id = 109,
                    questionText = "Berapa balon terbang di langit?",
                    speechPrompt = "Ayo hitung balon-balon indah di langit!",
                    type = GameModeType.COUNTING,
                    visualItemEmoji = "🎈",
                    visualItemCount = 10,
                    options = listOf("8", "9", "10", "11"),
                    correctAnswer = "10",
                    explanation = "Keren sekali! Ada 10 balon ceria!"
                ),
                Question(
                    id = 110,
                    questionText = "Hitung kelinci sahabat Zorra!",
                    speechPrompt = "Berapa kelinci yang sedang melompat?",
                    type = GameModeType.COUNTING,
                    visualItemEmoji = "🐰",
                    visualItemCount = 5,
                    options = listOf("4", "5", "6", "7"),
                    correctAnswer = "5",
                    explanation = "Luar biasa! Ada 5 kelinci lucu!"
                )
            )
            3 -> listOf(
                Question(
                    id = 111,
                    questionText = "Mana yang LEBIH BANYAK?",
                    speechPrompt = "Pilih kelompok yang jumlahnya lebih banyak!",
                    type = GameModeType.COMPARISON,
                    visualItemEmoji = "🍓",
                    visualItemCount = 6,
                    visualItemCount2 = 3,
                    options = listOf("Kiri (6)", "Kanan (3)", "Sama Banyak"),
                    correctAnswer = "Kiri (6)",
                    explanation = "Benar! 6 buah stroberi lebih banyak daripada 3 stroberi!"
                ),
                Question(
                    id = 112,
                    questionText = "Mana yang LEBIH SEDIKIT?",
                    speechPrompt = "Pilih kelompok yang jumlahnya lebih sedikit!",
                    type = GameModeType.COMPARISON,
                    visualItemEmoji = "🥕",
                    visualItemCount = 4,
                    visualItemCount2 = 8,
                    options = listOf("Kiri (4)", "Kanan (8)", "Sama Banyak"),
                    correctAnswer = "Kiri (4)",
                    explanation = "Pintar! 4 wortel lebih sedikit daripada 8 wortel!"
                ),
                Question(
                    id = 113,
                    questionText = "Bandingkan jumlah bintang ini:",
                    speechPrompt = "Apakah kedua kelompok bintang ini sama atau berbeda?",
                    type = GameModeType.COMPARISON,
                    visualItemEmoji = "⭐",
                    visualItemCount = 5,
                    visualItemCount2 = 5,
                    options = listOf("Kiri lebih banyak", "Kanan lebih banyak", "Sama Banyak (5 = 5)"),
                    correctAnswer = "Sama Banyak (5 = 5)",
                    explanation = "Tepat! Keduanya berjumlah 5, jadi sama banyak!"
                ),
                Question(
                    id = 114,
                    questionText = "Simbol mana yang tepat untuk 7 ... 4 ?",
                    speechPrompt = "Tujuh lebih besar atau lebih kecil dari empat?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf(">", "<", "="),
                    correctAnswer = ">",
                    explanation = "7 lebih besar dari 4, jadi tandanya '>'!"
                ),
                Question(
                    id = 115,
                    questionText = "Mana angka yang PALING BESAR?",
                    speechPrompt = "Pilih angka yang paling besar nilainya!",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("3", "9", "5", "7"),
                    correctAnswer = "9",
                    explanation = "Hebat! Angka 9 adalah yang paling besar!"
                )
            )
            4 -> listOf(
                Question(
                    id = 116,
                    questionText = "Lengkapi urutan: 1, 2, 3, [ ? ], 5",
                    speechPrompt = "Angka berapa yang hilang setelah angka 3?",
                    type = GameModeType.SEQUENCE,
                    options = listOf("4", "6", "2", "5"),
                    correctAnswer = "4",
                    explanation = "Bagus! Urutan yang benar adalah 1, 2, 3, 4, 5!"
                ),
                Question(
                    id = 117,
                    questionText = "Lengkapi urutan: 5, 6, [ ? ], 8, 9",
                    speechPrompt = "Angka berapa di antara 6 dan 8?",
                    type = GameModeType.SEQUENCE,
                    options = listOf("7", "6", "10", "4"),
                    correctAnswer = "7",
                    explanation = "Pintar! Angka di antara 6 dan 8 adalah 7!"
                ),
                Question(
                    id = 118,
                    questionText = "Lengkapi urutan mundur: 10, 9, 8, [ ? ], 6",
                    speechPrompt = "Ayo hitung mundur, angka apa setelah 8?",
                    type = GameModeType.SEQUENCE,
                    options = listOf("7", "5", "9", "6"),
                    correctAnswer = "7",
                    explanation = "Hebat! Hitung mundur: 10, 9, 8, 7, 6!"
                ),
                Question(
                    id = 119,
                    questionText = "Lengkapi urutan loncat 2: 2, 4, [ ? ], 8",
                    speechPrompt = "Hitung loncat dua: dua, empat, lalu berapa?",
                    type = GameModeType.SEQUENCE,
                    options = listOf("5", "6", "7", "9"),
                    correctAnswer = "6",
                    explanation = "Keren! 2, 4, 6, 8!"
                ),
                Question(
                    id = 120,
                    questionText = "Lengkapi urutan: 6, 7, 8, 9, [ ? ]",
                    speechPrompt = "Angka berapa setelah sembilan?",
                    type = GameModeType.SEQUENCE,
                    options = listOf("10", "11", "12", "8"),
                    correctAnswer = "10",
                    explanation = "Tepat sekali! Setelah 9 adalah 10!"
                )
            )
            else -> listOf(
                Question(
                    id = 121,
                    questionText = "Pecahkan balon dengan ANGKA 5!",
                    speechPrompt = "Sentuh dan pecahkan balon yang ada angka lima!",
                    type = GameModeType.BALLOON_POP,
                    options = listOf("3", "5", "8", "2"),
                    correctAnswer = "5",
                    explanation = "Dooor! Kamu berhasil memecahkan balon angka 5!"
                ),
                Question(
                    id = 122,
                    questionText = "Pecahkan balon dengan ANGKA 8!",
                    speechPrompt = "Pecahkan balon angka delapan!",
                    type = GameModeType.BALLOON_POP,
                    options = listOf("6", "8", "4", "9"),
                    correctAnswer = "8",
                    explanation = "Pop! Balon angka 8 pecah!"
                ),
                Question(
                    id = 123,
                    questionText = "Pecahkan balon dengan ANGKA 10!",
                    speechPrompt = "Cari dan pecahkan balon angka sepuluh!",
                    type = GameModeType.BALLOON_POP,
                    options = listOf("1", "7", "10", "5"),
                    correctAnswer = "10",
                    explanation = "Hebat! Balon angka 10 berhasil kamu pecahkan!"
                ),
                Question(
                    id = 124,
                    questionText = "Pecahkan balon dengan ANGKA 7!",
                    speechPrompt = "Pecahkan balon angka tujuh!",
                    type = GameModeType.BALLOON_POP,
                    options = listOf("7", "2", "6", "9"),
                    correctAnswer = "7",
                    explanation = "Sempurna! Balon angka 7 pecah berkilau!"
                ),
                Question(
                    id = 125,
                    questionText = "Pecahkan balon dengan ANGKA 4!",
                    speechPrompt = "Pecahkan balon angka empat!",
                    type = GameModeType.BALLOON_POP,
                    options = listOf("4", "3", "5", "1"),
                    correctAnswer = "4",
                    explanation = "Hore! Kamu memenangkan Pulau Angka!"
                )
            )
        }
    }

    private fun getForestQuestions(level: Int): List<Question> {
        return when (level) {
            1 -> listOf(
                Question(
                    id = 201,
                    questionText = "Zorra punya 3 apel 🍎 lalu memetik 2 apel lagi 🍎. Berapa semua apel Zorra?",
                    speechPrompt = "Tiga apel ditambah dua apel, sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    visualItemEmoji = "🍎",
                    visualItemCount = 3,
                    visualItemCount2 = 2,
                    options = listOf("4", "5", "6", "7"),
                    correctAnswer = "5",
                    explanation = "3 + 2 = 5 apel!"
                ),
                Question(
                    id = 202,
                    questionText = "Berapakah hasil dari 4 + 3 ?",
                    speechPrompt = "Empat ditambah tiga sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    visualItemEmoji = "⭐",
                    visualItemCount = 4,
                    visualItemCount2 = 3,
                    options = listOf("6", "7", "8", "9"),
                    correctAnswer = "7",
                    explanation = "4 + 3 = 7!"
                ),
                Question(
                    id = 203,
                    questionText = "Berapakah hasil dari 5 + 5 ?",
                    speechPrompt = "Lima ditambah lima sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("9", "10", "11", "12"),
                    correctAnswer = "10",
                    explanation = "5 + 5 = 10!"
                ),
                Question(
                    id = 204,
                    questionText = "6 wortel 🥕 + 3 wortel 🥕 = ... wortel?",
                    speechPrompt = "Enam wortel ditambah tiga wortel jadi berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    visualItemEmoji = "🥕",
                    visualItemCount = 6,
                    visualItemCount2 = 3,
                    options = listOf("8", "9", "10", "7"),
                    correctAnswer = "9",
                    explanation = "6 + 3 = 9 wortel!"
                ),
                Question(
                    id = 205,
                    questionText = "Berapakah hasil dari 7 + 4 ?",
                    speechPrompt = "Tujuh ditambah empat sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("10", "11", "12", "13"),
                    correctAnswer = "11",
                    explanation = "7 + 4 = 11!"
                )
            )
            2 -> listOf(
                Question(
                    id = 206,
                    questionText = "Ada 6 balon 🎈, 2 balon meletus. Berapa balon yang tersisa?",
                    speechPrompt = "Enam balon dikurang dua balon, tinggal berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    visualItemEmoji = "🎈",
                    visualItemCount = 6,
                    visualItemCount2 = 2,
                    options = listOf("3", "4", "5", "2"),
                    correctAnswer = "4",
                    explanation = "6 - 2 = 4 balon!"
                ),
                Question(
                    id = 207,
                    questionText = "Berapakah hasil dari 8 - 3 ?",
                    speechPrompt = "Delapan dikurang tiga sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("4", "5", "6", "7"),
                    correctAnswer = "5",
                    explanation = "8 - 3 = 5!"
                ),
                Question(
                    id = 208,
                    questionText = "Zorra punya 10 stroberi 🍓, dimakan 4 stroberi. Sisa berapa?",
                    speechPrompt = "Sepuluh stroberi dikurang empat stroberi, sisa berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    visualItemEmoji = "🍓",
                    visualItemCount = 10,
                    visualItemCount2 = 4,
                    options = listOf("5", "6", "7", "8"),
                    correctAnswer = "6",
                    explanation = "10 - 4 = 6 stroberi!"
                ),
                Question(
                    id = 209,
                    questionText = "Berapakah hasil dari 9 - 5 ?",
                    speechPrompt = "Sembilan dikurang lima sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("3", "4", "5", "6"),
                    correctAnswer = "4",
                    explanation = "9 - 5 = 4!"
                ),
                Question(
                    id = 210,
                    questionText = "Berapakah hasil dari 12 - 5 ?",
                    speechPrompt = "Dua belas dikurang lima sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("6", "7", "8", "9"),
                    correctAnswer = "7",
                    explanation = "12 - 5 = 7!"
                )
            )
            3 -> listOf(
                Question(
                    id = 211,
                    questionText = "Tebak angka rahasia: 4 + [ ? ] = 9",
                    speechPrompt = "Empat ditambah berapa supaya jadi sembilan?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("3", "4", "5", "6"),
                    correctAnswer = "5",
                    explanation = "4 + 5 = 9!"
                ),
                Question(
                    id = 212,
                    questionText = "Tebak angka rahasia: 10 - [ ? ] = 3",
                    speechPrompt = "Sepuluh dikurang berapa supaya jadi tiga?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("6", "7", "8", "5"),
                    correctAnswer = "7",
                    explanation = "10 - 7 = 3!"
                ),
                Question(
                    id = 213,
                    questionText = "Tebak angka rahasia: [ ? ] + 6 = 11",
                    speechPrompt = "Berapa ditambah enam yang hasilnya sebelas?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("4", "5", "6", "7"),
                    correctAnswer = "5",
                    explanation = "5 + 6 = 11!"
                ),
                Question(
                    id = 214,
                    questionText = "Tebak angka rahasia: [ ? ] - 4 = 8",
                    speechPrompt = "Berapa dikurang empat yang sisanya delapan?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("10", "11", "12", "14"),
                    correctAnswer = "12",
                    explanation = "12 - 4 = 8!"
                ),
                Question(
                    id = 215,
                    questionText = "Tebak angka rahasia: 8 + [ ? ] = 15",
                    speechPrompt = "Delapan ditambah berapa supaya hasilnya lima belas?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("6", "7", "8", "9"),
                    correctAnswer = "7",
                    explanation = "8 + 7 = 15!"
                )
            )
            4 -> listOf(
                Question(
                    id = 216,
                    questionText = "Hitung cepat: 10 + 20 = ...",
                    speechPrompt = "Sepuluh ditambah dua puluh sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("25", "30", "35", "40"),
                    correctAnswer = "30",
                    explanation = "10 + 20 = 30!"
                ),
                Question(
                    id = 217,
                    questionText = "Hitung cepat: 25 + 5 = ...",
                    speechPrompt = "Dua puluh lima ditambah lima sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("28", "29", "30", "35"),
                    correctAnswer = "30",
                    explanation = "25 + 5 = 30!"
                ),
                Question(
                    id = 218,
                    questionText = "Hitung cepat: 50 - 20 = ...",
                    speechPrompt = "Lima puluh dikurang dua puluh sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("20", "30", "40", "25"),
                    correctAnswer = "30",
                    explanation = "50 - 20 = 30!"
                ),
                Question(
                    id = 219,
                    questionText = "Hitung cepat: 14 + 13 = ...",
                    speechPrompt = "Empat belas ditambah tiga belas sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("26", "27", "28", "29"),
                    correctAnswer = "27",
                    explanation = "14 + 13 = 27!"
                ),
                Question(
                    id = 220,
                    questionText = "Hitung cepat: 35 - 15 = ...",
                    speechPrompt = "Tiga puluh lima dikurang lima belas sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("15", "20", "25", "30"),
                    correctAnswer = "20",
                    explanation = "35 - 15 = 20!"
                )
            )
            else -> listOf(
                Question(
                    id = 221,
                    questionText = "🍄 Bos Jamur: Berapakah 8 + 8 ?",
                    speechPrompt = "Pertarungan Bos Jamur! Delapan ditambah delapan sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("14", "15", "16", "17"),
                    correctAnswer = "16",
                    explanation = "Serangan tepat! 8 + 8 = 16!"
                ),
                Question(
                    id = 222,
                    questionText = "🍄 Bos Jamur: Berapakah 20 - 7 ?",
                    speechPrompt = "Dua puluh dikurang tujuh sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("12", "13", "14", "15"),
                    correctAnswer = "13",
                    explanation = "Kena! 20 - 7 = 13!"
                ),
                Question(
                    id = 223,
                    questionText = "🍄 Bos Jamur: Berapakah 15 + 15 ?",
                    speechPrompt = "Lima belas ditambah lima belas sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("25", "28", "30", "35"),
                    correctAnswer = "30",
                    explanation = "Hebat! 15 + 15 = 30!"
                ),
                Question(
                    id = 224,
                    questionText = "🍄 Bos Jamur: Berapakah 24 - 9 ?",
                    speechPrompt = "Dua puluh empat dikurang sembilan sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("13", "14", "15", "16"),
                    correctAnswer = "15",
                    explanation = "Bagus sekali! 24 - 9 = 15!"
                ),
                Question(
                    id = 225,
                    questionText = "🍄 Bos Jamur: Berapakah 12 + 8 - 5 ?",
                    speechPrompt = "Dua belas ditambah delapan dikurang lima sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("14", "15", "16", "17"),
                    correctAnswer = "15",
                    explanation = "Hore! Bos Jamur berhasil dikalahkan! Hutan Tambah Kurang aman!"
                )
            )
        }
    }

    private fun getValleyQuestions(level: Int): List<Question> {
        return when (level) {
            1 -> listOf(
                Question(
                    id = 301,
                    questionText = "Ada 3 kotak, tiap kotak ada 4 ceri 🍒. Berapa ceri semuanya? (3 × 4)",
                    speechPrompt = "Tiga dikali empat sama dengan berapa ceri?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("10", "12", "14", "15"),
                    correctAnswer = "12",
                    explanation = "3 × 4 = 12 ceri!"
                ),
                Question(
                    id = 302,
                    questionText = "Berapakah hasil dari 2 × 5 ?",
                    speechPrompt = "Dua dikali lima sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("7", "10", "12", "15"),
                    correctAnswer = "10",
                    explanation = "2 × 5 = 10!"
                ),
                Question(
                    id = 303,
                    questionText = "Berapakah hasil dari 4 × 4 ?",
                    speechPrompt = "Empat dikali empat sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("14", "15", "16", "18"),
                    correctAnswer = "16",
                    explanation = "4 × 4 = 16!"
                ),
                Question(
                    id = 304,
                    questionText = "Ada 5 piring, tiap piring ada 3 donat 🍩. Berapa total donat?",
                    speechPrompt = "Lima dikali tiga sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("12", "15", "18", "20"),
                    correctAnswer = "15",
                    explanation = "5 × 3 = 15 donat!"
                ),
                Question(
                    id = 305,
                    questionText = "Berapakah hasil dari 3 × 6 ?",
                    speechPrompt = "Tiga dikali enam sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("16", "18", "21", "24"),
                    correctAnswer = "18",
                    explanation = "3 × 6 = 18!"
                )
            )
            2 -> listOf(
                Question(
                    id = 306,
                    questionText = "Berapakah hasil dari 6 × 6 ?",
                    speechPrompt = "Enam dikali enam sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("32", "36", "40", "42"),
                    correctAnswer = "36",
                    explanation = "6 × 6 = 36!"
                ),
                Question(
                    id = 307,
                    questionText = "Berapakah hasil dari 7 × 5 ?",
                    speechPrompt = "Tujuh dikali lima sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("30", "35", "40", "45"),
                    correctAnswer = "35",
                    explanation = "7 × 5 = 35!"
                ),
                Question(
                    id = 308,
                    questionText = "Berapakah hasil dari 8 × 4 ?",
                    speechPrompt = "Delapan dikali empat sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("28", "30", "32", "36"),
                    correctAnswer = "32",
                    explanation = "8 × 4 = 32!"
                ),
                Question(
                    id = 309,
                    questionText = "Berapakah hasil dari 9 × 3 ?",
                    speechPrompt = "Sembilan dikali tiga sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("24", "27", "30", "33"),
                    correctAnswer = "27",
                    explanation = "9 × 3 = 27!"
                ),
                Question(
                    id = 310,
                    questionText = "Berapakah hasil dari 8 × 7 ?",
                    speechPrompt = "Delapan dikali tujuh sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("54", "56", "58", "64"),
                    correctAnswer = "56",
                    explanation = "8 × 7 = 56!"
                )
            )
            3 -> listOf(
                Question(
                    id = 311,
                    questionText = "12 permen 🍬 dibagi rata untuk 3 anak. Masing-masing dapat berapa?",
                    speechPrompt = "Dua belas dibagi tiga sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("3", "4", "5", "6"),
                    correctAnswer = "4",
                    explanation = "12 ÷ 3 = 4 permen tiap anak!"
                ),
                Question(
                    id = 312,
                    questionText = "Berapakah hasil dari 15 ÷ 5 ?",
                    speechPrompt = "Lima belas dibagi lima sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("2", "3", "4", "5"),
                    correctAnswer = "3",
                    explanation = "15 ÷ 5 = 3!"
                ),
                Question(
                    id = 313,
                    questionText = "Berapakah hasil dari 20 ÷ 4 ?",
                    speechPrompt = "Dua puluh dibagi empat sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("4", "5", "6", "8"),
                    correctAnswer = "5",
                    explanation = "20 ÷ 4 = 5!"
                ),
                Question(
                    id = 314,
                    questionText = "18 kue 🧁 dibagikan ke 6 piring sama banyak. Berapa kue per piring?",
                    speechPrompt = "Delapan belas dibagi enam sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("2", "3", "4", "5"),
                    correctAnswer = "3",
                    explanation = "18 ÷ 6 = 3 kue per piring!"
                ),
                Question(
                    id = 315,
                    questionText = "Berapakah hasil dari 28 ÷ 7 ?",
                    speechPrompt = "Dua puluh delapan dibagi tujuh sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("3", "4", "5", "6"),
                    correctAnswer = "4",
                    explanation = "28 ÷ 7 = 4!"
                )
            )
            4 -> listOf(
                Question(
                    id = 316,
                    questionText = "Berapakah hasil dari 36 ÷ 6 ?",
                    speechPrompt = "Tiga puluh enam dibagi enam sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("5", "6", "7", "8"),
                    correctAnswer = "6",
                    explanation = "36 ÷ 6 = 6!"
                ),
                Question(
                    id = 317,
                    questionText = "Berapakah hasil dari 45 ÷ 9 ?",
                    speechPrompt = "Empat puluh lima dibagi sembilan sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("4", "5", "6", "7"),
                    correctAnswer = "5",
                    explanation = "45 ÷ 9 = 5!"
                ),
                Question(
                    id = 318,
                    questionText = "Berapakah hasil dari 48 ÷ 8 ?",
                    speechPrompt = "Empat puluh delapan dibagi delapan sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("5", "6", "7", "8"),
                    correctAnswer = "6",
                    explanation = "48 ÷ 8 = 6!"
                ),
                Question(
                    id = 319,
                    questionText = "Jika [ ? ] × 6 = 42, berapakah angka tersebut?",
                    speechPrompt = "Berapa dikali enam yang hasilnya empat puluh dua?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("6", "7", "8", "9"),
                    correctAnswer = "7",
                    explanation = "7 × 6 = 42!"
                ),
                Question(
                    id = 320,
                    questionText = "Berapakah hasil dari 63 ÷ 7 ?",
                    speechPrompt = "Enam puluh tiga dibagi tujuh sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("8", "9", "10", "7"),
                    correctAnswer = "9",
                    explanation = "63 ÷ 7 = 9!"
                )
            )
            else -> listOf(
                Question(
                    id = 321,
                    questionText = "💎 Bos Kristal: Berapakah 7 × 7 ?",
                    speechPrompt = "Tujuh dikali tujuh sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("42", "47", "49", "54"),
                    correctAnswer = "49",
                    explanation = "7 × 7 = 49!"
                ),
                Question(
                    id = 322,
                    questionText = "💎 Bos Kristal: Berapakah 56 ÷ 8 ?",
                    speechPrompt = "Lima puluh enam dibagi delapan sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("6", "7", "8", "9"),
                    correctAnswer = "7",
                    explanation = "56 ÷ 8 = 7!"
                ),
                Question(
                    id = 323,
                    questionText = "💎 Bos Kristal: Berapakah 9 × 8 ?",
                    speechPrompt = "Sembilan dikali delapan sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("64", "70", "72", "76"),
                    correctAnswer = "72",
                    explanation = "9 × 8 = 72!"
                ),
                Question(
                    id = 324,
                    questionText = "💎 Bos Kristal: Berapakah (4 × 5) + 12 ?",
                    speechPrompt = "Empat dikali lima ditambah dua belas sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("30", "32", "34", "36"),
                    correctAnswer = "32",
                    explanation = "(4 × 5) + 12 = 20 + 12 = 32!"
                ),
                Question(
                    id = 325,
                    questionText = "💎 Bos Kristal: Berapakah (50 ÷ 5) × 3 ?",
                    speechPrompt = "Lima puluh dibagi lima dikali tiga sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("25", "30", "35", "40"),
                    correctAnswer = "30",
                    explanation = "(50 ÷ 5) × 3 = 10 × 3 = 30! Kristal Emas bersinar!"
                )
            )
        }
    }

    private fun getCastleQuestions(level: Int): List<Question> {
        return when (level) {
            1 -> listOf(
                Question(
                    id = 401,
                    questionText = "Ingat urutan operasi (KABATAKU): 5 + 3 × 4 = ...",
                    speechPrompt = "Hitung operasi campuran: lima ditambah tiga dikali empat!",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("17", "32", "20", "19"),
                    correctAnswer = "17",
                    explanation = "Kerjakan perkalian dulu: 3 × 4 = 12, lalu 5 + 12 = 17!"
                ),
                Question(
                    id = 402,
                    questionText = "Berapakah hasil dari (15 - 5) × 4 ?",
                    speechPrompt = "Berapakah kurung lima belas kurang lima, lalu dikali empat?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("35", "40", "45", "50"),
                    correctAnswer = "40",
                    explanation = "Dalam kurung dulu: 15 - 5 = 10. Lalu 10 × 4 = 40!"
                ),
                Question(
                    id = 403,
                    questionText = "Berapakah hasil dari 24 - 8 ÷ 2 ?",
                    speechPrompt = "Dua puluh empat dikurang delapan dibagi dua sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("8", "16", "20", "22"),
                    correctAnswer = "20",
                    explanation = "Bagi dulu: 8 ÷ 2 = 4. Lalu 24 - 4 = 20!"
                ),
                Question(
                    id = 404,
                    questionText = "Berapakah hasil dari 6 × 3 + 18 ÷ 2 ?",
                    speechPrompt = "Enam kali tiga ditambah delapan belas bagi dua sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("25", "27", "30", "32"),
                    correctAnswer = "27",
                    explanation = "6 × 3 = 18, dan 18 ÷ 2 = 9. Jadi 18 + 9 = 27!"
                ),
                Question(
                    id = 405,
                    questionText = "Berapakah hasil dari 100 - (25 × 3) ?",
                    speechPrompt = "Seratus dikurang dua puluh lima kali tiga sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("20", "25", "30", "35"),
                    correctAnswer = "25",
                    explanation = "25 × 3 = 75. 100 - 75 = 25!"
                )
            )
            2 -> listOf(
                Question(
                    id = 406,
                    questionText = "Berapakah nilai dari 1/2 dari 24 buah apel 🍎?",
                    speechPrompt = "Satu per dua dari dua puluh empat apel adalah berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("10", "12", "14", "16"),
                    correctAnswer = "12",
                    explanation = "1/2 × 24 = 24 ÷ 2 = 12 apel!"
                ),
                Question(
                    id = 407,
                    questionText = "Berapakah hasil penjumlahan pecahan: 1/4 + 2/4 ?",
                    speechPrompt = "Satu per empat ditambah dua per empat sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("2/4", "3/4", "3/8", "1"),
                    correctAnswer = "3/4",
                    explanation = "Penyebutnya sama: (1 + 2) / 4 = 3/4!"
                ),
                Question(
                    id = 408,
                    questionText = "Bentuk paling sederhana dari 4/8 adalah...",
                    speechPrompt = "Pecahan empat per delapan jika disederhanakan menjadi berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("1/4", "1/2", "2/3", "3/4"),
                    correctAnswer = "1/2",
                    explanation = "Bagi pembilang dan penyebut dengan 4: 4/8 = 1/2!"
                ),
                Question(
                    id = 409,
                    questionText = "Berapakah 25% dari angka 80?",
                    speechPrompt = "Dua puluh lima persen dari delapan puluh adalah berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("15", "20", "25", "30"),
                    correctAnswer = "20",
                    explanation = "25% = 1/4. Maka 1/4 × 80 = 20!"
                ),
                Question(
                    id = 410,
                    questionText = "Jika pizza dibagi 8 potong, Zorra makan 3 potong. Sisa berapa pecahan pizza?",
                    speechPrompt = "Delapan potong dimakan tiga potong, tersisa berapa bagian?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("3/8", "4/8", "5/8", "6/8"),
                    correctAnswer = "5/8",
                    explanation = "8/8 - 3/8 = 5/8 pizza!"
                )
            )
            3 -> listOf(
                Question(
                    id = 411,
                    questionText = "Lengkapi deret angka: 2, 4, 8, 16, [ ? ]",
                    speechPrompt = "Dua, empat, delapan, enam belas, selanjutnya berapa?",
                    type = GameModeType.SEQUENCE,
                    options = listOf("24", "30", "32", "36"),
                    correctAnswer = "32",
                    explanation = "Polanya dikali 2 setiap langkah: 16 × 2 = 32!"
                ),
                Question(
                    id = 412,
                    questionText = "Lengkapi deret kuadrat: 1, 4, 9, 16, [ ? ]",
                    speechPrompt = "Satu, empat, sembilan, enam belas, angka berikutnya berapa?",
                    type = GameModeType.SEQUENCE,
                    options = listOf("20", "25", "30", "36"),
                    correctAnswer = "25",
                    explanation = "Pola bilangan kuadrat: 1², 2², 3², 4², 5² = 25!"
                ),
                Question(
                    id = 413,
                    questionText = "KPK (Kelipatan Persekutuan Terkecil) dari 4 dan 6 adalah...",
                    speechPrompt = "Berapa kelipatan persekutuan terkecil dari empat dan enam?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("12", "18", "24", "10"),
                    correctAnswer = "12",
                    explanation = "Kelipatan 4: 4, 8, 12... Kelipatan 6: 6, 12... Jadi KPK = 12!"
                ),
                Question(
                    id = 414,
                    questionText = "FPB (Faktor Persekutuan Terbesar) dari 18 dan 24 adalah...",
                    speechPrompt = "Berapa faktor persekutuan terbesar dari delapan belas dan dua puluh empat?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("3", "4", "6", "8"),
                    correctAnswer = "6",
                    explanation = "Faktor persekutuan yang paling besar dari 18 dan 24 adalah 6!"
                ),
                Question(
                    id = 415,
                    questionText = "Lengkapi deret bilangan prima: 2, 3, 5, 7, [ ? ]",
                    speechPrompt = "Dua, tiga, lima, tujuh, bilangan prima berikutnya berapa?",
                    type = GameModeType.SEQUENCE,
                    options = listOf("9", "10", "11", "13"),
                    correctAnswer = "11",
                    explanation = "Bilangan prima berikutnya adalah 11!"
                )
            )
            4 -> listOf(
                Question(
                    id = 416,
                    questionText = "Tantangan Cepat: Berapakah 15 × 12 ?",
                    speechPrompt = "Lima belas dikali dua belas sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("160", "170", "180", "190"),
                    correctAnswer = "180",
                    explanation = "15 × 10 = 150, ditambah 15 × 2 = 30. Total = 180!"
                ),
                Question(
                    id = 417,
                    questionText = "Berapakah hasil dari √64 + √36 ?",
                    speechPrompt = "Akar enam puluh empat ditambah akar tiga puluh enam adalah...",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("12", "14", "16", "18"),
                    correctAnswer = "14",
                    explanation = "√64 = 8 dan √36 = 6. Maka 8 + 6 = 14!"
                ),
                Question(
                    id = 418,
                    questionText = "Sebuah persegi memiliki sisi 8 cm. Berapakah luasnya?",
                    speechPrompt = "Persegi bersisi delapan sentimeter, berapa luasnya?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("32 cm²", "64 cm²", "48 cm²", "56 cm²"),
                    correctAnswer = "64 cm²",
                    explanation = "Luas persegi = sisi × sisi = 8 × 8 = 64 cm²!"
                ),
                Question(
                    id = 419,
                    questionText = "Berapakah keliling segitiga sama sisi dengan panjang sisi 9 cm?",
                    speechPrompt = "Keliling segitiga sama sisi dengan sisi sembilan sentimeter adalah...",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("18 cm", "27 cm", "36 cm", "81 cm"),
                    correctAnswer = "27 cm",
                    explanation = "Keliling = 9 + 9 + 9 = 27 cm!"
                ),
                Question(
                    id = 420,
                    questionText = "Berapakah hasil dari 2³ + 3² ?",
                    speechPrompt = "Dua pangkat tiga ditambah tiga pangkat dua sama dengan berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("15", "16", "17", "18"),
                    correctAnswer = "17",
                    explanation = "2³ = 8 dan 3² = 9. Maka 8 + 9 = 17!"
                )
            )
            else -> listOf(
                Question(
                    id = 421,
                    questionText = "👑 RAJA ANGKA: Hitung (18 + 12) ÷ 5 × 4 !",
                    speechPrompt = "Pertarungan Raja Angka! Delapan belas tambah dua belas dibagi lima kali empat!",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("20", "24", "28", "32"),
                    correctAnswer = "24",
                    explanation = "(30) ÷ 5 = 6. Lalu 6 × 4 = 24!"
                ),
                Question(
                    id = 422,
                    questionText = "👑 RAJA ANGKA: Pecahan desimal dari 3/4 adalah...",
                    speechPrompt = "Bentuk desimal dari tiga per empat adalah berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("0,25", "0,50", "0,75", "0,80"),
                    correctAnswer = "0,75",
                    explanation = "3 ÷ 4 = 0,75!"
                ),
                Question(
                    id = 423,
                    questionText = "👑 RAJA ANGKA: Jika sudut segitiga adalah 60° dan 70°, sudut ketiganya adalah...",
                    speechPrompt = "Total sudut segitiga seratus delapan puluh derajat. Sudut ketiga berapa?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("40°", "50°", "60°", "70°"),
                    correctAnswer = "50°",
                    explanation = "180° - (60° + 70°) = 180° - 130° = 50°!"
                ),
                Question(
                    id = 424,
                    questionText = "👑 RAJA ANGKA: Rata-rata dari nilai 8, 9, 10 adalah...",
                    speechPrompt = "Berapa nilai rata-rata dari delapan, sembilan, dan sepuluh?",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("8,5", "9", "9,5", "10"),
                    correctAnswer = "9",
                    explanation = "(8 + 9 + 10) ÷ 3 = 27 ÷ 3 = 9!"
                ),
                Question(
                    id = 425,
                    questionText = "👑 RAJA ANGKA: Berapakah hasil dari (15 × 4) - (36 ÷ 6) + 10 ?",
                    speechPrompt = "Tantangan pamungkas Raja Angka! Hitung dengan teliti!",
                    type = GameModeType.MULTIPLE_CHOICE,
                    options = listOf("60", "64", "68", "72"),
                    correctAnswer = "64",
                    explanation = "60 - 6 + 10 = 64! Kamu berhasil menjadi Juara Agung ZorraMath!"
                )
            )
        }
    }

    fun generatePracticeQuestions(
        operation: String, // "+", "-", "×", "÷", "COUNT"
        difficulty: String, // "Mudah", "Sedang", "Sulit"
        count: Int = 5
    ): List<Question> {
        val list = mutableListOf<Question>()
        for (i in 1..count) {
            when (operation) {
                "COUNT" -> {
                    val target = when (difficulty) {
                        "Mudah" -> Random.nextInt(2, 6)
                        "Sedang" -> Random.nextInt(5, 11)
                        else -> Random.nextInt(10, 16)
                    }
                    val emojis = listOf("🍎", "⭐", "🍓", "🎈", "🍌", "🥕", "🧁", "🍩")
                    val emoji = emojis.random()
                    val options = mutableSetOf(target.toString())
                    while (options.size < 4) {
                        val fake = (target + Random.nextInt(-3, 4)).coerceAtLeast(1)
                        options.add(fake.toString())
                    }
                    list.add(
                        Question(
                            id = 5000 + i,
                            questionText = "Hitung ada berapa banyak $emoji ini!",
                            speechPrompt = "Ayo hitung ada berapa $emoji di layar!",
                            type = GameModeType.COUNTING,
                            visualItemEmoji = emoji,
                            visualItemCount = target,
                            options = options.shuffled(),
                            correctAnswer = target.toString(),
                            explanation = "Hebat! Totalnya ada $target $emoji!"
                        )
                    )
                }
                "+" -> {
                    val (a, b) = when (difficulty) {
                        "Mudah" -> Random.nextInt(1, 10) to Random.nextInt(1, 10)
                        "Sedang" -> Random.nextInt(10, 50) to Random.nextInt(5, 30)
                        else -> Random.nextInt(50, 200) to Random.nextInt(20, 150)
                    }
                    val ans = a + b
                    val options = mutableSetOf(ans.toString())
                    while (options.size < 4) {
                        val fake = ans + listOf(-10, -2, -1, 1, 2, 10).random()
                        options.add(fake.toString())
                    }
                    list.add(
                        Question(
                            id = 6000 + i,
                            questionText = "Berapakah $a + $b ?",
                            speechPrompt = "$a ditambah $b sama dengan berapa?",
                            type = GameModeType.MULTIPLE_CHOICE,
                            options = options.shuffled(),
                            correctAnswer = ans.toString(),
                            explanation = "$a + $b = $ans!"
                        )
                    )
                }
                "-" -> {
                    val (a, b) = when (difficulty) {
                        "Mudah" -> {
                            val x = Random.nextInt(5, 15)
                            val y = Random.nextInt(1, x)
                            x to y
                        }
                        "Sedang" -> {
                            val x = Random.nextInt(20, 60)
                            val y = Random.nextInt(5, x)
                            x to y
                        }
                        else -> {
                            val x = Random.nextInt(100, 300)
                            val y = Random.nextInt(20, x)
                            x to y
                        }
                    }
                    val ans = a - b
                    val options = mutableSetOf(ans.toString())
                    while (options.size < 4) {
                        val fake = (ans + listOf(-5, -2, -1, 1, 2, 5).random()).coerceAtLeast(0)
                        options.add(fake.toString())
                    }
                    list.add(
                        Question(
                            id = 7000 + i,
                            questionText = "Berapakah $a - $b ?",
                            speechPrompt = "$a dikurang $b sama dengan berapa?",
                            type = GameModeType.MULTIPLE_CHOICE,
                            options = options.shuffled(),
                            correctAnswer = ans.toString(),
                            explanation = "$a - $b = $ans!"
                        )
                    )
                }
                "×" -> {
                    val (a, b) = when (difficulty) {
                        "Mudah" -> Random.nextInt(1, 6) to Random.nextInt(1, 6)
                        "Sedang" -> Random.nextInt(3, 10) to Random.nextInt(3, 10)
                        else -> Random.nextInt(8, 15) to Random.nextInt(6, 12)
                    }
                    val ans = a * b
                    val options = mutableSetOf(ans.toString())
                    while (options.size < 4) {
                        val fake = ans + listOf(-4, -2, -1, 1, 2, 4, 10).random()
                        options.add(fake.toString())
                    }
                    list.add(
                        Question(
                            id = 8000 + i,
                            questionText = "Berapakah $a × $b ?",
                            speechPrompt = "$a dikali $b sama dengan berapa?",
                            type = GameModeType.MULTIPLE_CHOICE,
                            options = options.shuffled(),
                            correctAnswer = ans.toString(),
                            explanation = "$a × $b = $ans!"
                        )
                    )
                }
                "÷" -> {
                    val (ans, b) = when (difficulty) {
                        "Mudah" -> Random.nextInt(1, 6) to Random.nextInt(2, 6)
                        "Sedang" -> Random.nextInt(3, 10) to Random.nextInt(3, 9)
                        else -> Random.nextInt(6, 15) to Random.nextInt(5, 12)
                    }
                    val a = ans * b
                    val options = mutableSetOf(ans.toString())
                    while (options.size < 4) {
                        val fake = (ans + listOf(-3, -2, -1, 1, 2, 3).random()).coerceAtLeast(1)
                        options.add(fake.toString())
                    }
                    list.add(
                        Question(
                            id = 9000 + i,
                            questionText = "Berapakah $a ÷ $b ?",
                            speechPrompt = "$a dibagi $b sama dengan berapa?",
                            type = GameModeType.MULTIPLE_CHOICE,
                            options = options.shuffled(),
                            correctAnswer = ans.toString(),
                            explanation = "$a ÷ $b = $ans!"
                        )
                    )
                }
            }
        }
        return list
    }
}
