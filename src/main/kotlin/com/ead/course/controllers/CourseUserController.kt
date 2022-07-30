package com.ead.course.controllers

import com.ead.course.configs.EadLog
import com.ead.course.dtos.SubscriptionDto
import com.ead.course.services.CourseService
import com.ead.course.services.UserService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid


@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
class CourseUserController(
    private val courseService: CourseService,
    private val userService: UserService
): EadLog {

    @GetMapping("/courses/{courseId}/users")
    fun getAllUsersByCourse(
        @PageableDefault(page = 0, size = 10, sort = ["userId"], direction = Sort.Direction.ASC) pageable: Pageable,
        @PathVariable(value = "courseId") courseId: UUID
    ): ResponseEntity<Any> {

        val courseModelOptional = courseService.findById(courseId)

        return if (!courseModelOptional.isPresent) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.")
        } else ResponseEntity.status(HttpStatus.OK).body("")
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    fun saveSubscriptionUserInCourse(
        @PathVariable(value = "courseId") courseId: UUID,
        @RequestBody subscriptionDto: @Valid SubscriptionDto
    ): ResponseEntity<Any> {

        val courseModelOptional = courseService.findById(courseId)
        if (!courseModelOptional.isPresent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.")
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("")
    }
}