package com.example.submission.presentation.movies


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.submission.databinding.ItemMoviesFavoriteBinding
import com.example.submission.domain.entity.movie.MovieEntity
import com.example.submission.util.IMAGE_BASE_URL_POSTER
import com.example.submission.util.loadUrl
import timber.log.Timber

class MovieFavoriteRecycleAdapter :
    PagingDataAdapter<MovieEntity, MovieFavoriteRecycleAdapter.ViewHolder>(differCallback) {

    private var onMoviePressed: (MovieEntity) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMoviesFavoriteBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return, onMoviePressed)
    }

    class ViewHolder(private val binding: ItemMoviesFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity, onMoviePressed: (MovieEntity) -> Unit) {
            binding.movie = movie
            binding.ivMovie.loadUrl("$IMAGE_BASE_URL_POSTER${movie.posterPath}")
            binding.root.setOnClickListener { onMoviePressed(movie) }

            Timber.tag("ISINYAHEY").d(movie.title)
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

 /*   fun refreshMovieFavorite(movieFavorite: List<MovieEntity>) {
        this.movieFavorite = movieFavorite
        notifyDataSetChanged()
    }*/

    fun setOnMoviePressed(movie: (MovieEntity) -> Unit) {
        this.onMoviePressed = movie
    }
}