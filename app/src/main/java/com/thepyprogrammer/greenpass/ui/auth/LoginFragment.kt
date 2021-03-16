package com.thepyprogrammer.greenpass.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.thepyprogrammer.greenpass.R
import com.thepyprogrammer.greenpass.model.Util
import com.thepyprogrammer.greenpass.model.account.Result
import com.thepyprogrammer.greenpass.model.firebase.FirebaseUtil
import com.thepyprogrammer.greenpass.ui.main.MainActivity
import com.thepyprogrammer.greenpass.ui.scanner.QRCodeScanner


class LoginFragment : Fragment() {

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_login, container, false)


        val nric = root.findViewById<TextInputEditText>(R.id.nricInput)
        val password = root.findViewById<TextInputEditText>(R.id.passwordInput)
        val login = root.findViewById<Button>(R.id.login)
        val loading = root.findViewById<ProgressBar>(R.id.loading)
        val nricLayout = root.findViewById<TextInputLayout>(R.id.nricInputLayout)

        /**View Model**/
        viewModel = activity?.let { ViewModelProvider(it).get(AuthViewModel::class.java) }!!

        val nricObserver = Observer<String> { newNric ->
            nric.setText(newNric)
        }
        val passwordObserver = Observer<String> { newPassword ->
            password.setText(newPassword)
        }

        viewModel.NRIC.observe(requireActivity(), nricObserver)
        viewModel.password.observe(requireActivity(), passwordObserver)

        nric.afterTextChanged {
//            viewModel.NRIC.value = it
            if (it.length != 9)
                nricLayout.error = "NRIC Length should be ${nricLayout.counterMaxLength}"
            else if (!Util.checkNRIC(it))
                nricLayout.error = "NRIC format is inaccurate"
            else
                nricLayout.error = null;
        }
//
//        password.afterTextChanged {
//            viewModel.password.value = it
//        }

        login.setOnClickListener {
            loading.visibility = View.VISIBLE
            val result = viewModel.login()
            if (result is Result.Success) {
                val user = result.data
                FirebaseUtil.user = user
                startActivity(Intent(activity, MainActivity::class.java))
            }
        }

        return root
    }

}

