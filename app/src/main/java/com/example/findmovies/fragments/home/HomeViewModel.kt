package com.example.findmovies.fragments.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.findmovies.models.MovieModel

class HomeViewModel : ViewModel() {

    private val movies = MutableLiveData<List<MovieModel>>()
    var page: Int = 1
    var position: Int = 0

    fun setMovies(movies: List<MovieModel>) {
        Log.e("TAG", "setMovies: ${movies.size}")
        this.movies.value = movies
    }
    val getMovies = movies
}