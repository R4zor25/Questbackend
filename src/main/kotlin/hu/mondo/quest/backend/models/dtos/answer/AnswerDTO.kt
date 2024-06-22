package hu.mondo.quest.backend.models.dtos.answer

data class AnswerDTO(
    val isCorrect: Boolean,
    val answerText: String,
    val userPoint: Int,
    val isInteractiveAnswer: Boolean
)