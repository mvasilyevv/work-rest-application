package com.markvasilyevv.workrest.mapper

import com.markvasilyevv.workrest.dto.RoleDTO
import com.markvasilyevv.workrest.model.Role
import org.mapstruct.Mapper
@Mapper(componentModel = "spring")
interface RoleMapper {
    fun toDto(roles: Set<Role>): Set<RoleDTO>
    fun toEntity(roleDtos: Set<RoleDTO>): Set<Role>
}