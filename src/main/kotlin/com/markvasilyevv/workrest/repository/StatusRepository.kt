package com.markvasilyevv.workrest.repository

import com.markvasilyevv.workrest.model.Status
import org.springframework.data.jpa.repository.JpaRepository

interface StatusRepository: JpaRepository<Status, Long>