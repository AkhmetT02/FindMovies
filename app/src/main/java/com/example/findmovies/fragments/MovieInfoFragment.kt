package com.example.findmovies.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findmovies.R
import com.example.findmovies.adapters.MovieAdapter
import com.example.findmovies.adapters.SimilarMovieAdapter
import com.example.findmovies.adapters.TrailerAdapter
import com.example.findmovies.di.App
import com.example.findmovies.models.MovieModel
import com.example.findmovies.models.TrailerModel
import com.example.findmovies.network.NetworkHelper.BASE_IMAGE_URL
import com.example.findmovies.network.NetworkHelper.BIG_POSTER_SIZE
import com.example.findmovies.presenters.MovieInfoPresenter
import com.example.findmovies.views.MovieInfoView
import com.squareup.picasso.Picasso
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import javax.inject.Inject

class MovieInfoFragment : MvpAppCompatFragment(), MovieInfoView {

    private lateinit var image: ImageView
    private lateinit var title: TextView
    private lateinit var originalTitle: TextView
    private lateinit var originalLanguage: TextView
    private lateinit var popularity: TextView
    private lateinit var voteAverage: TextView
    private lateinit var overview: TextView
    private lateinit var genres: TextView
    private lateinit var releaseData: TextView

    private lateinit var loadingBar: ProgressBar
    private lateinit var layoutView: LinearLayout
    private lateinit var favouriteImg: ImageView
    private lateinit var recyclerSimilar: RecyclerView
    private lateinit var recyclerTrailers: RecyclerView
    private lateinit var movie: MovieModel
    private var oneTrailerPath = ""
    private var isFavourite = false

    @InjectPresenter lateinit var presenter: MovieInfoPresenter

    @Inject lateinit var similarMovieAdapter: SimilarMovieAdapter
    @Inject lateinit var trailerAdapter: TrailerAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_movie_info, container, false)

        (activity as AppCompatActivity).supportActionBar?.show()

        App.appComponent?.inject(this)

        image = view.findViewById(R.id.movie_image_info)
        title = view.findViewById(R.id.title_info)
        originalTitle = view.findViewById(R.id.original_title_info)
        originalLanguage = view.findViewById(R.id.original_language_info)
        popularity = view.findViewById(R.id.popularity_info)
        voteAverage = view.findViewById(R.id.vote_avg_info)
        overview = view.findViewById(R.id.overview_info)
        loadingBar = view.findViewById(R.id.loading_bar_info)
        layoutView = view.findViewById(R.id.info_layout)
        favouriteImg = view.findViewById(R.id.favourite_info)
        genres = view.findViewById(R.id.genres_info)
        recyclerSimilar = view.findViewById(R.id.recycler_similar_info)
        recyclerTrailers = view.findViewById(R.id.recycler_trailers_info)
        releaseData = view.findViewById(R.id.release_data_info)

        recyclerTrailers.layoutManager = LinearLayoutManager(context)
        recyclerTrailers.adapter = trailerAdapter

        recyclerSimilar.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerSimilar.adapter = similarMovieAdapter

        val movieId: Int = arguments?.getInt(getString(R.string.movie_id_for_navigate))!!


        presenter.loadMovie(movieId = movieId, context = requireContext())
        presenter.getMovieById(movieId, requireContext())



        trailerAdapter.setOnTrailerClickListener(object: TrailerAdapter.OnTrailerClickListener {
            override fun onTrailerClick(position: Int) {
                val trailer = trailerAdapter.trailers[position]

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.key))
                startActivity(intent)
            }
        })

        similarMovieAdapter.setOnPosterClickListener(object: MovieAdapter.OnPosterClickListener {
            override fun onPosterClick(position: Int) {
                val bundle = Bundle()
                bundle.putInt(getString(R.string.movie_id_for_navigate), similarMovieAdapter.movies[position].id)
                findNavController().navigate(R.id.action_movieInfoFragment_self, bundle)

                //Change ActionBar title
                activity?.findViewById<Toolbar>(R.id.main_toolbar)?.title = similarMovieAdapter.movies[position].title
            }
        })

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.actionbar_menu, menu)
        menu.findItem(R.id.share_menu_item).setOnMenuItemClickListener {
            val info = "Title: ${movie.title}\n\nGenres: ${genres.text}\n\nPopularity: ${movie.popularity}\n\n" +
                    "Vote average: ${movie.vote_average}\n\nVote count: ${movie.vote_count}\n\nOverview: ${movie.overview}\n\n" +
                    "Trailers: https://www.youtube.com/watch?v=$oneTrailerPath"
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, info)
            startActivity(Intent.createChooser(intent, "Share title"))
            return@setOnMenuItemClickListener true
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onDestroy() {
        //hide action bar when fragment changed
        (activity as AppCompatActivity).supportActionBar?.hide()
        super.onDestroy()
    }

    override fun showError(error: String) {
        Log.e("TAG", "showError: $error")
    }

    override fun setupMovie(movie: MovieModel) {
        this.movie = movie
        Picasso.get().load(BASE_IMAGE_URL + BIG_POSTER_SIZE + movie.poster_path).into(image)
        title.text = movie.title
        originalTitle.text = movie.original_title
        originalLanguage.text = movie.original_language
        popularity.text = movie.popularity.toString()
        voteAverage.text = movie.vote_average.toString()
        overview.text = movie.overview
        releaseData.text = movie.release_date


        //setup genres
        var genresAsString = ""
        movie.genres?.forEach {
            genresAsString += it.name + ", "
        }
        genres.text = genresAsString.substring(0, genresAsString.length - 2)

        //Change ActionBar title
        activity?.findViewById<Toolbar>(R.id.main_toolbar)?.title = movie.title

        //Change favourite status
        favouriteImg.setOnClickListener {
            isFavourite = if (isFavourite) {
                presenter.deleteMovieById(movie.id, requireContext())
                favouriteImg.setImageResource(R.drawable.ic_not_favourite)
                false
            } else {
                presenter.insertMovie(movie, requireContext())
                favouriteImg.setImageResource(R.drawable.ic_favourite)
                true
            }
        }
    }

    override fun setupSimilarMovies(movies: List<MovieModel>) {
        similarMovieAdapter.setMovies(movies)
    }

    override fun setupTrailers(trailers: List<TrailerModel>) {
        oneTrailerPath = trailers[0].key
        trailerAdapter.setTrailers(trailers)
    }

    override fun showMovieFromDB(exist: Boolean) {
        isFavourite = if (exist) {
            favouriteImg.setImageResource(R.drawable.ic_favourite)
            true
        } else {
            favouriteImg.setImageResource(R.drawable.ic_not_favourite)
            false
        }
    }

    override fun startLoading() {
        layoutView.visibility = View.INVISIBLE
        loadingBar.visibility = View.VISIBLE
    }

    override fun endLoading() {
        layoutView.visibility = View.VISIBLE
        loadingBar.visibility = View.INVISIBLE
    }
}