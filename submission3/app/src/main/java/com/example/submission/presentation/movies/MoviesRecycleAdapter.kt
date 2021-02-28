package com.example.submission.presentation.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submission.R
import com.example.submission.databinding.ItemMoviesBinding
import com.example.submission.domain.entity.movie.MovieNowPlaying
import com.example.submission.util.loadUrl

class MoviesRecycleAdapter(private var movieNowPlaying: List<MovieNowPlaying>) :
    RecyclerView.Adapter<MoviesRecycleAdapter.ViewHolder>() {

    private var onMoviePressed: (MovieNowPlaying) -> Unit = {}
    private var onFavoriteMovieChecked: (MovieNowPlaying) -> Unit = {}
    private var onFavoriteMovieUnChecked: (MovieNowPlaying) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMoviesBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            movieNowPlaying[position],
            onMoviePressed,
            onFavoriteMovieChecked,
            onFavoriteMovieUnChecked
        )
    }

    override fun getItemCount(): Int = movieNowPlaying.size

    class ViewHolder(private val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            movieNowPlaying: MovieNowPlaying,
            onMoviePressed: (MovieNowPlaying) -> Unit,
            onFavoriteMovieChecked: (MovieNowPlaying) -> Unit,
            onFavoriteMovieUnChecked: (MovieNowPlaying) -> Unit
        ) {
            binding.movie = movieNowPlaying
            binding.ivMovie.loadUrl(movieNowPlaying.posterPath)
            binding.root.setOnClickListener { onMoviePressed(movieNowPlaying) }
            if (movieNowPlaying.isFavorite) {
                binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
            } else {
                binding.ivFavorite.setImageResource(R.drawable.ic_unfavorite)
            }

            binding.ivFavorite.setOnClickListener {
                movieNowPlaying.isFavorite = !movieNowPlaying.isFavorite
                if (movieNowPlaying.isFavorite) {
                    binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
                    onFavoriteMovieChecked(movieNowPlaying)
                } else {
                    binding.ivFavorite.setImageResource(R.drawable.ic_unfavorite)
                    onFavoriteMovieUnChecked(movieNowPlaying)
                }
            }
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

    fun setOnFavoriteMovieChecked(movie: (MovieNowPlaying) -> Unit) {
        this.onFavoriteMovieChecked = movie
    }

    fun setOnFavoriteMovieUnChecked(movie: (MovieNowPlaying) -> Unit) {
        this.onFavoriteMovieUnChecked = movie
    }
}