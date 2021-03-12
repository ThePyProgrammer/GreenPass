package com.thepyprogrammer.greenpass.ui.main.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import com.thepyprogrammer.greenpass.R
import com.thepyprogrammer.greenpass.ui.image.ImageClickListener
import com.thepyprogrammer.greenpass.ui.main.ProfileViewModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.File
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class ProfileFragment : Fragment() {

    var circleImageView: CircleImageView? = null
    var imageInfoFile: File? = null
    lateinit var nameTextView: TextView
    lateinit var NRICTextView: TextView
    lateinit var emailTextView: TextView
    lateinit var dateTextView: TextView
    lateinit var button: Button
    private lateinit var viewModel: ProfileViewModel


    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        return root
    }

    override fun onStart() {
        super.onStart()
        loadImage()
    }

    override fun onResume() {
        super.onResume()
        loadImage()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
        exitTransition = inflater.inflateTransition(R.transition.fade)
        imageInfoFile = File(activity?.filesDir, "profileImageURI.txt")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel

        nameTextView = view?.findViewById(R.id.name)!!
        NRICTextView = view?.findViewById(R.id.nric)!!
        emailTextView = view?.findViewById(R.id.email)!!
        dateTextView = view?.findViewById(R.id.date)!!


        /**testing code for viewmodel
         * button = view?.findViewById(R.id.button2)!!

        button.setOnClickListener{
            viewModel.pName.value = viewModel.pName.value + "thing"
        }**/

        imageView.setOnClickListener(ImageClickListener(this))

        val nameObserver = Observer<String> { newName ->
            // Update the UI, in this case, a TextView.
            nameTextView.text = newName
        }
        val emailObserver = Observer<String> { newEmail ->
            // Update the UI, in this case, a TextView.
            emailTextView.text = newEmail
        }
        val NRICObserver = Observer<String> { newNRIC ->
            // Update the UI, in this case, a TextView.
            NRICTextView.text = newNRIC
        }
        val vacinatedDateObserver = Observer<Date> { newDate ->
            // Update the UI, in this case, a TextView.
            var format = SimpleDateFormat("dd/MM/yyy")
            dateTextView.text = format.format(newDate)
        }
        viewModel.pName.observe(getViewLifecycleOwner(), nameObserver)
        viewModel.email.observe(getViewLifecycleOwner(), emailObserver)
        viewModel.NRIC.observe(getViewLifecycleOwner(), NRICObserver)
        viewModel.date.observe(getViewLifecycleOwner(), vacinatedDateObserver)
    }

    fun readData(): String {
        if (!imageInfoFile!!.exists()) {
            return ""
        }
        val scanner = Scanner(imageInfoFile)
        val string = StringBuilder(scanner.nextLine())

        while (scanner.hasNextLine())
            string.append("\n" + scanner.nextLine())


        scanner.close()
        return string.toString()
    }

    fun loadImage() {
        var string: String = readData()
        if (!string.isEmpty()) {
            imageView!!.setImageURI(Uri.parse(readData()))
        } else {
            imageView!!.setImageResource(R.drawable.edden_face)
        }
    }


}