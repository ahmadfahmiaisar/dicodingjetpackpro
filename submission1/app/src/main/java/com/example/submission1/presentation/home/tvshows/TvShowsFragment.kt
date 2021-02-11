package com.example.submission1.presentation.home.tvshows

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1.R
import com.example.submission1.abstraction.BaseFragment
import com.example.submission1.data.vo.Result
import com.example.submission1.databinding.FragmentTvShowsBinding
import com.example.submission1.util.gone
import com.example.submission1.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowFragment : BaseFragment<FragmentTvShowsBinding, TvShowsViewModel>() {
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
                    binding.recyclerView.gone()
                }
                is Result.Success -> {
                    binding.recyclerView.visible()
                    binding.shimmerView.stop()
                    adapter.refreshTvShows(it.data)
                }
                is Result.Error -> {
                    binding.shimmerView.stop()
                    binding.recyclerView.gone()
                }
                else -> {
                }
            }
        })
    }

    private fun setupRecycleView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        adapter.setOnTvShowPressed {
            //TODO in detail
        }
    }
}