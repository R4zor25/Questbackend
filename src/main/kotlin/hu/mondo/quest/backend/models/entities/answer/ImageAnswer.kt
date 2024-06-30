package hu.mondo.quest.backend.models.entities.answer

import com.fasterxml.jackson.annotation.JsonBackReference
import hu.mondo.quest.backend.models.dtos.answer.ImageAnswerDTO
import hu.mondo.quest.backend.models.dtos.answer.SharedImageAnswerDTO
import hu.mondo.quest.backend.models.dtos.answer.UserImageAnswerDTO
import hu.mondo.quest.backend.models.entities.user.QuestUser
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class ImageAnswer(
    @Lob
    var imageFile: ByteArray = byteArrayOf(),

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "questuser_id", referencedColumnName = "questUserId")
    var user: QuestUser? = null,
    var imageAnswerState: ImageAnswerState = ImageAnswerState.PENDING,
    var questionGroupType: QuestionGroupType? = null
) : Answer() {
    override fun mapToImageAnswerDTO(): ImageAnswerDTO {
        return ImageAnswerDTO(
            answerId = answerId!!,
            title = question!!.title,
            description = question!!.description,
            imageAnswerState = imageAnswerState,
            questionGroupType = questionGroupType!!,
            imageFile = imageFile
        )
    }

    override fun mapToUserImageAnswerDTO(): UserImageAnswerDTO {
        return UserImageAnswerDTO(
            title = question!!.title,
            description = question!!.description,
            imageFile = imageFile,
            imageAnswerState = imageAnswerState,
            questionGroupType = questionGroupType!!
        )
    }

    override fun maptoSharedImageAnswerDTO(): SharedImageAnswerDTO {
        return SharedImageAnswerDTO(
            title = question!!.title,
            description = question!!.description,
            imageFile = imageFile
        )
    }
}

enum class ImageAnswerState{
    PENDING,
    APPROVED,
    REJECTED
}

enum class QuestionGroupType{
    STORY,
    INFINITE
}