package com.example.findmovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.findmovies.R
import com.example.findmovies.models.TrailerModel

class TrailerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onTrailerClickListener: OnTrailerClickListener? = null

    val trailers: MutableList<TrailerModel> = ArrayList()

    fun setOnTrailerClickListener(onTrailerClickListener: OnTrailerClickListener) {
        this.onTrailerClickListener = onTrailerClickListener
    }

    fun setTrailers(trailers: List<TrailerModel>) {
        this.trailers.addAll(trailers)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trailer_item, parent, false)
        return TrailerViewHolder(view)
    }

    override fun getItemCount(): Int = trailers.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TrailerViewHolder) {
            holder.bind(trailer = trailers[0])
        }
    }

    inner class TrailerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = itemView.findViewById(R.id.trailer_name_item)

        init {
            itemView.setOnClickListener {
                onTrailerClickListener?.onTrailerClick(adapterPosition)
            }
        }

        fun bind(trailer: TrailerModel) {
            name.text = trailer.name
        }
    }

    interface OnTrailerClickListener {
        fun onTrailerClick(position: Int)
    }
}