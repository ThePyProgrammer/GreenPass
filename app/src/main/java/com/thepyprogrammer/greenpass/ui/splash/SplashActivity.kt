package com.thepyprogrammer.greenpass.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.thepyprogrammer.greenpass.R
import com.thepyprogrammer.greenpass.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            val home = Intent(applicationContext, MainActivity::class.java)
            startActivity(home)
            finish()
        }, SPLASH_TIME_OUT.toLong())
    }

    companion object {
        private const val SPLASH_TIME_OUT = 3000
    }
}