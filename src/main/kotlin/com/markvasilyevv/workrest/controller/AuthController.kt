package com.markvasilyevv.workrest.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@Controller
class AuthController {

    @GetMapping("/login")
    fun loginPage(): String {
        return "auth/login"
    }
}