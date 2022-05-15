package com.ead.course.specifications

import com.ead.course.models.CourseModel
import com.ead.course.models.LessonModel
import com.ead.course.models.ModuleModel
import net.kaczmarzyk.spring.data.jpa.domain.Equal
import net.kaczmarzyk.spring.data.jpa.domain.Like
import net.kaczmarzyk.spring.data.jpa.web.annotation.And
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec
import org.springframework.data.jpa.domain.Specification
import java.util.UUID
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Expression
import javax.persistence.criteria.Root


object SpecificationTemplate {
    fun moduleCourseId(courseId: UUID): Specification<ModuleModel> {
        return Specification { root: Root<ModuleModel>, query: CriteriaQuery<*>, cb: CriteriaBuilder ->
            query.distinct(true)
            val course = query.from(CourseModel::class.java)
            val coursesModules: Expression<Collection<ModuleModel>> =
                course.get("modules")
            cb.and(
                cb.equal(course.get<Any>("courseId"), courseId),
                cb.isMember(root, coursesModules)
            )
        }
    }

    fun lessonModuleId(moduleId: UUID?): Specification<LessonModel> {
        return Specification { root: Root<LessonModel>, query: CriteriaQuery<*>, cb: CriteriaBuilder ->
            query.distinct(true)
            val module = query.from(ModuleModel::class.java)
            val moduleLessons: Expression<Collection<LessonModel>> =
                module.get("lessons")
            cb.and(
                cb.equal(module.get<Any>("moduleId"), moduleId),
                cb.isMember(root, moduleLessons)
            )
        }
    }

    @And(
        Spec(path = "courseLevel", spec = Equal::class),
        Spec(path = "courseStatus", spec = Equal::class),
        Spec(path = "name", spec = Like::class)
    )
    interface CourseSpec : Specification<CourseModel>

    @Spec(path = "title", spec = Like::class)
    interface ModuleSpec : Specification<ModuleModel>

    @Spec(path = "title", spec = Like::class)
    interface LessonSpec : Specification<LessonModel>
}
