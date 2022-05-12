package com.ead.course.services.impl

import com.ead.course.models.ModuleModel
import com.ead.course.repositories.ModuleRepository
import com.ead.course.services.ModuleService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.*

@Service
class ModuleServiceImpl(private val moduleRepository: ModuleRepository) : ModuleService {
    override fun delete(moduleModel: ModuleModel) {
        TODO("Not yet implemented")
    }

    override fun save(moduleModel: ModuleModel): ModuleModel {
        TODO("Not yet implemented")
    }

    override fun findModuleIntoCourse(courseId: UUID, moduleId: UUID): Optional<ModuleModel> {
        TODO("Not yet implemented")
    }

    override fun findAllByCourse(courseId: UUID): List<ModuleModel> {
        TODO("Not yet implemented")
    }

    override fun findById(moduleId: UUID): Optional<ModuleModel> {
        TODO("Not yet implemented")
    }

    override fun findAllByCourse(spec: Specification<ModuleModel>, pageable: Pageable): Page<ModuleModel> {
        TODO("Not yet implemented")
    }
}