package com.ead.course.dtos

import com.ead.course.models.LessonModel
import com.ead.course.models.ModuleModel
import java.time.LocalDateTime
import java.time.ZoneId
import javax.validation.constraints.NotBlank

data class LessonDto(
    @field:NotBlank
    val title: String,
    @field:NotBlank
    val description: String,
    @field:NotBlank
    val videoUrl: String
)

fun LessonDto.toModel(moduleModel: ModuleModel) =
    LessonModel(
        title,
        description,
        videoUrl,
        LocalDateTime.now(ZoneId.of("UTC")),
        moduleModel
    )