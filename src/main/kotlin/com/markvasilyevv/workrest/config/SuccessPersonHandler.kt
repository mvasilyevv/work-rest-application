package com.markvasilyevv.workrest.config

import com.markvasilyevv.workrest.model.RoleType
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class SuccessPersonHandler : AuthenticationSuccessHandler {
    private val logger = KotlinLogging.logger {}

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val roles = AuthorityUtils.authorityListToSet(authentication.authorities)
        if (roles.contains(RoleType.SHIP_ADMIN.name) || roles.contains(RoleType.GENERAL_ADMIN.name)) {
            logger.info { "Redirecting to /admin" }
            response.sendRedirect("/admin")
        } else {
            logger.info { "Redirecting to /person" }
            response.sendRedirect("/person")
        }
    }
}
