package com.ead.course.configs.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationJwtFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var jwtProvider: JwtProvider

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val jwtStr = getTokenHeader(httpServletRequest)
            if (jwtStr != null && jwtProvider.validateJwt(jwtStr)) {
                val userId = jwtProvider.getSubjectJwt(jwtStr)
                val rolesStr = jwtProvider.getClaimNameJwt(jwtStr, "roles")

                val userDetails = UserDetailsImpl.build(UUID.fromString(userId), rolesStr)

                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                authentication.details = WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: Exception) {
            logger.error("Cannot set User Authentication: {}", e)
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse)
    }

    private fun getTokenHeader(request: HttpServletRequest): String? {
        val headerAuth = request.getHeader("Authorization")
        return if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            headerAuth.substring(7, headerAuth.length)
        } else null
    }
}