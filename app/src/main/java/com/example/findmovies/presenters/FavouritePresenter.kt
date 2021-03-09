package com.example.findmovies.presenters

import android.content.Context
import com.example.findmovies.models.MovieModel
import com.example.findmovies.providers.FavouriteProvider
import com.example.findmovies.views.FavouriteView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class FavouritePresenter : MvpPresenter<FavouriteView>() {
    fun loadMovies(context: Context) {
        FavouriteProvider(presenter = this, context = context).loadMovies()
    }

    fun finishLoadMovies(movies: List<MovieModel>) {
        if (movies.isEmpty()) {
            viewState.setupEmptyMovies()
        } else {
            viewState.setupMovies(movies)
        }
    }
    fun showError(error: String) {
        viewState.showError(error)
    }
}