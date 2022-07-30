package com.ead.course.controllers

import com.ead.course.configs.EadLog
import com.ead.course.configs.log
import com.ead.course.dtos.CourseDto
import com.ead.course.dtos.toModel
import com.ead.course.models.CourseModel
import com.ead.course.services.CourseService
import com.ead.course.specifications.SpecificationTemplate.CourseSpec
import com.ead.course.validation.CourseValidator
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.validation.Valid


@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = ["*"], maxAge = 3600)
class CourseController(private val courseService: CourseService, private val courseValidator: CourseValidator): EadLog {

    @PostMapping
    fun saveCourse(@RequestBody courseDto: CourseDto, errors: Errors): ResponseEntity<Any> {
        log().debug("POST saveCourse courseDto received {} ", courseDto.toString())
        courseValidator.validate(courseDto, errors)
        if(errors.hasErrors()){
            if(errors.allErrors.first().code == "UserInstructorNotFoundError")
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors.allErrors)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.allErrors)
        }

        val courseModel = courseDto.toModel()
        courseService.save(courseModel)

        log().debug("POST saveCourse courseId saved {} ", courseModel.courseId)
        log().info("Course saved successfully courseId {} ", courseModel.courseId)
        return ResponseEntity.status(HttpStatus.CREATED).body(courseModel)
    }

    @DeleteMapping("/{courseId}")
    fun deleteCourse(@PathVariable(value = "courseId") courseId: UUID): ResponseEntity<Any> {
        log().debug("DELETE deleteCourse courseId received {} ", courseId)
        val courseModelOptional = courseService.findById(courseId)
        if (!courseModelOptional.isPresent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.")
        }
        courseService.delete(courseModelOptional.get())
        log().debug("DELETE deleteCourse courseId deleted {} ", courseId)
        log().info("Course deleted successfully courseId {} ", courseId)
        return ResponseEntity.status(HttpStatus.OK).body("Course deleted successfully.")
    }

    @PutMapping("/{courseId}")
    fun updateCourse(
        @PathVariable(value = "courseId") courseId: UUID,
        @RequestBody @Valid courseDto: CourseDto
    ): ResponseEntity<Any> {
        log().debug("PUT updateCourse courseDto received {} ", courseDto.toString())

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

        courseService.save(courseModel)

        log().debug("PUT updateCourse courseId saved {} ", courseModel.courseId)
        log().info("Course updated successfully courseId {} ", courseModel.courseId)

        return ResponseEntity.status(HttpStatus.OK).body(courseModel)
    }

    @GetMapping
    fun getAllCourses(
        spec: CourseSpec?,
        @PageableDefault(page = 0, size = 10, sort = ["courseId"], direction = Sort.Direction.ASC) pageable: Pageable,
        @RequestParam(required = false) userId: UUID
    ): ResponseEntity<Page<CourseModel>> {

        return ResponseEntity.status(HttpStatus.OK).body(
            courseService.findAll(
                spec,
                pageable
            )
        )

    }

    @GetMapping("/{courseId}")
    fun getOneCourse(@PathVariable(value = "courseId") courseId: UUID): ResponseEntity<Any> {
        val courseModelOptional: Optional<CourseModel> = courseService.findById(courseId)
        return if (!courseModelOptional.isPresent) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.")
        } else ResponseEntity.status(HttpStatus.OK).body(courseModelOptional.get())
    }
}