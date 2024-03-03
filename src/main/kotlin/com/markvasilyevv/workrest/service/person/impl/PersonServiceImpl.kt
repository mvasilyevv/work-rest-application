package com.markvasilyevv.workrest.service.person.impl

import com.markvasilyevv.workrest.constant.ADMINS_ROLES
import com.markvasilyevv.workrest.model.Person
import com.markvasilyevv.workrest.model.RoleType
import com.markvasilyevv.workrest.model.StatusType
import com.markvasilyevv.workrest.repository.PersonRepository
import com.markvasilyevv.workrest.service.person.PersonService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class PersonServiceImpl(
    val personRepository: PersonRepository,
    val passwordEncoder: PasswordEncoder
): PersonService {
    override fun findAll(): List<Person> {
        return personRepository.findAll()
    }

    override fun findById(id: Long): Person? {
        return personRepository.findById(id).orElse(null)
    }

    override fun findByEmail(email: String): Person? {
       return personRepository.findByEmail(email)
    }

    override fun findAllPersonsByStatusType(statusType: StatusType): List<Person> {
        val adminsRoles: Set<RoleType> = ADMINS_ROLES
        return personRepository.findAllPersonsByStatusTypeWithRoleUser(statusType, adminsRoles)
    }

    override fun save(person: Person) {
        person.password = passwordEncoder.encode(person.password)
        personRepository.save(person)
    }

    override fun update(updatedPerson: Person, id: Long) {
        findById(id)?.let {person ->
            if (person.password != updatedPerson.password) {
                updatedPerson.password = passwordEncoder.encode(
                    person.password
                )
            }
            person.firstName = updatedPerson.firstName
            person.lastName = updatedPerson.lastName
            person.password = updatedPerson.password
            person.email = updatedPerson.email
            person.parameters = updatedPerson.parameters
            person.roles = updatedPerson.roles
            person.employerNumber = updatedPerson.employerNumber
            personRepository.save(person)
        }
    }

    override fun delete(person: Person) {
        personRepository.delete(person)
    }
}