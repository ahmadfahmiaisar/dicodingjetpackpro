package com.example.submission.presentation.home.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.submission.abstraction.UseCase
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.tvshow.TvOnTheAir
import com.example.submission.domain.usecase.tvshow.GetTvOnTheAirUseCase
import com.example.submission.presentation.tvshows.TvShowsViewModel
import com.example.submission.utils.CoroutinesTestRule
import com.example.submission.utils.observerTest
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

@ExperimentalCoroutinesApi
class TvShowsViewModelTest {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private val getTvOnTheAirUseCase = mock(GetTvOnTheAirUseCase::class.java)

    private lateinit var viewModel: TvShowsViewModel


 /*   @Before
    fun setup() {
        viewModel = TvShowsViewModel(getTvOnTheAirUseCase)
    }*/

   /* @Test
    fun getTvShows() {
        coroutinesTestRule.dispatcher.runBlockingTest {
            val tvShow = MutableLiveData<Result<List<TvOnTheAir>>>()
            tvShow.value = dummyTvShow()
            Mockito.`when`(getTvOnTheAirUseCase.invoke(UseCase.None)).thenReturn(tvShow.value)
            viewModel.getTvShows()
            Mockito.verify(getTvOnTheAirUseCase).invoke(UseCase.None)
            viewModel.tvShows.observerTest {
                when (it) {
                    is Result.Success -> {
                        assertNotNull(it.data)
                        assertEquals(1, it.data.size)
                    }
                }
            }
        }
    }*/

   /* private fun dummyTvShow(): Result<List<TvOnTheAir>> {
        val dummyTv = mutableListOf<TvOnTheAir>()
        dummyTv.add(TvOnTheAir(1, "tv", "desc", ""))
        return Result.Success(dummyTv)
    }*/
}