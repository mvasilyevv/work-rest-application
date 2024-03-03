package com.markvasilyevv.workrest.service.person.impl

import com.markvasilyevv.workrest.repository.PersonRepository
import com.markvasilyevv.workrest.security.PersonDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class PersonDetailsService(
    private val personRepository: PersonRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val person = personRepository.findByEmail(username) ?: throw UsernameNotFoundException("User not found")
        return PersonDetails(person)
    }
}