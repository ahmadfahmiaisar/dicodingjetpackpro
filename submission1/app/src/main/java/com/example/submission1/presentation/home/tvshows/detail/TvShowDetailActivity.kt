package com.example.submission1.presentation.home.tvshows.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.submission1.R
import com.example.submission1.abstraction.BaseActivity
import com.example.submission1.abstraction.HasToolbar
import com.example.submission1.data.vo.Result
import com.example.submission1.databinding.ActivityTvShowDetailBinding
import com.example.submission1.util.loadUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowDetailActivity : BaseActivity<ActivityTvShowDetailBinding, TvShowDetailViewModel>(),
    HasToolbar {
    override fun getLayoutResourceId(): Int = R.layout.activity_tv_show_detail
    override fun getViewModelClass(): Class<TvShowDetailViewModel> =
        TvShowDetailViewModel::class.java

    companion object {
        private const val INTENT_KEY_TV_ID = "tvid"

        @JvmStatic
        fun start(context: Context, tvId: Int) {
            val starter = Intent(context, TvShowDetailActivity::class.java)
                .putExtra(INTENT_KEY_TV_ID, tvId)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeTvShowDetail()
        handleIntentArguments()
        setupToolbar()
    }

    private fun observeTvShowDetail() {
        val tvId = intent.extras?.getInt(INTENT_KEY_TV_ID) ?: return
        vm.getTvShowDetail(tvId)
    }

    private fun handleIntentArguments() {
        vm.tvShowDetail.observe(this, {
            when (it) {
                is Result.Loading -> {
                    binding.shimmerView.start(1, R.layout.placeholder_detail_movie)
                }
                is Result.Success -> {
                    binding.shimmerView.stop()
                    binding.ivMovieDetail.loadUrl(it.data.posterPath)
                    binding.tvDetail = it.data
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