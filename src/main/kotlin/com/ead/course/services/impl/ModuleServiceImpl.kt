package com.ead.course.services.impl

import com.ead.course.models.LessonModel
import com.ead.course.models.ModuleModel
import com.ead.course.repositories.LessonRepository
import com.ead.course.repositories.ModuleRepository
import com.ead.course.services.ModuleService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional


@Service
class ModuleServiceImpl(
    private val moduleRepository: ModuleRepository,
    private val lessonRepository: LessonRepository
) : ModuleService {

    @Transactional
    override fun delete(moduleModel: ModuleModel) {
        val lessonModelList: List<LessonModel> = lessonRepository.findAllLessonsIntoModule(moduleModel.moduleId!!)
        if (lessonModelList.isNotEmpty()) {
            lessonRepository.deleteAll(lessonModelList)
        }
        moduleRepository.delete(moduleModel)
    }

    override fun save(moduleModel: ModuleModel): ModuleModel {
        return moduleRepository.save(moduleModel)
    }

    override fun findModuleIntoCourse(courseId: UUID, moduleId: UUID): Optional<ModuleModel> {
        return moduleRepository.findModuleIntoCourse(courseId, moduleId)
    }

    override fun findAllByCourse(courseId: UUID): List<ModuleModel> {
        return moduleRepository.findAllLModulesIntoCourse(courseId)
    }

    override fun findById(moduleId: UUID): Optional<ModuleModel> {
        return moduleRepository.findById(moduleId)
    }

    override fun findAllByCourse(spec: Specification<ModuleModel>, pageable: Pageable): Page<ModuleModel> {
        return moduleRepository.findAll(spec, pageable)
    }
}