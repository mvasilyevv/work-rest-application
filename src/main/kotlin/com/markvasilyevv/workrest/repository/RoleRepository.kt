package com.markvasilyevv.workrest.repository

import com.markvasilyevv.workrest.model.Role
import com.markvasilyevv.workrest.model.RoleType
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository: JpaRepository<Role, Long> {
    fun findByRoleType(roleType: RoleType): Role?
}