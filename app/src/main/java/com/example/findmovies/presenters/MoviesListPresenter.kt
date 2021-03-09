package com.example.findmovies.presenters

import com.example.findmovies.models.MovieModel
import com.example.findmovies.providers.MoviesListProvider
import com.example.findmovies.views.MoviesListView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class MoviesListPresenter : MvpPresenter<MoviesListView>() {

    fun loadMovies(page: Int) {
        viewState.startLoading()
        MoviesListProvider(presenter = this).getMovies(page = page)
    }

    fun loadMoviesWithGenres(page: Int, genreId: Int) {
        viewState.startLoading()
        MoviesListProvider(presenter = this).getMoviesWithGenres(page = page, genreId = genreId)
    }

    fun finishLoadMovies(movies: List<MovieModel>) {
        viewState.endLoading()
        if (movies.isEmpty()) {
            viewState.setupEmptyMovies()
        } else {
            viewState.setupMovies(movies = movies)
        }
    }

    fun showError(error: String) {
        viewState.showError(error = error)
    }
}