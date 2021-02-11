package com.example.submission1.presentation.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission1.data.vo.Result
import com.example.submission1.domain.entity.movie.MovieDetailUiModel
import com.example.submission1.domain.usecase.movie.GetMovieDetailUseCase
import kotlinx.coroutines.launch

class MovieDetailViewModel @ViewModelInject constructor(private val getMovieDetailUseCase: GetMovieDetailUseCase) :
    ViewModel() {

    private val _movieDetail = MutableLiveData<Result<MovieDetailUiModel>>()
    val movieDetail: LiveData<Result<MovieDetailUiModel>>
        get() = _movieDetail

    fun getMovieDetail(idMovie: Int) {
        _movieDetail.value = Result.Loading
        viewModelScope.launch {
            _movieDetail.value = getMovieDetailUseCase(idMovie)
        }
    }
}