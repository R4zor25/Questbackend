package hu.mondo.quest.backend.models.entities.answer

import com.fasterxml.jackson.annotation.JsonBackReference
import hu.mondo.quest.backend.models.dtos.answer.AnswerDTO
import hu.mondo.quest.backend.models.dtos.answer.ImageAnswerDTO
import hu.mondo.quest.backend.models.dtos.answer.SharedImageAnswerDTO
import hu.mondo.quest.backend.models.dtos.answer.UserImageAnswerDTO
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
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "question_id", referencedColumnName = "questionId")
    open var question: Question? = null,
    open val answerText: String = "",
) {
    open fun mapToImageAnswerDTO(): ImageAnswerDTO {
        return ImageAnswerDTO(
            answerId = answerId!!,
            title = question!!.title,
            description = question!!.description,
            imageAnswerState = ImageAnswerState.PENDING,
            questionGroupType = QuestionGroupType.STORY,
            imageFile = byteArrayOf()
        )
    }

    open fun maptoSharedImageAnswerDTO(): SharedImageAnswerDTO {
        return SharedImageAnswerDTO(
            title = question!!.title,
            description = question!!.description,
            imageFile = byteArrayOf()
        )
    }

    open fun mapToUserImageAnswerDTO(): UserImageAnswerDTO {
        return UserImageAnswerDTO(
            title = question!!.title,
            description = question!!.description,
            imageFile = byteArrayOf(),
            imageAnswerState = ImageAnswerState.APPROVED,
            questionGroupType = QuestionGroupType.STORY
        )
    }

}