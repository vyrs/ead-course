package com.ead.course.configs.security

import com.ead.course.configs.EadLog
import com.ead.course.configs.log
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationEntryPointImpl : AuthenticationEntryPoint, EadLog {

    @Throws(IOException::class, ServletException::class)
    override fun commence(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        e: AuthenticationException
    ) {
        log().error("Unauthorized error: {}", e.message)
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
    }
}