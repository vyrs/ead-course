package com.ead.course.services

import com.ead.course.models.ModuleModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import java.util.UUID
import java.util.Optional


interface ModuleService {
    fun delete(moduleModel: ModuleModel)
    fun save(moduleModel: ModuleModel): ModuleModel
    fun findModuleIntoCourse(courseId: UUID, moduleId: UUID): Optional<ModuleModel>
    fun findAllByCourse(courseId: UUID): List<ModuleModel>
    fun findById(moduleId: UUID): Optional<ModuleModel>
    fun findAllByCourse(spec: Specification<ModuleModel>, pageable: Pageable): Page<ModuleModel>
}
