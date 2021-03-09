package com.example.findmovies.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.findmovies.models.MovieModel
import io.reactivex.rxjava3.core.Flowable

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flowable<List<MovieModel>>

    @Query("SELECT * FROM movies WHERE id=:movieId")
    fun getMovieById(movieId: Int): Flowable<MovieModel>

    @Query("DELETE FROM movies WHERE id=:movieId")
    fun deleteMovieById(movieId: Int)

    @Insert
    fun insertMovie(movie: MovieModel)
}