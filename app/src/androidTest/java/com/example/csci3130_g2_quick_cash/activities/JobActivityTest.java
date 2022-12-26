package com.example.csci3130_g2_quick_cash.activities;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;

import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.csci3130_g2_quick_cash.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


/**
 * Job Post validation Espresso test cases.
 *
 * @author Arman Singh Sidhu
 */
public class JobActivityTest {
    @Rule
    public ActivityScenarioRule<JobActivity> activityTestRule = new ActivityScenarioRule<>(JobActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @Test
    public void checkUIElementsExist() {
        onView(withId(R.id.jobdetails)).check(matches(isDisplayed()));
        onView(withId(R.id.jobTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.description)).check(matches(isDisplayed()));
        onView(withId(R.id.date)).check(matches(isDisplayed()));
        onView(withId(R.id.duration)).check(matches(isDisplayed()));
        onView(withId(R.id.place)).check(matches(isDisplayed()));
        onView(withId(R.id.salary)).check(matches(isDisplayed()));
        onView(withId(R.id.urgencyCheckBox)).check(matches(isDisplayed()));
        onView(withId(R.id.submitButton)).check(matches(isDisplayed()));
        onView(withId(R.id.bacToWelcome)).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfTitleIsInvalid() {
        onView(withId(R.id.jobTitle)).perform(typeText("a"));
        closeSoftKeyboard();
        onView(withId(R.id.submitButton)).perform(click());
        onView(withId(R.id.jobStatusLabel)).check(matches(withText(R.string.INVALID_TITLE)));
    }

    @Test
    public void checkIfTitleIsValid() {
        onView(withId(R.id.jobTitle)).perform(typeText("garden"));
        onView(withId(R.id.description)).perform(typeText("garden repair"));
        onView(withId(R.id.date)).perform(typeText("22/11/2023"));
        onView(withId(R.id.duration)).perform(typeText("3"));
        onView(withId(R.id.place)).perform(typeText("Halifax, B3H0A6"));
        closeSoftKeyboard();
        onView(withId(R.id.salary)).perform(typeText("25"));
        closeSoftKeyboard();
        onView(withId(R.id.submitButton)).perform(click());
        intended(hasComponent(MyPostingsActivity.class.getName()));
    }

    @Test
    public void checkIfDescriptionIsInvalid() {
        closeSoftKeyboard();
        onView(withId(R.id.jobTitle)).perform(typeText("computer"));
        onView(withId(R.id.description)).perform(typeText("a"));
        closeSoftKeyboard();
        onView(withId(R.id.submitButton)).perform(click());
        onView(withId(R.id.jobStatusLabel)).check(matches(withText(R.string.INVALID_DESCRIPTION)));
    }

    @Test
    public void checkIfDescriptionIsValid() {
        onView(withId(R.id.jobTitle)).perform(typeText("computer"));
        onView(withId(R.id.description)).perform(typeText("computer repair"));
        onView(withId(R.id.date)).perform(typeText("22/11/2023"));
        onView(withId(R.id.duration)).perform(typeText("3"));
        onView(withId(R.id.place)).perform(typeText("Halifax, B3H0A6"));
        closeSoftKeyboard();
        onView(withId(R.id.salary)).perform(typeText("25"));
        closeSoftKeyboard();
        onView(withId(R.id.submitButton)).perform(click());
        intended(hasComponent(MyPostingsActivity.class.getName()));
    }

    @Test
    public void checkIfDateIsInvalid() {
        closeSoftKeyboard();
        onView(withId(R.id.jobTitle)).perform(typeText("computer"));
        onView(withId(R.id.description)).perform(typeText("comp repair"));
        onView(withId(R.id.date)).perform(typeText("09/22/2022"));
        closeSoftKeyboard();
        onView(withId(R.id.submitButton)).perform(click());
        onView(withId(R.id.jobStatusLabel)).check(matches(withText(R.string.INVALID_DATE)));
    }

    @Test
    public void checkIfDateIsPast() {
        onView(withId(R.id.jobTitle)).perform(typeText("computer"));
        onView(withId(R.id.description)).perform(typeText("comp repair"));
        onView(withId(R.id.date)).perform(typeText("19/09/2022"));
        closeSoftKeyboard();
        onView(withId(R.id.submitButton)).perform(click());
        onView(withId(R.id.jobStatusLabel)).check(matches(withText(R.string.PAST_DATE)));
    }

    @Test
    public void checkIfDateIsValid() {
        onView(withId(R.id.jobTitle)).perform(typeText("computer"));
        onView(withId(R.id.description)).perform(typeText("computer repair"));
        onView(withId(R.id.date)).perform(typeText("22/11/2023"));
        onView(withId(R.id.duration)).perform(typeText("3"));
        onView(withId(R.id.place)).perform(typeText("Halifax, B3H0A6"));
        closeSoftKeyboard();
        onView(withId(R.id.salary)).perform(typeText("25"));
        closeSoftKeyboard();
        onView(withId(R.id.submitButton)).perform(click());
        intended(hasComponent(MyPostingsActivity.class.getName()));
    }

    @Test
    public void checkIfDurationIsInvalid() {
        onView(withId(R.id.jobTitle)).perform(typeText("computer"));
        onView(withId(R.id.description)).perform(typeText("comp repair"));
        onView(withId(R.id.date)).perform(typeText("19/01/2023"));
        onView(withId(R.id.duration)).perform(typeText("0"));
        closeSoftKeyboard();
        onView(withId(R.id.submitButton)).perform(click());
        onView(withId(R.id.jobStatusLabel)).check(matches(withText(R.string.INVALID_DURATION)));
    }

    @Test
    public void checkIfDurationIsValid() {
        onView(withId(R.id.jobTitle)).perform(typeText("computer"));
        onView(withId(R.id.description)).perform(typeText("computer repair"));
        onView(withId(R.id.date)).perform(typeText("22/11/2023"));
        onView(withId(R.id.duration)).perform(typeText("3"));
        onView(withId(R.id.place)).perform(typeText("Halifax, B3H0A6"));
        closeSoftKeyboard();
        onView(withId(R.id.salary)).perform(typeText("25"));
        closeSoftKeyboard();
        onView(withId(R.id.submitButton)).perform(click());
        intended(hasComponent(MyPostingsActivity.class.getName()));
    }

    @Test
    public void checkIfPlaceIsInvalid() {
        onView(withId(R.id.jobTitle)).perform(typeText("computer"));
        onView(withId(R.id.description)).perform(typeText("comp repair"));
        onView(withId(R.id.date)).perform(typeText("19/01/2023"));
        onView(withId(R.id.duration)).perform(typeText("2"));
        onView(withId(R.id.place)).perform(typeText("ABC, X"));
        closeSoftKeyboard();
        onView(withId(R.id.submitButton)).perform(click());
        onView(withId(R.id.jobStatusLabel)).check(matches(withText(R.string.INVALID_PLACE)));
    }

    @Test
    public void checkIfPlaceIsValid() {
        onView(withId(R.id.jobTitle)).perform(typeText("computer"));
        onView(withId(R.id.description)).perform(typeText("computer repair"));
        onView(withId(R.id.date)).perform(typeText("22/11/2023"));
        onView(withId(R.id.duration)).perform(typeText("3"));
        onView(withId(R.id.place)).perform(typeText("Halifax, B3H0A6"));
        closeSoftKeyboard();
        onView(withId(R.id.salary)).perform(typeText("25"));
        closeSoftKeyboard();
        onView(withId(R.id.submitButton)).perform(click());
        intended(hasComponent(MyPostingsActivity.class.getName()));
    }

    @Test
    public void checkIfSalaryIsInvalid() {
        onView(withId(R.id.jobTitle)).perform(typeText("computer"));
        onView(withId(R.id.description)).perform(typeText("comp repair"));
        onView(withId(R.id.date)).perform(typeText("19/01/2023"));
        onView(withId(R.id.duration)).perform(typeText("2"));
        onView(withId(R.id.place)).perform(typeText("Halifax, B3H0A6"));
        closeSoftKeyboard();
        onView(withId(R.id.salary)).perform(typeText("0"));
        closeSoftKeyboard();
        onView(withId(R.id.submitButton)).perform(click());
        onView(withId(R.id.jobStatusLabel)).check(matches(withText(R.string.INVALID_SALARY)));
    }

    @Test
    public void checkIfSalaryIsValid() {
        onView(withId(R.id.jobTitle)).perform(typeText("computer"));
        onView(withId(R.id.description)).perform(typeText("computer repair"));
        onView(withId(R.id.date)).perform(typeText("22/11/2023"));
        onView(withId(R.id.duration)).perform(typeText("3"));
        onView(withId(R.id.place)).perform(typeText("Halifax, B3H0A6"));
        closeSoftKeyboard();
        onView(withId(R.id.salary)).perform(typeText("25"));
        closeSoftKeyboard();
        onView(withId(R.id.submitButton)).perform(click());
        intended(hasComponent(MyPostingsActivity.class.getName()));
    }

    @Test
    public void checkBackButton() {
        closeSoftKeyboard();
        onView(withId(R.id.bacToWelcome)).perform(click());
        intended(hasComponent(PredefinedJobsActivity.class.getName()));
    }

    @Test
    public void checkIfFieldsAreEmpty() {
        closeSoftKeyboard();
        onView(withId(R.id.submitButton)).perform(click());
        onView(withId(R.id.jobStatusLabel)).check(matches(withText(R.string.INVALID_TITLE)));
    }

    @After
    public void tearDown() {
        Intents.release();
    }

}
