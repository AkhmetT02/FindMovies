package com.example.findmovies.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.findmovies.R
import com.example.findmovies.models.MovieModel
import com.example.findmovies.network.NetworkHelper.BASE_IMAGE_URL
import com.example.findmovies.network.NetworkHelper.SMALL_POSTER_SIZE
import com.squareup.picasso.Picasso

class MovieAdapter(private val picasso: Picasso) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onPosterClickListener: OnPosterClickListener? = null
    private var onReachEndListener: OnReachEndListener? = null

    val movies: ArrayList<MovieModel> = ArrayList()

    fun setOnPosterClickListener(onPosterClickListener: OnPosterClickListener) {
        this.onPosterClickListener = onPosterClickListener
    }
    fun setOnReachEndListener(onReachEndListener: OnReachEndListener) {
        this.onReachEndListener = onReachEndListener
    }

    fun setMovies(movies: List<MovieModel>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }
    fun addMovies(movies: List<MovieModel>) {
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)

        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position >= movies.size - 1 && onReachEndListener != null && movies.size % 20 == 0) {
            onReachEndListener?.onReachEnd()
            Log.e("TAG", "onBindViewHolderPosition: $position")
        }
        if (holder is MovieViewHolder) {
            holder.bind(movies[position])
        }
    }

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val poster: ImageView = itemView.findViewById(R.id.movie_poster_item)

        init {
            itemView.setOnClickListener {
                onPosterClickListener?.onPosterClick(adapterPosition)
            }
        }

        fun bind(movie: MovieModel) {
            picasso.load(BASE_IMAGE_URL + SMALL_POSTER_SIZE + movie.poster_path).into(poster)
        }
    }

    interface OnPosterClickListener {
        fun onPosterClick(position: Int)
    }
    interface OnReachEndListener {
        fun onReachEnd()
    }
}