package hu.mondo.quest.backend.models.entities.answer

import com.fasterxml.jackson.annotation.JsonBackReference
import hu.mondo.quest.backend.models.dtos.answer.AnswerDTO
import hu.mondo.quest.backend.models.dtos.answer.ImageAnswerDTO
import hu.mondo.quest.backend.models.entities.question.Question
import hu.mondo.quest.backend.models.entities.user.QuestUser
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
open class Answer(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open val answerId: Long? = null,

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "question_id", referencedColumnName = "questionId")
    open var question: Question? = null,
    open val answerText: String = "",
) {
    open fun mapToImageAnswerDTO(): ImageAnswerDTO {
        return ImageAnswerDTO(
            title = question!!.title,
            description = question!!.description,
            user = 0L,  //TODO elvileg nem kéne előjönnie de ha mégis
            imageAnswerState = ImageAnswerState.PENDING,
            questionGroupType = QuestionGroupType.STORY,
            imageFile = byteArrayOf()
        )

    }

}