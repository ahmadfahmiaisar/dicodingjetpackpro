package com.example.submission.presentation.movies


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submission.R
import com.example.submission.databinding.ItemMoviesFavoriteBinding
import com.example.submission.domain.entity.movie.MovieNowPlaying
import com.example.submission.util.IMAGE_BASE_URL_POSTER
import com.example.submission.util.loadUrl

class MovieFavoriteRecycleAdapter(private var movieFavorite: List<MovieNowPlaying>) :
    RecyclerView.Adapter<MovieFavoriteRecycleAdapter.ViewHolder>() {

    private var onMoviePressed: (MovieNowPlaying) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMoviesFavoriteBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieFavorite[position], onMoviePressed)
    }

    override fun getItemCount(): Int = movieFavorite.size

    class ViewHolder(private val binding: ItemMoviesFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieNowPlaying, onMoviePressed: (MovieNowPlaying) -> Unit) {
            binding.movie = movie
            binding.ivMovie.loadUrl("$IMAGE_BASE_URL_POSTER${movie.posterPath}")
            binding.root.setOnClickListener { onMoviePressed(movie) }

            binding.executePendingBindings()
        }
    }

    fun refreshMovieFavorite(movieFavorite: List<MovieNowPlaying>) {
        this.movieFavorite = movieFavorite
        notifyDataSetChanged()
    }

    fun setOnMoviePressed(movie: (MovieNowPlaying) -> Unit) {
        this.onMoviePressed = movie
    }
}