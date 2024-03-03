package com.markvasilyevv.workrest.model

import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
@Table(name = "status")
data class Status(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    var id: Long = 0,

    @Column(name = "last_modified_date", nullable = false)
    var lastModifiedDate: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    var statusType: StatusType = StatusType.ACTIVE
)

enum class StatusType {
    ACTIVE,
    ON_BOARD
}

