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
        TODO("Not yet implemented")
    }

    override fun findLessonIntoModule(moduleId: UUID, lessonId: UUID): Optional<LessonModel> {
        TODO("Not yet implemented")
    }

    override fun delete(lessonModel: LessonModel) {
        TODO("Not yet implemented")
    }

    override fun findAllByModule(moduleId: UUID): List<LessonModel> {
        TODO("Not yet implemented")
    }

    override fun findAllByModule(spec: Specification<LessonModel>, pageable: Pageable): Page<LessonModel> {
        TODO("Not yet implemented")
    }
}