package com.ead.course.services.impl

import com.ead.course.models.LessonModel
import com.ead.course.repositories.LessonRepository
import com.ead.course.services.LessonService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.*

@Service
class LessonServiceImpl(private val lessonRepository: LessonRepository) : LessonService {
    override fun save(lessonModel: LessonModel): LessonModel {
        return lessonRepository.save(lessonModel)
    }

    override fun findLessonIntoModule(moduleId: UUID, lessonId: UUID): Optional<LessonModel> {
        return lessonRepository.findLessonIntoModule(moduleId, lessonId)
    }

    override fun delete(lessonModel: LessonModel) {
        lessonRepository.delete(lessonModel)
    }

    override fun findAllByModule(moduleId: UUID): List<LessonModel> {
        return lessonRepository.findAllLessonsIntoModule(moduleId)
    }

    override fun findAllByModule(spec: Specification<LessonModel>, pageable: Pageable): Page<LessonModel> {
        return lessonRepository.findAll(spec, pageable)
    }
}