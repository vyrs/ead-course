package com.ead.course.repositories

import com.ead.course.models.LessonModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID
import java.util.Optional


interface LessonRepository: JpaRepository<LessonModel, UUID>, JpaSpecificationExecutor<LessonModel> {

    @Query(value = "select * from tb_lessons where module_module_id = :moduleId", nativeQuery = true)
    fun findAllLessonsIntoModule(@Param("moduleId") moduleId: UUID): List<LessonModel>

    @Query(
        value = "select * from tb_lessons where module_module_id = :moduleId and lesson_id = :lessonId",
        nativeQuery = true
    )
    fun findLessonIntoModule(
        @Param("moduleId") moduleId: UUID,
        @Param("lessonId") lessonId: UUID
    ): Optional<LessonModel>
}