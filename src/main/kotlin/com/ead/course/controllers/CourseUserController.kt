package com.ead.course.controllers

import com.ead.course.clients.AuthUserClient
import com.ead.course.configs.EadLog
import com.ead.course.dtos.SubscriptionDto
import com.ead.course.dtos.UserDto
import com.ead.course.enums.UserStatus
import com.ead.course.services.CourseService
import com.ead.course.services.CourseUserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpStatusCodeException
import java.util.*
import javax.validation.Valid

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
class CourseUserController(
    private val authUserClient: AuthUserClient,
    private val courseService: CourseService,
    private val courseUserService: CourseUserService
): EadLog {

    @GetMapping("/courses/{courseId}/users")
    fun getAllUsersByCourse(
        @PageableDefault(page = 0, size = 10, sort = ["userId"], direction = Sort.Direction.ASC) pageable: Pageable,
        @PathVariable(value = "courseId") courseId: UUID
    ): ResponseEntity<Page<UserDto>> {
        return ResponseEntity.status(HttpStatus.OK).body(authUserClient.getAllUsersByCourse(courseId, pageable))
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    fun saveSubscriptionUserInCourse(
        @PathVariable(value = "courseId") courseId: UUID,
        @RequestBody subscriptionDto: @Valid SubscriptionDto
    ): ResponseEntity<Any> {
        val responseUser: ResponseEntity<UserDto>
        val courseModelOptional = courseService.findById(courseId)
        if (!courseModelOptional.isPresent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.")
        }
        if (courseUserService.existsByCourseAndUserId(courseModelOptional.get(), subscriptionDto.userId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: subscription already exists!")
        }
        try {
            responseUser = authUserClient.getOneUserById(subscriptionDto.userId)
            if (responseUser.body!!.userStatus == UserStatus.BLOCKED) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User is blocked.")
            }
        } catch (e: HttpStatusCodeException) {
            if (e.statusCode == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.")
            }
        }
        val courseUserModel = courseUserService.saveAndSendSubscriptionUserInCourse(
            courseModelOptional.get().convertToCourseUserModel(subscriptionDto.userId)
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(courseUserModel)
    }
}