package com.example

import com.example.model.QuestionBank
import com.example.model.WorldId
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun questionBank_generatesQuestionsForAllWorlds() {
        WorldId.entries.forEach { world ->
            for (lvl in 1..5) {
                val questions = QuestionBank.getQuestionsForLevel(world, lvl)
                assertEquals("World $world Level $lvl should have 5 questions", 5, questions.size)
                questions.forEach { q ->
                    assertNotNull(q.questionText)
                    assertTrue("Question ${q.id} must have non-empty options", q.options.isNotEmpty())
                    assertTrue(
                        "Question ${q.id} options must contain correct answer '${q.correctAnswer}'",
                        q.options.any { it.trim() == q.correctAnswer.trim() }
                    )
                }
            }
        }
    }
}
