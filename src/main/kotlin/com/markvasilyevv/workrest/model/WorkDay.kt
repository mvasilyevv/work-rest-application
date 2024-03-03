package com.markvasilyevv.workrest.model

import com.markvasilyevv.workrest.model.Person
import java.time.LocalDate
import jakarta.persistence.*

@Entity
@Table(name = "work_day")
data class WorkDay (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_day_id")
    var id: Long = 0,

    @Column(nullable = false)
    var currentDay: LocalDate = LocalDate.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    var person: Person,

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "work_hours", joinColumns = [JoinColumn(name = "work_day_id")])
    @Column(name = "hour")
    var workHours: Set<Int> = emptySet(),
) {
    fun convertDateToString(): String {
        return this.currentDay.toString()
    }
}