package com.example.csci3130_g2_quick_cash.activities;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Intent;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.csci3130_g2_quick_cash.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class PostActivityTest {
    @Rule
    public ActivityScenarioRule<PostActivity> activityTestRule = new ActivityScenarioRule<>(PostActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @Test
    public void checkUIElementsExist() {
        onView(ViewMatchers.withId(R.id.closePostViewBtn)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.postInformation)).check(matches(isDisplayed()));
        onView(withId(R.id.postTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.postDesc)).check(matches(isDisplayed()));
        onView(withId(R.id.postDuration)).check(matches(isDisplayed()));
        onView(withId(R.id.postPlace)).check(matches(isDisplayed()));
        onView(withId(R.id.postPay)).check(matches(isDisplayed()));
        onView(withId(R.id.suggestedEmployeeBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.textButton)).check(matches(isDisplayed()));
        onView(withId(R.id.closeListingB2)).check(matches(isDisplayed()));
        onView(withId(R.id.callButton)).check(matches(isDisplayed()));
        onView(withId(R.id.postIdBox)).check(matches(isDisplayed()));
    }

    @Test
    public void checkPayButton() {
        closeSoftKeyboard();
        onView(withId(R.id.closeListingB2)).perform(click());
        intended(hasComponent(PayPalActivity.class.getName()));
    }

    @Test
    public void checkMessageButton() {
        closeSoftKeyboard();
        onView(withId(R.id.textButton)).perform(click());
        intended(hasAction(Intent.ACTION_VIEW));
    }

    @Test
    public void checkCallButton() {
        closeSoftKeyboard();
        onView(withId(R.id.callButton)).perform(click());
        intended(hasAction(Intent.ACTION_DIAL));
    }

    @After
    public void tearDown() {
        Intents.release();
    }
}
