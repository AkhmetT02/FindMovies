package com.example.findmovies.fragments.favourite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findmovies.R
import com.example.findmovies.adapters.FavouriteMovieAdapter
import com.example.findmovies.di.App
import com.example.findmovies.models.MovieModel
import com.example.findmovies.presenters.FavouritePresenter
import com.example.findmovies.views.FavouriteView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import javax.inject.Inject

class FavouriteFragment : MvpAppCompatFragment(), FavouriteView {

    @InjectPresenter lateinit var presenter: FavouritePresenter
    @Inject lateinit var favouriteAdapter: FavouriteMovieAdapter

    private lateinit var recyclerMovies: RecyclerView
    private lateinit var messageTv: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_favourite, container, false)

        App.appComponent?.inject(this)

        recyclerMovies = view.findViewById(R.id.favourite_recycler_movies)
        messageTv = view.findViewById(R.id.favourite_message)

        recyclerMovies.layoutManager = LinearLayoutManager(context)
        recyclerMovies.adapter = favouriteAdapter

        presenter.loadMovies(context = requireContext())

        favouriteAdapter.setOnMovieClickListener(object: FavouriteMovieAdapter.OnMovieClickListener {
            override fun onMovieClick(position: Int) {
                val bundle = Bundle()
                bundle.putInt(getString(R.string.movie_id_for_navigate), favouriteAdapter.movies[position].id)
                findNavController().navigate(R.id.action_favouriteFragment_to_movieInfoFragment, bundle)
            }
        })

        return view
    }

    override fun showError(error: String) {
        messageTv.text = error
        messageTv.visibility = View.VISIBLE
    }

    override fun setupMovies(movies: List<MovieModel>) {
        favouriteAdapter.setMovies(movies = movies)
        messageTv.visibility = View.INVISIBLE
        Log.e("TAG", "setupMoviesSize: ${movies.size}")
    }

    override fun setupEmptyMovies() {
        messageTv.text = "Favourite movies not fount :("
        messageTv.visibility = View.VISIBLE
    }
}