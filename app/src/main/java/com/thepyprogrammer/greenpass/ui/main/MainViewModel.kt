package com.thepyprogrammer.greenpass.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var image = MutableLiveData<String>()

    var pName = MutableLiveData("name")
    var NRIC = MutableLiveData("IC")
    var email = MutableLiveData("email")
    var date = MutableLiveData(Date())

    fun checkNRIC(NRIC: String): Boolean {
        return NRIC.matches(Regex("[ST]\\d{7}[A-Z]"))
    }

    fun checkEmail(email: String): Boolean {
        return email.matches(Regex("\\S+@\\S+"))
    }

    fun getResultName() = pName

    fun getResultNRIC() = NRIC

    fun getResultEmail() = email


    /*
    fun setNRIC(NRIC_: String): Boolean {
        if (NRIC_.matches(Regex("[ST]\\d{7}[A-Z]"))){
            NRIC.value = NRIC_
        }
        return NRIC_.matches(Regex("[ST]\\d{7}[A-Z]"))
    }

    fun setEmail(email_: String): Boolean {
        if (email_.matches(Regex("\\S+@\\S+"))){
            email.value = email_
        }
        return email_.matches(Regex("\\S+@\\S+"))
    }
     */


}