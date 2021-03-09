package com.example.findmovies.network

import com.example.findmovies.models.GenresResult
import com.example.findmovies.models.MovieModel
import com.example.findmovies.models.Result
import com.example.findmovies.models.TrailerResult
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("discover/movie")
    fun getMovieList(@Query("api_key") apiKey: String,
                     @Query("language") language: String,
                     @Query("sort_by") sortBy: String,
                     @Query("page") page: Int): Observable<Result>

    @GET("search/movie")
    fun getMovieFromQuery(@Query("api_key") apiKey: String,
                     @Query("query") query: String): Observable<Result>

    @GET("movie/{movieId}")
    fun getMovie(@Path("movieId") movieId: Int,
                 @Query("api_key") apiKey: String,
                 @Query("language") language: String): Observable<MovieModel>

    @GET("movie/{movieId}/similar")
    fun getSimilarMovies(@Path("movieId") movieId: Int,
                         @Query("api_key") apiKey: String,
                         @Query("language") language: String): Observable<Result>

    @GET("movie/{movieId}/videos")
    fun getTrailers(@Path("movieId") movieId: Int,
                    @Query("api_key") apiKey: String,
                    @Query("language") language: String): Observable<TrailerResult>

    @GET("genre/movie/list")
    fun getGenres(@Query("api_key") apiKey: String,
                  @Query("language") language: String): Observable<GenresResult>

    @GET("discover/movie")
    fun getMoviesWithGenres(@Query("api_key") apiKey: String,
                            @Query("language") language: String,
                            @Query("sort_by") sortBy: String,
                            @Query("page") page: Int,
                            @Query("with_genres") genre: Int): Observable<Result>
}