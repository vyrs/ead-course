package com.ead.course.dtos

import java.util.UUID

data class CourseUserDto(
    val userId: UUID,
    val courseId: UUID
)
