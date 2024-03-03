package com.markvasilyevv.workrest.repository

import com.markvasilyevv.workrest.model.WorkDay
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface WorkDayRepository: JpaRepository<WorkDay, Long> {
    fun findByPersonId(personId: Long): List<WorkDay>
    fun findByPersonIdAndCurrentDay(personId: Long, currentDay: LocalDate): WorkDay?
    fun findByPersonIdAndCurrentDayBetween(personId: Long, startDate: LocalDate, endDate: LocalDate): List<WorkDay>
}