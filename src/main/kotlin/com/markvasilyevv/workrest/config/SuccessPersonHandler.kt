package com.markvasilyevv.workrest.config

import com.markvasilyevv.workrest.model.RoleType
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class SuccessPersonHandler: AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val roles = AuthorityUtils.authorityListToSet(authentication.authorities)
        if (roles.contains(RoleType.SHIP_ADMIN.name)) {
            response.sendRedirect("/admin")
        } else {
            response.sendRedirect("/person")
        }
    }

}
