package com.thepyprogrammer.greenpass.ui.main.settings

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import androidx.preference.SwitchPreferenceCompat
import com.thepyprogrammer.greenpass.R
import com.thepyprogrammer.greenpass.ui.main.MainActivity

class SettingsFragment : PreferenceFragmentCompat() {


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val shakePreference: SwitchPreferenceCompat? = findPreference("shake")
        shakePreference?.setOnPreferenceChangeListener { preference: Preference, any: Any ->
            (activity as MainActivity).shakeToOpen = any as Boolean
            true
        }
    }
}