package com.example.submission.presentation.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission.abstraction.UseCase
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.movie.MovieNowPlaying
import com.example.submission.domain.usecase.movie.GetMovieNowPlayingUseCase
import kotlinx.coroutines.launch

class MoviesViewModel @ViewModelInject constructor(private val getMovieNowPlayingUseCase: GetMovieNowPlayingUseCase) :
    ViewModel() {

    private val _movie = MutableLiveData<Result<List<MovieNowPlaying>>>()
    val movie: LiveData<Result<List<MovieNowPlaying>>>
        get() = _movie

    fun getMovie() {
        _movie.value = Result.Loading
        viewModelScope.launch {
            _movie.value = getMovieNowPlayingUseCase(UseCase.None)
        }
    }
}