package be.tftic.web2023.demo07_persistence_db.models

import java.time.LocalDate

data class Person(
    val id: Long = 0,
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDate,
    val email: String?,
    val phone: String?,
)
