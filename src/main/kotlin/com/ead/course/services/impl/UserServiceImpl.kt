package com.ead.course.services.impl

import com.ead.course.models.UserModel
import com.ead.course.repositories.UserRepository
import com.ead.course.services.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {
    override fun findAll(spec: Specification<UserModel>?, pageable: Pageable): Page<UserModel> {
        return userRepository.findAll(spec, pageable)
    }

    override fun save(userModel: UserModel): UserModel {
        TODO("Not yet implemented")
    }

    override fun delete(userId: UUID) {
        TODO("Not yet implemented")
    }

    override fun findById(userInstructor: UUID): Optional<UserModel> {
        TODO("Not yet implemented")
    }

}