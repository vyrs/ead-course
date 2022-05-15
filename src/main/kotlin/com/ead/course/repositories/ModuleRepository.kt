package com.ead.course.repositories

import com.ead.course.models.CourseModel
import com.ead.course.models.ModuleModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*


interface ModuleRepository: JpaRepository<ModuleModel, UUID>, JpaSpecificationExecutor<ModuleModel>{
    @Query(value = "select * from tb_modules where course_course_id = :courseId", nativeQuery = true)
    fun findAllLModulesIntoCourse(@Param("courseId") courseId: UUID): List<ModuleModel>

    @Query(
        value = "select * from tb_modules where course_course_id = :courseId and module_id = :moduleId",
        nativeQuery = true
    )
    fun findModuleIntoCourse(
        @Param("courseId") courseId: UUID,
        @Param("moduleId") moduleId: UUID
    ): Optional<ModuleModel>
}