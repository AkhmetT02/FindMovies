package com.example.findmovies.di.modules

import com.example.findmovies.adapters.TrailerAdapter
import dagger.Module
import dagger.Provides

@Module
class TrailerAdapterModule {

    @Provides
    fun provideTrailerAdapter(): TrailerAdapter {
        return TrailerAdapter()
    }
}