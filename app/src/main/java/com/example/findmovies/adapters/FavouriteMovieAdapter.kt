package com.example.findmovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.findmovies.R
import com.example.findmovies.models.MovieModel
import com.example.findmovies.network.NetworkHelper.BASE_IMAGE_URL
import com.example.findmovies.network.NetworkHelper.SMALL_POSTER_SIZE
import com.squareup.picasso.Picasso

class FavouriteMovieAdapter(private val picasso: Picasso) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onMovieClickListener: OnMovieClickListener? = null
    val movies: ArrayList<MovieModel> = ArrayList()

    fun setOnMovieClickListener(onMovieClickListener: OnMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener
    }

    fun setMovies(movies: List<MovieModel>) {
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.favourite_movie_item, parent, false)
        return FavouriteMovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FavouriteMovieViewHolder) {
            holder.bind(movie = movies[position])
        }
    }

    inner class FavouriteMovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val poster: ImageView = itemView.findViewById(R.id.favourite_image_item)
        private val title: TextView = itemView.findViewById(R.id.favourite_title_item)
        private val description: TextView = itemView.findViewById(R.id.favourite_description_item)

        init {
            itemView.setOnClickListener {
                onMovieClickListener?.onMovieClick(adapterPosition)
            }
        }

        fun bind(movie: MovieModel) {
            picasso.load(BASE_IMAGE_URL + SMALL_POSTER_SIZE + movie.poster_path).into(poster)
            title.text = movie.title
            var overview = ""
            overview = if (movie.overview.length > 150) {
                movie.overview.substring(0, 100).plus("...")
            } else {
                movie.overview
            }
            description.text = overview
        }
    }

    interface OnMovieClickListener {
        fun onMovieClick(position: Int)
    }
}