package com.example.submission.presentation.home.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.tvshow.TvShowDetail
import com.example.submission.domain.usecase.tvshow.GetTvShowDetailUseCase
import com.example.submission.presentation.tvshows.detail.TvShowDetailViewModel
import com.example.submission.utils.CoroutinesTestRule
import com.example.submission.utils.observerTest
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.never

@ExperimentalCoroutinesApi
class TvShowDetailViewModelTest {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private val getTvShowDetailUseCase = mock(GetTvShowDetailUseCase::class.java)

    private lateinit var viewModel: TvShowDetailViewModel

    @Before
    fun setup() {
        viewModel = TvShowDetailViewModel(getTvShowDetailUseCase)
    }

    @Test
    fun getTvShowDetail() {
        coroutinesTestRule.dispatcher.runBlockingTest {
            val tvId = 1
            val detailTvShow = MutableLiveData<Result<TvShowDetail>>()
            detailTvShow.value = dummyDetailTv()
            Mockito.`when`(getTvShowDetailUseCase.invoke(tvId)).thenReturn(detailTvShow.value)
            verify(getTvShowDetailUseCase, never()).invoke(tvId)
            viewModel.getTvShowDetail(1)
            viewModel.tvShowDetail.observerTest {
                when (it) {
                    is Result.Success -> {
                        assertNotNull(it.data)
                        assertEquals(tvId, it.data.id)
                    }
                }
            }
        }
    }

    private fun dummyDetailTv(): Result<TvShowDetail> {
        return Result.Success(
            TvShowDetail(1, "title", "desc", 0.0, "", "date", 0.0, 0)
        )
    }
}