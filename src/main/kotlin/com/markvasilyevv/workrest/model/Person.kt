package com.markvasilyevv.workrest.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "person")
data class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Long = 0,

    @Column(name = "employer_number", unique = true, nullable = false)
    var employerNumber: Long = 0,

    @Column(name = "first_name", nullable = false)
    var firstName: String = "",

    @Column(name = "last_name", nullable = false)
    var lastName: String = "",

    @Column(nullable = false)
    var password: String = "",

    @Column(unique = true, length = 30, nullable = false)
    var email: String = "",

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE])
    @JoinTable(
        name = "person_role",
        joinColumns = [JoinColumn(name = "person_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: Set<Role> = emptySet(),

    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE])
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    var status: Status = Status(),

    @Column(nullable = false)
    var mmsi: String = "",

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var workDays: Set<WorkDay> = emptySet(),

    @Embedded
    var parameters: Parameters = Parameters()
) {
    @Embeddable
    data class Parameters(
        @Column(name = "date_of_birthday")
        val dateOfBirthday: LocalDate? = null
    )
}

