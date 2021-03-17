package com.thepyprogrammer.greenpass.ui.auth

import android.util.Log
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
    var user_result = MutableLiveData<VaccinatedUser>(VaccinatedUser("", "", Timestamp(0, 0), "old"))

    fun register(){
        val fullName = pName.value!!.trim()
        val nric = NRIC.value!!.trim()
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
        Log.d("TAG", fullName + " " + nric + " " + pw)

        FirebaseUtil.userCollection()?.document(nric)?.get()
            ?.addOnSuccessListener {
                var data_ = it?.data
                if (data_ != null){
                    val user = VaccinatedUser("", "", Timestamp.now(), "")
                    user_result?.value = user
                    Result.Error(Exception("It seems you don't exist."))
                }
                else{

                    if (pw.length >= 8) {
                        FirebaseUtil.userCollection()
                            ?.document(nric)
                            ?.set(data)
                            ?.addOnSuccessListener {
                                user_result.value = VaccinatedUser(nric, fullName, dateOfVaccine, pw)
                                Log.d("TAG", "Data is Nice!")
                            }
                            ?.addOnFailureListener {
                                success = false
                                Log.d("TAG", "Data is Not Nice!")
                                val user = VaccinatedUser("", "", Timestamp.now(), "")
                                user_result?.value = user
                                e = it
                            }

                    } else {
                        Result.Error(Exception("Password is too small!"))
                        val user = VaccinatedUser("", "", Timestamp.now(), "3")
                        user_result?.value = user
                        Log.d("TAG", "Password Set to 3")
                    }

                }
            }?.addOnFailureListener {}
    }

    fun register_old(): Result<VaccinatedUser> {
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

    fun login(){
        val nric = NRIC.value!!.trim()
        val password = this.password.value!!
        var data: Map<String?, Any?>? = null
        var success = true

        FirebaseUtil.userCollection()?.document(nric)?.get()
            ?.addOnSuccessListener {
                data = it?.data
                if (data == null){
                    val user = VaccinatedUser("", "", Timestamp.now(), "")
                    user_result?.value = user
                    Result.Error(Exception("It seems you don't exist."))
                }
                else if ((data!!["password"] as String) == password) {
                    val fullName = data!!["fullName"] as String
                    val user = VaccinatedUser(nric, fullName, data!!["dateOfVaccine"] as Timestamp, password)
                    user_result?.value = user
                    Log.d("TAG", "Data is Correct!")
                } else {
                    val user = VaccinatedUser("", "", Timestamp.now(), "")
                    user_result?.value = user
                    Log.d("TAG", "Data is Wrong!")
                    Result.Error(Exception("It seems you don't exist."))
                }
            }?.addOnFailureListener {
                val user = VaccinatedUser("", "", Timestamp.now(), "")
                success = false
            }
    }

    fun login_old(): Result<VaccinatedUser> {
        return try {
            val nric = NRIC.value!!
            val password = this.password.value!!
            Log.d("TAG", nric + " " + password)
            var data: Map<String?, Any?>? = null
            var success = true
            var e: Exception = Exception()

            //FirebaseUtil.userCollection()?.get()?.addOnSuccessListener { result ->
            //    for (document in result) {
            //        Log.d("TAG", "${document.id} => ${document.data}")
            //    }
            //}
            //    ?.addOnFailureListener { exception ->
            //        Log.d("TAG", "Error getting documents.", exception)
            //    }

            FirebaseUtil.userCollection().document(nric).get()
                    .addOnSuccessListener {
                        data = it?.data
                        Log.d("TAG", "${data}")
                    }.addOnFailureListener {
                        success = false
                        e = it
                    Result.Error(e)
                    }

            Log.d("TAG", "Thing")
            if (!success) {
                Result.Error(e)
            } else {

                return if (data != null) {
                    if ((data!!["password"] as String) == password) {
                        Log.d("TAG", "Data is Correct!")
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

    //fun returnlogin(data: Map<String?, Any?>?, nric: String, password:String, success:Boolean, e: Exception): Result<VaccinatedUser> {
    //    if (!success) {
    //        Result.Error(e)
    //    } else {
//
    //        return if (data != null) {
    //            if ((data!!["password"] as String) == password) {
    //                val fullName = data!!["fullName"] as String
    //                val user = VaccinatedUser(nric, fullName, data!!["dateOfVaccine"] as Timestamp, password)
    //                Result.Success(user)
    //            } else {
    //                Result.Error(Exception("It seems you don't exist."))
    //            }
//
    //        } else {
    //            Result.Error(Exception("It seems you don't exist."))
    //        }
    //    }
    //}
}