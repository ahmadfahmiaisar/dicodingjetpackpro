package com.example.submission1.presentation.home.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submission1.databinding.ItemMoviesBinding
import com.example.submission1.domain.entity.movie.MovieNowPlaying
import com.example.submission1.util.loadUrl

class MoviesRecycleAdapter(private var movieNowPlaying: List<MovieNowPlaying>) :
    RecyclerView.Adapter<MoviesRecycleAdapter.ViewHolder>() {

    private var onMoviePressed: (MovieNowPlaying) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMoviesBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieNowPlaying[position], onMoviePressed)
    }

    override fun getItemCount(): Int = movieNowPlaying.size

    class ViewHolder(private val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieNowPlaying: MovieNowPlaying, onMoviePressed: (MovieNowPlaying) -> Unit) {
            binding.movie = movieNowPlaying
            binding.ivMovie.loadUrl(movieNowPlaying.posterPath)
            binding.root.setOnClickListener { onMoviePressed(movieNowPlaying) }
            binding.executePendingBindings()
        }
    }

    fun refreshMovieNowPlaying(movieNowPlaying: List<MovieNowPlaying>) {
        this.movieNowPlaying = movieNowPlaying
        notifyDataSetChanged()
    }

    fun setOnMoviePressed(movie: (MovieNowPlaying) -> Unit) {
        this.onMoviePressed = movie
    }
}