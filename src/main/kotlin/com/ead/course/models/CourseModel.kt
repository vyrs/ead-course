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
    var name: String,

    @Column(nullable = false, length = 250)
    var description: String,

    @Column
    var imageUrl: String?,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Column(nullable = false)
    private val creationDate: LocalDateTime,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Column(nullable = false)
    var lastUpdateDate: LocalDateTime,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var courseStatus: CourseStatus,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var courseLevel: CourseLevel,

    @Column(nullable = false)
    val userInstructor: UUID,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    var modules: Set<ModuleModel>? = null,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    val coursesUsers: Set<CourseUserModel>? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var courseId: UUID? = null
}
