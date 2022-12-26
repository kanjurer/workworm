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
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.csci3130_g2_quick_cash.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @Test
    public void checkUIElementsExist() {
        onView(ViewMatchers.withId(R.id.email)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).check(matches(isDisplayed()));
        onView(withId(R.id.login)).check(matches(isDisplayed()));
        onView(withId(R.id.newUser)).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfEmailIsInvalid() {
        onView(withId(R.id.email)).perform(typeText("dev@"));
        closeSoftKeyboard();
        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.mainStatusLabel)).check(matches(withText(R.string.INVALID_EMAIL)));
    }

    @Test
    public void checkIfPasswordIsInvalid() {
        onView(withId(R.id.email)).perform(typeText("dv817395@dal.ca"));
        onView(withId(R.id.password)).perform(typeText(""));
        closeSoftKeyboard();
        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.mainStatusLabel)).check(matches(withText(R.string.INVALID_PASSWORD)));
    }

//    @Test
//    public void checkIfCredentialsAreInvalid() {
//        onView(withId(R.id.email)).perform(typeText("kajas@dal.ca"));
//        closeSoftKeyboard();
//        onView(withId(R.id.password)).perform(typeText("dsdsdsdsdsds"));
//        closeSoftKeyboard();
//        onView(withId(R.id.login)).perform(click());
//        onView(withId(R.id.mainStatusLabel)).check(matches(withText(R.string.INVALID_CREDENTIALS)));
//    }
//
//    @Test
//    public void checkIfMovedToWelcomeActivity() {
//        closeSoftKeyboard();
//        onView(withId(R.id.email)).perform(typeText("johndoe@gmail.com"));
//        closeSoftKeyboard();
//        onView(withId(R.id.password)).perform(typeText("johndoeisfunny"));
//        closeSoftKeyboard();
//        onView(withId(R.id.login)).perform(click());
//        closeSoftKeyboard();
//        intended(hasComponent(WelcomeActivity.class.getName()));
//    }

    @After
    public void tearDown() {
        Intents.release();
    }
}