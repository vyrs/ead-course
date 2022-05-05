package com.ead.course.models

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_LESSONS")
class LessonModel(
    @Column(nullable = false, length = 150)
    private val title: String? = null,

    @Column(nullable = false, length = 250)
    private val description: String? = null,

    @Column(nullable = false)
    private val videoUrl: String? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Column(nullable = false)
    private val creationDate: LocalDateTime? = null,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private val module: ModuleModel? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val lessonId: UUID? = null
}