package com.example.submission.presentation.home.tvshows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submission.databinding.ItemTvShowBinding
import com.example.submission.domain.entity.tvshow.TvOnTheAir

class TvShowsRecycleAdapter(private var tvshows: List<TvOnTheAir>) :
    RecyclerView.Adapter<TvShowsRecycleAdapter.ViewHolder>() {

    private var onTvShowPressed: (TvOnTheAir) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTvShowBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tvshows[position], onTvShowPressed)
    }

    override fun getItemCount(): Int = tvshows.size

    class ViewHolder(private val binding: ItemTvShowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvOnTheAir: TvOnTheAir, onTvShowPressed: (TvOnTheAir) -> Unit) {
            binding.tvshow = tvOnTheAir
            binding.root.setOnClickListener {
                onTvShowPressed(tvOnTheAir)
            }
            binding.executePendingBindings()
        }
    }

    fun refreshTvShows(tvshow: List<TvOnTheAir>) {
        this.tvshows = tvshow
        notifyDataSetChanged()
    }

    fun setOnTvShowPressed(tvShow: (TvOnTheAir) -> Unit) {
        this.onTvShowPressed = tvShow
    }

}