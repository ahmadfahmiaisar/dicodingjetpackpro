package com.example.submission.presentation.movies

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission.R
import com.example.submission.abstraction.BaseFragment
import com.example.submission.databinding.FragmentMoviesBinding
import com.example.submission.presentation.movies.detail.MovieDetailActivity
import com.example.submission.util.RecycleViewLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding, MoviesViewModel>() {
    override fun getLayoutResourceId(): Int = R.layout.fragment_movies
    override fun getViewModelClass(): Class<MoviesViewModel> = MoviesViewModel::class.java

    private val adapter by lazy { MoviesRecycleAdapter() }
    private val adapterMovieFavorite by lazy { MovieFavoriteRecycleAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycleView()
        setupRecycleViewMovieFavorite()
        observeSetStatusFavorite()

        observeGetAllMovie()
        observeGetAllMovieFavorite()
    }



    private fun observeGetAllMovie() {
        lifecycleScope.launch {
            vm.getAllMovie().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun observeGetAllMovieFavorite() {
        lifecycleScope.launch {
            vm.getAllMovieFavorite().collectLatest {
                adapterMovieFavorite.submitData(it)
                Timber.tag("ISINYAIT").d("$it")
            }
        }
    }


    private fun observeSetStatusFavorite() {
        vm.areStatusFavorite.observe(viewLifecycleOwner, {
            Toast.makeText(requireActivity(), "favorite changed", Toast.LENGTH_SHORT).show()
            observeGetAllMovieFavorite()
        })
    }

    private fun setupRecycleView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvMovie.layoutManager = layoutManager
        binding.rvMovie.adapter =
            adapter.withLoadStateFooter(footer = RecycleViewLoadStateAdapter())

        adapter.setOnMoviePressed {
            MovieDetailActivity.start(requireActivity(), it.movieId)
        }
        adapter.setOnFavoriteMovieChecked {
            vm.setStatusFavoriteMovie(true, it.movieId)
        }
        adapter.setOnFavoriteMovieUnChecked {
            vm.setStatusFavoriteMovie(false, it.movieId)
        }
    }

    private fun setupRecycleViewMovieFavorite() {
        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFavoriteMovie.layoutManager = layoutManager
        binding.rvFavoriteMovie.adapter = adapterMovieFavorite

        adapterMovieFavorite.setOnMoviePressed {
            MovieDetailActivity.start(requireActivity(), it.movieId)
        }
    }
}