package com.example.submission.presentation.home.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.tvshow.TvShowDetail
import com.example.submission.domain.repository.TvShowRepository
import com.example.submission.domain.usecase.tvshow.GetTvShowDetailUseCase
import com.example.submission.presentation.tvshows.detail.TvShowDetailViewModel
import com.example.submission.utils.CoroutinesTestRule
import com.example.submission.utils.observerTest
import dalvik.annotation.TestTarget
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class TvShowDetailViewModelTest {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private val repository = Mockito.mock(TvShowRepository::class.java)

    @Mock
    private lateinit var getTvShowDetailUseCase: GetTvShowDetailUseCase

    private lateinit var viewModel: TvShowDetailViewModel

    @Before
    fun setup() {
        getTvShowDetailUseCase = GetTvShowDetailUseCase(repository)
        viewModel = TvShowDetailViewModel(getTvShowDetailUseCase)
    }

    @Test
    fun getTvShowDetail() {
        coroutinesTestRule.dispatcher.runBlockingTest {
            val dummy = TvShowDetail(0, "title", "desc", 0.0, "", "date", 0.0, 0)

            val tvDetailSucces = Result.Success(dummy)
            Mockito.`when`(getTvShowDetailUseCase.invoke(1)).thenReturn(tvDetailSucces)

            viewModel.getTvShowDetail(1)
            viewModel.tvShowDetail.observerTest {
                when (it) {
                    is Result.Success -> {
                        assertNotNull(it.data)
                        assertEquals(dummy.id, it.data.id)
                    }
                }
            }
        }
    }
}