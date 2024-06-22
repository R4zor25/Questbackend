package hu.mondo.quest.backend.repositories

import hu.mondo.quest.backend.models.entities.user.QuestUser
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<QuestUser, Long> {
    fun findByUsername(username: String): QuestUser?
}