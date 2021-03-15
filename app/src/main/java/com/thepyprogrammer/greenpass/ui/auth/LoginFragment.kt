package com.thepyprogrammer.greenpass.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.thepyprogrammer.greenpass.R


class LoginFragment : Fragment() {

    private lateinit var authViewModel: LoginViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_login, container, false)


        val username = root.findViewById<EditText>(R.id.nricInput)
        val password = root.findViewById<EditText>(R.id.passwordInput)
        val login = root.findViewById<Button>(R.id.login)
        val loading = root.findViewById<ProgressBar>(R.id.loading)




//
//        authViewModel = ViewModelProvider(this, LoginViewModelFactory())
//                .get(LoginViewModel::class.java)
//
//        authViewModel.loginFormState.observe(viewLifecycleOwner, Observer {
//            val loginState = it ?: return@Observer
//
//            // disable login button unless both username / password is valid
//            login.isEnabled = loginState.isDataValid
//
//            if (loginState.usernameError != null) {
//                username.error = getString(loginState.usernameError)
//            }
//            if (loginState.passwordError != null) {
//                password.error = getString(loginState.passwordError)
//            }
//        })
//
//        authViewModel.loginResult.observe(viewLifecycleOwner, Observer {
//            val loginResult = it ?: return@Observer
//
//            loading.visibility = View.GONE
//            if (loginResult.error != null) {
//                showLoginFailed(loginResult.error)
//            }
//            if (loginResult.success != null) {
//                updateUiWithUser(loginResult.success)
//            }
//
//        })
//
//        username.afterTextChanged {
//            authViewModel.loginDataChanged(
//                    username.text.toString(),
//                    password.text.toString()
//            )
//        }
//
//        password.apply {
//            afterTextChanged {
//                authViewModel.loginDataChanged(
//                        username.text.toString(),
//                        password.text.toString()
//                )
//            }
//
//            setOnEditorActionListener { _, actionId, _ ->
//                when (actionId) {
//                    EditorInfo.IME_ACTION_DONE ->
//                        authViewModel.login(
//                                username.text.toString(),
//                                password.text.toString()
//                        )
//                }
//                false
//            }
//
//            login.setOnClickListener {
//                loading.visibility = View.VISIBLE
//                authViewModel.login(username.text.toString(), password.text.toString())
//            }
//        }
        return root
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
                activity,
                "$welcome $displayName",
                Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(activity, errorString, Toast.LENGTH_SHORT).show()
    }

}