package com.thepyprogrammer.greenpass.ui.main.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.thepyprogrammer.greenpass.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}