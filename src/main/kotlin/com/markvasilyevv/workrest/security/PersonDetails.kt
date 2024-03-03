package com.markvasilyevv.workrest.security

import com.markvasilyevv.workrest.model.Person
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class PersonDetails(
    private val person: Person
): UserDetails {
    override fun getAuthorities(): List<GrantedAuthority> =
        person.roles.map {role ->
            SimpleGrantedAuthority(role.authority)
        }

    override fun getPassword(): String =
        person.password

    override fun getUsername(): String =
        person.email

    override fun isAccountNonExpired(): Boolean  = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    fun getPerson(): Person {
        return person
    }
}