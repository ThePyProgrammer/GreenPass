package com.thepyprogrammer.greenpass.model.account

import com.google.firebase.Timestamp

data class VaccinatedUser(
        val fullName: String,
        val nric: String,
        val email: String,
        val dateOfVaccine: Timestamp
)
