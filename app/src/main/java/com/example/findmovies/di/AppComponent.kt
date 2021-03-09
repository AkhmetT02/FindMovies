package com.example.findmovies.di

import com.example.findmovies.di.modules.*
import com.example.findmovies.fragments.MovieInfoFragment
import com.example.findmovies.fragments.category.CategoryFragment
import com.example.findmovies.fragments.favourite.FavouriteFragment
import com.example.findmovies.fragments.home.HomeFragment
import com.example.findmovies.fragments.movies_with_category.MoviesWithCategoryFragment
import com.example.findmovies.providers.CategoryProvider
import com.example.findmovies.providers.MovieInfoProvider
import com.example.findmovies.providers.MoviesListProvider
import dagger.Component
import javax.inject.Singleton

@Component(modules = [RetrofitModule::class, RetrofitServiceModule::class, PicassoModule::class,
    MovieAdapterModule::class, FavouriteMovieAdapterModule::class, SimilarMovieAdapterModule::class,
    TrailerAdapterModule::class])
@Singleton
interface AppComponent {

    //Fragment
    fun inject(homeFragment: HomeFragment)
    fun inject(favouriteFragment: FavouriteFragment)
    fun inject(moviesWithCategoryFragment: MoviesWithCategoryFragment)
    fun inject(movieInfoFragment: MovieInfoFragment)
    fun inject(categoryFragment: CategoryFragment)

    //Providers
    fun inject(provider: MoviesListProvider)
    fun inject(provider: MovieInfoProvider)
    fun inject(provider: CategoryProvider)
}