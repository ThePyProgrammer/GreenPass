package com.thepyprogrammer.greenpass.model.account

import com.google.firebase.Timestamp
import java.io.Serializable

data class VaccinatedUser(
        val nric: String,
        val fullName: String,
        val dateOfVaccine: Timestamp,
        val password: String
) : Serializable
