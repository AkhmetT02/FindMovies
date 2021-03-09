package com.example.findmovies.providers

import com.example.findmovies.di.App
import com.example.findmovies.network.NetworkHelper.API_KEY
import com.example.findmovies.network.NetworkHelper.LANGUAGE
import com.example.findmovies.network.NetworkHelper.SORT_BY_POPULARITY
import com.example.findmovies.network.RetrofitService
import com.example.findmovies.presenters.MoviesListPresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MoviesListProvider(private val presenter: MoviesListPresenter) {

    @Inject lateinit var mService: RetrofitService

    init {
        App.appComponent?.inject(this)
    }

    fun getMovies(page: Int) {
        mService.getMovieList(apiKey = API_KEY, language = LANGUAGE, sortBy = SORT_BY_POPULARITY, page = page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    presenter.finishLoadMovies(movies = it.results)
                }, {
                    presenter.showError(error = it.message!!)
                })
    }
    fun getMoviesWithGenres(page: Int, genreId: Int) {
        mService.getMoviesWithGenres(apiKey = API_KEY, language = LANGUAGE, sortBy = SORT_BY_POPULARITY, page = page, genre = genreId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    presenter.finishLoadMovies(movies = it.results)
                }, {
                    presenter.showError(error = it.message!!)
                })
    }
}