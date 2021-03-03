package com.example.submission.presentation.tvshows

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission.abstraction.UseCase
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.tvshow.TvOnTheAir
import com.example.submission.domain.entity.tvshow.TvShowEntity
import com.example.submission.domain.usecase.tvshow.GetAllTvFavoriteUseCase
import com.example.submission.domain.usecase.tvshow.GetTvOnTheAirUseCase
import com.example.submission.domain.usecase.tvshow.UpdateFavoriteTvUseCase
import com.example.submission.helper.EspressoIdlingResourceWrapper
import kotlinx.coroutines.launch

class TvShowsViewModel @ViewModelInject constructor(
    private val getTvOnTheAirUseCase: GetTvOnTheAirUseCase,
    private val getAllTvFavoriteUseCase: GetAllTvFavoriteUseCase,
    private val updateFavoriteTvUseCase: UpdateFavoriteTvUseCase
) :
    ViewModel() {

    private val _tvShows = MutableLiveData<Result<List<TvOnTheAir>>>()
    val tvShows: LiveData<Result<List<TvOnTheAir>>>
        get() = _tvShows

    private val _favoriteTv = MutableLiveData<Result<List<TvShowEntity>>>()
    val favoriteTv: LiveData<Result<List<TvShowEntity>>>
        get() = _favoriteTv

    private val _areStatusFavorite = MutableLiveData<Unit>()
    val areStatusFavorite: LiveData<Unit>
        get() = _areStatusFavorite

    fun getTvShows() {
        EspressoIdlingResourceWrapper.increment()
        _tvShows.value = Result.Loading
        viewModelScope.launch {
            _tvShows.value = getTvOnTheAirUseCase(UseCase.None)
            EspressoIdlingResourceWrapper.decrement()
        }
    }

    fun getTvFavorite() {
        _favoriteTv.value = Result.Loading
        viewModelScope.launch {
            _favoriteTv.value = getAllTvFavoriteUseCase(UseCase.None)
        }
    }

    fun setStatusFavoriteMovie(isFavorite: Boolean, movieId: Int) {
        viewModelScope.launch {
            _areStatusFavorite.value = updateFavoriteTvUseCase(isFavorite, movieId)
        }
    }
}