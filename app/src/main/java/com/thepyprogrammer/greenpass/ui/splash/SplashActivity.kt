package com.thepyprogrammer.greenpass.ui.splash

import android.app.TaskStackBuilder
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.thepyprogrammer.greenpass.ui.intro.IntroActivity
import com.thepyprogrammer.greenpass.ui.main.MainActivity


class SplashActivity : AppCompatActivity() {
    override fun onStart() {
        super.onStart()
        TaskStackBuilder.create(this)
            .addNextIntentWithParentStack(Intent(this, MainActivity::class.java))
            .addNextIntent(Intent(this, IntroActivity::class.java))
            .startActivities()
    }
}