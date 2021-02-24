package com.thepyprogrammer.greenpass.ui.loginRegister.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thepyprogrammer.greenpass.model.loginRegister.login.LoginDataSource
import com.thepyprogrammer.greenpass.model.loginRegister.login.LoginRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepository(
                    dataSource = LoginDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}