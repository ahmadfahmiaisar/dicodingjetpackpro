package com.example.submission.presentation.home.tvshows.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.tvshow.TvShowDetail
import com.example.submission.domain.entity.tvshow.TvShowDetailUiModel
import com.example.submission.domain.usecase.tvshow.GetTvShowDetailUseCase
import kotlinx.coroutines.launch

class TvShowDetailViewModel @ViewModelInject constructor(private val getTvShowDetailUseCase: GetTvShowDetailUseCase) :
    ViewModel() {

    private val _tvShowDetail = MutableLiveData<Result<TvShowDetail>>()
    val tvShowDetail: LiveData<Result<TvShowDetail>>
        get() = _tvShowDetail

    fun getTvShowDetail(idTv: Int) {
        _tvShowDetail.value = Result.Loading
        viewModelScope.launch {
            _tvShowDetail.value = getTvShowDetailUseCase(idTv)
        }
    }
}