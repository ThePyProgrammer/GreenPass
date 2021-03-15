package com.thepyprogrammer.greenpass.model

import android.util.Patterns;

object Validate {
    val nricRegex = Regex("[STFG]\\d{7}[A-Z]")
    val emailRegex = Patterns.EMAIL_ADDRESS.toRegex()

    fun checkNRIC(NRIC: String) = NRIC.matches(nricRegex)

    fun checkEmail(email: String) = email.matches(emailRegex)
}