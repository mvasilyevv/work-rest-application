package com.markvasilyevv.workrest.service.person.impl

import com.markvasilyevv.workrest.repository.PersonRepository
import com.markvasilyevv.workrest.security.PersonDetails
import mu.KotlinLogging
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class PersonDetailsService(
    private val personRepository: PersonRepository
): UserDetailsService {
    private val logger = KotlinLogging.logger {}

    override fun loadUserByUsername(username: String): UserDetails {
        logger.info { "Loading user by username: $username" }
        val person = personRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("User not found")
        logger.info { "Loaded user: $person" }
        return PersonDetails(person)
    }
}