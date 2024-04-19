package com.markvasilyevv.workrest.mapper

import com.markvasilyevv.workrest.dto.PersonDTO
import com.markvasilyevv.workrest.dto.RoleDTO
import com.markvasilyevv.workrest.dto.StatusDTO
import com.markvasilyevv.workrest.model.Person
import com.markvasilyevv.workrest.model.Role
import com.markvasilyevv.workrest.model.Status
import org.mapstruct.*

@Mapper(
    componentModel = "spring",
    uses = [RoleMapper::class, StatusMapper::class]
)
interface PersonMapper {

    @Mappings(
        value = [
            Mapping(target = "plainPassword", ignore = true),
            Mapping(target = "dateOfBirthday", source = "parameters.dateOfBirthday"),
            Mapping(target = "roles", qualifiedByName = ["mapToRoles"]),
            Mapping(target = "status", qualifiedByName = ["mapToStatus"])
        ]
    )
    fun toDto(person: Person): PersonDTO

    @Mappings(
        value = [
            Mapping(target = "password", ignore = true),
            Mapping(target = "parameters", expression = "java(new Person.Parameters(personDto.getDateOfBirthday()))"),
            Mapping(target = "roles", qualifiedByName = ["mapRoleToDto"]),
            Mapping(target = "status", qualifiedByName = ["mapStatusToDto"])
        ]
    )
    fun toEntity(personDto: PersonDTO): Person

    @Named("mapToRoles")
    @IterableMapping(elementTargetType = RoleDTO::class)
    fun mapToRoles(roles: Set<Role>): Set<RoleDTO>

    @Named("mapRoleToDto")
    @IterableMapping(elementTargetType = Role::class)
    fun mapRoleToDto(roleDtos: Set<RoleDTO>): Set<Role>

    @Named("mapToStatus")
    fun mapToStatus(status: Status): StatusDTO

    @Named("mapStatusToDto")
    fun mapStatusToDto(statusDto: StatusDTO): Status
}