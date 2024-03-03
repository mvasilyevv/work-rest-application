package com.markvasilyevv.workrest.config

import com.markvasilyevv.workrest.model.RoleType
import com.markvasilyevv.workrest.service.person.impl.PersonDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val successPersonHandler: SuccessPersonHandler,
    private val personDetailsService: PersonDetailsService
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf().disable()
            .authorizeHttpRequests {
                it.requestMatchers("/admin/**").hasRole(RoleType.SHIP_ADMIN.name)
                it.requestMatchers("/login").permitAll()
                it.requestMatchers("/css/style.css", "/js/script.js").permitAll()
                it.anyRequest().authenticated()
            }
            .exceptionHandling {
                it.accessDeniedPage("/forbidden")
            }
            .formLogin {
                it.loginPage("/login")
                it.usernameParameter("email")
                it.loginProcessingUrl("/process_login")
                it.successHandler(successPersonHandler)
                it.failureUrl("/login")
                it.permitAll()
            }
            .logout {
                it.logoutUrl("/logout")
                it.logoutSuccessUrl("/login")
            }
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun userDetailsService(): PersonDetailsService {
        return personDetailsService
    }
}

