package com.ead.course.controllers

import com.ead.course.dtos.CourseDto
import com.ead.course.dtos.toModel
import com.ead.course.models.CourseModel
import com.ead.course.services.CourseService
import com.ead.course.specifications.SpecificationTemplate.CourseSpec
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.validation.Valid


@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = ["*"], maxAge = 3600)
class CourseController(private val courseService: CourseService) {

    @PostMapping
    fun saveCourse(@RequestBody @Valid courseDto: CourseDto): ResponseEntity<Any> {
        val courseModel = courseDto.toModel()

        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(courseModel))
    }

    @DeleteMapping("/{courseId}")
    fun deleteCourse(@PathVariable(value = "courseId") courseId: UUID?): ResponseEntity<Any> {
        val courseModelOptional = courseService.findById(courseId)
        if (!courseModelOptional.isPresent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.")
        }
        courseService.delete(courseModelOptional.get())
        return ResponseEntity.status(HttpStatus.OK).body("Course deleted successfully.")
    }

    @PutMapping("/{courseId}")
    fun updateCourse(
        @PathVariable(value = "courseId") courseId: UUID,
        @RequestBody @Valid courseDto: CourseDto
    ): ResponseEntity<Any> {
        val courseModelOptional = courseService.findById(courseId)
        if (!courseModelOptional.isPresent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.")
        }
        val courseModel = courseModelOptional.get()
        courseModel.name = courseDto.name
        courseModel.description = courseDto.description
        courseModel.imageUrl = courseDto.imageUrl
        courseModel.courseStatus = courseDto.courseStatus
        courseModel.courseLevel = courseDto.courseLevel
        courseModel.lastUpdateDate = LocalDateTime.now(ZoneId.of("UTC"))

        return ResponseEntity.status(HttpStatus.OK).body(courseService.save(courseModel))
    }

    @GetMapping
    fun getAllCourses(
        spec: CourseSpec?,
        @PageableDefault(page = 0, size = 10, sort = ["courseId"], direction = Sort.Direction.ASC) pageable: Pageable
    ): ResponseEntity<Page<CourseModel>> {
        return ResponseEntity.status(HttpStatus.OK).body(
            courseService.findAll(spec, pageable)
        )
    }

    @GetMapping("/{courseId}")
    fun getOneCourse(@PathVariable(value = "courseId") courseId: UUID): ResponseEntity<Any> {
        val courseModelOptional = courseService.findById(courseId)
        return if (!courseModelOptional.isPresent) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.")
        } else ResponseEntity.status(HttpStatus.OK).body(courseModelOptional.get())
    }
}