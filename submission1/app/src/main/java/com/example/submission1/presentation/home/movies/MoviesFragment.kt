package com.example.submission1.presentation.home.movies

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1.R
import com.example.submission1.abstraction.BaseFragment
import com.example.submission1.data.vo.Result
import com.example.submission1.databinding.FragmentMoviesBinding
import com.example.submission1.util.gone
import com.example.submission1.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding, MoviesViewModel>() {
    override fun getLayoutResourceId(): Int = R.layout.fragment_movies
    override fun getViewModelClass(): Class<MoviesViewModel> = MoviesViewModel::class.java

    private val adapter by lazy { MoviesRecycleAdapter(emptyList()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycleView()
        observeMovieNowPlaying()
        vm.getMovie()
    }

    private fun observeMovieNowPlaying() {
        vm.movie.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Loading -> {
                    binding.shimmerView.start(5, R.layout.placeholder_item_movie)
                    binding.recyclerView.gone()
                }
                is Result.Success -> {
                    binding.recyclerView.visible()
                    binding.shimmerView.stop()
                    adapter.refreshMovieNowPlaying(it.data)
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

        adapter.setOnMoviePressed {
            //TODO to screen detail
        }
    }
}