package com.example.findmovies.presenters

import com.example.findmovies.models.GenreModel
import com.example.findmovies.providers.CategoryProvider
import com.example.findmovies.views.CategoryView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class CategoryPresenter : MvpPresenter<CategoryView>() {

    val provider = CategoryProvider(presenter = this)

    fun loadGenres() {
        provider.loadGenres()
    }

    fun finishLoadGenres(genres: List<GenreModel>) {
        viewState.setupGenres(genres)
    }

    fun disposeAll() {
        provider.dispose()
    }

    fun showError(error: String) {

    }
}