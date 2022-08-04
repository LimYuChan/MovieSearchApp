package com.devsurfer.moviesearchapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devsurfer.domain.model.movie.Movie
import com.devsurfer.moviesearchapp.R
import com.devsurfer.moviesearchapp.databinding.ItemMovieBinding

class MovieAdapter(
    val onItemClick: (Movie) -> Unit
): ListAdapter<Movie, MovieAdapter.MovieItemViewHolder>(diffUtil){

    inner class MovieItemViewHolder(val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: Movie){
            binding.movie = data
            binding.itemRoot.setOnClickListener {
                onItemClick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder =
        MovieItemViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_movie, parent, false))

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        currentList[position]?.let{ holder.bind(it) }
    }

    companion object{
        val diffUtil =object: DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = "${oldItem.title}${oldItem.actor}".hashCode() == "${newItem.title}${newItem.actor}".hashCode()

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
        }
    }
}