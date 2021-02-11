package com.example.submission1.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.example.submission1.R
import com.example.submission1.abstraction.BaseActivity
import com.example.submission1.abstraction.HasToolbar
import com.example.submission1.data.vo.Result
import com.example.submission1.databinding.ActivityMovieDetailBinding
import com.example.submission1.util.loadUrl
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
                    binding.ivMovieDetail.loadUrl(it.data.posterPath)
                    binding.movieDetail = it.data
                }
                is Result.Error -> {
                    binding.shimmerView.stop()
                }
            }
        })
    }

    override fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}