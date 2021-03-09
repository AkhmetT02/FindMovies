package com.example.findmovies.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findmovies.R
import com.example.findmovies.adapters.MovieAdapter
import com.example.findmovies.di.App
import com.example.findmovies.models.MovieModel
import com.example.findmovies.presenters.MoviesListPresenter
import com.example.findmovies.views.MoviesListView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import javax.inject.Inject

class HomeFragment : MvpAppCompatFragment(), MoviesListView {

    @InjectPresenter lateinit var presenter: MoviesListPresenter
    @Inject lateinit var adapter: MovieAdapter

    private lateinit var recyclerMovies: RecyclerView
    private lateinit var loadingBar: ProgressBar


    private lateinit var homeViewModel: HomeViewModel
    private var isLoading = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        App.appComponent?.inject(this)

        homeViewModel = ViewModelProvider(activity as ViewModelStoreOwner).get(HomeViewModel::class.java)

        recyclerMovies = view.findViewById(R.id.home_recycler_movies)
        loadingBar = view.findViewById(R.id.loading_bar_home)

        recyclerMovies.layoutManager = GridLayoutManager(context, 2)
        recyclerMovies.adapter = adapter


        recyclerMovies.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val gridLayout = recyclerView.layoutManager as GridLayoutManager

                if (!isLoading) {
                    Log.e("TAG", "onScrolledMovieSize: ${gridLayout.findLastCompletelyVisibleItemPosition()}")
                    if (gridLayout.findLastCompletelyVisibleItemPosition() >adapter.movies.size - 2) {
                        isLoading = true
                        presenter.loadMovies(page = homeViewModel.page)
                    }
                }
            }
        })

        adapter.setOnPosterClickListener(object: MovieAdapter.OnPosterClickListener {
            override fun onPosterClick(position: Int) {
                val bundle = Bundle()
                bundle.putInt(getString(R.string.movie_id_for_navigate), adapter.movies[position].id)
                findNavController().navigate(R.id.action_homeFragment_to_movieInfoFragment, bundle)

                //Change ActionBar title
                activity?.findViewById<Toolbar>(R.id.main_toolbar)?.title = adapter.movies[position].title
            }
        })


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (homeViewModel.getMovies.value == null) {
            presenter.loadMovies(page = homeViewModel.page)
            Log.e("TAG", "onCreateView: NULL")
        } else {
            adapter.setMovies(homeViewModel.getMovies.value!!)
            Log.e("TAG", "onViewCreated: ${homeViewModel.getMovies.value?.size}")
            if (homeViewModel.getMovies.value?.size!! >= homeViewModel.position) {
                recyclerMovies.scrollToPosition(homeViewModel.position)
            }
        }
    }

    override fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        loadingBar.visibility = View.INVISIBLE
    }

    override fun setupMovies(movies: List<MovieModel>) {
        adapter.addMovies(movies = movies)

        homeViewModel.page++
        isLoading = false
    }

    override fun setupEmptyMovies() {
        Log.e("TAG", "setupEmptyMovies: Empty")
    }

    override fun startLoading() {
        loadingBar.visibility = View.VISIBLE
    }

    override fun endLoading() {
        loadingBar.visibility = View.INVISIBLE
    }

    override fun onStop() {
        super.onStop()
        homeViewModel.setMovies(movies = this.adapter.movies)
        Log.e("TAG", "onStop: ${homeViewModel.getMovies.value?.size}")
    }
}