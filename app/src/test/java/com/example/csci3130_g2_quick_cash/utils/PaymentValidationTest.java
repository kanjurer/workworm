package com.example.csci3130_g2_quick_cash.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PaymentValidationTest {

    final static String badHour = "-1";
    final static String badHour2 = "-8492.49503";
    final static String goodHour = "1";
    final static String goodHour2 = "18492.49492";
    final static String empty = null;
    final static String zero = "0";

    @Test
    public void isHourlyValid() {
        assertFalse(PaymentValidation.isHourlyValid(badHour));
        assertFalse(PaymentValidation.isHourlyValid(badHour2));
        assertFalse(PaymentValidation.isHourlyValid(empty));
        assertFalse(PaymentValidation.isHourlyValid(zero));
        assertTrue(PaymentValidation.isHourlyValid(goodHour));
        assertTrue(PaymentValidation.isHourlyValid(goodHour2));
    }

    @Test
    public void isHoursValid() {
        assertFalse(PaymentValidation.isHoursValid(badHour));
        assertFalse(PaymentValidation.isHoursValid(badHour2));
        assertFalse(PaymentValidation.isHoursValid(empty));
        assertFalse(PaymentValidation.isHoursValid(zero));
        assertTrue(PaymentValidation.isHoursValid(goodHour));
        assertTrue(PaymentValidation.isHoursValid(goodHour2));
    }

    @Test
    public void isRatingValid() {
        assertFalse(PaymentValidation.isRatingValid(Integer.valueOf(zero)));
        assertTrue(PaymentValidation.isRatingValid(1));
        assertTrue(PaymentValidation.isRatingValid(2));
        assertTrue(PaymentValidation.isRatingValid(3));
        assertTrue(PaymentValidation.isRatingValid(4));
        assertTrue(PaymentValidation.isRatingValid(5));
        assertTrue(PaymentValidation.isRatingValid((int) 1.5));
        assertTrue(PaymentValidation.isRatingValid((int) 2.5));
        assertTrue(PaymentValidation.isRatingValid((int) 3.5));
        assertTrue(PaymentValidation.isRatingValid((int) 4.5));
    }

}