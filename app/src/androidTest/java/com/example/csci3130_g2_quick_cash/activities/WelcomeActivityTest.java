package com.example.csci3130_g2_quick_cash.activities;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.csci3130_g2_quick_cash.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class WelcomeActivityTest {
    @Rule
    public ActivityScenarioRule<WelcomeActivity> activityTestRule = new ActivityScenarioRule<>(WelcomeActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @Test
    public void checkUIElementsExist() {
        onView(withId(R.id.MenuIcon)).check(matches(isDisplayed()));
        onView(withId(R.id.preferredListings)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.searchPostsBTN)).check(matches(isDisplayed()));
        onView(withId(R.id.refresh)).check(matches(isDisplayed()));
    }

    @Test
    public void MenuIconWorks(){
        onView(withId(R.id.MenuIcon)).perform(click());
        onView(withId(R.id.mypostings)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).check(matches(isDisplayed()));
        onView(withId(R.id.logout)).check(matches(isDisplayed()));
    }

    @Test
    public void PreferredListingsButtonWorks(){
        onView(withId(R.id.preferredListings)).perform(click());
        intended(hasComponent(PreferredPostingsActivity.class.getName()));
    }

    @Test
    public void SearchPostsButtonWorks(){
        onView(withId(R.id.searchPostsBTN)).perform(click());
        intended(hasComponent(PreferenceActivity.class.getName()));
    }

    @Test
    public void MenuBackButtonWorks(){
        onView(withId(R.id.MenuIcon)).perform(click());
        onView(withId(R.id.back)).perform(click());
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void MenuMyPostingsButtonWorks(){
        onView(withId(R.id.MenuIcon)).perform(click());
        onView(withId(R.id.mypostings)).perform(click());
        intended(hasComponent(MyPostingsActivity.class.getName()));
    }

    @Test
    public void MenuProfileButtonWorks(){
        onView(withId(R.id.MenuIcon)).perform(click());
        onView(withId(R.id.profile)).perform(click());
        intended(hasComponent(ProfileActivity.class.getName()));
    }

    @Test
    public void MenuLogoutButtonWorks(){
        onView(withId(R.id.MenuIcon)).perform(click());
        onView(withId(R.id.logout)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }

    @After
    public void tearDown() {
        Intents.release();
    }
}