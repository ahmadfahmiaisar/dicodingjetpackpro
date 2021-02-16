package com.example.submission.presentation.tvshows.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.submission.R
import com.example.submission.abstraction.BaseActivity
import com.example.submission.abstraction.HasToolbar
import com.example.submission.data.vo.Result
import com.example.submission.databinding.ActivityTvShowDetailBinding
import com.example.submission.domain.entity.tvshow.TvShowDetail
import com.example.submission.util.loadUrl
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
                    displayDetailTv(it.data)
                }
                is Result.Error -> {
                    binding.shimmerView.stop()
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun displayDetailTv(data: TvShowDetail) {
        binding.ivMovieDetail.loadUrl(data.posterPath)
        binding.tvTitle.text = data.name
        binding.tvPopularity.text = "popularity: ${data.popularity}"
        binding.tvDate.text = data.lastAirDate
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