package com.ead.course.dtos

import com.ead.course.enums.UserStatus
import com.ead.course.enums.UserType
import java.util.*


data class UserDto(
    val userId: UUID,
    val username: String,
    val email: String,
    val fullName: String,
    val userStatus: UserStatus,
    val userType: UserType,
    val phoneNumber: String,
    val cpf: String,
    val imageUrl: String?
)
