package com.example.csci3130_g2_quick_cash.activities;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.app.Activity;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.csci3130_g2_quick_cash.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * My ProfileActivity UI Espresso test cases.
 *
 * @author Arman Singh Sidhu
 */
public class ProfileActivityTest {
    @Rule
    public ActivityScenarioRule<ProfileActivity> activityTestRule = new ActivityScenarioRule<>(ProfileActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @Test
    public void checkUIElementsExist() {
        onView(ViewMatchers.withId(R.id.profileTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.verifiedStatus)).check(matches(isDisplayed()));
        onView(withId(R.id.backBut)).check(matches(isDisplayed()));
        onView(withId(R.id.preferencesB)).check(matches(isDisplayed()));
        onView(withId(R.id.statisticsButton)).check(matches(isDisplayed()));
        onView(withId(R.id.historyButton)).check(matches(isDisplayed()));
    }

    @Test
    public void BackButtonsWorks(){
        onView(withId(R.id.backBut)).perform(click());
        intended(hasComponent(WelcomeActivity.class.getName()));
    }

    @Test
    public void PreferencesButtonWorks(){
        onView(withId(R.id.preferencesB)).perform(click());
        intended(hasComponent(PreferenceActivity.class.getName()));
    }

    @Test
    public void HistoryButtonWorks(){
        onView(withId(R.id.historyButton)).perform(click());
        intended(hasComponent(HistoryActivity.class.getName()));
    }

    @Test
    public void StatisticsButtonWorks(){
        onView(withId(R.id.statisticsButton)).perform(click());
        intended(hasComponent(StatisticsActivity.class.getName()));
    }

    @After
    public void tearDown() {
        Intents.release();
    }
}
