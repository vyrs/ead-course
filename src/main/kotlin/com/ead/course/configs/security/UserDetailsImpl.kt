package com.ead.course.configs.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*


class UserDetailsImpl(
    val userId: UUID,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String? {
        return null
    }

    override fun getUsername(): String? {
        return null
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    companion object {
        fun build(userId: UUID, rolesStr: String): UserDetailsImpl {
            val authorities: List<GrantedAuthority> = rolesStr.split(",").map { SimpleGrantedAuthority(it) }

            return UserDetailsImpl(
                userId,
                authorities
            )
        }
    }
}