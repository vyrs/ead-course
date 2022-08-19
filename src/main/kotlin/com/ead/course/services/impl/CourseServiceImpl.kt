package com.ead.course.services.impl

import com.ead.course.configs.EadLog
import com.ead.course.configs.log
import com.ead.course.dtos.NotificationCommandDto
import com.ead.course.models.CourseModel
import com.ead.course.models.UserModel
import com.ead.course.publishers.NotificationCommandPublisher
import com.ead.course.repositories.CourseRepository
import com.ead.course.repositories.LessonRepository
import com.ead.course.repositories.ModuleRepository
import com.ead.course.repositories.UserRepository
import com.ead.course.services.CourseService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional


@Service
class CourseServiceImpl(
    private val courseRepository: CourseRepository,
    private val moduleRepository: ModuleRepository,
    private val lessonRepository: LessonRepository,
    private val notificationCommandPublisher: NotificationCommandPublisher
) : CourseService, EadLog {

    @Transactional
    override fun delete(courseModel: CourseModel) {
        val moduleModelList = moduleRepository.findAllLModulesIntoCourse(courseModel.courseId!!)
        if (moduleModelList.isNotEmpty()) {
            moduleModelList.forEach {
                val lessonModelList = lessonRepository.findAllLessonsIntoModule(it.moduleId!!)
                if (lessonModelList.isNotEmpty()) {
                    lessonRepository.deleteAll(lessonModelList)
                }
            }
            moduleRepository.deleteAll(moduleModelList)
        }
        courseRepository.deleteCourseUserByCourse(courseModel.courseId!!)
        courseRepository.delete(courseModel)
    }

    override fun save(courseModel: CourseModel): CourseModel {
        return courseRepository.save(courseModel)
    }

    override fun findById(courseId: UUID): Optional<CourseModel> {
        return courseRepository.findById(courseId)
    }

    override fun findAll(spec: Specification<CourseModel>?, pageable: Pageable): Page<CourseModel> {
        return courseRepository.findAll(spec, pageable)
    }

    override fun existsByCourseAndUser(courseId: UUID, userId: UUID): Boolean {
        return courseRepository.existsByCourseAndUser(courseId, userId)
    }

    @Transactional
    override fun saveSubscriptionUserInCourse(courseId: UUID, userId: UUID) {
        courseRepository.saveCourseUser(courseId, userId)
    }

    @Transactional
    override fun saveSubscriptionUserInCourseAndSendNotification(course: CourseModel, user: UserModel) {
        courseRepository.saveCourseUser(course.courseId!!, user.userId)
        try {
            val notificationCommandDto = NotificationCommandDto(
                title = "Bem-Vindo(a) ao Curso: " + course.name,
                message = user.fullName + " a sua inscrição foi realizada com sucesso!",
                userId = user.userId
            )

            notificationCommandPublisher.publishNotificationCommand(notificationCommandDto)
        } catch (e: Exception) {
            log().warn("Error sending notification!")
        }
    }
}
