package hu.mondo.quest.backend.services.question

import hu.mondo.quest.backend.models.entities.question.Question
import hu.mondo.quest.backend.models.entities.question.QuestionDifficulty
import org.springframework.stereotype.Service

@Service
class AnswerHelperService {

    fun calculatePointForQuestion(question: Question, isCorrect: Boolean): Int {
        val point = if(isCorrect){
            when(question.difficulty){
                QuestionDifficulty.VERY_EASY -> 1
                QuestionDifficulty.EASY -> 2
                QuestionDifficulty.MEDIUM -> 3
                QuestionDifficulty.HARD -> 4
                QuestionDifficulty.VERY_HARD -> 5
                QuestionDifficulty.EXTREMELY_HARD -> 6
                QuestionDifficulty.IMPOSSIBLE -> 7
            }
        } else {
            when(question.difficulty){
                QuestionDifficulty.VERY_EASY -> -5
                QuestionDifficulty.EASY -> -4
                QuestionDifficulty.MEDIUM -> -3
                QuestionDifficulty.HARD -> -2
                QuestionDifficulty.VERY_HARD -> -1
                QuestionDifficulty.EXTREMELY_HARD -> 0
                QuestionDifficulty.IMPOSSIBLE -> 0
            }
        }
        return point
    }
}