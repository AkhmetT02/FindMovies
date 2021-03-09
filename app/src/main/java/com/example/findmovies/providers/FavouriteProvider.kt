package com.example.findmovies.providers

import android.content.Context
import android.util.Log
import com.example.findmovies.db.MovieDatabase
import com.example.findmovies.presenters.FavouritePresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class FavouriteProvider(private val presenter: FavouritePresenter, context: Context) {

    private val database = MovieDatabase.getInstance(context = context)

    fun loadMovies() {
        database.getMovieDao.getAllMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                presenter.finishLoadMovies(movies = it)
                Log.e("TAG", "loadMoviesFavouriteSuccess: ")
            }, {
                presenter.showError(error = it.message!!)
                Log.e("TAG", "loadMoviesFavouriteFailed: ")
            })
    }

}