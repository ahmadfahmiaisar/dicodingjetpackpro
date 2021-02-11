package com.example.submission1.presentation.home.tvshows

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission1.abstraction.UseCase
import com.example.submission1.data.vo.Result
import com.example.submission1.domain.entity.tvshow.TvOnTheAir
import com.example.submission1.domain.usecase.tvshow.GetTvOnTheAirUseCase
import kotlinx.coroutines.launch

class TvShowsViewModel @ViewModelInject constructor(private val getTvOnTheAirUseCase: GetTvOnTheAirUseCase) :
    ViewModel() {

    private val _tvShows = MutableLiveData<Result<List<TvOnTheAir>>>()
    val tvShows: LiveData<Result<List<TvOnTheAir>>>
        get() = _tvShows

    fun getTvShows() {
        _tvShows.value = Result.Loading
        viewModelScope.launch {
            _tvShows.value = getTvOnTheAirUseCase(UseCase.None)
        }
    }
}