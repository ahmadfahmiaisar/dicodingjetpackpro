package com.example.submission.presentation.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.submission.abstraction.UseCase
import com.example.submission.domain.entity.movie.MovieEntity
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.movie.MovieNowPlaying
import com.example.submission.domain.usecase.movie.GetAllMovieFavoriteUseCase
import com.example.submission.domain.usecase.movie.GetMovieNowPlayingUseCase
import com.example.submission.domain.usecase.movie.UpdateFavoriteMovieUseCase
import com.example.submission.helper.EspressoIdlingResourceWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MoviesViewModel @ViewModelInject constructor(
    private val getMovieNowPlayingUseCase: GetMovieNowPlayingUseCase,
    private val getAllMovieFavoriteUseCase: GetAllMovieFavoriteUseCase,
    private val updateFavoriteMovieUseCase: UpdateFavoriteMovieUseCase
) :
    ViewModel() {

    private val _movie = MutableLiveData<Result<List<MovieNowPlaying>>>()
    val movie: LiveData<Result<List<MovieNowPlaying>>>
        get() = _movie

    private val _favoriteMovie = MutableLiveData<Result<List<MovieEntity>>>()
    val favoriteMovie: LiveData<Result<List<MovieEntity>>>
        get() = _favoriteMovie

    private val _areStatusFavorite = MutableLiveData<Unit>()
    val areStatusFavorite: LiveData<Unit>
        get() = _areStatusFavorite

    fun getMovie() {
        EspressoIdlingResourceWrapper.increment()
        _movie.value = Result.Loading
        viewModelScope.launch {
//            _movie.value = getMovieNowPlayingUseCase(UseCase.None)
            EspressoIdlingResourceWrapper.decrement()
        }
    }

    fun getMovieFavorite() {
        _favoriteMovie.value = Result.Loading
        viewModelScope.launch {
            _favoriteMovie.value = getAllMovieFavoriteUseCase(UseCase.None)
        }
    }

    fun setStatusFavoriteMovie(isFavorite: Boolean, movieId: Int) {
        viewModelScope.launch {
            _areStatusFavorite.value = updateFavoriteMovieUseCase(isFavorite, movieId)
        }
    }

    fun getAllMovie(): Flow<PagingData<MovieNowPlaying>>{
        return getMovieNowPlayingUseCase()
    }
}