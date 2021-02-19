package com.example.submission.rule

import androidx.test.espresso.IdlingRegistry
import com.example.submission.helper.EspressoIdlingResourceWrapper
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class IdlingResourceRule : TestWatcher() {
    override fun starting(description: Description?) {
        IdlingRegistry.getInstance().register(EspressoIdlingResourceWrapper.idlingResource)
        super.starting(description)
    }

    override fun finished(description: Description?) {
        IdlingRegistry.getInstance().register(EspressoIdlingResourceWrapper.idlingResource)
        super.finished(description)
    }
}