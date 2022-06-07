package com.ead.course.models

import com.fasterxml.jackson.annotation.JsonInclude
import java.util.UUID
import javax.persistence.*


@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_COURSES_USERS")
class CourseUserModel(
    @Column(nullable = false)
    val userId: UUID,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val course: CourseModel? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null
}
