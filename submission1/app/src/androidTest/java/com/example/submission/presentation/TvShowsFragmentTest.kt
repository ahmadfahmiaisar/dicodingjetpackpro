package com.example.submission.presentation

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.example.submission.R
import org.junit.Rule
import org.junit.Test

class TvShowsFragmentTest {
    @get:Rule
    val activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun tvShowFragment() {
        onView(withId(R.id.viewPager)).check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(R.id.viewPager)).perform(swipeLeft())
        Thread.sleep(1000)
        onView(withId(R.id.rvTvShow)).check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(R.id.rvTvShow)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                5
            )
        )
        Thread.sleep(2000)
        onView(withId(R.id.rvTvShow)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                5,
                ViewActions.click()
            )
        )
        Thread.sleep(3000)
    }
}