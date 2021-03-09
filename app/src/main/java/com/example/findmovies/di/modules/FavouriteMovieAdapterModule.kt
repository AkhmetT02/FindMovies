package com.example.findmovies.di.modules

import com.example.findmovies.adapters.FavouriteMovieAdapter
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

@Module(includes = [PicassoModule::class])
class FavouriteMovieAdapterModule {

    @Provides
    fun provideFavouriteMovieAdapter(picasso: Picasso): FavouriteMovieAdapter {
        return FavouriteMovieAdapter(picasso = picasso)
    }
}