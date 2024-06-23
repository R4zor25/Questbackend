package hu.mondo.quest.backend.repositories

import hu.mondo.quest.backend.models.entities.answer.Answer
import hu.mondo.quest.backend.models.entities.answer.ImageAnswer
import hu.mondo.quest.backend.models.entities.answer.ImageAnswerState
import hu.mondo.quest.backend.models.entities.answer.QuestionGroupType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AnswerRepository : JpaRepository<Answer, Long> {
    // GET 10 LATEST APPROVED ANSWERS
    @Query("Select a from Answer as a where a.imageAnswerState = 1  Order by a.id desc limit 10") //TODO check ON THESE
    fun get10LatestApprovedAnswers(): List<ImageAnswer>
    // GET ALL ANSWERS BY ANSWER STATE
    @Query("Select a from Answer as a where a.imageAnswerState = :imageAnswerState")
    fun findAllImageAnswersByImageAnswerState(imageAnswerState: ImageAnswerState): List<ImageAnswer>
    // GET ALL ANSWERS BY ANSWER STATE AND GROUP TYPE
    //@Query("Select a from Answer where a.imageAnswerState = :imageAnswerState and a.questionGroupType = :groupType")
    //fun findAllImageAnswersByQuestionGroupTypeAndImageAnswerState(groupType: QuestionGroupType, imageAnswerState: ImageAnswerState): List<ImageAnswer>
    // GET ALL ANSWERS BY QUEST USER ID
    @Query("Select a from Answer as a where a.user.questUserId = :questUserId")
    fun findAllImageAnswersByQuestUserId(questUserId: Long): List<ImageAnswer>
}