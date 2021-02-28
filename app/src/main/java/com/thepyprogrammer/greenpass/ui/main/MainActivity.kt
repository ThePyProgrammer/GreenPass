package com.thepyprogrammer.greenpass.ui.main

import android.hardware.SensorManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.squareup.seismic.ShakeDetector
import com.thepyprogrammer.greenpass.R


class MainActivity : AppCompatActivity(), ShakeDetector.Listener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val sd = ShakeDetector(this)
        sd.start(sensorManager)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            val navController = findNavController(R.id.nav_host_fragment)
            navController.navigate(R.id.nav_pass)
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        // val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        val navView: NavigationView = findViewById(R.id.nav_view)
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
        // bottomNavigation.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem) =
        when(item.itemId) {
            R.id.action_settings -> {
                val navController = findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.nav_settings)
                true
            }
            else -> false
        }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun hearShake() {
        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigate(R.id.nav_pass)
    }
}