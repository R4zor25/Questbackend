package hu.mondo.quest.backend.repositories

import hu.mondo.quest.backend.models.entities.question.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface QuestionRepository : JpaRepository<Question, Long> {
    @Query("SELECT q.id FROM Question q")
    fun findAllIds(): List<Long>
}