package com.example.submission.presentation.movies

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission.R
import com.example.submission.abstraction.BaseFragment
import com.example.submission.data.vo.Result
import com.example.submission.databinding.FragmentMoviesBinding
import com.example.submission.presentation.movies.detail.MovieDetailActivity
import com.example.submission.util.gone
import com.example.submission.util.visible
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding, MoviesViewModel>() {
    override fun getLayoutResourceId(): Int = R.layout.fragment_movies
    override fun getViewModelClass(): Class<MoviesViewModel> = MoviesViewModel::class.java

    private val adapter by lazy { MoviesRecycleAdapter(emptyList()) }
    private val adapterMovieFavorite by lazy { MovieFavoriteRecycleAdapter(emptyList()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycleView()
        setupRecycleViewMovieFavorite()
        observeMovieNowPlaying()
        observeMovieFavorite()
        vm.getMovie()
        vm.getMovieFavorite()
    }

    private fun observeMovieFavorite() {
        vm.favoriteMovie.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Loading -> {
                    binding.tvFavoriteMovie.gone()
                    binding.horizontalScrollView.gone()
                }
                is Result.Success -> {
                    binding.tvFavoriteMovie.visible()
                    binding.horizontalScrollView.visible()
                    adapterMovieFavorite.refreshMovieFavorite(it.data)
                }
                is Result.Error -> {
                    binding.tvFavoriteMovie.visible()
                    binding.tvFavoriteMovie.text = "you don't have a favorite movie"
                    binding.horizontalScrollView.gone()
                }
            }
        })
    }

    private fun observeMovieNowPlaying() {
        vm.movie.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Loading -> {
                    binding.shimmerView.start(9, R.layout.placeholder_item_movie)
                    binding.rvMovie.gone()
                }
                is Result.Success -> {
                    binding.rvMovie.visible()
                    binding.shimmerView.stop()
                    adapter.refreshMovieNowPlaying(it.data)
                }
                is Result.Error -> {
                    binding.shimmerView.stop()
                    binding.rvMovie.gone()
                }
                else -> {
                }
            }
        })
    }

    private fun setupRecycleView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvMovie.layoutManager = layoutManager
        binding.rvMovie.adapter = adapter

        adapter.setOnMoviePressed {
            MovieDetailActivity.start(requireActivity(), it.id)
        }
    }

    private fun setupRecycleViewMovieFavorite() {
        val layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFavoriteMovie.layoutManager = layoutManager
        binding.rvFavoriteMovie.adapter = adapterMovieFavorite

        adapterMovieFavorite.setOnMoviePressed {
            MovieDetailActivity.start(requireActivity(), it.id)
        }
    }
}