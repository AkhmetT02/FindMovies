package com.example.findmovies.di.modules

import com.example.findmovies.adapters.MovieAdapter
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

@Module(includes = [PicassoModule::class])
class MovieAdapterModule {

    @Provides
    fun provideAdapter(picasso: Picasso): MovieAdapter {
        return MovieAdapter(picasso)
    }
}