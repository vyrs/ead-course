package com.ead.course.services.impl

import com.ead.course.services.UtilsService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UtilsServiceImpl: UtilsService {
    override fun createUrlGetAllUsersByCourse(courseId: UUID, pageable: Pageable): String {
        return ("/users?courseId=" + courseId + "&page=" + pageable.pageNumber + "&size="
        + pageable.pageSize + "&sort=" + pageable.sort.toString().replace(": ", ","))
    }
}