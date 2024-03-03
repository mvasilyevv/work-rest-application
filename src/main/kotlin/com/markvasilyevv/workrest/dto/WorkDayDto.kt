package com.markvasilyevv.workrest.dto

import com.markvasilyevv.workrest.model.Person
import com.markvasilyevv.workrest.model.WorkDay
import java.time.LocalDate

data class WorkDayDto(
    val personId: Long,
    val currentDay: LocalDate,
    val workHours: Set<Int>
) {
    fun toWorkDay(person: Person): WorkDay = WorkDay(
        currentDay = this.currentDay,
        workHours = this.workHours,
        person = person
    )
}
