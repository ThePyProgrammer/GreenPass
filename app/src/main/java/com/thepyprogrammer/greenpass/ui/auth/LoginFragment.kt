package com.thepyprogrammer.greenpass.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Timestamp
import com.thepyprogrammer.greenpass.R
import com.thepyprogrammer.greenpass.model.Util
import com.thepyprogrammer.greenpass.model.account.Result
import com.thepyprogrammer.greenpass.model.account.VaccinatedUser
import com.thepyprogrammer.greenpass.model.firebase.FirebaseUtil
import com.thepyprogrammer.greenpass.ui.main.MainActivity


class LoginFragment : Fragment() {

    private lateinit var viewModel: AuthViewModel
    lateinit var nric: TextInputEditText
    lateinit var password: TextInputEditText
    lateinit var login: Button
    lateinit var esc: Button
    lateinit var loading: ProgressBar
    lateinit var nricLayout: TextInputLayout



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_login, container, false)


        nric = root.findViewById<TextInputEditText>(R.id.nricInput)
        password = root.findViewById<TextInputEditText>(R.id.passwordInput)
        login = root.findViewById<Button>(R.id.login)
        esc = root.findViewById<Button>(R.id.escape)
        loading = root.findViewById<ProgressBar>(R.id.loading)
        nricLayout = root.findViewById<TextInputLayout>(R.id.nricInputLayout)

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
            viewModel.NRIC.value = nric.text.toString()
            viewModel.password.value = password.text.toString()
            loading.visibility = View.VISIBLE
            viewModel.login()
        }

        esc.setOnClickListener {
            FirebaseUtil.user = VaccinatedUser(
                "S1234567D",
                "Prannay Gupta",
                Timestamp.now(),
                "helloWorld"
            )
            startActivity(Intent(activity, MainActivity::class.java))
        }

        return root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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

        val resultObserver =
            Observer<VaccinatedUser> { result ->
                if (viewModel.user_result?.value?.password == ""){}
                else {
                    FirebaseUtil.user = viewModel.user_result?.value
                    Log.d("TAG", "Data is Correct second!")
                    startActivity(Intent(activity, MainActivity::class.java))
                }
            }
        viewModel.user_result?.observe(getViewLifecycleOwner(),resultObserver);
    }

}

