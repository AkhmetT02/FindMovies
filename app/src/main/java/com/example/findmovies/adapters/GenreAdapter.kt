package com.example.findmovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.findmovies.R
import com.example.findmovies.models.GenreModel

class GenreAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val genres: ArrayList<GenreModel> = ArrayList()

    private var onGenreClickListener: OnGenreClickListener? = null

    fun setOnGenreClickListener(onGenreClickListener: OnGenreClickListener) {
        this.onGenreClickListener = onGenreClickListener
    }

    fun setGenres(genres: List<GenreModel>) {
        this.genres.addAll(genres)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.genre_item, parent, false)

        return GenreViewHolder(view)
    }

    override fun getItemCount(): Int = genres.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GenreViewHolder) {
            holder.bind(genre = genres[position])
        }
    }

    inner class GenreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val genreName: TextView = itemView.findViewById(R.id.genre_name_item)

        init {
            itemView.setOnClickListener {
                if (onGenreClickListener != null) {
                    onGenreClickListener?.onGenreClick(adapterPosition)
                }
            }
        }

        fun bind(genre: GenreModel) {
            genreName.text = genre.name
        }
    }
    interface OnGenreClickListener {
        fun onGenreClick(position: Int)
    }
}