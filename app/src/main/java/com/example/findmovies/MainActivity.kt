package com.example.findmovies

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.main_toolbar))
        supportActionBar?.hide()

        val sharedPreferences = getSharedPreferences(getString(R.string.shared_pref_name), Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean(getString(R.string.dark_mode_key), false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        val nightModeFlags: Int = applicationContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        //if ui node is night || app mode is night then change action bar drawable to black
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES
            || AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            supportActionBar?.setBackgroundDrawable(ResourcesCompat.getDrawable(resources, R.drawable.action_bar_drawable_night, theme))
        }

        bottomNavigation = findViewById(R.id.bottom_navigation)
        navController = findNavController(R.id.main_nav_host)
        bottomNavigation.setupWithNavController(navController)


        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.homeFragment,
            R.id.categoryFragment,
            R.id.favouriteFragment
        ))

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}