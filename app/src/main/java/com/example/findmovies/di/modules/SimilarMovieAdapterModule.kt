package com.example.findmovies.di.modules

import com.example.findmovies.adapters.SimilarMovieAdapter
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

@Module(includes = [PicassoModule::class])
class SimilarMovieAdapterModule {

    @Provides
    fun provideSimilarMovieAdapter(picasso: Picasso): SimilarMovieAdapter {
        return SimilarMovieAdapter(picasso)
    }
}