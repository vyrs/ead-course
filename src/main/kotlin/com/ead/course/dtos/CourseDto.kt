package com.ead.course.dtos

import com.ead.course.enums.CourseLevel
import com.ead.course.enums.CourseStatus
import com.ead.course.models.CourseModel
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CourseDto(
    @field:NotBlank
    val name: String,
    @field:NotBlank
    val description: String,
    val imageUrl: String,
    @field:NotNull
    val courseStatus: CourseStatus,
    @field:NotNull
    val userInstructor: UUID,
    @field:NotNull
    val courseLevel: CourseLevel
)

fun CourseDto.toModel() =
    CourseModel(
        name,
        description,
        imageUrl,
        LocalDateTime.now(ZoneId.of("UTC")),
        LocalDateTime.now(ZoneId.of("UTC")),
        courseStatus,
        courseLevel,
        userInstructor
    )
