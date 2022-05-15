package com.ead.course.dtos

import com.ead.course.models.CourseModel
import com.ead.course.models.ModuleModel
import java.time.LocalDateTime
import java.time.ZoneId
import javax.validation.constraints.NotBlank

data class ModuleDto(
    @field:NotBlank
    val title: String,
    @field:NotBlank
    val description: String
)

fun ModuleDto.toModel(courseModel: CourseModel) =
    ModuleModel(
        title,
        description,
        LocalDateTime.now(ZoneId.of("UTC")),
        courseModel
    )
