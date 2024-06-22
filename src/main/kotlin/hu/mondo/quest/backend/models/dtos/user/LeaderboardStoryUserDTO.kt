package hu.mondo.quest.backend.models.dtos.user

import hu.mondo.quest.backend.models.entities.user.QuestUser
import java.util.*

data class LeaderboardStoryUserDTO(
    val username: String,
    val score: Double,
    val startTime: Date?,
    val finishTime: Date?
){
    fun mapFromUser(user: QuestUser): LeaderboardStoryUserDTO {
        return LeaderboardStoryUserDTO(user.username, user.storyPoints , user.questStartDate, user.questEndDate)
    }
}