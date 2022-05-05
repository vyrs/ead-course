package com.ead.course.models

import com.ead.course.enums.CourseLevel
import com.ead.course.enums.CourseStatus
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_COURSES")
class CourseModel(
    @Column(nullable = false, length = 150)
    private val name: String? = null,

    @Column(nullable = false, length = 250)
    private val description: String? = null,

    @Column
    private val imageUrl: String? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Column(nullable = false)
    private val creationDate: LocalDateTime? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Column(nullable = false)
    private val lastUpdateDate: LocalDateTime? = null,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private val courseStatus: CourseStatus? = null,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private val courseLevel: CourseLevel? = null,

    @Column(nullable = false)
    private val userInstructor: UUID? = null,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private val modules: Set<ModuleModel>? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val courseId: UUID? = null
}
