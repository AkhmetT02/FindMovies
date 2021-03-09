package com.example.findmovies.db.converters

import androidx.room.TypeConverter
import com.example.findmovies.models.GenreModel
import com.google.gson.Gson

class GenresListConverter {

    @TypeConverter
    fun genresListToString(genres: List<GenreModel>?): String {
        return Gson().toJson(genres)
    }

    @TypeConverter
    fun genresStringToList(genresAsString: String): List<GenreModel>? {
        val objects = Gson().fromJson(genresAsString, Array<GenreModel>::class.java)
        return objects?.toList() ?: listOf()
    }
}