package hu.mondo.quest.backend.models.entities.user

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonFormat
import hu.mondo.quest.backend.models.entities.question.Question
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import java.io.Serializable
import java.util.Date


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class QuestUser(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var questUserId: Long? = null,

    @Column(unique = true)
    var username: String = "",

    var password: String = "",

    var infinitePoints: Double = 0.0,

    var storyPoints: Double = 0.0,

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var answeredInfiniteQuestions: MutableCollection<Question> = mutableListOf(),

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var answeredStoryQuestions: MutableCollection<Question> = mutableListOf(),

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "current_story_question_id")
    var currentStoryQuestion: Question? = null,

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "current_infinite_question_id")
    var currentInfiniteQuestion: Question? = null,

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    var creationDate : Date = Date(),

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    var questStartDate : Date? = null,

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    var questEndDate : Date? = null,

    @Enumerated(EnumType.STRING)
    val role: Role,

    @ManyToMany(cascade = [CascadeType.ALL])
    var ratedQuestions: MutableList<Question> = mutableListOf()

) : Serializable {
}

enum class Role(val value: String) {
    USER("USER"),
    ADMIN("ADMIN")
}