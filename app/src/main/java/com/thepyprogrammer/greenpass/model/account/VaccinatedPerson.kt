package com.thepyprogrammer.greenpass.model.account

import java.time.LocalDate

data class VaccinatedPerson(
    val name: String,
    val ic: String,
    val email: String,
    val dateOfVaccine: LocalDate
)
