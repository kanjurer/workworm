package com.example.csci3130_g2_quick_cash.utils;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Post test cases.
 * @author Kanav Bhardwaj
 */

public class PostValidationTest {
    final static String emptyString = "";
    final static String title = "Teaching Assistant";
    final static String badTitle = "a";
    final static String description = "Job requires you to teach";
    final static String badDescription = "<4";
    final static String badDescription1 = "   1111    ";
    final static String badDescription2 = "     ";
    final static String salary = "45000";
    final static String badSalary1 = "1";
    final static String badSalary2 = "text";
    final static String place = "Halifax, B1A4W3";
    final static String badPlace1 = "wrong formatting";
    final static String badPlace2 = "a,a";
    final static String duration = "130";
    final static String badDuration1 = "  ";
    final static String badDuration2 = "0";
    final static String badDuration3 = "    ";
    final static String date = "16/10/2029";
    final static String oldDate = "01/01/1999";
    final static String badDate1 = "01-01-1999";
    final static String badDate2 = " 16/10/2029 ";

    @Test
    public void isValidDate() {
        // empty string
        assertFalse(PostValidation.isValidDate(emptyString));
        // good
        assertTrue(PostValidation.isValidDate(date));
        assertTrue(PostValidation.isValidDate(oldDate));
        // bad
        assertFalse(PostValidation.isValidDate(badDate1));
        assertFalse(PostValidation.isValidDate(badDate2));
    }

    @Test
    public void isNotPastDate() {
        // empty string
        assertFalse(PostValidation.isValidDate(emptyString));

        // good
        assertTrue(PostValidation.isValidDate(date));

        // bad
        assertFalse(PostValidation.isNotPastDate(oldDate));
        assertFalse(PostValidation.isNotPastDate(badDate1));

    }

    @Test
    public void isValidDuration() {
        // empty string
        assertFalse(PostValidation.isValidDuration(emptyString));

        // good
        assertTrue(PostValidation.isValidDuration(duration));

        // bad
        assertFalse(PostValidation.isValidDuration(badDuration1));
        assertFalse(PostValidation.isValidDuration(badDuration2));
        assertFalse(PostValidation.isValidDuration(badDuration3));
    }

    @Test
    public void isValidDescription() {
        // empty string
        assertFalse(PostValidation.isValidDescription(emptyString));

        // good
        assertTrue(PostValidation.isValidDescription(description));

        // bad
        assertFalse(PostValidation.isValidDescription(badDescription));
        assertFalse(PostValidation.isValidDescription(badDescription1));
        assertFalse(PostValidation.isValidDescription(badDescription2));

    }

    @Test
    public void isValidTitle() {
        // empty string
        assertFalse(PostValidation.isValidSalary(emptyString));

        // good
        assertTrue(PostValidation.isValidTitle(title));

        // bad
        assertFalse(PostValidation.isValidDate(badTitle));
    }

    @Test
    public void isValidPlace() {
        // empty string
        assertFalse(PostValidation.isValidSalary(emptyString));

        // good
        assertTrue(PostValidation.isValidPlace(place));

        // bad
        assertFalse(PostValidation.isValidPlace(badPlace1));
        assertFalse(PostValidation.isValidPlace(badPlace2));
    }

    @Test
    public void isValidSalary() {
        // empty string
        assertFalse(PostValidation.isValidSalary(emptyString));

        // good
        assertTrue(PostValidation.isValidSalary(salary));

        // bad
        assertFalse(PostValidation.isValidSalary(badSalary1));
        assertFalse(PostValidation.isValidSalary(badSalary2));
    }
}