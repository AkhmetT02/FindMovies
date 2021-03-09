package com.example.findmovies.fragments.movies_with_category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
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

class MoviesWithCategoryFragment : MvpAppCompatFragment(), MoviesListView {

    @InjectPresenter lateinit var presenter: MoviesListPresenter
    @Inject lateinit var adapter: MovieAdapter


    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingBar: ProgressBar

    private lateinit var viewModel: MoviesWithCategoryViewModel
    private lateinit var onReachEndListener: MovieAdapter.OnReachEndListener
    private val movies: ArrayList<MovieModel> = ArrayList()
    private var genreId: Int = 16

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        viewModel = ViewModelProvider(activity as ViewModelStoreOwner).get(MoviesWithCategoryViewModel::class.java)
        App.appComponent?.inject(this)

        recyclerView = view.findViewById(R.id.home_recycler_movies)
        loadingBar = view.findViewById(R.id.loading_bar_home)

        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter

        genreId = arguments?.getInt("genreId")!!


        onReachEndListener = object: MovieAdapter.OnReachEndListener {
            override fun onReachEnd() {
                presenter.loadMoviesWithGenres(page = viewModel.page, genreId = genreId)
            }
        }
        adapter.setOnPosterClickListener(object: MovieAdapter.OnPosterClickListener {
            override fun onPosterClick(position: Int) {
                val bundle = Bundle()
                bundle.putInt("movieId", adapter.movies[position].id)
                findNavController().navigate(R.id.action_moviesWithCategoryFragment_to_movieInfoFragment, bundle)

                //Change ActionBar title
                activity?.findViewById<Toolbar>(R.id.main_toolbar)?.title = adapter.movies[position].title
            }
        })

        if (viewModel.getMovies.value == null || viewModel.genreId != genreId) {
            viewModel.page = 1
            viewModel.getMovies.value = null
            presenter.loadMoviesWithGenres(page = viewModel.page, genreId = genreId)
        } else if (viewModel.genreId == genreId) {
            adapter.setMovies(viewModel.getMovies.value!!)
        }


        return view
    }

    override fun showError(error: String) {
        Log.e("TAG", "showError: $error")
    }

    override fun setupMovies(movies: List<MovieModel>) {
        Log.e("TAG", "setupMovies: ${movies.size}")
        this.movies.addAll(movies)
        adapter.addMovies(movies)
        viewModel.setMovies(this.movies)
        viewModel.page++
        viewModel.genreId = genreId
    }

    override fun setupEmptyMovies() {

    }

    override fun startLoading() {
        loadingBar.visibility = View.VISIBLE
        adapter.setOnReachEndListener(onReachEndListener = onReachEndListener)
    }

    override fun endLoading() {
        loadingBar.visibility = View.INVISIBLE
    }
}