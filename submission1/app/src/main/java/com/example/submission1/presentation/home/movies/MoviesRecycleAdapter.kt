package com.example.submission1.presentation.home.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submission1.databinding.ItemMoviesBinding
import com.example.submission1.domain.entity.NowPlaying
import com.example.submission1.util.loadUrl

class MoviesRecycleAdapter(private var nowPlaying: List<NowPlaying>) :
    RecyclerView.Adapter<MoviesRecycleAdapter.ViewHolder>() {

    private var onMoviePressed: (NowPlaying) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMoviesBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(nowPlaying[position], onMoviePressed)
    }

    override fun getItemCount(): Int = nowPlaying.size

    class ViewHolder(private val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(nowPlaying: NowPlaying, onMoviePressed: (NowPlaying) -> Unit) {
            binding.movie = nowPlaying
            binding.ivMovie.loadUrl(nowPlaying.posterPath)
            binding.root.setOnClickListener { onMoviePressed(nowPlaying) }
            binding.executePendingBindings()
        }
    }

    fun refreshMovieNowPlaying(nowPlaying: List<NowPlaying>) {
        this.nowPlaying = nowPlaying
        notifyDataSetChanged()
    }

    fun setOnMoviePressed(movie: (NowPlaying) -> Unit) {
        this.onMoviePressed = movie
    }
}