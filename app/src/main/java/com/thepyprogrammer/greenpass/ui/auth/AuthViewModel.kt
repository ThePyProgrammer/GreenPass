package com.thepyprogrammer.greenpass.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.thepyprogrammer.greenpass.model.account.Result
import com.thepyprogrammer.greenpass.model.account.VaccinatedUser
import com.thepyprogrammer.greenpass.model.firebase.FirebaseUtil

class AuthViewModel(): ViewModel() {
    var pName = MutableLiveData("")
    var NRIC = MutableLiveData("")
    var date = MutableLiveData(Timestamp.now())
    var password = MutableLiveData("")

    fun register(): Result<VaccinatedUser> {
        return try {
            val fullName = pName.value!!
            val nric = NRIC.value!!
            val dateOfVaccine = date.value!!
            val pw = password.value!!
            var success = true
            var e: Exception = Exception()
            val data = hashMapOf(
                    "fullName" to fullName,
                    "nric" to nric,
                    "dateOfVaccine" to dateOfVaccine,
                    "password" to pw
            )
            if (pw.length >= 8) {
                FirebaseUtil.userCollection()
                        ?.document(nric)
                        ?.set(data)
                        ?.addOnSuccessListener {}
                        ?.addOnFailureListener {
                            success = false
                            e = it
                        }
                if(!success) {
                    Result.Error(e)
                } else {
                    val user = VaccinatedUser(nric, fullName, dateOfVaccine, pw)
                    Result.Success(user)
                }

            } else {
                Result.Error(Exception("Password is too small!"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    fun login(): Result<VaccinatedUser> {
        return try {
            val nric = NRIC.value!!
            val password = this.password.value!!
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
                Result.Error(e)
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
                    Result.Error(Exception("It seems you don't exist."))
                }
            }

        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}