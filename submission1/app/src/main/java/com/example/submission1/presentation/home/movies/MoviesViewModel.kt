package com.example.submission1.presentation.home.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission1.abstraction.UseCase
import com.example.submission1.data.vo.Result
import com.example.submission1.domain.entity.NowPlaying
import com.example.submission1.domain.usecase.GetMovieNowPlayingUseCase
import kotlinx.coroutines.launch

class MoviesViewModel @ViewModelInject constructor(private val getMovieNowPlayingUseCase: GetMovieNowPlayingUseCase) :
    ViewModel() {

    private val _movie = MutableLiveData<Result<List<NowPlaying>>>()
    val movie: LiveData<Result<List<NowPlaying>>>
        get() = _movie

    fun getMovie() {
        _movie.value = Result.Loading
        viewModelScope.launch {
            _movie.value = getMovieNowPlayingUseCase(UseCase.None)
        }
    }
}