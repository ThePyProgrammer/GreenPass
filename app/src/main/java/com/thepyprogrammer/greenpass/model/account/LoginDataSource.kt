package com.thepyprogrammer.greenpass.model.account

import com.google.firebase.Timestamp
import com.thepyprogrammer.greenpass.model.firebase.FirebaseUtil
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(nric: String, password: String): Result<VaccinatedUser> {
        return try {
            var data: Map<String?, Any?>? = null
            var success = true
            var e: Exception = Exception()
            FirebaseUtil.userCollection()?.document(nric)?.get()
                    ?.addOnSuccessListener {
                        data = it?.data
                    }?.addOnFailureListener {
                        success = false
                        e = it
                    }

            if (!success) {
                return Result.Error(e)
            } else {

                return if (data != null) {
                    if ((data!!["password"] as String) == password) {
                        val fullName = data!!["fullName"] as String
                        val user = VaccinatedUser(nric, fullName, data!!["dateOfVaccine"] as Timestamp, password)
                        Result.Success(user)
                    } else {
                        Result.Error(Exception("It seems you don't exist."))
                    }

                } else {
                    return Result.Error(Exception("It seems you don't exist."))
                }
            }

        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}