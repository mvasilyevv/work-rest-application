package com.markvasilyevv.workrest.service.role

import com.markvasilyevv.workrest.model.Role
import com.markvasilyevv.workrest.model.RoleType

interface RoleService {
    fun findAll(): List<Role>
    fun findById(id: Long): Role?
    fun findByRole(roleType: RoleType): Role?
}