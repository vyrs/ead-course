package com.ead.course.services.impl

import com.ead.course.models.UserModel
import com.ead.course.repositories.CourseRepository
import com.ead.course.repositories.UserRepository
import com.ead.course.services.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional


@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val courseRepository: CourseRepository
) : UserService {
    override fun findAll(spec: Specification<UserModel>?, pageable: Pageable): Page<UserModel> {
        return userRepository.findAll(spec, pageable)
    }

    override fun save(userModel: UserModel): UserModel {
        return userRepository.save(userModel)
    }

    @Transactional
    override fun delete(userId: UUID) {
        courseRepository.deleteCourseUserByUser(userId)
        userRepository.deleteById(userId)
    }

    override fun findById(userInstructor: UUID): Optional<UserModel> {
        return userRepository.findById(userInstructor)
    }

}