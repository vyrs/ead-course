package com.ead.course.dtos

import java.util.UUID
import javax.validation.constraints.NotBlank

data class SubscriptionDto(
    @field:NotBlank
    val userId: UUID
)
