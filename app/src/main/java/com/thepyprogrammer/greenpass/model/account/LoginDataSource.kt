package com.thepyprogrammer.greenpass.model.account

import com.google.firebase.Timestamp
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(fullName: String, email: String, password: String): Result<VaccinatedUser> {
        return try {
            // TODO: handle loggedInUser authentication
            val fakeUser = VaccinatedUser(java.util.UUID.randomUUID().toString(), fullName, email, Timestamp.now(), password)
            Result.Success(fakeUser)
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}