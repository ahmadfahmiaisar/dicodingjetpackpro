package com.example.submission.presentation.tvshows

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.submission.R
import com.example.submission.abstraction.BaseFragment
import com.example.submission.data.vo.Result
import com.example.submission.databinding.FragmentTvShowsBinding
import com.example.submission.presentation.movies.detail.MovieDetailActivity
import com.example.submission.presentation.tvshows.detail.TvShowDetailActivity
import com.example.submission.util.gone
import com.example.submission.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowsFragment : BaseFragment<FragmentTvShowsBinding, TvShowsViewModel>() {
    override fun getLayoutResourceId(): Int = R.layout.fragment_tv_shows
    override fun getViewModelClass(): Class<TvShowsViewModel> = TvShowsViewModel::class.java

    private val adapter by lazy { TvShowsRecycleAdapter(emptyList()) }
    private val adapterTvFavorite by lazy { TvFavoriteRecycleAdapter(emptyList()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycleView()
        setupRecycleViewTvFavorite()
        observeTvShowsResult()
        observeTvFavorite()
        observeSetStatusFavorite()
        vm.getTvShows()
        vm.getTvFavorite()
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

    private fun observeTvFavorite() {
        vm.favoriteTv.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Loading -> {
                    binding.shimmerViewFavoriteTV.start(
                        1,
                        R.layout.placeholder_item_movie_favorite
                    )
                    binding.tvFavoriteTV.gone()
                    binding.horizontalScrollView.gone()
                }
                is Result.Success -> {
                    binding.shimmerViewFavoriteTV.stop()
                    binding.tvFavoriteTV.visible()
                    binding.horizontalScrollView.visible()
                    adapterTvFavorite.refreshMovieFavorite(it.data)
                }
                is Result.Error -> {
                    binding.shimmerViewFavoriteTV.stop()
                    binding.tvFavoriteTV.visible()
                    binding.tvFavoriteTV.text = "you don't have a favorite movie"
                    binding.horizontalScrollView.gone()
                }
            }
        })
    }

    private fun observeSetStatusFavorite() {
        vm.areStatusFavorite.observe(viewLifecycleOwner, {
            Toast.makeText(requireActivity(), "favorite changed", Toast.LENGTH_SHORT).show()
            vm.getTvFavorite()
        })
    }


    private fun setupRecycleView() {
        val layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.rvTvShow.layoutManager = layoutManager
        binding.rvTvShow.adapter = adapter

        adapter.setOnTvShowPressed {
            TvShowDetailActivity.start(requireActivity(), it.id)
        }
        adapter.setOnFavoriteTvChecked {
            vm.setStatusFavoriteMovie(true, it.id)
        }
        adapter.setOnFavoriteTvUnChecked {
            vm.setStatusFavoriteMovie(false, it.id)
        }
    }

    private fun setupRecycleViewTvFavorite() {
        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFavoriteTV.layoutManager = layoutManager
        binding.rvFavoriteTV.adapter = adapterTvFavorite

        adapterTvFavorite.setOnMoviePressed {
            MovieDetailActivity.start(requireActivity(), it.tvId.toInt())
        }
    }
}