package com.markvasilyevv.workrest.config

import com.markvasilyevv.workrest.model.Role
import com.markvasilyevv.workrest.model.RoleType
import com.markvasilyevv.workrest.model.Status
import com.markvasilyevv.workrest.model.StatusType
import com.markvasilyevv.workrest.model.StatusType.ACTIVE
import com.markvasilyevv.workrest.model.StatusType.ON_BOARD
import com.markvasilyevv.workrest.model.Person
import com.markvasilyevv.workrest.repository.RoleRepository
import com.markvasilyevv.workrest.repository.StatusRepository
import com.markvasilyevv.workrest.service.person.impl.PersonServiceImpl
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException
import java.time.LocalDate

@Component
class DataInitialization(
    private val personServiceImpl: PersonServiceImpl,
    private val roleRepository: RoleRepository,
    private val statusRepository: StatusRepository
) {
    fun init() {
        val roles = initRoles()
        val statuses = initStatuses()

        createAdmin(roles, statuses)
        createUsers(roles, statuses)
    }

    private fun initRoles(): Map<RoleType, Role> {
        return roleRepository.saveAll(
            listOf(
                Role(roleType = RoleType.GENERAL_ADMIN),
                Role(roleType = RoleType.USER),
                Role(roleType = RoleType.SHIP_ADMIN)
            )
        ).associateBy { it.roleType }
    }

    private fun initStatuses(): Map<StatusType, Status> {
        return statusRepository.saveAll(
            listOf(
                Status(statusType = ACTIVE),
                Status(statusType = ON_BOARD)
            )
        ).associateBy { it.statusType }
    }

    private fun createAdmin(roles: Map<RoleType, Role>, statuses: Map<StatusType, Status>) {
        val admin = Person(
            firstName = "Alexey",
            lastName = "Ivanov",
            password = "admin",
            email = "admin@mail.com",
            employerNumber = 1,
            roles = setOf(roles[RoleType.GENERAL_ADMIN]!!),
            parameters = Person.Parameters(dateOfBirthday = LocalDate.of(1975, 3, 15)),
            status = statuses.getStatus(ON_BOARD),
            mmsi = "MMSI0"
        )
        personServiceImpl.save(admin)
        println("Добавлен в бд администратор $admin")
    }


    private fun createUsers(roles: Map<RoleType, Role>, statuses: Map<StatusType, Status>) {
        val usersData = listOf(
            Triple("Maria", "Petrova", "maria.petrova@mail.com"),
            Triple("Ivan", "Sidorov", "ivan.sidorov@mail.com"),
            Triple("Elena", "Volkova", "elena.volkova@mail.com")
        )

        usersData.forEachIndexed { index, (firstName, lastName, email) ->
            val activeStatus = if (index == 1) {
                val status = Status(statusType = ACTIVE)
                statusRepository.save(status)
                status
            } else {
                val status = Status(statusType = ON_BOARD)
                statusRepository.save(status)
                status
            }

            val user = Person(
                firstName = firstName,
                lastName = lastName,
                password = "password${index + 1}",
                email = email,
                employerNumber = index.toLong() + 2,
                roles = setOf(roles[RoleType.USER]!!),
                parameters = Person.Parameters(dateOfBirthday = LocalDate.of(1985 + index, 7, 5 + index)),
                status = activeStatus,
                mmsi = "MMSI${index + 1}"
            )
            personServiceImpl.save(user)
            println("Добавлен в бд пользователь $user")
        }
    }

    private fun Map<StatusType, Status>.getStatus(statusType: StatusType): Status {
        return this[statusType] ?: throw IllegalArgumentException("Status $statusType not found")
    }
}
