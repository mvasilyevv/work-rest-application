package com.markvasilyevv.workrest.dto

import com.markvasilyevv.workrest.model.StatusType
import com.markvasilyevv.workrest.model.WorkDay
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.time.Period

data class PersonDTO(
    var employerNumber: Long = 0,
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var roles: Set<String> = emptySet(),
    var status: String = StatusType.ACTIVE.name,
    var mmsi: String = "",
    var workDays: Set<WorkDay> = emptySet(),
    var plainPassword: String? = null,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var dateOfBirthday: LocalDate = LocalDate.now(),
) {
    fun getAge(): Int {
        return dateOfBirthday.let {
            Period.between(it, LocalDate.now()).years
        }
    }
}