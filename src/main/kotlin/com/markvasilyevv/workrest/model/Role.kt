package com.markvasilyevv.workrest.model


import org.springframework.security.core.GrantedAuthority
import jakarta.persistence.*


@Entity
@Table(name = "role")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    var id: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    var roleType: RoleType
) : GrantedAuthority {
    override fun getAuthority() = roleType.name
}

enum class RoleType {
    GENERAL_ADMIN,
    USER,
    SHIP_ADMIN
}

