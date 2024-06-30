package hu.mondo.quest.backend.models.dtos.answer

data class CreateImageAnswerDTO(
    val imageFile: ByteArray = byteArrayOf()
) {
}