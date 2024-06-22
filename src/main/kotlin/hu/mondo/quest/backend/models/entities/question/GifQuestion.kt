package hu.mondo.quest.backend.models.entities.question

import com.fasterxml.jackson.annotation.JsonManagedReference
import hu.mondo.quest.backend.models.dtos.question.QuestionDTO
import hu.mondo.quest.backend.models.dtos.question.QuestionType
import hu.mondo.quest.backend.models.entities.answer.Answer
import hu.mondo.quest.backend.models.entities.user.QuestUser
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class GifQuestion(
    override var title: String,
    override var description: String,
    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER, cascade = [jakarta.persistence.CascadeType.ALL])
    override var answers : MutableCollection<Answer>,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "answer_id", referencedColumnName = "answerId")
    override var correctAnswer: Answer,
    override var averageRating: Double,
    override var ratings: MutableList<Int>,
    override var difficulty: QuestionDifficulty,
    @ManyToMany(cascade = [CascadeType.ALL])
    override var ratedByUsers: MutableList<QuestUser>,
    @Lob
    val gifFile: ByteArray = byteArrayOf(),
) : Question() {
    override fun mapToQuestionDTO(): QuestionDTO {
        return QuestionDTO(
            id = questionId!!,
            title = title,
            description = description,
            answers = answers.distinctBy{ it.answerText }.shuffled().toMutableList(),
            averageRating = averageRating,
            difficulty = difficulty,
            file = gifFile,
            questionType = QuestionType.GIF
        )
    }
}