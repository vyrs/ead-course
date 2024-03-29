package com.ead.course.services.impl

import com.ead.course.clients.AuthUserClient
import com.ead.course.models.CourseModel
import com.ead.course.models.CourseUserModel
import com.ead.course.repositories.CourseUserRepository
import com.ead.course.services.CourseUserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
class CourseUserServiceImpl(
    private val courseUserRepository: CourseUserRepository,
    private val authUserClient: AuthUserClient
) : CourseUserService {

    override fun existsByCourseAndUserId(courseModel: CourseModel, userId: UUID): Boolean {
        return courseUserRepository.existsByCourseAndUserId(courseModel, userId)
    }

    override fun save(courseUserModel: CourseUserModel): CourseUserModel {
        return courseUserRepository.save(courseUserModel)
    }

    @Transactional
    override fun saveAndSendSubscriptionUserInCourse(courseUserModel: CourseUserModel): CourseUserModel {
        authUserClient.postSubscriptionUserInCourse(courseUserModel.course.courseId!!, courseUserModel.userId)
        return courseUserRepository.save(courseUserModel)
    }

    override fun existsByUserId(userId: UUID): Boolean {
        return courseUserRepository.existsByUserId(userId)
    }

    @Transactional
    override fun deleteCourseUserByUser(userId: UUID) {
        courseUserRepository.deleteAllByUserId(userId)
    }
}