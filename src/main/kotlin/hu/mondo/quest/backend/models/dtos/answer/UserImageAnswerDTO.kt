package hu.mondo.quest.backend.models.dtos.answer

import hu.mondo.quest.backend.models.entities.answer.ImageAnswerState
import hu.mondo.quest.backend.models.entities.answer.QuestionGroupType

data class UserImageAnswerDTO(
    var title: String,
    var description: String,
    var imageAnswerState: ImageAnswerState,
    var questionGroupType: QuestionGroupType,
    var imageFile: ByteArray
)