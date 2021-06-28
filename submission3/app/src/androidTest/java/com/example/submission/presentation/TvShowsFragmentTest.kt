package com.example.submission.presentation

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.example.submission.R
import com.example.submission.helper.EspressoIdlingResourceWrapper
import com.example.submission.presentation.tvshows.detail.TvShowDetailActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TvShowsFragmentTest {
    @get:Rule
    val activityRule = ActivityTestRule(HomeActivity::class.java)

    @get:Rule
    val activityRuleDetailTv = ActivityTestRule(TvShowDetailActivity::class.java, false, false)

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResourceWrapper.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResourceWrapper.idlingResource)
    }

    @Test
    fun tvShowFragment() {
        onView(withId(R.id.viewPager)).check(matches(isDisplayed()))
        onView(withId(R.id.viewPager)).perform(swipeLeft())

        onView(withId(R.id.rvTvShow)).check(matches(isDisplayed()))
        onView(withId(R.id.rvTvShow)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                10
            )
        )
        onView(withId(R.id.rvTvShow)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
    }

    @Test
    fun tvShowDetail() {
        val intent = Intent().putExtra(
            TvShowDetailActivity.INTENT_KEY_TV_ID, 85271
        )
        activityRuleDetailTv.launchActivity(intent)
        onView(withId(R.id.tvTitleTv)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTitleTv)).check(matches(withText("WandaVision")))
        onView(withId(R.id.tvPopularityTvShow)).check(matches(isDisplayed()))
        onView(withId(R.id.tvDateTvShow)).check(matches(isDisplayed()))
        onView(withId(R.id.tvVoteAverageTvShow)).check(matches(isDisplayed()))
        onView(withId(R.id.tvVoteCountTvShow)).check(matches(isDisplayed()))
        onView(withId(R.id.tvOverViewTvShow)).check(matches(isDisplayed()))
    }
}