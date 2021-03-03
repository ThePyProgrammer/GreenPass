package com.thepyprogrammer.greenpass.ui.main

import android.hardware.SensorManager
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.squareup.seismic.ShakeDetector
import com.thepyprogrammer.greenpass.R


class MainActivity : AppCompatActivity(), ShakeDetector.Listener {

    var shakeToOpen = true

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        val bottomAppBar: BottomAppBar = findViewById(R.id.bottomAppBar)
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)

        setSupportActionBar(toolbar)

        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val sd = ShakeDetector(this)
        sd.start(sensorManager)

        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_profile, R.id.nav_pass, R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val actionBarDrawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.openDrawer,
            R.string.closeDrawer
        ) {

        }

        //Setting the actionbarToggle to drawer layout

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle)

        //calling sync state is necessary or else your hamburger icon wont show up

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState()


        bottomNavigation.setupWithNavController(navController)
        bottomNavigation.menu.getItem(1).isEnabled = false
        bottomNavigation.background = null

        // viewPager.setAdapter(PageAdapter(this))


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

        fab.setOnClickListener { view ->
            val navController = findNavController(R.id.nav_host_fragment)
            navController.navigate(R.id.nav_pass)
        }
    }


//    inner class PageAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
//        override fun createFragment(position: Int): Fragment {
//            when(position) {
//                0 -> navController.navigate(R.id.nav_profile)
//                1 -> navController.navigate(R.id.nav_pass)
//                2 -> navController.navigate(R.id.nav_settings)
//            }
//            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
//            return navHostFragment?.childFragmentManager?.fragments?.get(0)!!
//
//        }
//
//        override fun getItemCount(): Int = 3
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.overflow_menu, menu)

        val item: MenuItem = menu.findItem(R.id.action_settings)
        val builder = SpannableStringBuilder("* Settings")
        // replace "*" with icon

        builder.setSpan(
            ImageSpan(this, R.drawable.ic_settings),
            0,
            1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )


        item.title = builder

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_settings -> {
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.nav_settings)
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

    override fun hearShake() {
        if (shakeToOpen) {
            val navController = findNavController(R.id.nav_host_fragment)
            navController.navigate(R.id.nav_pass)
        }
    }
}