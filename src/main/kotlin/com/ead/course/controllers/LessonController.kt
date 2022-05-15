package com.ead.course.controllers

import com.ead.course.dtos.LessonDto
import com.ead.course.dtos.toModel
import com.ead.course.models.LessonModel
import com.ead.course.services.LessonService
import com.ead.course.services.ModuleService
import com.ead.course.specifications.SpecificationTemplate.LessonSpec
import com.ead.course.specifications.SpecificationTemplate.lessonModuleId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid


@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
class LessonController(
    private val lessonService: LessonService,
    private val moduleService: ModuleService
) {
    
    @PostMapping("/modules/{moduleId}/lessons")
    fun saveLesson(
        @PathVariable(value = "moduleId") moduleId: UUID,
        @RequestBody @Valid lessonDto: LessonDto
    ): ResponseEntity<Any> {
        val moduleModelOptional = moduleService.findById(moduleId)
        if (!moduleModelOptional.isPresent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module Not Found.")
        }
        val lessonModel = lessonDto.toModel(moduleModelOptional.get())
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.save(lessonModel))
    }

    @DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
    fun deleteLesson(
        @PathVariable(value = "moduleId") moduleId: UUID,
        @PathVariable(value = "lessonId") lessonId: UUID
    ): ResponseEntity<Any> {
        val lessonModelOptional = lessonService.findLessonIntoModule(
            moduleId,
            lessonId
        )
        if (!lessonModelOptional.isPresent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.")
        }
        lessonService.delete(lessonModelOptional.get())
        return ResponseEntity.status(HttpStatus.OK).body("Lesson deleted successfully.")
    }

    @PutMapping("/modules/{moduleId}/lessons/{lessonId}")
    fun updateLesson(
        @PathVariable(value = "moduleId") moduleId: UUID,
        @PathVariable(value = "lessonId") lessonId: UUID,
        @RequestBody @Valid lessonDto: LessonDto
    ): ResponseEntity<Any> {
        val lessonModelOptional = lessonService.findLessonIntoModule(
            moduleId,
            lessonId
        )
        if (!lessonModelOptional.isPresent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.")
        }
        val lessonModel = lessonModelOptional.get()
        lessonModel.title = lessonDto.title
        lessonModel.description = lessonDto.description
        lessonModel.videoUrl = lessonDto.videoUrl

        return ResponseEntity.status(HttpStatus.OK).body(lessonService.save(lessonModel))
    }

    @GetMapping("/modules/{moduleId}/lessons")
    fun getAllLessons(
        @PathVariable(value = "moduleId") moduleId: UUID,
        spec: LessonSpec?,
        @PageableDefault(page = 0, size = 10, sort = ["lessonId"], direction = Sort.Direction.ASC) pageable: Pageable
    ): ResponseEntity<Page<LessonModel>> {
        return ResponseEntity.status(HttpStatus.OK).body(
            lessonService.findAllByModule(lessonModuleId(moduleId).and(spec), pageable)
        )
    }

    @GetMapping("/modules/{moduleId}/lessons/{lessonId}")
    fun getOneLesson(
        @PathVariable(value = "moduleId") moduleId: UUID,
        @PathVariable(value = "lessonId") lessonId: UUID
    ): ResponseEntity<Any> {
        val lessonModelOptional = lessonService.findLessonIntoModule(
            moduleId,
            lessonId
        )
        return if (!lessonModelOptional.isPresent) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.")
        } else ResponseEntity.status(HttpStatus.OK).body(lessonModelOptional.get())
    }
}
