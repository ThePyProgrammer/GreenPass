package com.thepyprogrammer.greenpass.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thepyprogrammer.greenpass.model.Validate
import java.util.*

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var image = MutableLiveData<String>()

    var pName = MutableLiveData("name")
    var NRIC = MutableLiveData("IC")
    var email = MutableLiveData("email")
    var date = MutableLiveData(Date())

    fun getResultName() = pName

    fun getResultNRIC() = NRIC

    fun getResultEmail() = email



    fun setNRIC(NRIC: String): Boolean {
        if (Validate.checkNRIC(NRIC)){
            this.NRIC.value = NRIC
        }
        return NRIC.matches(Validate.nricRegex)
    }

    fun setEmail(email: String): Boolean {
        if (Validate.checkEmail(email)){
            this.email.value = email
        }
        return email.matches(Validate.emailRegex)
    }



}