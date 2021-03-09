package com.example.findmovies.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.findmovies.db.converters.GenresListConverter
import com.example.findmovies.db.converters.IntegerListConverter

@Entity(tableName = "movies")
@TypeConverters(value = [IntegerListConverter::class, GenresListConverter::class])
data class MovieModel(
    @PrimaryKey(autoGenerate = true) var uniqueId: Int,
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>?,
    val genres: List<GenreModel>?,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MovieModel

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}