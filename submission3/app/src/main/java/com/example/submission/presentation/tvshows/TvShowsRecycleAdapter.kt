package com.example.submission.presentation.tvshows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.submission.R
import com.example.submission.databinding.ItemTvShowBinding
import com.example.submission.domain.entity.tvshow.TvShowEntity

class TvShowsRecycleAdapter : PagingDataAdapter<TvShowEntity, TvShowsRecycleAdapter.ViewHolder>(
    differCallback
) {

    private var onTvShowPressed: (TvShowEntity) -> Unit = {}
    private var onFavoriteTvChecked: (TvShowEntity) -> Unit = {}
    private var onFavoriteTvUnChecked: (TvShowEntity) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTvShowBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            getItem(position) ?: return,
            onTvShowPressed,
            onFavoriteTvChecked,
            onFavoriteTvUnChecked
        )

    }

    class ViewHolder(private val binding: ItemTvShowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            tvOnTheAir: TvShowEntity,
            onTvShowPressed: (TvShowEntity) -> Unit,
            onFavoriteTvChecked: (TvShowEntity) -> Unit,
            onFavoriteTvUnChecked: (TvShowEntity) -> Unit
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

    fun setOnTvShowPressed(tvShow: (TvShowEntity) -> Unit) {
        this.onTvShowPressed = tvShow
    }

    fun setOnFavoriteTvChecked(tvShow: (TvShowEntity) -> Unit) {
        this.onFavoriteTvChecked = tvShow
    }

    fun setOnFavoriteTvUnChecked(tvShow: (TvShowEntity) -> Unit) {
        this.onFavoriteTvUnChecked = tvShow
    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(
                oldItem: TvShowEntity,
                newItem: TvShowEntity
            ): Boolean {
                return oldItem.tvId == newItem.tvId
            }

            override fun areContentsTheSame(
                oldItem: TvShowEntity,
                newItem: TvShowEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}