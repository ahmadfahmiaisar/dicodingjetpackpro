package com.example.submission.presentation.tvshows

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.submission.domain.entity.tvshow.TvShowEntity
import com.example.submission.domain.usecase.tvshow.GetAllTvFavoriteUseCase
import com.example.submission.domain.usecase.tvshow.GetTvOnTheAirUseCase
import com.example.submission.domain.usecase.tvshow.UpdateFavoriteTvUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TvShowsViewModel @ViewModelInject constructor(
    private val getTvOnTheAirUseCase: GetTvOnTheAirUseCase,
    private val getAllTvFavoriteUseCase: GetAllTvFavoriteUseCase,
    private val updateFavoriteTvUseCase: UpdateFavoriteTvUseCase
) :
    ViewModel() {

    private val _areStatusFavorite = MutableLiveData<Unit>()
    val areStatusFavorite: LiveData<Unit>
        get() = _areStatusFavorite

    fun setStatusFavoriteMovie(isFavorite: Boolean, movieId: Int) {
        viewModelScope.launch {
            _areStatusFavorite.value = updateFavoriteTvUseCase(isFavorite, movieId)
        }
    }

    suspend fun getTvShows(): Flow<PagingData<TvShowEntity>> {
        return getTvOnTheAirUseCase().cachedIn(viewModelScope)
    }

    fun getAllTvShowFavorite(): Flow<PagingData<TvShowEntity>> {
        return getAllTvFavoriteUseCase()
    }
}