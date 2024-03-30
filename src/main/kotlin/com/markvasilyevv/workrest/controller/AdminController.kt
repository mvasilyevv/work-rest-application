package com.markvasilyevv.workrest.controller

import com.markvasilyevv.workrest.dto.PersonDTO
import com.markvasilyevv.workrest.mapper.PersonMapper
import com.markvasilyevv.workrest.model.StatusType
import com.markvasilyevv.workrest.security.PersonDetails
import com.markvasilyevv.workrest.service.person.PersonService
import com.markvasilyevv.workrest.service.role.RoleService
import mu.KotlinLogging
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping("/admin")
class AdminController(
    private val personService: PersonService,
    private val roleService: RoleService,
    private val personMapper: PersonMapper
){

    private val logger = KotlinLogging.logger {}

    @GetMapping
    fun index(model: Model): String {
        logger.info { "Accessing /admin endpoint" }
        val authentication = SecurityContextHolder.getContext().authentication
        val personDetails = authentication.principal as PersonDetails
        logger.info { "Authenticated user: ${personDetails.username}" }
        model.addAttribute("currentPerson", personMapper.toDto(personDetails.getPerson()))
        return "bootstrap/admin/index"
    }

    @PatchMapping("/person/{id}/edit")
    fun updateUser(
        @PathVariable("id") id: Long,
        @ModelAttribute("person") personDTO: PersonDTO,
        @RequestParam("newPassword") newPassword: String
    ): String {
        val person = personService.findById(id)
        person?.let {
//            val updatedPerson = personMapper.toPerson(personDTO)
//            personService.update(updatedPerson, id)
        }
        return "redirect:/admin"
    }

    @PostMapping("/person/new")
    fun addNewUser(
        @ModelAttribute("person") newPersonDTO: PersonDTO,
        @RequestParam("password") password: String
    ): String {

//        val newPerson = personMapper.mapDtoToEntity(newPersonDTO).apply {
//            this.password = password
//        }
//        personService.save(newPerson)
        return "redirect:/admin"
    }

    @DeleteMapping("/person/{id}/delete")
    fun delete(@PathVariable("id") id: Long): String {
        personService.findById(id)?.let { personService.delete(it) }
        return "redirect:/admin"
    }
}