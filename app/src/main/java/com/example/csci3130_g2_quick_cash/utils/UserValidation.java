package com.example.csci3130_g2_quick_cash.utils;

import com.example.csci3130_g2_quick_cash.models.CreditCard;
import com.example.csci3130_g2_quick_cash.models.User;

import java.util.regex.Pattern;

/**
 * This class determines user validation upon login/registration.
 * JUnit test cases.
 * @author Adam Sarty, Kanav Bhardwaj
 */

public class UserValidation {

    /**
     * Lint debug.
     */
    private UserValidation() {}

    /**
     * This method checks if the given email is valid or not
     * @param emailAddress the email address to be checked
     * @return TRUE : If @emailAddress is a valid email
     *         FALSE otherwise
     */
    public static boolean isValidEmailAddress(String emailAddress) {
        if (emailAddress == null) {
            return false;
        }

        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);

        return pattern.matcher(emailAddress).matches();
    }

    /**
     * This method checks if the given password is valid or not
     * @param password the email address to be checked
     * @return TRUE : If @password is a valid email
     *         FALSE otherwise
     */
    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        return password.length() > 5;
    }

    /**
     * This method checks if the given full name is valid or not
     * @param fullName the name to be checked
     * @return TRUE : If @fullName is a valid Name
     *         FALSE otherwise
     */
    public static boolean isValidFullName(String fullName) {
        if (fullName == null) {
            return false;
        }
        return fullName.split(" ").length >= 2;
    }

    /**
     * This method checks if the given credit card is valid or not
     * @param card to be checked
     * @return TRUE : If @card is a valid credit card
     *         FALSE otherwise
     */
    public static boolean isValidCard(CreditCard card){
        if(card == null) return false;

        if(card.getExpiry().equals("")
                &&  card.getCardNum().equals("") && card.getSecurity().equals("")) return true;

        return card.getCardNum().length() == 16 && card.getSecurity().length()==3
                && card.getExpiry().length() == 4;
    }

    /**
     * This method checks if the given contact is valid or not
     * @param contact to be checked
     * @return TRUE : If @contact is a valid phone number
     *         FALSE otherwise
     */
    public static boolean isValidContact(String contact) {
        return contact.matches("^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$");
    }

    /**
     * This method checks if the given user is valid or not.
     * Checks strictly name, email and password - only required registration parameters.
     * @param user to be checked
     * @return TRUE : If @user is valid
     *         FALSE otherwise
     */
    public static boolean isValidUser(User user) {
        if(user == null) return false;
        return isValidFullName(user.getFullName()) && isValidPassword(user.getPassword()) && isValidEmailAddress(user.getEmail());
    }
}
