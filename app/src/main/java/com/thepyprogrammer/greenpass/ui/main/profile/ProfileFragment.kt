package com.thepyprogrammer.greenpass.ui.main.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import com.thepyprogrammer.greenpass.R
import com.thepyprogrammer.greenpass.ui.main.profile.image.ImageDetailsActivity
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.imageView
import kotlinx.android.synthetic.main.nav_header_main.*
import java.io.File
import java.lang.StringBuilder
import java.util.*

class ProfileFragment : Fragment() {

    var circleImageView:CircleImageView? = null
    var imageInfoFile: File? = null;

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
        exitTransition = inflater.inflateTransition(R.transition.fade)
        imageInfoFile = File(activity?.getFilesDir(), "profileImageURI.txt")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel

        imageView.setOnClickListener { view ->
            val intent = Intent(context, ImageDetailsActivity::class.java)
            startActivity(intent)
        }
    }

    fun readData(): String {
        if (!imageInfoFile!!.exists()){
            return "";
        }
        val scanner = Scanner(imageInfoFile)
        val string = StringBuilder(scanner.nextLine())

        while (scanner.hasNextLine())
            string.append("\n"+scanner.nextLine())


        scanner.close()
        return string.toString()
    }

    fun loadImage(){
        var string: String  = readData();
        if (!string.isEmpty()){
            imageView!!.setImageURI(Uri.parse(readData()))
        }
        else{
            imageView!!.setImageResource(R.drawable.edden_face)
        }

    }



}