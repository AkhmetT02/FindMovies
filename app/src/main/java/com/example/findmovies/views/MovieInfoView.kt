package com.example.findmovies.views

import com.example.findmovies.models.MovieModel
import com.example.findmovies.models.TrailerModel
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface MovieInfoView : MvpView {
    fun showError(error: String)
    fun setupMovie(movie: MovieModel)
    fun setupSimilarMovies(movies: List<MovieModel>)
    fun setupTrailers(trailers: List<TrailerModel>)
    fun showMovieFromDB(exist: Boolean)
    fun startLoading()
    fun endLoading()
}