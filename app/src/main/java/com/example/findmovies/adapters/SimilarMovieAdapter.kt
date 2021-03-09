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
import com.example.findmovies.network.NetworkHelper.MEDIUM_POSTER_SIZE
import com.squareup.picasso.Picasso

class SimilarMovieAdapter(private val picasso: Picasso) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onPosterClickListener: MovieAdapter.OnPosterClickListener? = null

    val movies: MutableList<MovieModel> = ArrayList()

    fun setOnPosterClickListener(onPosterClickListener: MovieAdapter.OnPosterClickListener) {
        this.onPosterClickListener = onPosterClickListener
    }

    fun setMovies(movies: List<MovieModel>) {
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.similar_movie_item, parent, false)
        return SimilarMovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SimilarMovieViewHolder) {
            holder.bind(movie = movies[position])
        }
    }

    inner class SimilarMovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image: ImageView = itemView.findViewById(R.id.similar_movie_image_item)
        private val title: TextView = itemView.findViewById(R.id.similar_movie_title_item)


        init {
            itemView.setOnClickListener {
                onPosterClickListener?.onPosterClick(adapterPosition)
            }
        }

        fun bind(movie: MovieModel) {
            picasso.load(BASE_IMAGE_URL + MEDIUM_POSTER_SIZE + movie.poster_path).into(image)
            val modifiedTitle = if (movie.title.length > 20) {
                movie.title.substring(0, 20) + "..."
            } else {
                movie.title
            }
            title.text = modifiedTitle

        }
    }

}