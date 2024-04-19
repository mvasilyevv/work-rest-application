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
        val (pageCount, persons) = personService.getPaginatedPersons(StatusType.ON_BOARD)
        model.addAttribute("persons", persons.map(personMapper::toDto))
        model.addAttribute("pages", pageCount)
        logger.info("Page: $pageCount")
        model.addAttribute("emptyPerson", PersonDTO())
        model.addAttribute("roles", roleService.findAll().map { it.roleType.name })
        model.addAttribute("statuses", StatusType.entries.map { it.name })
        return "bootstrap/admin/index"
    }

    @PatchMapping("/person/{employerNumber}/edit")
    fun updateUser(
        @PathVariable("employerNumber") employerNumber: Long,
        @ModelAttribute("person") personDTO: PersonDTO,
        @RequestParam("newPassword") newPassword: String
    ): String {
        val person = personService.findByEmployerNumber(employerNumber)
        person?.let {
            val updatedPerson = personMapper.toEntity(personDTO)
            updatedPerson.password = newPassword
            personService.update(updatedPerson, it.id)
        }
        return "redirect:/admin"
    }

    @PostMapping("/person/new")
    fun addNewUser(
        @ModelAttribute("person") newPersonDTO: PersonDTO,
        @RequestParam("password") password: String
    ): String {

        val newPerson = personMapper.toEntity(newPersonDTO).apply {
            this.password = password
        }
        personService.save(newPerson)
        return "redirect:/admin"
    }

    @DeleteMapping("/person/{id}/delete")
    fun delete(@PathVariable("id") id: Long): String {
        personService.findById(id)?.let { personService.delete(it) }
        return "redirect:/admin"
    }
}