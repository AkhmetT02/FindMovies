package com.example.findmovies.providers

import com.example.findmovies.di.App
import com.example.findmovies.network.NetworkHelper.API_KEY
import com.example.findmovies.network.NetworkHelper.LANGUAGE
import com.example.findmovies.network.RetrofitService
import com.example.findmovies.presenters.CategoryPresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CategoryProvider(private val presenter: CategoryPresenter) {

    @Inject lateinit var mService: RetrofitService

    val compositeDisposable = CompositeDisposable()

    init {
        App.appComponent?.inject(this)
    }

    fun loadGenres() {
        val dispose = mService.getGenres(API_KEY, LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    presenter.finishLoadGenres(genres = it.genres)
                }, {
                    presenter.showError(it.message!!)
                })
        compositeDisposable.add(dispose)
    }

    fun dispose() {
        compositeDisposable.dispose()
    }
}