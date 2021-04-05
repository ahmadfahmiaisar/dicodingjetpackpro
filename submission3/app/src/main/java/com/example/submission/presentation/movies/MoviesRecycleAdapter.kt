package com.example.submission.presentation.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.submission.R
import com.example.submission.databinding.ItemMoviesBinding
import com.example.submission.domain.entity.movie.MovieEntity
import com.example.submission.util.loadUrl
import timber.log.Timber

class MoviesRecycleAdapter :
    PagingDataAdapter<MovieEntity, MoviesRecycleAdapter.ViewHolder>(differCallback) {

    private var onMoviePressed: (MovieEntity) -> Unit = {}
    private var onFavoriteMovieChecked: (MovieEntity) -> Unit = {}
    private var onFavoriteMovieUnChecked: (MovieEntity) -> Unit = {}

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
            movie: MovieEntity,
            onMoviePressed: (MovieEntity) -> Unit,
            onFavoriteMovieChecked: (MovieEntity) -> Unit,
            onFavoriteMovieUnChecked: (MovieEntity) -> Unit
        ) {
            binding.movie = movie
            binding.ivMovie.loadUrl(movie.posterPath)
            binding.root.setOnClickListener { onMoviePressed(movie) }
            if (movie.isFavorite) {
                binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
            } else {
                binding.ivFavorite.setImageResource(R.drawable.ic_unfavorite)
            }

            binding.ivFavorite.setOnClickListener {
                movie.isFavorite = !movie.isFavorite
                if (movie.isFavorite) {
                    binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
                    onFavoriteMovieChecked(movie)
                } else {
                    binding.ivFavorite.setImageResource(R.drawable.ic_unfavorite)
                    onFavoriteMovieUnChecked(movie)
                }
            }
            binding.executePendingBindings()
        }
    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(
                oldItem: MovieEntity,
                newItem: MovieEntity
            ): Boolean {
                return oldItem.movieId == newItem.movieId
            }

            override fun areContentsTheSame(
                oldItem: MovieEntity,
                newItem: MovieEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    /*   fun refreshMovieEntity(pagingData: PagingData<MovieEntity>) {
             submitData(pagingData)
         }*/

    fun setOnMoviePressed(movie: (MovieEntity) -> Unit) {
        this.onMoviePressed = movie
    }

    fun setOnFavoriteMovieChecked(movie: (MovieEntity) -> Unit) {
        this.onFavoriteMovieChecked = movie
    }

    fun setOnFavoriteMovieUnChecked(movie: (MovieEntity) -> Unit) {
        this.onFavoriteMovieUnChecked = movie
    }
}