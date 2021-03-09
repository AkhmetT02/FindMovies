package com.example.findmovies.views

import com.example.findmovies.models.GenreModel
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface CategoryView : MvpView {
    fun setupGenres(genres: List<GenreModel>)
}