package hu.mondo.quest.backend.services.question

import hu.mondo.quest.backend.models.dtos.answer.*
import hu.mondo.quest.backend.models.dtos.question.CreateQuestionDTO
import hu.mondo.quest.backend.models.dtos.question.QuestionDTO
import hu.mondo.quest.backend.models.entities.answer.Answer
import hu.mondo.quest.backend.models.entities.answer.ImageAnswer
import hu.mondo.quest.backend.models.entities.answer.ImageAnswerState
import hu.mondo.quest.backend.models.entities.answer.QuestionGroupType

interface QuestionService {
    fun getRandomStoryQuestion(userId: Long) : QuestionDTO
    fun getRandomInfiniteQuestion(userId: Long) : QuestionDTO
    fun createQuestion(createQuestionDTO: CreateQuestionDTO)
    fun answerInfiniteQuestion(questionId: Long, userId: Long, answer: Answer): AnswerDTO
    fun answerInfiniteInteractiveQuestion(questionId: Long, userId: Long, createImageAnswerDTO: CreateImageAnswerDTO)
    fun answerStoryQuestion(questionId: Long, userId: Long, answer: Answer): AnswerDTO
    fun answerStoryInteractiveQuestion(questionId: Long, userId: Long, imageAnswer: ImageAnswer) : Boolean
    fun getAllQuestions(): List<Long>
    fun getQuestionById(questionId: Long): QuestionDTO
    fun deleteQuestion(questionId: Long)
    fun rateQuestion(questionId: Long, userId: Long, rating: Int)
    fun getAllImageAnswersByUserId(userId: Long): List<UserImageAnswerDTO>
    fun getAllImageAnswersByImageAnswerState(imageAnswerState: ImageAnswerState): List<ImageAnswerDTO>
    fun getAllAcceptedImageAnswers(): List<SharedImageAnswerDTO>
    fun getLatestImageAnswers(): List<SharedImageAnswerDTO>
    fun acceptImageAnswer(answerId: Long)
    fun declineImageAnswer(answerId: Long)
}