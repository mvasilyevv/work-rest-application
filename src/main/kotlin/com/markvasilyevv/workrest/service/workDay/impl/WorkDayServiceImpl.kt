package com.markvasilyevv.workrest.service.workDay.impl

import com.markvasilyevv.workrest.model.WorkDay
import com.markvasilyevv.workrest.repository.PersonRepository
import com.markvasilyevv.workrest.repository.WorkDayRepository
import com.markvasilyevv.workrest.service.workDay.WorkDayService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class WorkDayServiceImpl(
    private val workDayRepository: WorkDayRepository,
    private val personRepository: PersonRepository
): WorkDayService {

    override fun addWorkDay(personId: Long, currentDay: LocalDate, workHours: Set<Int>): WorkDay {
        val person = personRepository.findById(personId).orElseThrow {
            RuntimeException("Person not found with ID: $personId")
        }
        val workDay = WorkDay(currentDay = currentDay, workHours = workHours, person = person)
        return workDayRepository.save(workDay)
    }

    override fun updateWorkDay(workDayId: Long, newWorkHours: Set<Int>): WorkDay {
        val workDay = workDayRepository.findById(workDayId).orElseThrow {
            RuntimeException("WorkDay not found with ID: $workDayId")
        }
        workDay.workHours = newWorkHours
        return workDayRepository.save(workDay)
    }

    override fun getWorkDaysForPerson(personId: Long): List<WorkDay> {
        return workDayRepository.findByPersonId(personId)
    }

    override fun getWorkDaysForPersonBetweenDates(personId: Long, startDate: LocalDate, endDate: LocalDate): List<WorkDay> {
        return workDayRepository.findByPersonIdAndCurrentDayBetween(personId, startDate, endDate)
    }
}
