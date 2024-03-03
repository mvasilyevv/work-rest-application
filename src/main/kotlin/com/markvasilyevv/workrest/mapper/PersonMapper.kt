package com.markvasilyevv.workrest.mapper

import com.markvasilyevv.workrest.dto.PersonDTO
import com.markvasilyevv.workrest.model.Person
import com.markvasilyevv.workrest.model.Status
import com.markvasilyevv.workrest.model.StatusType
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.Named

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
interface PersonMapper {

    @Mappings(value = [
        Mapping(target = "plainPassword", ignore = true),
        Mapping(target = "roles", ignore = true),
        Mapping(target = "status", qualifiedByName = ["statusToString"])
    ])
    fun toDto(person: Person): PersonDTO


//    fun toPerson(personDto: PersonDTO): Person

//    @Named("rolesToString")
//    fun rolesToString(roles: Set<Role>): Set<String> {
//        return roles.map { it.roleType.name }.toSet()
//    }
//
//    @Named("stringToRole")
//    fun stringToRole(roleTypeName: String) =
//        Role(roleType = RoleType.valueOf(roleTypeName))

    @Named("statusToString")
    fun statusToString(status: Status): String =
        status.statusType.name

    @Named("stringToStatus")
    fun stringToStatus(statusName: String): Status = Status(statusType = StatusType.valueOf(statusName))

}