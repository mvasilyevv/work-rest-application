package com.markvasilyevv.workrest.config

import com.markvasilyevv.workrest.model.RoleType
import com.markvasilyevv.workrest.service.person.impl.PersonDetailsService
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val successPersonHandler: SuccessPersonHandler,
    private val personDetailsService: PersonDetailsService
) {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/admin/**").hasAnyAuthority(RoleType.SHIP_ADMIN.name, RoleType.GENERAL_ADMIN.name)
                it.requestMatchers("/login", "/css/**", "/js/**", "/webjars/**").permitAll()
                it.anyRequest().authenticated()
            }
            .exceptionHandling {
                it.accessDeniedPage("/forbidden")
            }
            .formLogin {
                it.loginPage("/login")
                it.usernameParameter("email")
                it.loginProcessingUrl("/process_login")
                it.successHandler { request, response, authentication ->
                    logger.info { "Authentication successful for user: ${authentication.name}" }
                    successPersonHandler.onAuthenticationSuccess(request, response, authentication)
                }
                it.failureHandler { _, response, exception ->
                    logger.error { "Authentication failed: ${exception.message}" }
                    response.sendRedirect("/login?error")
                }
                it.permitAll()
            }
            .exceptionHandling {
                it.accessDeniedHandler { request, response, accessDeniedException ->
                    logger.warn { "Access denied for URL: ${request.requestURL}" }
                    response.sendRedirect("/forbidden")
                }
            }
            .logout {
                it.logoutUrl("/logout")
                it.logoutSuccessUrl("/login?logout")
                it.permitAll()
            }
            .build()

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(personDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }
}