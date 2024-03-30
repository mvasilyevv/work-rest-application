package com.markvasilyevv.workrest.controller

import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/forbidden")
class ForbiddenController {

    @GetMapping
    fun forbidden(): String {
        return "/error/forbidden"
    }
}