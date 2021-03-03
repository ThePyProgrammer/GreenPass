package com.thepyprogrammer.greenpass.ui.main.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import androidx.transition.TransitionInflater
import com.thepyprogrammer.greenpass.R
import com.thepyprogrammer.greenpass.ui.main.MainActivity

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
        exitTransition = inflater.inflateTransition(R.transition.fade)
    }


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val shakePreference: SwitchPreferenceCompat? = findPreference("shake")
        val themePreference: SwitchPreferenceCompat? = findPreference("theme")

        shakePreference?.setOnPreferenceChangeListener { _: Preference, any: Any ->
            (activity as MainActivity).shakeToOpen = any as Boolean
            true
        }


        themePreference?.setOnPreferenceChangeListener { _: Preference, any: Any ->
            AppCompatDelegate.setDefaultNightMode(if (any as Boolean) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
            true
        }
    }
}