package com.example.submission.presentation.movies.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.example.submission.R
import com.example.submission.abstraction.BaseActivity
import com.example.submission.abstraction.HasToolbar
import com.example.submission.data.vo.Result
import com.example.submission.databinding.ActivityMovieDetailBinding
import com.example.submission.domain.entity.movie.MovieDetail
import com.example.submission.util.loadUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : BaseActivity<ActivityMovieDetailBinding, MovieDetailViewModel>(),
    HasToolbar {
    override fun getLayoutResourceId(): Int = R.layout.activity_movie_detail
    override fun getViewModelClass(): Class<MovieDetailViewModel> = MovieDetailViewModel::class.java

    companion object {
        private const val INTENT_KEY_MOVIE_ID = "movieid"

        @JvmStatic
        fun start(context: Context, movieId: Int) {
            val starter = Intent(context, MovieDetailActivity::class.java)
                .putExtra(INTENT_KEY_MOVIE_ID, movieId)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeMovieDetail()
        handleIntentArguments()
        setupToolbar()
    }

    private fun handleIntentArguments() {
        val movieId = intent.extras?.getInt(INTENT_KEY_MOVIE_ID) ?: return
        vm.getMovieDetail(movieId)
    }

    private fun observeMovieDetail() {
        vm.movieDetail.observe(this, Observer {
            when (it) {
                is Result.Loading -> {
                    binding.shimmerView.start(1, R.layout.placeholder_detail_movie)
                }
                is Result.Success -> {
                    binding.shimmerView.stop()
                    displayDetailMovie(it.data)
                }
                is Result.Error -> {
                    binding.shimmerView.stop()
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun displayDetailMovie(data: MovieDetail) {
        binding.ivMovieDetail.loadUrl(data.posterPath)
        binding.tvTitle.text = data.title
        binding.tvPopularity.text = "popularity: ${data.popularity}"
        binding.tvDate.text = data.releaseDate
        binding.tvVoteAverage.text = "vote average: ${data.voteAverage}"
        binding.tvVoteCount.text = "vote count: ${data.voteCount}"
        binding.tvOverview.text = data.overview
    }

    override fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}