package com.ead.course.services.impl

import com.ead.course.models.CourseModel
import com.ead.course.models.CourseUserModel
import com.ead.course.repositories.CourseUserRepository
import com.ead.course.services.CourseUserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
class CourseUserServiceImpl(private val courseUserRepository: CourseUserRepository) : CourseUserService {

    override fun existsByCourseAndUserId(courseModel: CourseModel, userId: UUID): Boolean {
        return courseUserRepository.existsByCourseAndUserId(courseModel, userId)
    }

    override fun save(courseUserModel: CourseUserModel): CourseUserModel {
        return courseUserRepository.save(courseUserModel)
    }

    @Transactional
    override fun saveAndSendSubscriptionUserInCourse(courseUserModel: CourseUserModel): CourseUserModel {
        var courseUserModel = courseUserModel
        courseUserModel = courseUserRepository.save(courseUserModel)
        return courseUserModel
    }
}