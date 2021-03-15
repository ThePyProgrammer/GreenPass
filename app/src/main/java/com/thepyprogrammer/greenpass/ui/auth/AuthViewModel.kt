package com.thepyprogrammer.greenpass.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import java.util.*

class AuthViewModel(): ViewModel() {

    var pName = MutableLiveData("name")
    var NRIC = MutableLiveData("IC")
    var date = MutableLiveData(Timestamp.now())
    var password = MutableLiveData("")

    fun login() {

    }
}