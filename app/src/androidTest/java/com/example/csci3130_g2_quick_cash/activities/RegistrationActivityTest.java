package com.example.csci3130_g2_quick_cash.activities;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
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


public class RegistrationActivityTest {
    @Rule
    public ActivityScenarioRule<RegistrationActivity> activityTestRule = new ActivityScenarioRule<>(RegistrationActivity.class);


    @Before
    public void setUp() {
        Intents.init();
    }

    @Test
    public void checkUIElementsExist() {
        onView(withId(R.id.regFullName)).check(matches(isDisplayed()));
        onView(withId(R.id.regEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.regPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.regPhoneNumber)).check(matches(isDisplayed()));
        onView(withId(R.id.regCardNumber)).check(matches(isDisplayed()));
        onView(withId(R.id.regSecurity)).check(matches(isDisplayed()));
        onView(withId(R.id.regExpiry)).check(matches(isDisplayed()));
        onView(withId(R.id.registerStatusLabel)).check(matches(isDisplayed()));

    }

    @Test
    public void checkIfFullNameIsInvalid() {
        onView(withId(R.id.regFullName)).perform(typeText(""));
        closeSoftKeyboard();
        onView(withId(R.id.register)).perform(click());
        onView(withId(R.id.registerStatusLabel)).check(matches(withText(R.string.INVALID_FULL_NAME)));

        onView(withId(R.id.regFullName)).perform(clearText());
        onView(withId(R.id.regFullName)).perform(typeText("d"));
        closeSoftKeyboard();
        onView(withId(R.id.register)).perform(click());
        onView(withId(R.id.registerStatusLabel)).check(matches(withText(R.string.INVALID_FULL_NAME)));

        onView(withId(R.id.regFullName)).perform(clearText());
        onView(withId(R.id.regFullName)).perform(typeText("    "));
        closeSoftKeyboard();
        onView(withId(R.id.register)).perform(click());
        onView(withId(R.id.registerStatusLabel)).check(matches(withText(R.string.INVALID_FULL_NAME)));
    }

    @Test
    public void checkIfEmailIsInvalid() {
        onView(withId(R.id.regFullName)).perform(typeText("Joe Doe"));
        onView(withId(R.id.regEmail)).perform(typeText("dev@"));
        closeSoftKeyboard();
        onView(withId(R.id.register)).perform(click());
        onView(withId(R.id.registerStatusLabel)).check(matches(withText(R.string.INVALID_EMAIL)));
    }

    @Test
    public void checkIfPasswordIsInvalid() {
        onView(withId(R.id.regFullName)).perform(typeText("Joe Doe"));
        onView(withId(R.id.regEmail)).perform(typeText("test@gmail.com"));
        onView(withId(R.id.regPassword)).perform(typeText("dev"));
        closeSoftKeyboard();
        onView(withId(R.id.register)).perform(click());
        onView(withId(R.id.registerStatusLabel)).check(matches(withText(R.string.INVALID_PASSWORD)));
    }

    @Test
    public void checkIfContactIsInvalid() {
        onView(withId(R.id.regFullName)).perform(typeText("Joe Doe"));
        onView(withId(R.id.regEmail)).perform(typeText("test@gmail.com"));
        onView(withId(R.id.regPassword)).perform(typeText("123456"));
        onView(withId(R.id.regPhoneNumber)).perform(typeText("123"));
        closeSoftKeyboard();
        onView(withId(R.id.register)).perform(click());
        onView(withId(R.id.registerStatusLabel)).check(matches(withText(R.string.INVALID_CONTACT)));
    }

    @After
    public void tearDown() {
        Intents.release();
    }
}