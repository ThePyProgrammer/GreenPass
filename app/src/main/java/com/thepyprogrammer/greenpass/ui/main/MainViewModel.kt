package com.thepyprogrammer.greenpass.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var image = MutableLiveData<String>()

    var pName = MutableLiveData<String>("name")
    var NRIC = MutableLiveData<String>("IC")
    var email = MutableLiveData<String>("email")
    var date = MutableLiveData<Date>(Date())

    fun checkNRIC(NRIC_: String): Boolean {
        return NRIC_.matches(Regex("[ST]\\d{7}[A-Z]"))
    }

    fun checkEmail(email_: String): Boolean {
        return email_.matches(Regex("\\S+@\\S+"))
    }

    fun getResultName(): MutableLiveData<String> {
        return pName
    }

    fun getResultNRIC(): MutableLiveData<String> {
        return NRIC
    }

    fun getResultEmail(): MutableLiveData<String> {
        return email
    }


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