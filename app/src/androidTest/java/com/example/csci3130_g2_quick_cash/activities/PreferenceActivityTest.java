package com.example.csci3130_g2_quick_cash.activities;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.csci3130_g2_quick_cash.R;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Preference validation Espresso test cases.
 *
 * @author Gavin Jayeshkum Jariwala, Arman Singh Sidhu
 */
public class PreferenceActivityTest {
    @Rule
    public ActivityScenarioRule<PreferenceActivity> activityTestRule = new ActivityScenarioRule<>(PreferenceActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @Test
    public void checkIfPreferencePageIsVisible() {
        onView(ViewMatchers.withId(R.id.textView7)).check(matches(isDisplayed()));
        onView(withId(R.id.backBut)).check(matches(isDisplayed()));
        onView(withId(R.id.jobTitlePreference)).check(matches(isDisplayed()));
        onView(withId(R.id.jobSalaryPreference)).check(matches(isDisplayed()));
        onView(withId(R.id.jobDurationPreference)).check(matches(isDisplayed()));
        onView(withId(R.id.jobDatePreference)).check(matches(isDisplayed()));
        onView(withId(R.id.jobDatePreference2)).check(matches(isDisplayed()));
        onView(withId(R.id.jobPlacePreference)).check(matches(isDisplayed()));
        onView(withId(R.id.jobUrgencyPreference)).check(matches(isDisplayed()));
        onView(withId(R.id.confirmPreference)).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfTitleIsInvalid() {
        onView(withId(R.id.jobTitlePreference)).perform(typeText("b"));
        closeSoftKeyboard();
        onView(withId(R.id.confirmPreference)).perform(click());
        onView(withId(R.id.preferenceStatusLabel)).check(matches(withText(R.string.INVALID_TITLE)));
    }

    @Test
    public void checkIfTitleIsValid() {
        onView(withId(R.id.jobTitlePreference)).perform(typeText("garden"));
        closeSoftKeyboard();
        onView(withId(R.id.confirmPreference)).perform(click());
        intended(hasComponent(PreferredPostingsActivity.class.getName()));
    }

    @Test
    public void checkIfSalaryIsInvalid() {
        onView(withId(R.id.jobSalaryPreference)).perform(typeText("3"));
        closeSoftKeyboard();
        onView(withId(R.id.confirmPreference)).perform(click());
        onView(withId(R.id.preferenceStatusLabel)).check(matches(withText(R.string.INVALID_SALARY)));
    }

    @Test
    public void checkIfSalaryIsValid() {
        onView(withId(R.id.jobSalaryPreference)).perform(typeText("5"));
        closeSoftKeyboard();
        onView(withId(R.id.confirmPreference)).perform(click());
        intended(hasComponent(PreferredPostingsActivity.class.getName()));
    }

    @Test
    public void checkIfDurationIsInvalid() {
        onView(withId(R.id.jobDurationPreference2)).perform(typeText("0"));
        closeSoftKeyboard();
        onView(withId(R.id.confirmPreference)).perform(click());
        onView(withId(R.id.preferenceStatusLabel)).check(matches(withText(R.string.INVALID_DURATION)));
    }

    @Test
    public void checkIfDurationIsValid() {
        onView(withId(R.id.jobDurationPreference2)).perform(typeText("5"));
        closeSoftKeyboard();
        onView(withId(R.id.confirmPreference)).perform(click());
        intended(hasComponent(PreferredPostingsActivity.class.getName()));
    }

    @Test
    public void checkIfDurationRangeIsInvalid() {
        onView(withId(R.id.jobDurationPreference)).perform(typeText("10"));
        onView(withId(R.id.jobDurationPreference2)).perform(typeText("8"));
        closeSoftKeyboard();
        onView(withId(R.id.confirmPreference)).perform(click());
        onView(withId(R.id.preferenceStatusLabel)).check(matches(withText(R.string.INVALID_DURATION_RANGE)));
    }

    @Test
    public void checkIfDurationRangeIsValid() {
        onView(withId(R.id.jobDurationPreference)).perform(typeText("10"));
        onView(withId(R.id.jobDurationPreference2)).perform(typeText("12"));
        closeSoftKeyboard();
        onView(withId(R.id.confirmPreference)).perform(click());
        intended(hasComponent(PreferredPostingsActivity.class.getName()));
    }

    @Test
    public void checkIfDateIsInvalid() {
        onView(withId(R.id.jobDatePreference2)).perform(typeText("02/25/2023"));
        closeSoftKeyboard();
        onView(withId(R.id.confirmPreference)).perform(click());
        onView(withId(R.id.preferenceStatusLabel)).check(matches(withText(R.string.INVALID_DATE)));
    }

    @Test
    public void checkIfDateIsValid() {
        onView(withId(R.id.jobDatePreference2)).perform(typeText("25/05/2023"));
        closeSoftKeyboard();
        onView(withId(R.id.confirmPreference)).perform(click());
        intended(hasComponent(PreferredPostingsActivity.class.getName()));
    }

    @Test
    public void checkIfDateRangeIsInvalid() {
        onView(withId(R.id.jobDatePreference)).perform(typeText("22/05/2023"));
        onView(withId(R.id.jobDatePreference2)).perform(typeText("19/05/2023"));
        closeSoftKeyboard();
        onView(withId(R.id.confirmPreference)).perform(click());
        onView(withId(R.id.preferenceStatusLabel)).check(matches(withText(R.string.INVALID_DATE_RANGE)));
    }

    @Test
    public void checkIfDateRangeIsValid() {
        onView(withId(R.id.jobDatePreference)).perform(typeText("22/05/2023"));
        onView(withId(R.id.jobDatePreference2)).perform(typeText("26/06/2023"));
        closeSoftKeyboard();
        onView(withId(R.id.confirmPreference)).perform(click());
        intended(hasComponent(PreferredPostingsActivity.class.getName()));
    }

    @Test
    public void checkIfNotPastDate() {
        onView(withId(R.id.jobDatePreference)).perform(typeText("19/01/2022"));
        onView(withId(R.id.jobDatePreference2)).perform(typeText("19/01/2023"));
        closeSoftKeyboard();
        onView(withId(R.id.confirmPreference)).perform(click());
        onView(withId(R.id.preferenceStatusLabel)).check(matches(withText(R.string.PAST_DATE)));
    }

    @Test
    public void checkIfPlaceIsInvalid() {
        onView(withId(R.id.jobPlacePreference)).perform(typeText("Halifax B3J2N3"));
        closeSoftKeyboard();
        onView(withId(R.id.confirmPreference)).perform(click());
        onView(withId(R.id.preferenceStatusLabel)).check(matches(withText(R.string.INVALID_PLACE)));
    }

    @Test
    public void checkIfPlaceIsValidCityPostal() {
        onView(withId(R.id.jobPlacePreference)).perform(typeText("Halifax, B3J2N3"));
        closeSoftKeyboard();
        onView(withId(R.id.confirmPreference)).perform(click());
        intended(hasComponent(PreferredPostingsActivity.class.getName()));
    }

    @Test
    public void checkIfPlaceIsValidCity() {
        onView(withId(R.id.jobPlacePreference)).perform(typeText("Halifax"));
        closeSoftKeyboard();
        onView(withId(R.id.confirmPreference)).perform(click());
        intended(hasComponent(PreferredPostingsActivity.class.getName()));
    }

    @After
    public void tearDown() {
        Intents.release();
    }
}