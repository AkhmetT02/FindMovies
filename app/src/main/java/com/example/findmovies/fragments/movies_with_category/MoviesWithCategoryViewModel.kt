package com.example.findmovies.fragments.movies_with_category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.findmovies.models.MovieModel

class MoviesWithCategoryViewModel : ViewModel() {

    private val movies = MutableLiveData<List<MovieModel>>()

    var page: Int = 1
    var genreId: Int = 0

    fun setMovies(movies: List<MovieModel>) {
        this.movies.value = movies
    }
    val getMovies = movies
}