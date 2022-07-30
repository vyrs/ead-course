package com.ead.course.validation

import com.ead.course.dtos.CourseDto
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator
import java.util.*


@Component
class CourseValidator(private val validator: Validator): Validator {
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
//        val responseUserInstructor: ResponseEntity<UserDto>
//        try {
//            responseUserInstructor = authUserClient.getOneUserById(userInstructor)
//            if (responseUserInstructor.body!!.userType == UserType.STUDENT) {
//                errors.rejectValue("userInstructor", "UserInstructorError", "User must be INSTRUCTOR or ADMIN.")
//            }
//        } catch (e: HttpStatusCodeException) {
//            if (e.statusCode == HttpStatus.NOT_FOUND) {
//                errors.rejectValue("userInstructor", "UserInstructorNotFoundError", "Instructor not found.")
//            }
//        }
    }
}