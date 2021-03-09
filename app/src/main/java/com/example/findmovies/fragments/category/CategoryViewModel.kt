package com.example.findmovies.fragments.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.findmovies.models.GenreModel

class CategoryViewModel : ViewModel() {

    private val categories = MutableLiveData<List<GenreModel>>()

    fun addCategories(genres: List<GenreModel>){
        categories.value = genres
    }
    val getCategories = categories
}