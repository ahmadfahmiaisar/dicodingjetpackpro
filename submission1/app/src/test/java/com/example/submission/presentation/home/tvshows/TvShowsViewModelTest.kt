package com.example.submission.presentation.home.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.submission.abstraction.UseCase
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.tvshow.TvOnTheAir
import com.example.submission.domain.repository.MovieRepository
import com.example.submission.domain.repository.TvShowRepository
import com.example.submission.domain.usecase.tvshow.GetTvOnTheAirUseCase
import com.example.submission.utils.CoroutinesTestRule
import com.example.submission.utils.observerTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class TvShowsViewModelTest {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private val repository = Mockito.mock(TvShowRepository::class.java)

    @Mock
    private lateinit var getTvOnTheAirUseCase: GetTvOnTheAirUseCase

    private lateinit var viewModel: TvShowsViewModel


    @Before
    fun setup() {
        getTvOnTheAirUseCase = GetTvOnTheAirUseCase(repository)
        viewModel = TvShowsViewModel(getTvOnTheAirUseCase)
    }

    @Test
    fun getTvShows() {
        coroutinesTestRule.dispatcher.runBlockingTest {
            val dummyList = mutableListOf<TvOnTheAir>()
            dummyList.add(TvOnTheAir(1, "tv", "desc", ""))

            val listTvSucces = Result.Success(dummyList)
            Mockito.`when`(getTvOnTheAirUseCase.invoke(UseCase.None)).thenReturn(listTvSucces)

            viewModel.getTvShows()
            viewModel.tvShows.observerTest {
                when (it) {
                    is Result.Success -> {
                        assertNotNull(it.data)
                        assertEquals(1, it.data.size)
                    }
                }
            }
        }
    }
}