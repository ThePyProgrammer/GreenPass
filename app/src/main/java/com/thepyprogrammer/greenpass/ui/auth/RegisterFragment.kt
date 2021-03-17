package com.thepyprogrammer.greenpass.ui.auth

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Timestamp
import com.thepyprogrammer.greenpass.R
import com.thepyprogrammer.greenpass.model.Util
import com.thepyprogrammer.greenpass.model.Util.format
import com.thepyprogrammer.greenpass.model.account.Result
import com.thepyprogrammer.greenpass.model.account.VaccinatedUser
import com.thepyprogrammer.greenpass.model.firebase.FirebaseUtil
import com.thepyprogrammer.greenpass.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.nricInput
import kotlinx.android.synthetic.main.fragment_register.passwordInput
import java.time.LocalDate
import java.time.ZoneId
import java.util.*


class RegisterFragment : Fragment() {

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_register, container, false)

        val fullname = root.findViewById<TextInputEditText>(R.id.fullNameInput)
        val nric = root.findViewById<TextInputEditText>(R.id.nricInput)
        val password = root.findViewById<TextInputEditText>(R.id.passwordInput)
        val dateSelector = root.findViewById<Button>(R.id.dateSelector)
        val register = root.findViewById<Button>(R.id.register)
        val loading = root.findViewById<ProgressBar>(R.id.loading)
        val nricLayout = root.findViewById<TextInputLayout>(R.id.nricInputLayout)

        dateSelector.setOnClickListener {
            val datePickerDialog = context?.let { it1 ->
                DatePickerDialog(it1,
                        { _, year, monthOfYear, dayOfMonth ->
                            viewModel.date.value = Timestamp(
                                    Date.from(LocalDate.of(year, monthOfYear + 1, dayOfMonth)
                                            .atStartOfDay()
                                            .atZone(ZoneId.systemDefault())
                                            .toInstant()
                                    )
                            )
                            dateSelector.text = format.format(viewModel.date.value!!.toDate())

                        }, viewModel.date.value!!.toDate().year, viewModel.date.value!!.toDate().month - 1, viewModel.date.value!!.toDate().day)
            }
            datePickerDialog?.datePicker?.maxDate = System.currentTimeMillis() - 1000
            datePickerDialog?.datePicker?.minDate = Date(120, 12, 30).toInstant().toEpochMilli()
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

        nric.afterTextChanged {
//            viewModel.NRIC.value = it
            if (it.length != 9)
                nricLayout.error = "NRIC Length should be ${nricLayout.counterMaxLength}"
            else if (!Util.checkNRIC(it))
                nricLayout.error = "NRIC format is inaccurate"
            else
                nricLayout.error = null
        }

//
//        fullname.afterTextChanged {
//            viewModel.pName.value = it
//        }
//
//        nric.afterTextChanged {
//            viewModel.NRIC.value = it
//        }
//
//        password.afterTextChanged {
//            viewModel.password.value = it
//        }

        register.setOnClickListener {
            loading.visibility = View.VISIBLE
            viewModel.NRIC.value = nricInput.text.toString()
            viewModel.pName.value = fullNameInput.text.toString()
            viewModel.password.value = passwordInput.text.toString()
            viewModel.register()
        }

        val resultObserver =
<<<<<<< HEAD
            Observer<VaccinatedUser> { result ->
                if (viewModel.user_result?.value?.password == "old"){}
                else if (viewModel.user_result?.value?.password == ""){
                    loading.visibility = View.GONE
                    val sb =
                        view?.let { it1 -> Snackbar.make(it1, "NRIC Already Registered!", Snackbar.LENGTH_LONG) }
                    sb?.show()
                } else if (viewModel.user_result?.value?.password.equals("3")){
                    loading.visibility = View.GONE
                    val sb =
                        view?.let { it1 -> Snackbar.make(it1, "Password Length Too Short!", Snackbar.LENGTH_LONG) }
                    sb?.show()
                } else {
                    FirebaseUtil.user = viewModel.user_result?.value
                    viewModel.user_result?.value?.password?.let { Log.d("TAG", it) }
=======
            Observer<VaccinatedUser> {
                if (viewModel.user_result.value?.password != "") {
                    FirebaseUtil.user = viewModel.user_result.value
                    Log.d("TAG", "Data is Correct second!")
>>>>>>> 7a59adf6b57131a7e4e1138367e17e0946c98576
                    loading.visibility = View.GONE

                    startActivityForResult(Intent(activity, MainActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }, 0)

                    activity?.setResult(Activity.RESULT_OK)

                    //Complete and destroy login activity once successful
                    activity?.finish()
                }
            }
        viewModel.user_result.observe(viewLifecycleOwner,resultObserver)

        return root
    }
}