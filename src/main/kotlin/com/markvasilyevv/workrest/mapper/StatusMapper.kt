package com.markvasilyevv.workrest.mapper

import com.markvasilyevv.workrest.dto.StatusDTO
import com.markvasilyevv.workrest.model.Status
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface StatusMapper {
    fun toDto(status: Status): StatusDTO
    fun toEntity(statusDto: StatusDTO): Status
}