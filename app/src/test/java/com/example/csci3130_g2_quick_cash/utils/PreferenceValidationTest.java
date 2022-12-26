package com.example.csci3130_g2_quick_cash.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Preference test cases.
 * @author Gavin Jayeshkum Jariwala, Arman Singh Sidhu
 */
public class PreferenceValidationTest {
    String date = "";
    String dateValid = "11/11/2023";
    String dateInvalid = "10";
    String datePast = "10/10/2022";

    @Before
    public void setUp() throws Exception{

        String login = "dv817395@dal.ca";
        String password = "dev1234";
    }

    @After
    public void tearDown() throws Exception{
        System.gc();
    }

    @Test
    public void testIsValidTitle(){
        //empty title
        assertTrue(PreferenceValidation.isValidTitle(""));

        //Good
        assertTrue(PreferenceValidation.isValidTitle("Computer repair"));

        //bad
        assertFalse(PreferenceValidation.isValidTitle("tx"));
    }

    @Test
    public void testIsValidDate(){
        //empty date
        assertTrue(PreferenceValidation.isValidDate(date));

        //Good
        assertTrue(PreferenceValidation.isValidDate(dateValid));

        //bad
        assertFalse(PreferenceValidation.isValidDate(dateInvalid));
    }


    @Test
    public void testIsNotPastDate(){
        assertFalse(PreferenceValidation.isNotPastDate(datePast));
    }

    @Test
    public void testIsValidDuration(){
        //empty
        assertTrue(PreferenceValidation.isValidDuration(""));

        //good
        assertTrue(PreferenceValidation.isValidDuration("5"));

        //bad
        assertFalse(PreferenceValidation.isValidDuration("-2"));
    }

    @Test
    public void testIsValidRange(){
        //empty
        assertTrue(PreferenceValidation.isValidRange("", ""));

        //good
        assertTrue(PreferenceValidation.isValidRange("3", "5"));
        assertTrue(PreferenceValidation.isValidRange("3", ""));
        assertTrue(PreferenceValidation.isValidRange("", "5"));

        //bad
        assertFalse(PreferenceValidation.isValidRange("4", "2"));
    }

    @Test
    public void testIsValidDateRange(){
        //empty
        assertTrue(PreferenceValidation.isValidDateRange("", ""));

        //good
        assertTrue(PreferenceValidation.isValidDateRange("15/05/2023", "20/05/2023"));
        assertTrue(PreferenceValidation.isValidDateRange("15/05/2023", ""));
        assertTrue(PreferenceValidation.isValidDateRange("", "15/05/2023"));

        //bad
        assertFalse(PreferenceValidation.isValidDateRange("15/05/2023", "11/05/2023"));
    }

    @Test
    public void testIsValidPlace(){
        //empty
        assertTrue(PreferenceValidation.isValidPlace(""));

        //good
        assertTrue(PreferenceValidation.isValidPlace("Halifax, B3J2N5"));

        //bad
        assertFalse(PreferenceValidation.isValidPlace("H,"));
    }

    @Test
    public void testIsValidSalary(){
        //empty
        assertTrue(PreferenceValidation.isValidSalary(""));

        //good
        assertTrue(PreferenceValidation.isValidSalary("6"));

        //bad
        assertFalse(PreferenceValidation.isValidSalary("4"));
    }



}
