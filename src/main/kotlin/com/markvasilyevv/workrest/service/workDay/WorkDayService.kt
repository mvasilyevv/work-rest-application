package com.markvasilyevv.workrest.service.workDay

import com.markvasilyevv.workrest.model.WorkDay
import java.time.LocalDate

interface WorkDayService {
    fun addWorkDay(personId: Long, currentDay: LocalDate, workHours: Set<Int>): WorkDay
    fun updateWorkDay(workDayId: Long, newWorkHours: Set<Int>): WorkDay
    fun getWorkDaysForPerson(personId: Long): List<WorkDay>
    fun getWorkDaysForPersonBetweenDates(personId: Long, startDate: LocalDate, endDate: LocalDate): List<WorkDay>
}