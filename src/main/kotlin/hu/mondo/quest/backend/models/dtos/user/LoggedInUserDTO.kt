package hu.mondo.quest.backend.models.dtos.user

data class LoggedInUserDTO(
    val userId: Long,
    val username: String,
    val storyPoints: Double,
    val infinitePoints: Double,
    val role: String,
    val finishedStory: Boolean,
    val finishedInfinite: Boolean
)