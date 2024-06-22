package hu.mondo.quest.backend.models.dtos.question

import hu.mondo.quest.backend.models.entities.answer.Answer
import hu.mondo.quest.backend.models.entities.question.QuestionDifficulty

data class QuestionDTO(
    val id : Long,
    val title: String,
    val description: String,
    val answers : MutableCollection<Answer>,
    val averageRating: Double,
    val difficulty: QuestionDifficulty,
    val questionType: QuestionType,
    val file : ByteArray = byteArrayOf()
)
