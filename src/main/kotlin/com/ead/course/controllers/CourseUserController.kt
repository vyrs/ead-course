package com.ead.course.controllers

import com.ead.course.configs.EadLog
import com.ead.course.dtos.SubscriptionDto
import com.ead.course.enums.UserStatus
import com.ead.course.services.CourseService
import com.ead.course.services.UserService
import com.ead.course.specifications.SpecificationTemplate
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid


@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
class CourseUserController(
    private val courseService: CourseService,
    private val userService: UserService
): EadLog {

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @GetMapping("/courses/{courseId}/users")
    fun getAllUsersByCourse(
        spec: SpecificationTemplate.UserSpec?,
        @PageableDefault(page = 0, size = 10, sort = ["userId"], direction = Sort.Direction.ASC) pageable: Pageable,
        @PathVariable(value = "courseId") courseId: UUID
    ): ResponseEntity<Any> {

        val courseModelOptional = courseService.findById(courseId)

        return if (!courseModelOptional.isPresent) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.")
        } else ResponseEntity.status(HttpStatus.OK).body(userService.findAll(SpecificationTemplate.userCourseId(courseId).and(spec), pageable))
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @PostMapping("/courses/{courseId}/users/subscription")
    fun saveSubscriptionUserInCourse(
        @PathVariable(value = "courseId") courseId: UUID,
        @RequestBody subscriptionDto: @Valid SubscriptionDto
    ): ResponseEntity<Any> {

        val courseModelOptional = courseService.findById(courseId)
        if (!courseModelOptional.isPresent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.")
        }

        if (courseService.existsByCourseAndUser(courseId, subscriptionDto.userId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: subscription already exists!")
        }
        val userModelOptional = userService.findById(subscriptionDto.userId)
        if (!userModelOptional.isPresent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.")
        }
        if (userModelOptional.get().userStatus == UserStatus.BLOCKED.toString()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User is blocked.")
        }
        courseService.saveSubscriptionUserInCourseAndSendNotification(
            courseModelOptional.get(),
            userModelOptional.get()
        )
        return ResponseEntity.status(HttpStatus.CREATED).body("Subscription created successfully.")
    }
}