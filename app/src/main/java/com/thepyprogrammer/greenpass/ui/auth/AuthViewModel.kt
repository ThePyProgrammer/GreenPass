package com.thepyprogrammer.greenpass.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.thepyprogrammer.greenpass.model.account.Result
import com.thepyprogrammer.greenpass.model.account.VaccinatedUser
import com.thepyprogrammer.greenpass.model.firebase.FirebaseUtil
import java.io.IOException

class AuthViewModel(): ViewModel() {
    var pName = MutableLiveData("name")
    var NRIC = MutableLiveData("IC")
    var date = MutableLiveData(Timestamp.now())
    var password = MutableLiveData("")

    fun register(): Result<VaccinatedUser> {

    }

    fun login(): Result<VaccinatedUser> {
        val nric = NRIC.value!!
        val password = this.password.value!!

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
}