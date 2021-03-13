package com.thepyprogrammer.greenpass.model.account

import java.io.Serializable
import com.google.firebase.Timestamp

data class VaccinatedUser(
        val nric: String,
        val fullName: String,
        val email: String,
        val dateOfVaccine: Timestamp,
        val password: String
): Serializable
