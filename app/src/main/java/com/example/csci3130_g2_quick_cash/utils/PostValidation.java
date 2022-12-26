package com.example.csci3130_g2_quick_cash.utils;

import android.annotation.SuppressLint;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * This class tests post validation upon creation.
 * @author Kanav Bhardwaj, Arman Singh Sidhu
 */

public class PostValidation {

    /**
     * Lint debug.
     */
    private PostValidation() {}

    /**
     * This method checks if the given date is valid or not
     * @param date the date to be checked
     * @return TRUE : If @date is valid
     *         FALSE otherwise
     */
    public static boolean isValidDate(String date) {
        if (date == null ) return false;

        DateTimeFormatter datetimeFormat = DateTimeFormatter.ofPattern("dd/MM/uuuu", Locale.ENGLISH)
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate.parse(date, datetimeFormat);
        } catch (DateTimeException e) {
            return false;
        }

        return true;
    }

    /**
     * This method checks if the given date is not a past date
     * @param date the date to be checked
     * @return TRUE : If @date is not a past date
     *         FALSE otherwise
     */
    public static boolean isNotPastDate(String date) {
        if (!isValidDate(date)) return false;

        try {
            Date jobDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                    .parse(date);

            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String todayDateString = dateFormat.format(new Date());

            Date todayDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                    .parse(todayDateString);

            if (jobDate == null || jobDate.before(todayDate)) {
                return false;
            }

        }
        catch (ParseException e) {
            return false;
        }

        return true;
    }

    /**
     * This method checks if the given duration is valid (greater than 0) or not
     * @param duration the duration to be checked
     * @return TRUE : If @duration is valid
     *         FALSE otherwise
     */
    public static boolean isValidDuration(String duration) {
        if (duration == null) {
            return false;
        }

        if (duration.isEmpty()) {
            return false;
        }

        try {
            if (Integer.parseInt(duration) <= 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * This method checks if the given description is valid (more than 5 characters) or not
     * @param description the description to be checked
     * @return TRUE : If @description is valid
     *         FALSE otherwise
     */
    public static boolean isValidDescription(String description) {
        if (description == null) {
            return false;
        }
        return description.trim().length() >= 5;
    }

    /**
     * This method checks if the given title is valid (more than 3 characters) or not
     * @param title the title to be checked
     * @return TRUE : If @title is valid
     *         FALSE otherwise
     */
    public static boolean isValidTitle(String title) {
        if (title == null) {
            return false;
        }
        return title.trim().length() >= 3;
    }

    /**
     * This method checks if the given place is in valid format (City, Postal Code) or not
     * @param place the place to be checked
     * @return TRUE : If @place is in valid format
     *         FALSE otherwise
     */
    public static boolean isValidPlace(String place) {
        if (place == null) return false;

        return place.matches("[a-zA-Z]+, [ABCEGHJ-NPRSTVXY]\\d[ABCEGHJ-NPRSTV-Z][ -]?\\d[ABCEGHJ-NPRSTV-Z]\\d");
    }

    /**
     * This method checks if the given salary preference is valid i.e. 5 CAD or more
     * @param salary the salary to be checked
     * @return TRUE : If @salary is valid
     *         FALSE otherwise
     */
    public static boolean isValidSalary(String salary) {
        if (salary == null) return false;
        if (salary.isEmpty()) return false;

        try {
            if (Integer.parseInt(salary) < 5) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
