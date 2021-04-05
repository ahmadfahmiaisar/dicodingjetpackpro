package com.example.submission.presentation.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.submission.domain.entity.movie.MovieEntity
import com.example.submission.domain.usecase.movie.GetAllMovieFavoriteUseCase
import com.example.submission.domain.usecase.movie.GetMovieNowPlayingUseCase
import com.example.submission.domain.usecase.movie.UpdateFavoriteMovieUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MoviesViewModel @ViewModelInject constructor(
    private val getMovieNowPlayingUseCase: GetMovieNowPlayingUseCase,
    private val getAllMovieFavoriteUseCase: GetAllMovieFavoriteUseCase,
    private val updateFavoriteMovieUseCase: UpdateFavoriteMovieUseCase
) :
    ViewModel() {

    private val _areStatusFavorite = MutableLiveData<Unit>()
    val areStatusFavorite: LiveData<Unit>
        get() = _areStatusFavorite


    fun setStatusFavoriteMovie(isFavorite: Boolean, movieId: Int) {
        viewModelScope.launch {
            _areStatusFavorite.value = updateFavoriteMovieUseCase(isFavorite, movieId)
        }
    }

    suspend fun getAllMovie(): Flow<PagingData<MovieEntity>> {
        return getMovieNowPlayingUseCase().cachedIn(viewModelScope)
    }

    fun getAllMovieFavorite(): Flow<PagingData<MovieEntity>> {
        return getAllMovieFavoriteUseCase()
    }
}