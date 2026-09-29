package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.BadgeEntity
import com.example.data.LevelProgressEntity
import com.example.data.PlayerProfileEntity
import com.example.data.PlayerRepository
import com.example.model.GameModeType
import com.example.model.Question
import com.example.model.QuestionBank
import com.example.model.WorldId
import com.example.sound.SoundEffect
import com.example.sound.SoundManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class Screen {
    HOME,
    ADVENTURE_MAP,
    GAME_PLAY,
    PRACTICE,
    TROPHIES,
    SETTINGS_ABOUT
}

data class ActiveGameState(
    val worldId: WorldId? = null,
    val levelNumber: Int = 1,
    val isPractice: Boolean = false,
    val questions: List<Question> = emptyList(),
    val currentIndex: Int = 0,
    val currentScore: Int = 0,
    val correctCount: Int = 0,
    val selectedOption: String? = null,
    val isAnswerChecked: Boolean = false,
    val isCorrect: Boolean = false,
    val isLevelCompleted: Boolean = false,
    val starsEarned: Int = 0,
    val coinsEarned: Int = 0,
    val countedItems: Set<Int> = emptySet(),
    val poppedBalloons: Set<String> = emptySet()
) {
    val currentQuestion: Question?
        get() = questions.getOrNull(currentIndex)
}

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = PlayerRepository(database.playerDao())
    val soundManager = SoundManager(application)

    val playerProfile: StateFlow<PlayerProfileEntity> = repository.playerProfile
        .map { it ?: PlayerProfileEntity() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PlayerProfileEntity()
        )

    val allProgress: StateFlow<List<LevelProgressEntity>> = repository.allProgress
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allBadges: StateFlow<List<BadgeEntity>> = repository.allBadges
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _currentScreen = MutableStateFlow(Screen.HOME)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _gameState = MutableStateFlow(ActiveGameState())
    val gameState: StateFlow<ActiveGameState> = _gameState.asStateFlow()

    private val _selectedWorldForMap = MutableStateFlow(WorldId.NUMBER_ISLAND)
    val selectedWorldForMap: StateFlow<WorldId> = _selectedWorldForMap.asStateFlow()

    init {
        // Sync sound preferences from saved profile when loaded
        viewModelScope.launch {
            playerProfile.collect { profile ->
                soundManager.isSoundEnabled = profile.soundEnabled
                soundManager.isMusicEnabled = profile.musicEnabled
                soundManager.isTtsEnabled = profile.ttsEnabled
            }
        }
    }

    fun navigateTo(screen: Screen) {
        soundManager.playSound(SoundEffect.CLICK)
        _currentScreen.value = screen
    }

    fun selectWorldForMap(worldId: WorldId) {
        soundManager.playSound(SoundEffect.CLICK)
        _selectedWorldForMap.value = worldId
    }

    fun startLevel(worldId: WorldId, levelNumber: Int) {
        soundManager.playSound(SoundEffect.CLICK)
        val questions = QuestionBank.getQuestionsForLevel(worldId, levelNumber)
        _gameState.value = ActiveGameState(
            worldId = worldId,
            levelNumber = levelNumber,
            isPractice = false,
            questions = questions,
            currentIndex = 0,
            currentScore = 0,
            correctCount = 0
        )
        _currentScreen.value = Screen.GAME_PLAY
        speakPromptForCurrentQuestion()
    }

    fun startPractice(operation: String, difficulty: String) {
        soundManager.playSound(SoundEffect.CLICK)
        val questions = QuestionBank.generatePracticeQuestions(operation, difficulty, count = 5)
        _gameState.value = ActiveGameState(
            worldId = null,
            levelNumber = 1,
            isPractice = true,
            questions = questions,
            currentIndex = 0,
            currentScore = 0,
            correctCount = 0
        )
        _currentScreen.value = Screen.GAME_PLAY
        speakPromptForCurrentQuestion()
    }

    fun tapCountItem(index: Int) {
        val current = _gameState.value
        val items = current.countedItems.toMutableSet()
        if (items.contains(index)) {
            items.remove(index)
            soundManager.playSound(SoundEffect.POP)
        } else {
            items.add(index)
            soundManager.playSound(SoundEffect.COUNT, countStep = items.size)
            // Voice count step
            if (items.size <= 10) {
                val indonesianNumbers = listOf("Satu", "Dua", "Tiga", "Empat", "Lima", "Enam", "Tujuh", "Delapan", "Sembilan", "Sepuluh")
                soundManager.speak(indonesianNumbers[items.size - 1])
            }
        }
        _gameState.value = current.copy(countedItems = items)
    }

    fun submitAnswer(answer: String) {
        val current = _gameState.value
        if (current.isAnswerChecked) return

        val question = current.currentQuestion ?: return
        val isCorrect = answer.trim() == question.correctAnswer.trim()
        val scoreBonus = if (isCorrect) 20 else 0
        val newCorrectCount = if (isCorrect) current.correctCount + 1 else current.correctCount

        if (isCorrect) {
            soundManager.playSound(SoundEffect.CORRECT)
            val praises = listOf("Bagus sekali!", "Hebat!", "Jawabanmu benar!", "Luar biasa, pintar!")
            soundManager.speak(praises.random())
        } else {
            soundManager.playSound(SoundEffect.WRONG)
            val encouraging = listOf("Hampir benar, yuk coba lagi!", "Jangan menyerah, kamu pasti bisa!")
            soundManager.speak(encouraging.random())
        }

        _gameState.value = current.copy(
            selectedOption = answer,
            isAnswerChecked = true,
            isCorrect = isCorrect,
            currentScore = current.currentScore + scoreBonus,
            correctCount = newCorrectCount
        )
    }

    fun popBalloon(balloonValue: String) {
        val current = _gameState.value
        if (current.isAnswerChecked) return
        val currentPopped = current.poppedBalloons.toMutableSet()
        currentPopped.add(balloonValue)
        soundManager.playSound(SoundEffect.POP)

        _gameState.value = current.copy(poppedBalloons = currentPopped)
        submitAnswer(balloonValue)
    }

    fun nextQuestion() {
        val current = _gameState.value
        soundManager.playSound(SoundEffect.CLICK)

        val nextIndex = current.currentIndex + 1
        if (nextIndex < current.questions.size) {
            _gameState.value = current.copy(
                currentIndex = nextIndex,
                selectedOption = null,
                isAnswerChecked = false,
                isCorrect = false,
                countedItems = emptySet(),
                poppedBalloons = emptySet()
            )
            speakPromptForCurrentQuestion()
        } else {
            // Level completed! Calculate stars
            val total = current.questions.size
            val ratio = current.correctCount.toFloat() / total.toFloat()
            val stars = when {
                ratio >= 0.8f -> 3
                ratio >= 0.6f -> 2
                ratio >= 0.3f -> 1
                else -> 1
            }
            val coinsEarned = stars * 15 + (current.currentScore / 5)
            val xpEarned = stars * 30 + 20

            soundManager.playSound(SoundEffect.FANFARE)
            soundManager.speak("Selamat! Kamu menyelesaikan level ini dengan luar biasa!")

            _gameState.value = current.copy(
                isLevelCompleted = true,
                starsEarned = stars,
                coinsEarned = coinsEarned
            )

            // Persist progress to Room Database
            if (!current.isPractice && current.worldId != null) {
                viewModelScope.launch {
                    repository.completeLevel(
                        worldId = current.worldId.name,
                        levelNumber = current.levelNumber,
                        starsEarned = stars,
                        scoreEarned = current.currentScore,
                        coinsEarned = coinsEarned,
                        xpEarned = xpEarned
                    )
                }
            }
        }
    }

    fun retryLevel() {
        val current = _gameState.value
        if (current.worldId != null) {
            startLevel(current.worldId, current.levelNumber)
        } else {
            _gameState.value = current.copy(
                currentIndex = 0,
                currentScore = 0,
                correctCount = 0,
                selectedOption = null,
                isAnswerChecked = false,
                isCorrect = false,
                isLevelCompleted = false,
                countedItems = emptySet(),
                poppedBalloons = emptySet()
            )
            speakPromptForCurrentQuestion()
        }
    }

    fun speakPromptForCurrentQuestion() {
        val question = _gameState.value.currentQuestion ?: return
        soundManager.speak(question.speechPrompt)
    }

    fun toggleSound() {
        viewModelScope.launch {
            val current = playerProfile.value
            val newVal = !current.soundEnabled
            soundManager.isSoundEnabled = newVal
            repository.updateSettings(newVal, current.musicEnabled, current.ttsEnabled)
        }
    }

    fun toggleMusic() {
        viewModelScope.launch {
            val current = playerProfile.value
            val newVal = !current.musicEnabled
            soundManager.isMusicEnabled = newVal
            repository.updateSettings(current.soundEnabled, newVal, current.ttsEnabled)
        }
    }

    fun toggleTts() {
        viewModelScope.launch {
            val current = playerProfile.value
            val newVal = !current.ttsEnabled
            soundManager.isTtsEnabled = newVal
            repository.updateSettings(current.soundEnabled, current.musicEnabled, newVal)
        }
    }

    fun updatePlayerName(newName: String) {
        viewModelScope.launch {
            repository.updateName(newName)
        }
    }

    fun selectCompanion(petId: String) {
        soundManager.playSound(SoundEffect.CLICK)
        viewModelScope.launch {
            repository.updateSelectedPet(petId)
        }
    }

    override fun onCleared() {
        super.onCleared()
        soundManager.release()
    }
}
