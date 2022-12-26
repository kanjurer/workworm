package com.example.csci3130_g2_quick_cash.activities;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.csci3130_g2_quick_cash.R;
import com.example.csci3130_g2_quick_cash.activities.PayPalActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * This class contains Espresso testing for Payment validation to be redirected to PayPal.
 *
 * @author Adam Sarty
 */

public class PayPalActivityTest {

    @Rule
    public ActivityScenarioRule<PayPalActivity> activityTestRule = new ActivityScenarioRule<>(PayPalActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @Test
    public void checkUIElementsExist() {
        onView(ViewMatchers.withId(R.id.edtHourlyAmount)).check(matches(isDisplayed()));
        onView(withId(R.id.edtHoursAmount)).check(matches(isDisplayed()));
        onView(withId(R.id.emailOfEmployee)).check(matches(isDisplayed()));
        onView(withId(R.id.btnPayNow)).check(matches(isDisplayed()));
        onView(withId(R.id.idTVStatus)).check(matches(isDisplayed()));
        onView(withId(R.id.ratingBar)).check(matches(isDisplayed()));
    }

    @Test
    public void checkHourlyIsValid() {
        onView(withId(R.id.edtHourlyAmount)).perform(typeText("3"));
        closeSoftKeyboard();
        onView(withId(R.id.btnPayNow)).perform(click());
        onView(withId(R.id.paymentStatus)).check(matches(withText(R.string.INVALID_HOURS)));
    }

    @Test
    public void checkHourlyIsInvalid() {
        onView(withId(R.id.edtHoursAmount)).perform(typeText("10"));
        onView(withId(R.id.edtHourlyAmount)).perform(typeText("0"));
        closeSoftKeyboard();
        onView(withId(R.id.btnPayNow)).perform(click());
        onView(withId(R.id.paymentStatus)).check(matches(withText(R.string.INVALID_HOURLY)));
    }

    @Test
    public void checkHoursIsValid() {
        onView(withId(R.id.edtHoursAmount)).perform(typeText("3"));
        closeSoftKeyboard();
        onView(withId(R.id.btnPayNow)).perform(click());
        onView(withId(R.id.paymentStatus)).check(matches(withText(R.string.INVALID_HOURLY)));
    }

    @Test
    public void checkHoursIsInvalid() {
        onView(withId(R.id.edtHoursAmount)).perform(typeText("0"));
        closeSoftKeyboard();
        onView(withId(R.id.btnPayNow)).perform(click());
        onView(withId(R.id.paymentStatus)).check(matches(withText(R.string.INVALID_HOURS)));
    }

    @After
    public void tearDown() {
        Intents.release();
    }
}
