package com.example.findmovies.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson

class IntegerListConverter {

    @TypeConverter
    fun listIntegerToString(genres: List<Int>?): String {
        return Gson().toJson(genres)
    }

    @TypeConverter
    fun stringToIntegerList(genresAsString: String = ""): List<Int>? {
        val objects = Gson().fromJson(genresAsString, Array<Int>::class.java)
        return objects?.toList() ?: listOf()
    }
}