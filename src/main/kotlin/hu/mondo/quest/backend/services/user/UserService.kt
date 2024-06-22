package hu.mondo.quest.backend.services.user

import hu.mondo.quest.backend.models.dtos.user.AuthDTO
import hu.mondo.quest.backend.models.dtos.user.LeaderboardInfiniteUserDTO
import hu.mondo.quest.backend.models.dtos.user.LeaderboardStoryUserDTO
import hu.mondo.quest.backend.models.entities.user.QuestUser

interface UserService {
    fun getInfiniteLeaderboard(): List<LeaderboardInfiniteUserDTO>
    fun getStoryLeaderboard(): List<LeaderboardStoryUserDTO>
    fun registerUser(authDTO: AuthDTO): QuestUser
    fun loginUser(authDTO: AuthDTO): QuestUser
    fun findByUsername(username: String): QuestUser?
}