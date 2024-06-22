package hu.mondo.quest.backend.models.dtos.answer

import hu.mondo.quest.backend.models.dtos.question.QuestionDTO
import hu.mondo.quest.backend.models.entities.answer.ImageAnswerState
import hu.mondo.quest.backend.models.entities.answer.QuestionGroupType

data class ImageAnswerDTO(
    var answerId: Long,
    var title: String,
    var description: String,
    var user: Long,
    var imageAnswerState: ImageAnswerState,
    var questionGroupType: QuestionGroupType,
    var imageFile: ByteArray
) {

}