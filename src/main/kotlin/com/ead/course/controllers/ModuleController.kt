package com.ead.course.controllers

import com.ead.course.dtos.ModuleDto
import com.ead.course.dtos.toModel
import com.ead.course.models.ModuleModel
import com.ead.course.services.CourseService
import com.ead.course.services.ModuleService
import com.ead.course.specifications.SpecificationTemplate.ModuleSpec
import com.ead.course.specifications.SpecificationTemplate.moduleCourseId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID
import javax.validation.Valid


@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
class ModuleController(
    private val moduleService: ModuleService,
    private val courseService: CourseService
) {

    @PostMapping("/courses/{courseId}/modules")
    fun saveModule(
        @PathVariable(value = "courseId") courseId: UUID,
        @RequestBody @Valid moduleDto: ModuleDto
    ): ResponseEntity<Any> {
        val courseModelOptional = courseService.findById(courseId)
        if (!courseModelOptional.isPresent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.")
        }

        val moduleModel = moduleDto.toModel(courseModelOptional.get())

        return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.save(moduleModel))
    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    fun deleteModule(
        @PathVariable(value = "courseId") courseId: UUID,
        @PathVariable(value = "moduleId") moduleId: UUID
    ): ResponseEntity<Any> {
        val moduleModelOptional = moduleService.findModuleIntoCourse(
            courseId,
            moduleId
        )
        if (!moduleModelOptional.isPresent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course.")
        }
        moduleService.delete(moduleModelOptional.get())
        return ResponseEntity.status(HttpStatus.OK).body("Module deleted successfully.")
    }

    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    fun updateModule(
        @PathVariable(value = "courseId") courseId: UUID,
        @PathVariable(value = "moduleId") moduleId: UUID,
        @RequestBody @Valid moduleDto: ModuleDto
    ): ResponseEntity<Any> {
        val moduleModelOptional = moduleService.findModuleIntoCourse(
            courseId,
            moduleId
        )
        if (!moduleModelOptional.isPresent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course.")
        }
        val moduleModel = moduleModelOptional.get()
        moduleModel.title = moduleDto.title
        moduleModel.description = moduleDto.description
        return ResponseEntity.status(HttpStatus.OK).body(moduleService.save(moduleModel))
    }

    @GetMapping("/courses/{courseId}/modules")
    fun getAllModules(
        @PathVariable(value = "courseId") courseId: UUID,
        spec: ModuleSpec?,
        @PageableDefault(page = 0, size = 10, sort = ["moduleId"], direction = Sort.Direction.ASC) pageable: Pageable
    ): ResponseEntity<Page<ModuleModel>> {
        return ResponseEntity.status(HttpStatus.OK).body(
            moduleService.findAllByCourse(moduleCourseId(courseId).and(spec), pageable)
        )
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    fun getOneModule(
        @PathVariable(value = "courseId") courseId: UUID,
        @PathVariable(value = "moduleId") moduleId: UUID
    ): ResponseEntity<Any> {
        val moduleModelOptional = moduleService.findModuleIntoCourse(
            courseId,
            moduleId
        )
        return if (!moduleModelOptional.isPresent) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course.")
        } else ResponseEntity.status(HttpStatus.OK).body(moduleModelOptional.get())
    }
}
