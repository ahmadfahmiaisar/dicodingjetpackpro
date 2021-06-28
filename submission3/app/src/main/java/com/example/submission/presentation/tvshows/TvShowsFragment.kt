package com.example.submission.presentation.tvshows

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.submission.R
import com.example.submission.abstraction.BaseFragment
import com.example.submission.databinding.FragmentTvShowsBinding
import com.example.submission.presentation.movies.detail.MovieDetailActivity
import com.example.submission.presentation.tvshows.detail.TvShowDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvShowsFragment : BaseFragment<FragmentTvShowsBinding, TvShowsViewModel>() {
    override fun getLayoutResourceId(): Int = R.layout.fragment_tv_shows
    override fun getViewModelClass(): Class<TvShowsViewModel> = TvShowsViewModel::class.java

    private val adapter by lazy { TvShowsRecycleAdapter() }
    private val adapterTvFavorite by lazy { TvFavoriteRecycleAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycleView()
        setupRecycleViewTvFavorite()

        observeSetStatusFavorite()

        observeGetAllTvShowFavorite()
        observeGetAllTvShow()
    }

    private fun observeGetAllTvShow() {
        lifecycleScope.launch {
            vm.getTvShows().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun observeGetAllTvShowFavorite() {
        lifecycleScope.launch {
            vm.getAllTvShowFavorite().collectLatest {
                adapterTvFavorite.submitData(it)
            }
        }
    }

    private fun observeSetStatusFavorite() {
        vm.areStatusFavorite.observe(viewLifecycleOwner, {
            Toast.makeText(requireActivity(), "favorite changed", Toast.LENGTH_SHORT).show()
            observeGetAllTvShowFavorite()
        })
    }


    private fun setupRecycleView() {
        val layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.rvTvShow.layoutManager = layoutManager
        binding.rvTvShow.adapter = adapter

        adapter.setOnTvShowPressed {
            TvShowDetailActivity.start(requireActivity(), it.tvId)
        }
        adapter.setOnFavoriteTvChecked {
            vm.setStatusFavoriteMovie(true, it.tvId)
        }
        adapter.setOnFavoriteTvUnChecked {
            vm.setStatusFavoriteMovie(false, it.tvId)
        }
    }

    private fun setupRecycleViewTvFavorite() {
        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFavoriteTV.layoutManager = layoutManager
        binding.rvFavoriteTV.adapter = adapterTvFavorite

        adapterTvFavorite.setOnMoviePressed {
            MovieDetailActivity.start(requireActivity(), it.tvId)
        }
    }
}