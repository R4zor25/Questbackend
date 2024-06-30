package hu.mondo.quest.backend.models.dtos.answer


data class SharedImageAnswerDTO(
    var title: String,
    var description: String,
    var imageFile: ByteArray
)