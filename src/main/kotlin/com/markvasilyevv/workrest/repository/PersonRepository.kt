package com.markvasilyevv.workrest.repository

import com.markvasilyevv.workrest.model.Person
import com.markvasilyevv.workrest.model.RoleType
import com.markvasilyevv.workrest.model.StatusType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param


interface PersonRepository: JpaRepository<Person, Long> {
    fun findByEmail(email: String): Person?
    @Query(
        "SELECT p FROM Person p WHERE p.status.statusType = :statusType AND " +
                "NOT EXISTS (SELECT r FROM p.roles r WHERE r.roleType IN :excludedRoles)"
    )
    fun findAllPersonsByStatusTypeWithRoleUser(
        @Param("statusType") statusType: StatusType,
        @Param("excludedRoles") excludedRoles: Set<RoleType>
    ): List<Person>

    fun findByEmployerNumber(employerNumber: Long): Person?
}