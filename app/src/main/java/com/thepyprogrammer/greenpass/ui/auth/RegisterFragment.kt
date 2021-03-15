package com.thepyprogrammer.greenpass.ui.auth

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.Timestamp
import com.thepyprogrammer.greenpass.R
import com.thepyprogrammer.greenpass.model.Util.format
import com.thepyprogrammer.greenpass.model.account.Result
import com.thepyprogrammer.greenpass.model.firebase.FirebaseUtil
import com.thepyprogrammer.greenpass.ui.main.MainActivity
import java.util.Date

class RegisterFragment : Fragment() {

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_login, container, false)

        val fullname = root.findViewById<EditText>(R.id.fullNameInput)
        val nric = root.findViewById<EditText>(R.id.nricInput)
        val password = root.findViewById<EditText>(R.id.passwordInput)
        val dateSelector = root.findViewById<Button>(R.id.dateSelector)
        val login = root.findViewById<Button>(R.id.login)
        val loading = root.findViewById<ProgressBar>(R.id.loading)

        dateSelector.setOnClickListener {
            val datePickerDialog = context?.let { it1 ->
                DatePickerDialog(it1,
                        { _, year, monthOfYear, dayOfMonth ->
                            viewModel.date.value = Timestamp(Date(year, monthOfYear + 1, dayOfMonth))
                            dateSelector.text = format.format(viewModel.date.value!!.toDate())

                        }, viewModel.date.value!!.toDate().year, viewModel.date.value!!.toDate().month - 1, viewModel.date.value!!.toDate().day)
            }
            datePickerDialog?.datePicker?.minDate = System.currentTimeMillis() - 1000
            datePickerDialog?.show()
        }

        /**View Model**/
        viewModel = activity?.let { ViewModelProvider(it).get(AuthViewModel::class.java) }!!

        val nameObserver = Observer<String>{ newName ->
            fullname.setText(newName)
        }

        val nricObserver = Observer<String>{ newNric ->
            nric.setText(newNric)
        }

        val passwordObserver = Observer<String> { newPassword ->
            password.setText(newPassword)
        }

        val dateObserver = Observer<Timestamp> { newDate ->
            dateSelector.text = format.format(newDate.toDate())
        }

        viewModel.pName.observe(requireActivity(), nameObserver)
        viewModel.NRIC.observe(requireActivity(), nricObserver)
        viewModel.password.observe(requireActivity(), passwordObserver)
        viewModel.date.observe(requireActivity(), dateObserver)


        fullname.afterTextChanged {
            viewModel.pName.value = it
        }

        nric.afterTextChanged {
            viewModel.NRIC.value = it
        }

        password.afterTextChanged {
            viewModel.password.value = it
        }

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