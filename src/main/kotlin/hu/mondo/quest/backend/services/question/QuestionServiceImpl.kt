package hu.mondo.quest.backend.services.question

import hu.mondo.quest.backend.models.dtos.answer.AnswerDTO
import hu.mondo.quest.backend.models.dtos.answer.ImageAnswerDTO
import hu.mondo.quest.backend.models.dtos.question.CreateQuestionDTO
import hu.mondo.quest.backend.models.dtos.question.QuestionDTO
import hu.mondo.quest.backend.models.dtos.question.QuestionType
import hu.mondo.quest.backend.models.entities.answer.Answer
import hu.mondo.quest.backend.models.entities.answer.ImageAnswer
import hu.mondo.quest.backend.models.entities.answer.ImageAnswerState
import hu.mondo.quest.backend.models.entities.answer.QuestionGroupType
import hu.mondo.quest.backend.models.entities.question.*
import hu.mondo.quest.backend.models.entities.user.QuestUser
import hu.mondo.quest.backend.repositories.AnswerRepository
import hu.mondo.quest.backend.repositories.QuestionRepository
import hu.mondo.quest.backend.repositories.UserRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.time.Clock
import java.util.*

@Service
@RequiredArgsConstructor
@Transactional
open class QuestionServiceImpl(
    val questionRepository: QuestionRepository,
    val userRepository: UserRepository,
    val answerRepository: AnswerRepository,
    val answerHelperService: AnswerHelperService
) : QuestionService {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @PersistenceContext
    private lateinit var entityManager: EntityManager


    override fun getRandomStoryQuestion(userId: Long): QuestionDTO {
        val user = userRepository.findById(userId).get()
        if(user.answeredStoryQuestions.isEmpty())
            user.questStartDate = Date()
        if(user.currentStoryQuestion != null)
            return user.currentStoryQuestion!!.mapToQuestionDTO()
        val query = entityManager.createNativeQuery(
            "SELECT q.*\n" +
                    "FROM question AS q\n" +
                    "WHERE NOT EXISTS (\n" +
                    "    SELECT 1\n" +
                    "    FROM quest_user AS u\n" +
                    "    JOIN quest_user_answered_story_questions AS aiq ON u.quest_user_id = aiq.quest_user_quest_user_id\n" +
                    "    WHERE u.quest_user_id = $userId AND q.question_id = aiq.answered_story_questions_question_id\n" +
                    ")\n" +
                    "ORDER BY RANDOM()\n" +
                    "LIMIT 1;", Question::class.java
        )
        val question = query.singleResult as Question
        //TODO handle out of question
        saveUser(user)
        return question.mapToQuestionDTO()
    }

    override fun getRandomInfiniteQuestion(userId: Long): QuestionDTO {
        val user = userRepository.findById(userId).get()
        if (user.currentInfiniteQuestion != null)
            return user.currentInfiniteQuestion!!.mapToQuestionDTO()
        val query = entityManager.createNativeQuery(
            "SELECT q.*\n" +
                    "FROM question AS q\n" +
                    "WHERE NOT EXISTS (\n" +
                    "    SELECT 1\n" +
                    "    FROM quest_user AS u\n" +
                    "    JOIN quest_user_answered_infinite_questions AS aiq ON u.quest_user_id = aiq.quest_user_quest_user_id\n" +
                    "    WHERE u.quest_user_id = $userId AND q.question_id = aiq.answered_infinite_questions_question_id\n" +
                    ")\n" +
                    "ORDER BY RANDOM()\n" +
                    "LIMIT 1;", Question::class.java
        )
        val question = query.singleResult as Question
        //TODO handle out of question
        user.currentInfiniteQuestion = question
        saveUser(user)
        return question.mapToQuestionDTO()
    }

    override fun createQuestion(createQuestionDTO: CreateQuestionDTO) {
        val question = when (createQuestionDTO.questionType) {
            QuestionType.TEXT -> {
                TextQuestion(
                    title = createQuestionDTO.title,
                    description = createQuestionDTO.description,
                    answers = createQuestionDTO.answers,
                    correctAnswer = createQuestionDTO.correctAnswer,
                    ratings = mutableListOf(0, 0, 0, 0, 0),
                    averageRating = 0.0,
                    ratedByUsers = mutableListOf(),
                    difficulty = createQuestionDTO.questionDifficulty
                )

            }

            QuestionType.IMAGE -> {
                ImageQuestion(
                    title = createQuestionDTO.title,
                    description = createQuestionDTO.description,
                    answers = createQuestionDTO.answers,
                    correctAnswer = createQuestionDTO.correctAnswer,
                    ratings = mutableListOf(0, 0, 0, 0, 0),
                    averageRating = 0.0,
                    ratedByUsers = mutableListOf(),
                    difficulty = createQuestionDTO.questionDifficulty,
                    imageFile = createQuestionDTO.file
                )
            }

            QuestionType.VIDEO -> {
                VideoQuestion(
                    title = createQuestionDTO.title,
                    description = createQuestionDTO.description,
                    answers = createQuestionDTO.answers,
                    correctAnswer = createQuestionDTO.correctAnswer,
                    ratings = mutableListOf(0, 0, 0, 0, 0),
                    averageRating = 0.0,
                    ratedByUsers = mutableListOf(),
                    difficulty = createQuestionDTO.questionDifficulty,
                    videoUrl = createQuestionDTO.title //TODO change if real
                )
            }

            QuestionType.AUDIO -> {
                AudioQuestion(
                    title = createQuestionDTO.title,
                    description = createQuestionDTO.description,
                    answers = createQuestionDTO.answers,
                    correctAnswer = createQuestionDTO.correctAnswer,
                    ratings = mutableListOf(0, 0, 0, 0, 0),
                    averageRating = 0.0,
                    ratedByUsers = mutableListOf(),
                    difficulty = createQuestionDTO.questionDifficulty,
                    audioFile = createQuestionDTO.file
                )
            }

            QuestionType.GIF -> {
                GifQuestion(
                    title = createQuestionDTO.title,
                    description = createQuestionDTO.description,
                    answers = createQuestionDTO.answers,
                    correctAnswer = createQuestionDTO.correctAnswer,
                    ratings = mutableListOf(0, 0, 0, 0, 0),
                    averageRating = 0.0,
                    ratedByUsers = mutableListOf(),
                    difficulty = createQuestionDTO.questionDifficulty,
                    gifFile = createQuestionDTO.file
                )
            }

            QuestionType.INTERACTIVE -> {
                InteractiveQuestion(
                    title = createQuestionDTO.title,
                    description = createQuestionDTO.description,
                    answers = createQuestionDTO.answers,
                    correctAnswer = createQuestionDTO.correctAnswer,
                    ratings = mutableListOf(0, 0, 0, 0, 0),
                    averageRating = 0.0,
                    ratedByUsers = mutableListOf(),
                    difficulty = createQuestionDTO.questionDifficulty,
                    file = createQuestionDTO.file
                )
            }
        }
        question.answers.map { it.question = question }
        question.correctAnswer.question = question
        questionRepository.save(question)
    }

    @Transactional
    override fun answerInfiniteQuestion(questionId: Long, userId: Long, answer: Answer): AnswerDTO {
        val question = questionRepository.findById(questionId).get()
        val user = userRepository.findById(userId).get()

        val isCorrect = question.answers.sortedBy { it.answerId }.first().answerText == answer.answerText
        val point = answerHelperService.calculatePointForQuestion(question, isCorrect)

        user.answeredInfiniteQuestions.add(question)
        user.infinitePoints += point
        user.currentInfiniteQuestion = null
        saveUser(user)

        return AnswerDTO(
            isCorrect = isCorrect,
            answerText = question.correctAnswer.answerText,
            userPoint = user.infinitePoints.toInt(),
            isInteractiveAnswer = false
        )
    }

    override fun answerInfiniteInteractiveQuestion(
        questionId: Long,
        userId: Long,
        imageAnswer: ImageAnswer
    ) {
        val question = questionRepository.findById(questionId).get()
        val user = userRepository.findById(userId).get()
        user.currentInfiniteQuestion = null
        user.answeredInfiniteQuestions.add(question)

        saveUser(user)


        imageAnswer.apply {
            this.question = question
            this.user = user
            this.imageAnswerState = ImageAnswerState.PENDING
            this.questionGroupType = QuestionGroupType.INFINITE
        }

        answerRepository.save(imageAnswer)
    }

    override fun answerStoryQuestion(questionId: Long, userId: Long, answer: Answer): AnswerDTO {
        val question = questionRepository.findById(questionId).get()
        val user = userRepository.findById(userId).get()

        val isCorrect = question.answers.sortedBy { it.answerId }.first().answerText == answer.answerText
        val point = answerHelperService.calculatePointForQuestion(question, isCorrect)

        user.answeredStoryQuestions.add(question)
        user.storyPoints += point
        user.currentStoryQuestion = null
        saveUser(user)

        log.info("User $userId answered $questionId with $answer and got $point points")

        return AnswerDTO(
            isCorrect = isCorrect,
            answerText = question.correctAnswer.answerText,
            userPoint = user.storyPoints.toInt(),
            isInteractiveAnswer = false
        )
    }

    override fun answerStoryInteractiveQuestion(questionId: Long, userId: Long, imageAnswer: ImageAnswer): Boolean {
        val question = questionRepository.findById(questionId).get()
        val user = userRepository.findById(userId).get()

        user.currentStoryQuestion = null
        user.answeredStoryQuestions.add(question)
        saveUser(user)

        imageAnswer.apply {
            this.question = question
            this.user = user
            this.imageAnswerState = ImageAnswerState.PENDING
            this.questionGroupType = QuestionGroupType.STORY
        }

        saveAnswer(imageAnswer)
        if(user.answeredStoryQuestions.size == 100)
            return true
        else return false
    }

    override fun getAllQuestions(): List<Long> {
        return questionRepository.findAllIds().sorted()
    }

    override fun getQuestionById(questionId: Long): QuestionDTO {
        return questionRepository.findById(questionId).get().mapToQuestionDTO()
    }

    override fun deleteQuestion(questionId: Long) {
        questionRepository.deleteById(questionId)
    }

    override fun rateQuestion(questionId: Long, userId: Long, rating: Int) {
        val question = questionRepository.findById(questionId).get()
        val user = userRepository.findById(userId).get()
        question.ratings[rating - 1] += 1
        question.averageRating = calculateWeightedAverage(question.ratings)
        user.ratedQuestions.add(question)
        questionRepository.save(question)
        userRepository.save(user)
    }

    override fun getAllImageAnswersByUserId(userId: Long): List<ImageAnswerDTO> {
        val imageAnswers = answerRepository.findAllImageAnswersByQuestUserId(userId)
        return imageAnswers.map { it.mapToImageAnswerDTO() }
    }

    override fun getAllImageAnswersByImageAnswerState(imageAnswerState: ImageAnswerState): List<ImageAnswerDTO> {
        val imageAnswers = answerRepository.findAllImageAnswersByImageAnswerState(imageAnswerState)
        return imageAnswers.map { it.mapToImageAnswerDTO() }
    }

    override fun getLatestImageAnswers(): List<ImageAnswerDTO> {
        val imageAnswers = answerRepository.get10LatestApprovedAnswers()
        return imageAnswers.map { it.mapToImageAnswerDTO() }
    }

    override fun acceptImageAnswer(answerId: Long) {
        val answer = answerRepository.findById(answerId).get() as ImageAnswer
        answer.imageAnswerState = ImageAnswerState.APPROVED
        val points = answerHelperService.calculatePointForQuestion(answer.question!!, true)
        val user = userRepository.findById(answer.user.questUserId!!).get()

        when (answer.questionGroupType) {
            QuestionGroupType.STORY -> user.storyPoints += points
            QuestionGroupType.INFINITE -> user.infinitePoints += points
        }
        userRepository.save(user)
        answerRepository.save(answer)
    }

    override fun declineImageAnswer(answerId: Long) {
        val answer = answerRepository.findById(answerId).get() as ImageAnswer
        answer.imageAnswerState = ImageAnswerState.REJECTED
        val points = answerHelperService.calculatePointForQuestion(answer.question!!, false)
        val user = userRepository.findById(answer.user.questUserId!!).get()

        when (answer.questionGroupType) {
            QuestionGroupType.STORY -> user.storyPoints += points
            QuestionGroupType.INFINITE -> user.infinitePoints += points
        }
        userRepository.save(user)
        answerRepository.save(answer)
    }

    fun calculateWeightedAverage(numbers: List<Int>): Double {
        if (numbers.size != 5) {
            throw IllegalArgumentException("Input list must contain exactly 5 elements")
        }
        val weights = listOf(1, 2, 3, 4, 5)

        val weightedSum = numbers.zip(weights) { num, weight -> num * weight }.sum()
        val totalWeight = numbers.sum()
        return weightedSum / totalWeight.toDouble()
    }

    @Async
    fun saveUser(user: QuestUser) {
        userRepository.save(user)
    }

    @Async
    fun saveAnswer(answer: Answer) {
        answerRepository.save(answer)
    }
}