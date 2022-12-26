package com.example.csci3130_g2_quick_cash.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.csci3130_g2_quick_cash.models.Post;
import com.example.csci3130_g2_quick_cash.models.Preference;
import com.example.csci3130_g2_quick_cash.models.Salary;
import com.example.csci3130_g2_quick_cash.models.TimeRange;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Preferred Post validation test cases.
 * @author Arman Singh Sidhu
 */
public class PreferredPostValidationTest {
    private static Preference userPreference1;
    private static Preference userPreference2;
    private static Post jobPost;
    private static TimeRange timeRange = new TimeRange("15/05/2023", "23/05/2023", "5", "7");
    private static Salary salary = new Salary("14","20");
    private static String place = "Halifax, B3H0B1";
    private static String date = "16/05/2023";
    private static String description = "Fix my Windows PC";

    @BeforeClass
    public static void setup() {
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void checkIfPreferredTest() {
        userPreference1 = new Preference("PC fix", timeRange, salary, place, false);
        userPreference2 = new Preference("Garden fix", timeRange, salary, place, false);
        jobPost = new Post(null, "PC fix", description, false, date, "17", "6", place);

        // preferred
        assertTrue(PreferredPostValidation.checkIfPreferred(userPreference1, jobPost));

        // not preferred
        assertFalse(PreferredPostValidation.checkIfPreferred(userPreference2, jobPost));
    }

    @Test
    public void isTitlePreferredTest() {
        //empty
        assertTrue(PreferredPostValidation.isTitlePreferred("", "PC fix"));

        // preferred
        assertTrue(PreferredPostValidation.isTitlePreferred("PC fix", "PC fix"));

        // not preferred
        assertFalse(PreferredPostValidation.isTitlePreferred("Garden fix", "PC fix"));
    }

    @Test
    public void isPlacePreferredTest() {
        //empty
        assertTrue(PreferredPostValidation.isPlacePreferred("", place));

        // preferred
        assertTrue(PreferredPostValidation.isPlacePreferred("Halifax, B3H0B1", place));

        // not preferred
        assertFalse(PreferredPostValidation.isPlacePreferred("Halifax, B3H0C2", place));
    }

    @Test
    public void isValidRangeTest() {
        //empty
        assertTrue(PreferredPostValidation.isValidRange("","", "6"));

        // preferred
        assertTrue(PreferredPostValidation.isValidRange("5", "7", "6"));

        // not preferred
        assertFalse(PreferredPostValidation.isValidRange("5", "7", "9"));
    }

    @Test
    public void isPreferredDateTest() {
        //empty
        assertTrue(PreferredPostValidation.isPreferredDate("","", date));

        // preferred
        assertTrue(PreferredPostValidation.isPreferredDate("15/05/2023", "17/05/2023", date));

        // not preferred
        assertFalse(PreferredPostValidation.isPreferredDate("13/05/2023", "15/05/2023", date));
    }
}
