package com.thepyprogrammer.greenpass.ui.auth

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.google.firebase.Timestamp
import com.thepyprogrammer.greenpass.R
import com.thepyprogrammer.greenpass.model.Util.format
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.*

class RegisterFragment : Fragment() {
    private var dateSelected: Timestamp = Timestamp.now()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_login, container, false)

        val fullname = root.findViewById<EditText>(R.id.fullNameInput)
        val username = root.findViewById<EditText>(R.id.nricInput)
        val password = root.findViewById<EditText>(R.id.passwordInput)
        val dateSelector = root.findViewById<Button>(R.id.dateSelector)
        val login = root.findViewById<Button>(R.id.login)
        val loading = root.findViewById<ProgressBar>(R.id.loading)

        dateSelector.setOnClickListener {
            val datePickerDialog = context?.let { it1 ->
                DatePickerDialog(it1,
                        { _, year, monthOfYear, dayOfMonth ->
                            dateSelected = Timestamp(Date(year, monthOfYear + 1, dayOfMonth))
                            dateSelector.text = format.format(dateSelected.toDate())
                        }, dateSelected.toDate().year, dateSelected.toDate().month - 1, dateSelected.toDate().day)
            }
            datePickerDialog?.datePicker?.minDate = System.currentTimeMillis() - 1000
            datePickerDialog?.show()
        }

        return root
    }
}