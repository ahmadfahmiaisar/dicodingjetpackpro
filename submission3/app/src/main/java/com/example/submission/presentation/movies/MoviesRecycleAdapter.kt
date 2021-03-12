package com.example.submission.presentation.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.submission.R
import com.example.submission.databinding.ItemMoviesBinding
import com.example.submission.domain.entity.movie.MovieNowPlaying
import com.example.submission.util.loadUrl

class MoviesRecycleAdapter :
    PagingDataAdapter<MovieNowPlaying, MoviesRecycleAdapter.ViewHolder>(differCallback) {

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
            getItem(position) ?: return,
            onMoviePressed,
            onFavoriteMovieChecked,
            onFavoriteMovieUnChecked
        )
    }

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

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<MovieNowPlaying>() {
            override fun areItemsTheSame(
                oldItem: MovieNowPlaying,
                newItem: MovieNowPlaying
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MovieNowPlaying,
                newItem: MovieNowPlaying
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    /*   fun refreshMovieNowPlaying(pagingData: PagingData<MovieNowPlaying>) {
             submitData(pagingData)
         }*/

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