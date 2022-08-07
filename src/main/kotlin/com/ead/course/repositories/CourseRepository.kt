package com.ead.course.repositories

import com.ead.course.models.CourseModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID


interface CourseRepository: JpaRepository<CourseModel, UUID>, JpaSpecificationExecutor<CourseModel> {
    @Query(
        value = "select case when count(tcu) > 0 THEN true ELSE false END FROM tb_courses_users tcu WHERE tcu.course_id= :courseId and tcu.user_id= :userId",
        nativeQuery = true
    )
    fun existsByCourseAndUser(@Param("courseId") courseId: UUID, @Param("userId") userId: UUID): Boolean

    @Modifying
    @Query(value = "insert into tb_courses_users values (:courseId,:userId)", nativeQuery = true)
    fun saveCourseUser(@Param("courseId") courseId: UUID, @Param("userId") userId: UUID)

    @Modifying
    @Query(value = "delete from tb_courses_users where course_id= :courseId", nativeQuery = true)
    fun deleteCourseUserByCourse(@Param("courseId") courseId: UUID)

    @Modifying
    @Query(value = "delete from tb_courses_users where user_id= :userId", nativeQuery = true)
    fun deleteCourseUserByUser(@Param("userId") userId: UUID)
}