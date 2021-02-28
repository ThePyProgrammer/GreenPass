package com.thepyprogrammer.greenpass.ui.main.settings

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.thepyprogrammer.greenpass.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val shakePreference: SwitchPreference? = findPreference("shake")
        shakePreference?.setOnPreferenceChangeListener { preference: Preference, any: Any ->
            
            true
        }
    }
}