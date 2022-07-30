package com.ead.course.services

import com.ead.course.models.CourseModel
import com.ead.course.models.CourseUserModel
import java.util.*


interface CourseUserService {
    fun existsByCourseAndUserId(courseModel: CourseModel, userId: UUID): Boolean

    fun save(courseUserModel: CourseUserModel): CourseUserModel

    fun saveAndSendSubscriptionUserInCourse(courseUserModel: CourseUserModel): CourseUserModel

    fun existsByUserId(userId: UUID): Boolean

    fun deleteCourseUserByUser(userId: UUID)
}