package com.example.findmovies.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.findmovies.db.converters.IntegerListConverter
import com.example.findmovies.models.MovieModel

@Database(entities = [MovieModel::class], version = 1, exportSchema = false)
@TypeConverters(IntegerListConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    companion object {
        private var database: MovieDatabase? = null
        private val DB_NAME = "movies.db"

        fun getInstance(context: Context): MovieDatabase {
            if (database == null) {
                database = Room.databaseBuilder(context, MovieDatabase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return database!!
        }
    }

    abstract val getMovieDao: MovieDao

}