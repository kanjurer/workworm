package com.example.csci3130_g2_quick_cash.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.Locale;

/**
 * Saving Preferences Validation Class.
 * @author Arman Singh Sidhu
 */

public class PreferenceValidation {

    /**
     * Lint debug.
     */
    private PreferenceValidation() {}

    /**
     * This method checks if the given title is valid (empty or more than 3 characters) or not
     * @param title the title to be checked
     * @return TRUE : If @title is valid
     *         FALSE otherwise
     */
    public static boolean isValidTitle(String title) {
        if (title == null) {
            return false;
        }
        if (title.isEmpty()) {
            return true;
        }
        return title.trim().length() >= 3;
    }

    /**
     * This method checks if the given date is valid or not
     * @param date the date to be checked
     * @return TRUE : If @date is valid
     *         FALSE otherwise
     */
    public static boolean isValidDate(String date) {
        if (date == null) {
            return false;
        }

        if (date.isEmpty()) {
            return true;
        }

        DateTimeFormatter datetimeFormat = DateTimeFormatter.ofPattern("dd/MM/uuuu", Locale.ENGLISH)
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate parsedDate = LocalDate.parse(date, datetimeFormat);
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
        if (date.isEmpty()) {
            return true;
        }

        if (!isValidDate(date)) {
            return false;
        }

        try {
            Date jobDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                    .parse(date);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String todayDateString = dateFormat.format(new Date());

            Date todayDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                    .parse(todayDateString);

            if (jobDate.before(todayDate)) {
                return false;
            }

        }
        catch (ParseException e) {
            return false;
        }

        return true;
    }

    /**
     * This method checks if the given duration is valid (empty or greater than 0) or not
     * @param duration the duration to be checked
     * @return TRUE : If @duration is valid
     *         FALSE otherwise
     */
    public static boolean isValidDuration(String duration) {
        if (duration == null) {
            return false;
        }

        if (duration.isEmpty()) {
            return true;
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
     * This method checks if the given range is valid or not
     * @param lower  the lower value to be checked
     * @param upper the upper value to be checked
     * @return TRUE : If duration/ salary range is valid
     *         FALSE otherwise
     */
    public static boolean isValidRange(String lower, String upper) {
        if (lower.isEmpty() || upper.isEmpty()) {
            return true;
        }

        try {
            if (Integer.parseInt(upper) < Integer.parseInt(lower)) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * This method checks if the given date range is valid or not
     * @param lowerDate  the lower date value to be checked
     * @param upperDate the upper date value to be checked
     * @return TRUE : If date range is valid
     *         FALSE otherwise
     */
    public static boolean isValidDateRange(String lowerDate, String upperDate) {
        if (lowerDate.isEmpty() || upperDate.isEmpty()) {
            return true;
        }

        try {
            Date lowerJobDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                    .parse(lowerDate);

            Date upperJobDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                    .parse(upperDate);

            if (upperJobDate.before(lowerJobDate)) {
                return false;
            }

        }
        catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * This method checks if the given place is in valid format (empty or City, Postal Code) or not
     * @param place the place to be checked
     * @return TRUE : If @place is in valid format
     *         FALSE otherwise
     */
    public static boolean isValidPlace(String place) {
        if (place == null) {
            return false;
        }
        if (place.isEmpty()) {
            return true;
        }

        return place.matches("[a-zA-Z]+(, [ABCEGHJ-NPRSTVXY]\\d[ABCEGHJ-NPRSTV-Z][ -]?\\d[ABCEGHJ-NPRSTV-Z]\\d)?");
    }

    /**
     * This method checks if the given salary is valid (empty or 5 CAD or more) or not
     * @param salary the salary to be checked
     * @return TRUE : If @salary is valid
     *         FALSE otherwise
     */
    public static boolean isValidSalary(String salary) {
        if (salary == null) {
            return false;
        }

        if (salary.isEmpty()) {
            return true;
        }

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
