package com.example.csci3130_g2_quick_cash.utils;

import com.example.csci3130_g2_quick_cash.models.CreditCard;
import com.example.csci3130_g2_quick_cash.models.User;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * User test cases.
 * @author Adam Sarty, Kanav Bhardwaj
 */
public class UserValidationTest {
    private final String fullName = "Adam Sarty";
    private final String email = "abc.123@gmail.com";
    private final String emptyString = "";
    private final String contact = "9023329920";
    private final String password = "i am the best";
    private CreditCard creditCard;
    static User user;

    @Before
    public void setUp() throws Exception {
        String cardNum = "4444000000001111";
        String expiry = "1223";
        String security = "699";
        creditCard = new CreditCard(cardNum, expiry, security);
        user = new User(fullName, email, password, contact, creditCard);
    }

    @After
    public void tearDown() throws Exception {
        System.gc();
    }

    @Test
    public void testIsValidEmailAddress() {
        // empty string
        assertFalse(UserValidation.isValidEmailAddress(emptyString));

        // good
        assertTrue(UserValidation.isValidEmailAddress(email));

        // bad
        String badEmail1 = "abc";
        assertFalse(UserValidation.isValidEmailAddress(badEmail1));
        String badEmail2 = "abc@gmail";
        assertFalse(UserValidation.isValidEmailAddress(badEmail2));
    }

    @Test
    public void testIsValidPassword() {
        // empty string
        assertFalse(UserValidation.isValidPassword(emptyString));

        // good
        assertTrue(UserValidation.isValidPassword(password));

        // bad
        String badPassword1 = "1234";
        assertFalse(UserValidation.isValidPassword(badPassword1));
        String badPassword2 = "0";
        assertFalse(UserValidation.isValidPassword(badPassword2));

    }

    @Test
    public void testIsValidFullName() {
        // empty string
        assertFalse(UserValidation.isValidFullName(emptyString));

        // good
        assertTrue(UserValidation.isValidFullName(fullName));
        String fullName1 = "f u";
        assertTrue(UserValidation.isValidFullName(fullName1));
        String fullName2 = "Jean Marie Phillipe";
        assertTrue(UserValidation.isValidFullName(fullName2));

        // bad
        String badFullName1 = "f";
        assertFalse(UserValidation.isValidFullName(badFullName1));
        assertFalse(UserValidation.isValidFullName("      "));


    }

    @Test
    public void testIsValidCard() {
        CreditCard badCreditCard = new CreditCard("1", "", "");
        CreditCard emptyCreditCard = new CreditCard("", "", "");


        // empty
        assertTrue(UserValidation.isValidCard(emptyCreditCard));

        // good
        assertTrue(UserValidation.isValidCard(creditCard));

        // bad
        assertFalse(UserValidation.isValidCard(badCreditCard));
    }

    @Test
    public void testIsValidContact() {
        // empty
        assertFalse(UserValidation.isValidFullName(emptyString));

        // good
        assertTrue(UserValidation.isValidContact(contact));
        String contact1 = "19023329920";
        assertTrue(UserValidation.isValidContact(contact1));
        String contact2 = "+86 800 555 1234";
        assertTrue(UserValidation.isValidContact(contact2));
        String contact3 = "+1 (902) 217 7888";
        assertTrue(UserValidation.isValidContact(contact3));

        // bad
        String badContact1 = "22222222222222222222";
        assertFalse(UserValidation.isValidContact(badContact1));
        String badContact2 = "22222222222222222221111111111112";
        assertFalse(UserValidation.isValidContact(badContact2));
        String badContact3 = "2";
        assertFalse(UserValidation.isValidContact(badContact3));

    }

    @Test
    public void testIsValidUser() {
        User badUser = new User("", "", "", "", null);

        // empty
        assertFalse(UserValidation.isValidUser(null));

        // good
        assertTrue(UserValidation.isValidUser(user));

        // bad
        assertFalse(UserValidation.isValidUser(badUser));

    }
}