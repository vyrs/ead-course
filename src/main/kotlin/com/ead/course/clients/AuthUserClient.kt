package com.ead.course.clients

import com.ead.course.configs.EadLog
import com.ead.course.configs.log
import com.ead.course.dtos.CourseUserDto
import com.ead.course.dtos.ResponsePageDto
import com.ead.course.dtos.UserDto
import com.ead.course.services.UtilsService
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import java.util.*


@Component
class AuthUserClient(private val restTemplate: RestTemplate, private val utilsService: UtilsService): EadLog {

    @Value("\${ead.api.url.authuser}")
    lateinit var REQUEST_URL_AUTHUSER: String

    fun getAllUsersByCourse(courseId: UUID, pageable: Pageable): Page<UserDto> {
        var result: ResponseEntity<ResponsePageDto<UserDto>>? = null
        val url = REQUEST_URL_AUTHUSER + utilsService.createUrlGetAllUsersByCourse(courseId, pageable)
        log().debug("Request URL: {} ", url)
        log().info("Request URL: {} ", url)
        try {
            val responseType: ParameterizedTypeReference<ResponsePageDto<UserDto>> =
                object : ParameterizedTypeReference<ResponsePageDto<UserDto>>() {}

            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType)

            val searchResult = result.body!!.content
            log().debug("Response Number of Elements: {} ", searchResult.size)
        } catch (e: HttpStatusCodeException) {
            log().error("Error request /courses {} ", e)
        }
        log().info("Ending request /users courseId {} ", courseId)
        return result!!.body!!
    }

    fun getOneUserById(userId: UUID): ResponseEntity<UserDto> {
        val url = "$REQUEST_URL_AUTHUSER/users/$userId"
        return restTemplate.exchange(url, HttpMethod.GET, null, UserDto::class.java)
    }

    fun postSubscriptionUserInCourse(courseId: UUID, userId: UUID) {
        val url = "$REQUEST_URL_AUTHUSER/users/$userId/courses/subscription"
        val courseUserDto = CourseUserDto(userId, courseId)

        restTemplate.postForObject(url, courseUserDto, String::class.java)
    }

    fun deleteCourseInAuthUser(courseId: UUID) {
        val url = "$REQUEST_URL_AUTHUSER/users/courses/$courseId"
        restTemplate.exchange(url, HttpMethod.DELETE, null, String::class.java)
    }
}