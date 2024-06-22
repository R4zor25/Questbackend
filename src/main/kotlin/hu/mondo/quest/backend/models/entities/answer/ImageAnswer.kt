package hu.mondo.quest.backend.models.entities.answer

import com.fasterxml.jackson.annotation.JsonBackReference
import hu.mondo.quest.backend.models.dtos.answer.ImageAnswerDTO
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
    val imageFile: ByteArray = byteArrayOf(),

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "questuser_id", referencedColumnName = "questUserId")
    var user: QuestUser,
    var imageAnswerState: ImageAnswerState = ImageAnswerState.PENDING,
    var questionGroupType: QuestionGroupType
) : Answer() {
    override fun mapToImageAnswerDTO(): ImageAnswerDTO {
        return ImageAnswerDTO(
            title = question!!.title,
            description = question!!.description,
            user = user.questUserId!!,
            imageAnswerState = imageAnswerState,
            questionGroupType = questionGroupType,
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