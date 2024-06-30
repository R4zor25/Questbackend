package hu.mondo.quest.backend.services.user

import hu.mondo.quest.backend.models.dtos.user.AuthDTO
import hu.mondo.quest.backend.models.dtos.user.LeaderboardInfiniteUserDTO
import hu.mondo.quest.backend.models.dtos.user.LeaderboardStoryUserDTO
import hu.mondo.quest.backend.models.entities.user.QuestUser
import hu.mondo.quest.backend.models.entities.user.Role
import hu.mondo.quest.backend.repositories.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    override fun getInfiniteLeaderboard(): List<LeaderboardInfiniteUserDTO> {
        var leaderboardUserList = userRepository.findAll()
        leaderboardUserList = leaderboardUserList.filter  { it.role != Role.ADMIN }
        leaderboardUserList.sortByDescending { it.infinitePoints }
        return leaderboardUserList.map { LeaderboardInfiniteUserDTO(it.username, it.infinitePoints) }
    }

    override fun getStoryLeaderboard(): List<LeaderboardStoryUserDTO> {
        var leaderboardUserList = userRepository.findAll()
        leaderboardUserList = leaderboardUserList.filter  { it.role != Role.ADMIN }
        leaderboardUserList.sortByDescending { it.storyPoints }
        return leaderboardUserList.map {LeaderboardStoryUserDTO(it.username, it.storyPoints , it.questStartDate, it.questEndDate)}
    }

    override fun registerUser(authDTO: AuthDTO): QuestUser {
        val encodedPassword = passwordEncoder.encode(authDTO.password)
        val questUser = QuestUser(
            username = authDTO.username,
            password = encodedPassword,
            role = Role.USER)
        return userRepository.save(questUser)
    }

    override fun loginUser(authDTO: AuthDTO): QuestUser {
        val user = userRepository.findByUsername(authDTO.username)

        if (user != null && passwordEncoder.matches(authDTO.password, user.password)) {
            return user
        }
        throw Exception("Username or password is incorrect")
    }

    override fun findByUsername(username: String): QuestUser? {
        return userRepository.findByUsername(username)
    }
}