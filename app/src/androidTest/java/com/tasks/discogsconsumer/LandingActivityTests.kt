package com.tasks.discogsconsumer

import androidx.cardview.widget.CardView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.tasks.discogsconsumer.EspressoUtils.withIndex
import com.tasks.discogsconsumer.ui.activity.MainActivity
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class LandingActivityTests {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
    }

    @Test
    fun landing_widgets_are_displayed() {
        onView(withId(R.id.toolbar_title)).check(matches(isDisplayed()))
        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()))
        onView(withId(R.id.releases_rv)).check(matches(isDisplayed()))
    }

    @Test
    fun click_on_release_item_takes_user_to_detail_view() {
        onView(withIndex(allOf(instanceOf(CardView::class.java),
            withParent(withId(R.id.releases_rv))), 0)).perform(click())
        onView(withId(R.id.release_detail_lb)).check(matches(isDisplayed()))
        onView(withId(R.id.status)).check(matches(isDisplayed()))
        onView(withId(R.id.type)).check(matches(isDisplayed()))
        onView(withId(R.id.title)).check(matches(isDisplayed()))
        onView(withId(R.id.year)).check(matches(isDisplayed()))
        onView(withId(R.id.artist)).check(matches(isDisplayed()))
        onView(withId(R.id.label)).check(matches(isDisplayed()))
        onView(withId(R.id.format)).check(matches(isDisplayed()))
    }
}