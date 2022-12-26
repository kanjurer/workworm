package com.example.csci3130_g2_quick_cash.activities;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
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

public class PredefinedJobsActivityTest {
    @Rule
    public ActivityScenarioRule<PredefinedJobsActivity> activityTestRule = new ActivityScenarioRule<>(PredefinedJobsActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @Test
    public void checkUIElementsExist() {
        onView(withId(R.id.predefinedText)).check(matches(isDisplayed()));
        onView(withId(R.id.ScrollView)).check(matches(isDisplayed()));
        onView(withId(R.id.WalkADog)).check(matches(isDisplayed()));
        onView(withId(R.id.MowALawn)).check(matches(isDisplayed()));
        onView(withId(R.id.FixAComputer)).check(matches(isDisplayed()));
        onView(withId(R.id.PickUpGroceries)).check(matches(isDisplayed()));
        onView(withId(R.id.AirportPickup)).check(matches(isDisplayed()));
        onView(withId(R.id.DropToAirport)).check(matches(isDisplayed()));
        onView(withId(R.id.BabySitting)).check(matches(isDisplayed()));
    }

    @Test
    public void BackButtonsWork(){
        onView(withId(R.id.backButton)).perform(click());
        intended(hasComponent(MyPostingsActivity.class.getName()));
    }


    @Test
    public void WalkADogButtonsWork(){
        onView(withId(R.id.WalkADog)).perform(click());
        onView(withId(R.id.jobdetails)).check(matches(isDisplayed()));
        onView(withId(R.id.jobTitle)).check(matches(withText("Walk my Dog")));
    }

    @Test
    public void FixAComputerButtonsWork(){
        onView(withId(R.id.FixAComputer)).perform(click());
        onView(withId(R.id.jobdetails)).check(matches(isDisplayed()));
        onView(withId(R.id.jobTitle)).check(matches(withText("Fix my Computer")));
    }

    @Test
    public void MowALawnButtonsWork(){
        onView(withId(R.id.MowALawn)).perform(click());
        onView(withId(R.id.jobdetails)).check(matches(isDisplayed()));
        onView(withId(R.id.jobTitle)).check(matches(withText("Mow my Lawn")));
    }

    @Test
    public void PickUpGroceriesButtonsWork(){
        onView(withId(R.id.PickUpGroceries)).perform(click());
        onView(withId(R.id.jobdetails)).check(matches(isDisplayed()));
        onView(withId(R.id.jobTitle)).check(matches(withText("Pickup my Groceries")));
    }

    @Test
    public void BabySittingButtonsWork(){
        onView(withId(R.id.BabySitting)).perform(click());
        onView(withId(R.id.jobdetails)).check(matches(isDisplayed()));
        onView(withId(R.id.jobTitle)).check(matches(withText("Babysitting")));
    }

    @Test
    public void DropToAirportButtonsWork(){
        onView(withId(R.id.DropToAirport)).perform(click());
        onView(withId(R.id.jobdetails)).check(matches(isDisplayed()));
        onView(withId(R.id.jobTitle)).check(matches(withText("Dropoff to Airport")));
    }

    @Test
    public void AirportPickupButtonsWork(){
        onView(withId(R.id.AirportPickup)).perform(click());
        onView(withId(R.id.jobdetails)).check(matches(isDisplayed()));
        onView(withId(R.id.jobTitle)).check(matches(withText("Pickup from Airport")));
    }

    @Test
    public void StartFromScratchButtonsWork(){
        onView(withId(R.id.StartFromScratch)).perform(click());
        onView(withId(R.id.jobdetails)).check(matches(isDisplayed()));
        onView(withId(R.id.jobTitle)).check(matches(withText("")));
    }

    @After
    public void tearDown() {
        Intents.release();
    }





}
