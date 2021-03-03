package com.example.submission.presentation.tvshows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submission.databinding.ItemTvFavoriteBinding
import com.example.submission.domain.entity.tvshow.TvShowEntity
import com.example.submission.util.IMAGE_BASE_URL_POSTER
import com.example.submission.util.loadUrl

class TvFavoriteRecycleAdapter(private var movieFavorite: List<TvShowEntity>) :
    RecyclerView.Adapter<TvFavoriteRecycleAdapter.ViewHolder>() {

    private var onMoviePressed: (TvShowEntity) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTvFavoriteBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieFavorite[position], onMoviePressed)
    }

    override fun getItemCount(): Int = movieFavorite.size

    class ViewHolder(private val binding: ItemTvFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShowEntity: TvShowEntity, onMoviePressed: (TvShowEntity) -> Unit) {
            binding.tvShow = tvShowEntity
            binding.ivTvShow.loadUrl("$IMAGE_BASE_URL_POSTER${tvShowEntity.posterPath}")
            binding.root.setOnClickListener { onMoviePressed(tvShowEntity) }

            binding.executePendingBindings()
        }
    }

    fun refreshMovieFavorite(movieFavorite: List<TvShowEntity>) {
        this.movieFavorite = movieFavorite
        notifyDataSetChanged()
    }

    fun setOnMoviePressed(movie: (TvShowEntity) -> Unit) {
        this.onMoviePressed = movie
    }
}