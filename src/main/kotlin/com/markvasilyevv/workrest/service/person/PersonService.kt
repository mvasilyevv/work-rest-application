package com.markvasilyevv.workrest.service.person

import com.markvasilyevv.workrest.model.Person
import com.markvasilyevv.workrest.model.StatusType

interface PersonService {
    fun findAll(): List<Person>
    fun findById(id: Long): Person?
    fun findByEmail(email: String): Person?
    fun findAllPersonsByStatusType(statusType: StatusType): List<Person>
    fun save(person: Person)
    fun update(updatedPerson: Person, id: Long)
    fun delete(person: Person)
    fun findByEmployerNumber(employerNumber: Long): Person?
    fun getPaginatedPersons(statusType: StatusType): Pair<Int, List<Person>>
}