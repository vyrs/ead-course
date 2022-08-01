package com.ead.course.dtos

import com.ead.course.models.UserModel
import java.util.*

data class UserEventDto(
    val userId: UUID,
    val username: String,
    val email: String,
    val fullName: String,
    val userStatus: String,
    val userType: String,
    val phoneNumber: String,
    val cpf: String,
    val imageUrl: String?,
    val actionType: String
)

fun UserEventDto.convertToUserModel(): UserModel =
    UserModel(
        userId,
        email,
        fullName,
        userStatus,
        userType,
        cpf,
        imageUrl
    )
