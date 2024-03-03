package com.markvasilyevv.workrest.controller

import com.markvasilyevv.workrest.dto.WorkDayDto
import com.markvasilyevv.workrest.model.WorkDay
import com.markvasilyevv.workrest.service.workDay.WorkDayService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/workdays")
class WorkDayController(
    private val workDayService: WorkDayService
) {

    @PostMapping
    fun addWorkDay(@RequestBody workDayDto: WorkDayDto): ResponseEntity<Any> {
        return try {
            val savedWorkDay = workDayService.addWorkDay(workDayDto.personId, workDayDto.currentDay, workDayDto.workHours)
            ResponseEntity.ok(savedWorkDay)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: ${e.message}")
        }
    }

    @PutMapping("/{workDayId}")
    fun updateWorkDay(@PathVariable workDayId: Long, @RequestBody workDayDto: WorkDayDto): ResponseEntity<Any> {
        return try {
            val updatedWorkDay = workDayService.updateWorkDay(workDayId, workDayDto.workHours)
            ResponseEntity.ok(updatedWorkDay)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: ${e.message}")
        }
    }

    @GetMapping("/person/{personId}")
    fun getWorkDaysForPerson(@PathVariable personId: Long): ResponseEntity<List<WorkDay>> {
        val workDays = workDayService.getWorkDaysForPerson(personId)
        return ResponseEntity.ok(workDays)
    }

    @GetMapping("/person/{personId}/range")
    fun getWorkDaysForPersonBetweenDates(
        @PathVariable personId: Long,
        @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) startDate: LocalDate,
        @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) endDate: LocalDate
    ): ResponseEntity<List<WorkDay>> {
        val workDays = workDayService.getWorkDaysForPersonBetweenDates(personId, startDate, endDate)
        return ResponseEntity.ok(workDays)
    }
}
