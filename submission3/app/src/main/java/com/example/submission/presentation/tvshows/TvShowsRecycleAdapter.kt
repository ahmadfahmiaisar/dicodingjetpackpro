package com.example.submission.presentation.tvshows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submission.R
import com.example.submission.databinding.ItemTvShowBinding
import com.example.submission.domain.entity.tvshow.TvOnTheAir

class TvShowsRecycleAdapter(private var tvshows: List<TvOnTheAir>) :
    RecyclerView.Adapter<TvShowsRecycleAdapter.ViewHolder>() {

    private var onTvShowPressed: (TvOnTheAir) -> Unit = {}
    private var onFavoriteTvChecked: (TvOnTheAir) -> Unit = {}
    private var onFavoriteTvUnChecked: (TvOnTheAir) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTvShowBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tvshows[position], onTvShowPressed, onFavoriteTvChecked, onFavoriteTvUnChecked)
    }

    override fun getItemCount(): Int = tvshows.size

    class ViewHolder(private val binding: ItemTvShowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            tvOnTheAir: TvOnTheAir,
            onTvShowPressed: (TvOnTheAir) -> Unit,
            onFavoriteTvChecked: (TvOnTheAir) -> Unit,
            onFavoriteTvUnChecked: (TvOnTheAir) -> Unit
        ) {
            binding.tvshow = tvOnTheAir
            binding.root.setOnClickListener { onTvShowPressed(tvOnTheAir) }
            if (tvOnTheAir.isFavorite) {
                binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
            } else {
                binding.ivFavorite.setImageResource(R.drawable.ic_unfavorite)
            }

            binding.ivFavorite.setOnClickListener {
                tvOnTheAir.isFavorite = !tvOnTheAir.isFavorite
                if (tvOnTheAir.isFavorite) {
                    binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
                    onFavoriteTvChecked(tvOnTheAir)
                } else {
                    binding.ivFavorite.setImageResource(R.drawable.ic_unfavorite)
                    onFavoriteTvUnChecked(tvOnTheAir)
                }
            }
            binding.executePendingBindings()
        }
    }

    fun refreshTvShows(tvShow: List<TvOnTheAir>) {
        this.tvshows = tvShow
        notifyDataSetChanged()
    }

    fun setOnTvShowPressed(tvShow: (TvOnTheAir) -> Unit) {
        this.onTvShowPressed = tvShow
    }

    fun setOnFavoriteTvChecked(tvShow: (TvOnTheAir) -> Unit) {
        this.onFavoriteTvChecked = tvShow
    }

    fun setOnFavoriteTvUnChecked(tvShow: (TvOnTheAir) -> Unit) {
        this.onFavoriteTvUnChecked = tvShow
    }

}