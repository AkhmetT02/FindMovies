package com.example.findmovies.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.findmovies.R
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingFragment : Fragment() {

    private lateinit var darkModeSwitch: SwitchMaterial
    private lateinit var darkModeSwitchLt: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        darkModeSwitch = view.findViewById(R.id.dark_mode_switch)
        darkModeSwitchLt = view.findViewById(R.id.dark_mode_switch_lt)


        val sharedPreferences = activity?.getSharedPreferences(getString(R.string.shared_pref_name), Context.MODE_PRIVATE)
        val isChecked = sharedPreferences?.getBoolean(getString(R.string.dark_mode_key), false)

        darkModeSwitch.isChecked = isChecked!!

        darkModeSwitchLt.setOnClickListener {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                darkModeSwitch.isChecked = false
                sharedPreferences.edit().putBoolean(getString(R.string.dark_mode_key), false).apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                darkModeSwitch.isChecked = true
                sharedPreferences.edit().putBoolean(getString(R.string.dark_mode_key), true).apply()
            }
        }

        return view
    }
}