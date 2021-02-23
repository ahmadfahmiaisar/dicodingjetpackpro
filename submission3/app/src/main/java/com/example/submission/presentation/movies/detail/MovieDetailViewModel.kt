package com.example.submission.presentation.movies.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.movie.MovieDetail
import com.example.submission.domain.usecase.movie.GetMovieDetailUseCase
import com.example.submission.helper.EspressoIdlingResourceWrapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MovieDetailViewModel @ViewModelInject constructor(private val getMovieDetailUseCase: GetMovieDetailUseCase) :
    ViewModel() {

    private val _movieDetail = MutableLiveData<Result<MovieDetail>>()
    val movieDetail: LiveData<Result<MovieDetail>>
        get() = _movieDetail

    fun getMovieDetail(idMovie: Int) {
        EspressoIdlingResourceWrapper.increment()
        _movieDetail.value = Result.Loading
        viewModelScope.launch {
            _movieDetail.value = getMovieDetailUseCase(idMovie)
            EspressoIdlingResourceWrapper.decrement()
        }
    }
}