package com.example.submission.presentation.tvshows

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission.abstraction.UseCase
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.tvshow.TvOnTheAir
import com.example.submission.domain.usecase.tvshow.GetTvOnTheAirUseCase
import com.example.submission.helper.EspressoIdlingResourceWrapper
import kotlinx.coroutines.launch

class TvShowsViewModel @ViewModelInject constructor(private val getTvOnTheAirUseCase: GetTvOnTheAirUseCase) :
    ViewModel() {

    private val _tvShows = MutableLiveData<Result<List<TvOnTheAir>>>()
    val tvShows: LiveData<Result<List<TvOnTheAir>>>
        get() = _tvShows

    fun getTvShows() {
        EspressoIdlingResourceWrapper.increment()
        _tvShows.value = Result.Loading
        viewModelScope.launch {
            _tvShows.value = getTvOnTheAirUseCase(UseCase.None)
            EspressoIdlingResourceWrapper.decrement()
        }
    }
}