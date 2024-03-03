package com.markvasilyevv.workrest.controller

import com.markvasilyevv.workrest.mapper.PersonMapper
import com.markvasilyevv.workrest.security.PersonDetails
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/person")
class PersonController(
    private val personMapper: PersonMapper
) {
    @GetMapping
    fun showUser(model: Model): String {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val personDetails: PersonDetails = authentication.principal as PersonDetails
//        val personDto = personMapper.mapEntityToDto(personDetails.getPerson())
//        model.addAttribute("currentPerson", personDto)
        return "person/index"
    }
}