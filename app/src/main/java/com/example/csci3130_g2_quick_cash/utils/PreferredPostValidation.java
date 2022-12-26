package com.example.csci3130_g2_quick_cash.utils;

import com.example.csci3130_g2_quick_cash.models.Post;
import com.example.csci3130_g2_quick_cash.models.Preference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This class tests preferred post validation.
 * @author Arman Singh Sidhu
 */
public class PreferredPostValidation {
    /**
     * This is a method that checks if the post is preferred (i.e. matches all the preferences)
     * @return true if the post is preferred
     *         false if the post is not preferred
     */
    public static boolean checkIfPreferred(Preference userPreference, Post post) {
        // Checks if the title is preferred
        if (!isTitlePreferred(userPreference.getPreferenceTitle(), post.getPostTitle())) {
            return false;
        }
        // Checks if the place is preferred
        if (!isPlacePreferred(userPreference.getPreferencePlace(), post.getPostLocation())) {
            return false;
        }
        // Checks if the post salary is within range
        if (!isValidRange(userPreference.getPreferenceSalary().getMinSalary(),
                userPreference.getPreferenceSalary().getMaxSalary(), post.getPostSalary())) {
            return false;
        }

        // Checks if the post duration is within range
        if (!isValidRange(userPreference.getPreferenceTimeRange().getMinDuration(),
                userPreference.getPreferenceTimeRange().getMaxDuration(), post.getPostExpectedTime())) {
            return false;
        }

        // Checks if the post date is within range
        if (!isPreferredDate(userPreference.getPreferenceTimeRange().getMinDate(),
                userPreference.getPreferenceTimeRange().getMaxDate(), post.getPostCreationDate())) {
            return false;
        }
        // Checks if urgency matches
        return userPreference.isUrgent() == post.getPostUrgency();
    }

    /**
     * This is a method that checks if the post title is preferred (i.e. matches the preference title)
     * @return true if the post title is preferred
     *         false if the post title is not preferred
     */
    public static boolean isTitlePreferred(String preferenceTitle, String postTitle) {
        if (preferenceTitle.isEmpty()) {
            return true;
        }
        return postTitle.toLowerCase().contains(preferenceTitle.toLowerCase());
    }

    /**
     * This is a method that checks if the post place is preferred (i.e. matches the preference place)
     * @return true if the post place is preferred
     *         false if the post place is not preferred
     */
    public static boolean isPlacePreferred(String preferencePlace, String postPlace) {
        if (preferencePlace.isEmpty()) {
            return true;
        }

        if (preferencePlace.contains(",")) {
            return preferencePlace.equalsIgnoreCase(postPlace);
        }

        return preferencePlace.equalsIgnoreCase(postPlace.split(",")[0]);
    }

    /**
     * This is a method that checks if the post salary/duration is preferred
     * (i.e. within the preference salary/duration range)
     * @return true if the post salary/duration is preferred
     *         false if the post salary/duration is not preferred
     */
    public static boolean isValidRange(String lower, String upper, String givenValue) {
        if (lower.isEmpty() && upper.isEmpty()) {
            return true;
        }

        int value = Integer.parseInt(givenValue);
        boolean res = false;

        if (!lower.isEmpty()) {
            int min = Integer.parseInt(lower);
            res = value >= min;
        }
        if (!upper.isEmpty()) {
            int max = Integer.parseInt(upper);
            res = value <= max;
        }

        return res;
    }

    /**
     * This is a method that checks if the post date is preferred
     * (i.e. within the preference date range)
     * @return true if the post date is preferred
     *         false if the post date is not preferred
     */
    public static boolean isPreferredDate(String preferredMinDate, String preferredMaxDate, String postDateString) {
        if (preferredMinDate.isEmpty() && preferredMaxDate.isEmpty()) {
            return true;
        }
        boolean res = false;

        try {
            Date postDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                    .parse(postDateString);

            if (!preferredMinDate.isEmpty()) {
                Date min = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                        .parse(preferredMinDate);
                res = min.before(postDate) || min.equals(postDate);
            }
            if (!preferredMaxDate.isEmpty()) {
                Date max = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                        .parse(preferredMaxDate);
                res = postDate.before(max) || max.equals(postDate);
            }
        } catch (ParseException e) {
            return false;
        }
        return res;
    }
}
