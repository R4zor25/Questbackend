package hu.mondo.quest.backend.models.entities.question

import hu.mondo.quest.backend.models.dtos.question.QuestionDTO
import hu.mondo.quest.backend.models.entities.answer.Answer
import hu.mondo.quest.backend.models.entities.user.QuestUser
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import lombok.Data
import java.io.Serializable

@Entity
@Data
abstract class Question : Serializable{
    abstract fun mapToQuestionDTO(): QuestionDTO

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open val questionId: Long? = null
    abstract var title: String
    abstract var description: String
    abstract var answers: MutableCollection<Answer>
    abstract var correctAnswer: Answer
    abstract var difficulty : QuestionDifficulty
    abstract var averageRating: Double
    abstract var ratings: MutableList<Int>
    abstract var ratedByUsers: MutableList<QuestUser>
}



