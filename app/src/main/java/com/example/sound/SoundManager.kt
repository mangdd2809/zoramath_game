package com.example.sound

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.speech.tts.TextToSpeech
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sin

enum class SoundEffect {
    CLICK,
    POP,
    COUNT,
    CORRECT,
    WRONG,
    STAR,
    FANFARE,
    COIN
}

class SoundManager(private val context: Context) : TextToSpeech.OnInitListener {

    private val scope = CoroutineScope(Dispatchers.Default)
    private var musicJob: Job? = null

    var isSoundEnabled: Boolean = true
    var isMusicEnabled: Boolean = true
        set(value) {
            field = value
            if (value) startBackgroundMusic() else stopBackgroundMusic()
        }
    var isTtsEnabled: Boolean = true

    private var tts: TextToSpeech? = null
    private var isTtsReady: Boolean = false

    private val sampleRate = 44100

    init {
        try {
            tts = TextToSpeech(context.applicationContext, this)
        } catch (e: Exception) {
            Log.w("SoundManager", "TTS initialization failed: ${e.message}")
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale("id", "ID"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                tts?.setLanguage(Locale.US)
            }
            tts?.setSpeechRate(0.95f)
            tts?.setPitch(1.15f) // Friendly cheerful child-friendly tone
            isTtsReady = true
        }
    }

    fun speak(text: String) {
        if (!isTtsEnabled || !isTtsReady || text.isBlank()) return
        try {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "ZORRA_TTS")
        } catch (e: Exception) {
            Log.w("SoundManager", "Error speaking: ${e.message}")
        }
    }

    fun playSound(effect: SoundEffect, countStep: Int = 1) {
        if (!isSoundEnabled) return
        scope.launch {
            try {
                when (effect) {
                    SoundEffect.CLICK -> playMarimbaNote(800.0, 0.08, 0.5)
                    SoundEffect.POP -> playBubblePop()
                    SoundEffect.COUNT -> {
                        // Rising pitch scale based on count
                        val baseFreq = 440.0 * Math.pow(1.059463, ((countStep - 1) % 12).toDouble())
                        playMarimbaNote(baseFreq, 0.12, 0.6)
                    }
                    SoundEffect.CORRECT -> playJoyfulChime()
                    SoundEffect.WRONG -> playGentleBoing()
                    SoundEffect.STAR -> playSparkleChime()
                    SoundEffect.FANFARE -> playVictoryFanfare()
                    SoundEffect.COIN -> playCoinPickup()
                }
            } catch (e: Exception) {
                Log.w("SoundManager", "Audio playback error: ${e.message}")
            }
        }
    }

    fun startBackgroundMusic() {
        if (!isMusicEnabled || musicJob?.isActive == true) return
        musicJob = scope.launch {
            // Melodic pentatonic pleasant loop (C5, D5, E5, G5, A5, C6)
            // Cheerful, gentle, non-fatiguing for kids
            val melodyNotes = listOf(
                523.25, 659.25, 783.99, 659.25,
                880.00, 783.99, 659.25, 587.33,
                523.25, 587.33, 659.25, 783.99,
                1046.50, 880.00, 783.99, 659.25
            )
            val durations = listOf(
                350L, 350L, 350L, 350L,
                350L, 350L, 700L, 700L,
                350L, 350L, 350L, 350L,
                350L, 350L, 700L, 1000L
            )

            while (isActive && isMusicEnabled) {
                for (i in melodyNotes.indices) {
                    if (!isActive || !isMusicEnabled) break
                    val freq = melodyNotes[i]
                    val durationMs = durations[i]
                    playSoftMusicTone(freq, (durationMs.toDouble() / 1000.0) * 0.75, 0.12)
                    delay(durationMs)
                }
                delay(800) // Brief rest before melody loops
            }
        }
    }

    fun stopBackgroundMusic() {
        musicJob?.cancel()
        musicJob = null
    }

    private fun playSoftMusicTone(freq: Double, durationSec: Double, volume: Double) {
        val numSamples = (durationSec * sampleRate).toInt()
        val buffer = ShortArray(numSamples)
        for (i in 0 until numSamples) {
            val t = i.toDouble() / sampleRate
            val envelope = exp(-3.0 * (t / durationSec)) // Soft bell envelope
            // Fundamental + subtle 2nd harmonic for warm marimba/xylophone timbre
            val sample = (sin(2.0 * PI * freq * t) * 0.85 + sin(4.0 * PI * freq * t) * 0.15) * envelope * volume
            buffer[i] = (sample * Short.MAX_VALUE).toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
        }
        playAudioBuffer(buffer)
    }

    private fun playMarimbaNote(freq: Double, durationSec: Double, volume: Double) {
        val numSamples = (durationSec * sampleRate).toInt()
        val buffer = ShortArray(numSamples)
        for (i in 0 until numSamples) {
            val t = i.toDouble() / sampleRate
            val envelope = exp(-18.0 * t) // Quick percussive decay
            val sample = sin(2.0 * PI * freq * t) * envelope * volume
            buffer[i] = (sample * Short.MAX_VALUE).toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
        }
        playAudioBuffer(buffer)
    }

    private fun playBubblePop() {
        val durationSec = 0.09
        val numSamples = (durationSec * sampleRate).toInt()
        val buffer = ShortArray(numSamples)
        for (i in 0 until numSamples) {
            val t = i.toDouble() / sampleRate
            // Frequency slides up quickly from 300 to 900 Hz
            val freq = 300.0 + (t / durationSec) * 600.0
            val envelope = sin(PI * (t / durationSec))
            val sample = sin(2.0 * PI * freq * t) * envelope * 0.7
            buffer[i] = (sample * Short.MAX_VALUE).toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
        }
        playAudioBuffer(buffer)
    }

    private suspend fun playJoyfulChime() {
        val notes = listOf(523.25, 659.25, 783.99, 1046.50) // C5, E5, G5, C6
        for (freq in notes) {
            playMarimbaNote(freq, 0.18, 0.7)
            delay(70)
        }
    }

    private fun playGentleBoing() {
        val durationSec = 0.22
        val numSamples = (durationSec * sampleRate).toInt()
        val buffer = ShortArray(numSamples)
        for (i in 0 until numSamples) {
            val t = i.toDouble() / sampleRate
            // Wobbly pitch sliding down gently
            val freq = 320.0 - 120.0 * (t / durationSec) + 20.0 * sin(2.0 * PI * 18.0 * t)
            val envelope = exp(-6.0 * t)
            val sample = sin(2.0 * PI * freq * t) * envelope * 0.5
            buffer[i] = (sample * Short.MAX_VALUE).toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
        }
        playAudioBuffer(buffer)
    }

    private suspend fun playSparkleChime() {
        val sparkleNotes = listOf(1046.50, 1318.51, 1567.98, 2093.00)
        for (freq in sparkleNotes) {
            playMarimbaNote(freq, 0.15, 0.5)
            delay(50)
        }
    }

    private suspend fun playCoinPickup() {
        playMarimbaNote(987.77, 0.08, 0.6) // B5
        delay(60)
        playMarimbaNote(1318.51, 0.22, 0.7) // E6
    }

    private suspend fun playVictoryFanfare() {
        val fanfare = listOf(
            523.25 to 110L, 659.25 to 110L, 783.99 to 110L,
            1046.50 to 220L, 783.99 to 110L, 1046.50 to 450L
        )
        for ((freq, dur) in fanfare) {
            playMarimbaNote(freq, dur.toDouble() / 1000.0, 0.75)
            delay(dur + 20)
        }
    }

    private fun playAudioBuffer(buffer: ShortArray) {
        val minBufferSize = AudioTrack.getMinBufferSize(
            sampleRate,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        )
        val track = AudioTrack.Builder()
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
            .setAudioFormat(
                AudioFormat.Builder()
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setSampleRate(sampleRate)
                    .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                    .build()
            )
            .setBufferSizeInBytes(buffer.size.coerceAtLeast(minBufferSize) * 2)
            .setTransferMode(AudioTrack.MODE_STATIC)
            .build()

        try {
            track.write(buffer, 0, buffer.size)
            track.play()
            scope.launch {
                val durationMs = (buffer.size.toDouble() / sampleRate * 1000).toLong() + 100
                delay(durationMs)
                try {
                    track.stop()
                    track.release()
                } catch (_: Exception) {}
            }
        } catch (e: Exception) {
            Log.w("SoundManager", "AudioTrack write failed: ${e.message}")
            try { track.release() } catch (_: Exception) {}
        }
    }

    fun release() {
        stopBackgroundMusic()
        try {
            tts?.stop()
            tts?.shutdown()
        } catch (_: Exception) {}
    }
}
