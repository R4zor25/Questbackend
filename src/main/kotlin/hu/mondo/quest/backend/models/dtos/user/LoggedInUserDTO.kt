package hu.mondo.quest.backend.models.dtos.user

data class LoggedInUserDTO(
    val userId: Long,
    val username: String,
    val points: Double,
    val role: String,
    val hasStartedStory: Boolean
)