package hu.mondo.quest.backend.controllers

import hu.mondo.quest.backend.models.dtos.question.CreateQuestionDTO
import hu.mondo.quest.backend.models.dtos.question.RatingDTO
import hu.mondo.quest.backend.models.entities.answer.Answer
import hu.mondo.quest.backend.models.entities.answer.ImageAnswer
import hu.mondo.quest.backend.models.entities.answer.ImageAnswerState
import hu.mondo.quest.backend.services.question.QuestionService
import lombok.RequiredArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
@CrossOrigin
class QuestionController(
    private var questionService: QuestionService
){
    private val log = LoggerFactory.getLogger(this.javaClass)
    var UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads"

    @GetMapping("/infinite/random/{userId}")
    fun getRandomInfiniteQuestion(@PathVariable userId: Long) : ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(questionService.getRandomInfiniteQuestion(userId))
        } catch (e: Exception) {
            ResponseEntity.status(404).body(e.localizedMessage)
        }
    }

    @GetMapping("/story/random/{userId}")
    fun getRandomStoryQuestion(@PathVariable userId: Long) : ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(questionService.getRandomStoryQuestion(userId))
        } catch (e: Exception) {
            ResponseEntity.status(404).body(e.localizedMessage)
        }
    }

    @PostMapping("/create")
    fun createQuestion(@RequestBody createQuestionDTO: CreateQuestionDTO) : ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(questionService.createQuestion(createQuestionDTO))
        } catch (e: Exception) {
            ResponseEntity.status(404).body(e.localizedMessage)
        }
    }

    @PostMapping("{questionId}/story/answer/interactive/{userId}")
    fun answerInteractiveStoryQuestion(@PathVariable questionId: Long, @PathVariable userId: Long, @RequestBody imageAnswer: ImageAnswer) : ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(questionService.answerStoryInteractiveQuestion(questionId, userId, imageAnswer))
        } catch (e: Exception) {
            ResponseEntity.status(404).body(e.localizedMessage)
        }
    }


    @PostMapping("{questionId}/story/answer/{userId}")
    fun answerStoryQuestion(@PathVariable questionId: Long, @PathVariable userId: Long, @RequestBody answer: Answer) : ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(questionService.answerStoryQuestion(questionId, userId, answer))
        } catch (e: Exception) {
            ResponseEntity.status(404).body(e.localizedMessage)
        }
    }

    @PostMapping("{questionId}/infinite/answer/{userId}")
    fun answerInfiniteQuestion(@PathVariable questionId: Long, @PathVariable userId: Long, @RequestBody answer: Answer) : ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(questionService.answerInfiniteQuestion(questionId, userId, answer))
        } catch (e: Exception) {
            ResponseEntity.status(404).body(e.localizedMessage)
        }
    }

    @PostMapping("{questionId}/infinite/answer/interactive/{userId}")
    fun answerInteractiveInfiniteQuestion(@PathVariable questionId: Long, @PathVariable userId: Long, @RequestBody imageAnswer: ImageAnswer) : ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(questionService.answerInfiniteInteractiveQuestion(questionId, userId, imageAnswer))
        } catch (e: Exception) {
            ResponseEntity.status(404).body(e.localizedMessage)
        }
    }

    @GetMapping("/{questionId}")
    fun getQuestionById(@PathVariable questionId: Long) : ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(questionService.getQuestionById(questionId))
        } catch (e: Exception) {
            ResponseEntity.status(404).body(e.localizedMessage)
        }
    }

    @DeleteMapping("/{questionId}")
    fun deleteQuestionById(@PathVariable questionId: Long) : ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(questionService)
        } catch (e: Exception) {
            ResponseEntity.status(404).body(e.localizedMessage)
        }
    }


    @GetMapping("/all")
    fun getAllQuestions() : ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(questionService.getAllQuestions())
        } catch (e: Exception) {
            ResponseEntity.status(404).body(e.localizedMessage)
        }
    }

    @PostMapping("{questionId}/rate/{userId}")
    fun rateQuestion(@PathVariable questionId: Long, @PathVariable userId: Long, @RequestBody rating: RatingDTO) : ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(questionService.rateQuestion(questionId, userId, rating.rating))
        } catch (e: Exception) {
            ResponseEntity.status(404).body(e.localizedMessage)
        }
    }

    @GetMapping("/interactive")
    fun getAllImageAnswer() : ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(questionService.getAllQuestions())
        } catch (e: Exception) {
            ResponseEntity.status(404).body(e.localizedMessage)
        }
    }

    @GetMapping("interactive/pending")
    fun getAllPendingStoryImageAnswer() : ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(questionService.getAllImageAnswersByImageAnswerState(ImageAnswerState.PENDING))
        } catch (e: Exception) {
            ResponseEntity.status(404).body(e.localizedMessage)
        }
    }

    @GetMapping("/interactive/{userId}")
    fun getAllImageAnswerForUser(@PathVariable userId: Long) : ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(questionService.getAllImageAnswersByUserId(userId))
        } catch (e: Exception) {
            ResponseEntity.status(404).body(e.localizedMessage)
        }
    }

    @GetMapping("/interactive/latest")
    fun getLatestImageAnswers() : ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(questionService.getLatestImageAnswers())
        } catch (e: Exception) {
            ResponseEntity.status(404).body(e.localizedMessage)
        }
    }
    //TODO admin def
    @PostMapping("/interactive/{answerId}/accept")
    fun acceptImageAnswer(@PathVariable answerId: Long) : ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(questionService.acceptImageAnswer(answerId))
        } catch (e: Exception) {
            ResponseEntity.status(404).body(e.localizedMessage)
        }
    }

    @PostMapping("/interactive/{answerId}/reject")
    fun declineImageAnswer(@PathVariable answerId: Long) : ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(questionService.declineImageAnswer(answerId))
        } catch (e: Exception) {
            ResponseEntity.status(404).body(e.localizedMessage)
        }
    }
}