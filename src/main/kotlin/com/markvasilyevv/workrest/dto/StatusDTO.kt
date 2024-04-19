package com.markvasilyevv.workrest.dto

import java.time.LocalDateTime

data class StatusDTO(
    var id: Long = 0,
    var lastModifiedDate: LocalDateTime = LocalDateTime.now(),
    var statusType: String = ""
)