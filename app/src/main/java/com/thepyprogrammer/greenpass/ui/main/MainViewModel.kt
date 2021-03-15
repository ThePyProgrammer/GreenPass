package com.thepyprogrammer.greenpass.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thepyprogrammer.greenpass.model.Util
import java.util.*

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var image = MutableLiveData<String>()

    var pName = MutableLiveData("name")
    var NRIC = MutableLiveData("IC")
    var date = MutableLiveData(Date())

    fun getResultName() = pName

    fun getResultNRIC() = NRIC


    fun setNRIC(NRIC: String): Boolean {
        if (Util.checkNRIC(NRIC)) {
            this.NRIC.value = NRIC
        }
        return NRIC.matches(Util.nricRegex)
    }


}