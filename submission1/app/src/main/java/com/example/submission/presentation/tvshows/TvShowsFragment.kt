package com.example.submission.presentation.tvshows

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission.R
import com.example.submission.abstraction.BaseFragment
import com.example.submission.data.vo.Result
import com.example.submission.databinding.FragmentTvShowsBinding
import com.example.submission.presentation.tvshows.detail.TvShowDetailActivity
import com.example.submission.util.gone
import com.example.submission.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowsFragment : BaseFragment<FragmentTvShowsBinding, TvShowsViewModel>() {
    override fun getLayoutResourceId(): Int = R.layout.fragment_tv_shows
    override fun getViewModelClass(): Class<TvShowsViewModel> = TvShowsViewModel::class.java

    private val adapter by lazy { TvShowsRecycleAdapter(emptyList()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycleView()
        observeTvShowsResult()
        vm.getTvShows()
    }

    private fun observeTvShowsResult() {
        vm.tvShows.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Loading -> {
                    binding.shimmerView.start(9, R.layout.placeholder_item_movie)
                    binding.rvTvShow.gone()
                }
                is Result.Success -> {
                    binding.rvTvShow.visible()
                    binding.shimmerView.stop()
                    adapter.refreshTvShows(it.data)
                }
                is Result.Error -> {
                    binding.shimmerView.stop()
                    binding.rvTvShow.gone()
                }
                else -> {
                }
            }
        })
    }

    private fun setupRecycleView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvTvShow.layoutManager = layoutManager
        binding.rvTvShow.adapter = adapter

        adapter.setOnTvShowPressed {
            TvShowDetailActivity.start(requireActivity(), it.id)
        }
    }
}