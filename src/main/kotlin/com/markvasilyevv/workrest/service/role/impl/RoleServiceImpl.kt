package com.markvasilyevv.workrest.service.role.impl

import com.markvasilyevv.workrest.model.Role
import com.markvasilyevv.workrest.model.RoleType
import com.markvasilyevv.workrest.repository.RoleRepository
import com.markvasilyevv.workrest.service.role.RoleService
import org.springframework.stereotype.Service

@Service
class RoleServiceImpl(
    val roleRepository: RoleRepository
): RoleService {
    override fun findAll(): List<Role> {
        return roleRepository.findAll()
    }

    override fun findById(id: Long): Role? {
        return roleRepository.findById(id).orElse(null)
    }

    override fun findByRole(roleType: RoleType): Role? {
        return roleRepository.findByRoleType(roleType)
    }

}