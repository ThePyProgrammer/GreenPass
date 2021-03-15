package com.thepyprogrammer.greenpass.ui.main

import android.content.Intent
import android.graphics.Color
import android.hardware.SensorManager
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.infideap.drawerbehavior.AdvanceDrawerLayout
import com.squareup.seismic.ShakeDetector
import com.thepyprogrammer.greenpass.R
import com.thepyprogrammer.greenpass.model.firebase.FirebaseUtil
import com.thepyprogrammer.greenpass.ui.image.ImageClickListener
import com.thepyprogrammer.greenpass.ui.main.pass.PassFragment
import com.thepyprogrammer.greenpass.ui.main.profile.ProfileFragment
import com.thepyprogrammer.greenpass.ui.main.settings.SettingsFragment
import com.thepyprogrammer.greenpass.ui.scanner.QRCodeScanner
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.File
import java.util.*


class MainActivity : AppCompatActivity(), ShakeDetector.Listener {
    var shakeToOpen = true

    private var imageView: CircleImageView? = null
    private var imageNavMenuView: CircleImageView? = null

    private var imageInfoFile: File? = null

    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView

    private lateinit var viewModel: MainViewModel

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

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
        setContentView(R.layout.activity_main)

        FirebaseUtil.firestore?.collection("users")

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val drawerLayout: AdvanceDrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        val bottomAppBar: BottomAppBar = findViewById(R.id.bottomAppBar)

        imageInfoFile = File(filesDir, "profileImageURI.txt")

        FirebaseUtil.firestore?.collection("users")

        setSupportActionBar(toolbar)

        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        ShakeDetector(this).start(sensorManager)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_profile, R.id.nav_pass, R.id.nav_settings
            ), drawerLayout
        )

        navController = findNavController(R.id.nav_host_fragment).apply {
            setupActionBarWithNavController(this, appBarConfiguration)
            navView.setupWithNavController(this)
        }

        val actionBarDrawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.openDrawer,
            R.string.closeDrawer
        ) {}

        drawerLayout.setDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        drawerLayout.apply {
            setViewScale(GravityCompat.START, 0.9f) //set height scale for main view (0f to 1f)
            setViewElevation(GravityCompat.START, 20F) //set main view elevation when drawer open (dimension)
            setViewScrimColor(GravityCompat.START, Color.TRANSPARENT) //set drawer overlay coloe (color)
            drawerElevation = 20F //set drawer elevation (dimension)
            setContrastThreshold(3F) //set maximum of contrast ratio between white text and background color.
            setRadius(GravityCompat.START, 25F) //set end container's corner radius (dimension)
        }


        navView.getHeaderView(0).apply {
            imageNavMenuView = findViewById<CircleImageView>(R.id.imageView).also {
                it.setOnClickListener(ImageClickListener(this@MainActivity))
            }
        }

        loadImage()

        findViewById<BottomNavigationView>(R.id.bottom_navigation).apply {
            setupWithNavController(navController)
            menu.getItem(1).isEnabled = false
            background = null
        }


        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_profile, R.id.nav_settings -> {
                    showBottom(bottomAppBar)
                    fab.visibility = View.VISIBLE
                }
                else -> {
                    hideBottom(bottomAppBar)
                    fab.visibility = View.INVISIBLE
                }
            }
        }

        fab.setOnClickListener {
            navigateToPass()
        }


        val header = navView.getHeaderView(0)

        nameTextView = header.findViewById(R.id.nameicView)
        emailTextView = header.findViewById(R.id.emailView)

        /**View Model**/
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val nameObserver = Observer<String> { newName -> nameTextView.text = newName }
        val emailObserver = Observer<String> { newEmail -> emailTextView.text = newEmail }

        viewModel.pName.observe(this, nameObserver)
        viewModel.email.observe(this, emailObserver)

    }

    override fun onCreateOptionsMenu(menu: Menu) = run { // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.overflow_menu, menu)

        menu.findItem(R.id.action_settings).apply {
            title = SpannableStringBuilder("* Settings").also {
                it.setSpan( // replace "*" with icon
                        ImageSpan(this@MainActivity, R.drawable.ic_settings),
                        0,
                        1,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        menu.findItem(R.id.action_scanner).apply {
            title = SpannableStringBuilder("* Scanner").also {
                it.setSpan( // replace "*" with icon
                        ImageSpan(this@MainActivity, R.drawable.ic_qr_code_scanner),
                        0,
                        1,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        true
    }


    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                R.id.action_settings -> {
                    navigateToSettings()
                }
                R.id.action_scanner -> {
                    startActivity(Intent(this, QRCodeScanner::class.java))
                    true
                }
                else -> false
            }

    private fun hideBottom(view: BottomAppBar) {
        view.clearAnimation()
        view.animate().translationY(view.height.toFloat()).duration = 300
    }

    private fun showBottom(view: BottomAppBar) {
        view.clearAnimation()
        view.animate().translationY(0f).duration = 300
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private val currentFragment: Fragment
        get() = (
                supportFragmentManager
                        .findFragmentById(R.id.nav_host_fragment)?.childFragmentManager?.fragments?.get(0)
                )!!

    private fun navigateToProfile() =
        if ((currentFragment !is ProfileFragment)) {
            val navController = findNavController(R.id.nav_host_fragment)
            navController.navigate(R.id.nav_profile)
            true
        } else false

    private fun navigateToPass() =
        if ((currentFragment !is PassFragment)) {
            val navController = findNavController(R.id.nav_host_fragment)
            navController.navigate(R.id.nav_pass)
            true
        } else false

    private fun navigateToSettings() =
        if ((currentFragment !is SettingsFragment)) {
            val navController = findNavController(R.id.nav_host_fragment)
            navController.navigate(R.id.nav_settings)
            true
        } else false


    override fun hearShake() {
        if (shakeToOpen) {
            navigateToPass()
        }
    }

    private fun readData(): String {
        if (!imageInfoFile!!.exists()) return ""

        val scanner = Scanner(imageInfoFile)
        val string = StringBuilder(scanner.nextLine())

        while (scanner.hasNextLine())
            string.append("\n" + scanner.nextLine())

        scanner.close()
        return string.toString()
    }

    private fun loadImage() {
        val string: String = readData()
        if (string.isNotEmpty()) {
            imageView?.setImageURI(Uri.parse(readData()))
            imageNavMenuView?.setImageURI(Uri.parse(readData()))
        } else {
            imageView?.setImageResource(R.drawable.face)
            imageNavMenuView?.setImageResource(R.drawable.face)
        }
    }


}