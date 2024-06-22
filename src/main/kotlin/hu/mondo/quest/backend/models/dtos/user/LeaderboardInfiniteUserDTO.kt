package hu.mondo.quest.backend.models.dtos.user

import hu.mondo.quest.backend.models.entities.user.QuestUser

data class LeaderboardInfiniteUserDTO(
    val username: String,
    val score: Double
){
    fun mapFromUser(user: QuestUser): LeaderboardInfiniteUserDTO {
        return LeaderboardInfiniteUserDTO(user.username, user.infinitePoints)
    }
}