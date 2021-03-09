package com.example.findmovies.providers

import android.content.Context
import android.util.Log
import com.example.findmovies.db.MovieDatabase
import com.example.findmovies.di.App
import com.example.findmovies.models.MovieModel
import com.example.findmovies.network.NetworkHelper.API_KEY
import com.example.findmovies.network.NetworkHelper.LANGUAGE
import com.example.findmovies.network.RetrofitService
import com.example.findmovies.presenters.MovieInfoPresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieInfoProvider(private val presenter: MovieInfoPresenter, context: Context) {

    @Inject lateinit var mService: RetrofitService

    private val database = MovieDatabase.getInstance(context = context)

    init {
        App.appComponent?.inject(this)
    }

    fun loadMovie(movieId: Int) {
        mService.getMovie(movieId = movieId, apiKey = API_KEY, language = LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    presenter.finishLoadMovie(it)
                }, {
                    presenter.showError(it.message!!)
                })

        mService.getSimilarMovies(movieId = movieId, apiKey = API_KEY, language = LANGUAGE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                presenter.finishLoadSimilarMovies(movies = it.results)
            }, {
                presenter.showError(error = it.message!!)
            })
        mService.getTrailers(movieId = movieId, apiKey = API_KEY, language = LANGUAGE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                presenter.finishLoadTrailers(trailers = it.results)
            }, {
                presenter.showError(it.message!!)
            })
    }
    fun getMovieById(movieId: Int) {
        database.getMovieDao.getMovieById(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                presenter.finishLoadMovieFromDB(true)
                Log.e("TAG", "getMovieByIdSuccess: ")
            }, {
                presenter.finishLoadMovieFromDB(false)
                Log.e("TAG", "getMovieByIdFailed: ", it)
            })
    }
    fun insertMovie(movie: MovieModel) = CoroutineScope(Dispatchers.IO).launch {
        database.getMovieDao.insertMovie(movie = movie)
        Log.e("TAG", "insertMovie: INSERTED")
    }
    fun deleteMovieById(movieId: Int) = CoroutineScope(Dispatchers.IO).launch {
        database.getMovieDao.deleteMovieById(movieId = movieId)
    }
}