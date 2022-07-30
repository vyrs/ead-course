package com.ead.course.models

import com.fasterxml.jackson.annotation.JsonInclude
import java.util.UUID
import javax.persistence.*


@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USERS")
class UserModel(

) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null
}
