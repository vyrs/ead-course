package com.ead.course.models

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID
import javax.persistence.*


@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USERS")
class UserModel(
    @Id val id: UUID,
    @Column(nullable = false, unique = true, length = 50)
    val email: String,
    @Column(nullable = false, length = 150)
    var fullName: String,
    @Column(nullable = false)
    val userStatus: String,
    @Column(nullable = false)
    var userType: String,
    @Column(length = 20)
    var cpf: String,
    var imageUrl: String?,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    var courses: Set<CourseModel>? = null
)
