package com.example.csci3130_g2_quick_cash.activities;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
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

public class StatisticsActivityTest {
    @Rule
    public ActivityScenarioRule<StatisticsActivity> activityTestRule = new ActivityScenarioRule<>(StatisticsActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @Test
    public void checkUIElementsExist() {
        onView(ViewMatchers.withId(R.id.backButton2)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.statisticsTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.youMade)).check(matches(isDisplayed()));
        onView(withId(R.id.youCompleted)).check(matches(isDisplayed()));
        onView(withId(R.id.youClosed)).check(matches(isDisplayed()));
        onView(withId(R.id.youAre)).check(matches(isDisplayed()));
        onView(withId(R.id.textView14)).check(matches(isDisplayed()));
    }

    @Test
    public void checkBackButton() {
        closeSoftKeyboard();
        onView(withId(R.id.backButton2)).perform(click());
        intended(hasComponent(ProfileActivity.class.getName()));
    }

    @After
    public void tearDown() {
        Intents.release();
    }
}
