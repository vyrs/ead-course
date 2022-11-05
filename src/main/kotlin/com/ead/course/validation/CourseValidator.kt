package com.ead.course.validation

import com.ead.course.configs.security.AuthenticationCurrentUserService
import com.ead.course.dtos.CourseDto
import com.ead.course.enums.UserType
import com.ead.course.models.UserModel
import com.ead.course.services.UserService
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator
import org.springframework.security.access.AccessDeniedException
import java.util.UUID
import java.util.Optional


@Component
class CourseValidator(
    private val validator: Validator,
    private val userService: UserService,
    private val authenticationCurrentUserService: AuthenticationCurrentUserService
): Validator {
    override fun supports(clazz: Class<*>): Boolean {
        return false
    }

    override fun validate(target: Any, errors: Errors) {
        val courseDto = target as CourseDto
        validator.validate(courseDto, errors)
        if (!errors.hasErrors()) {
            validateUserInstructor(courseDto.userInstructor, errors)
        }
    }

    private fun validateUserInstructor(userInstructor: UUID, errors: Errors) {
        val currentUserId = authenticationCurrentUserService.currentUser.userId

        if(currentUserId == userInstructor) {
            val userModelOptional: Optional<UserModel> = userService.findById(userInstructor)
            if (!userModelOptional.isPresent) {
                errors.rejectValue("userInstructor", "UserInstructorError", "Instructor not found.")
            }
            if (userModelOptional.get().userType == UserType.STUDENT.toString()) {
                errors.rejectValue("userInstructor", "UserInstructorError", "User must be INSTRUCTOR or ADMIN.")
            }
        } else {
            throw AccessDeniedException("Forbidden")
        }
    }
}