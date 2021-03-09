package com.example.findmovies.presenters

import android.content.Context
import com.example.findmovies.models.MovieModel
import com.example.findmovies.models.TrailerModel
import com.example.findmovies.providers.MovieInfoProvider
import com.example.findmovies.views.MovieInfoView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class MovieInfoPresenter : MvpPresenter<MovieInfoView>() {

    fun loadMovie(movieId: Int, context: Context) {
        viewState.startLoading()
        MovieInfoProvider(presenter = this, context = context).loadMovie(movieId = movieId)
    }

    fun insertMovie(movie: MovieModel, context: Context) {
        MovieInfoProvider(presenter = this, context = context).insertMovie(movie = movie)
    }
    fun deleteMovieById(movieId: Int, context: Context) {
        MovieInfoProvider(presenter = this, context = context).deleteMovieById(movieId = movieId)
    }
    fun getMovieById(movieId: Int, context: Context) {
        MovieInfoProvider(presenter = this, context = context).getMovieById(movieId = movieId)
    }

    fun finishLoadMovie(movie: MovieModel) {
        viewState.endLoading()
        viewState.setupMovie(movie = movie)
    }
    fun finishLoadSimilarMovies(movies: List<MovieModel>) {
        viewState.setupSimilarMovies(movies)
    }
    fun finishLoadTrailers(trailers: List<TrailerModel>) {
        viewState.setupTrailers(trailers)
    }
    fun finishLoadMovieFromDB(exist: Boolean) {
        viewState.showMovieFromDB(exist)
    }
    fun showError(error: String) {
        viewState.showError(error = error)
    }
}