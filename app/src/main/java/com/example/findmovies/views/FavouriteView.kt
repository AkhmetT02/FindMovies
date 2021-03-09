package com.example.findmovies.views

import com.example.findmovies.models.MovieModel
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface FavouriteView : MvpView {
    fun showError(error: String)
    fun setupMovies(movies: List<MovieModel>)
    fun setupEmptyMovies()
}