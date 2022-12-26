package com.example.csci3130_g2_quick_cash.activities;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.csci3130_g2_quick_cash.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * My Postings UI Espresso test cases.
 *
 * @author Arman Singh Sidhu
 */
public class MyPostingsActivityTest {
    @Rule
    public ActivityScenarioRule<MyPostingsActivity> activityTestRule = new ActivityScenarioRule<>(MyPostingsActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @Test
    public void checkUIElementsExist() {
        onView(ViewMatchers.withId(R.id.toolbar_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.jobPost)).check(matches(isDisplayed()));
        onView(withId(R.id.fab)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void BackButtonsWorks(){
        onView(withId(R.id.fab)).perform(click());
        intended(hasComponent(WelcomeActivity.class.getName()));
    }

    @Test
    public void JobPostButtonWorks(){
        onView(withId(R.id.jobPost)).perform(click());
        intended(hasComponent(PredefinedJobsActivity.class.getName()));
    }

    @After
    public void tearDown() {
        Intents.release();
    }
}
