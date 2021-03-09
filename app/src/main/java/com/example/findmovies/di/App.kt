package com.example.findmovies.di

import android.app.Application

class App : Application() {

    companion object {
        var appComponent: AppComponent? = null
            get() = field
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().build()
    }
}