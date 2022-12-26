package com.example.csci3130_g2_quick_cash.utils;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.csci3130_g2_quick_cash.R;
import com.example.csci3130_g2_quick_cash.activities.PayPalActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

/**
 * This class handles errors regarding Payment Validation
 * @author Adam Sarty
 */

public class PaymentValidation {

    /**
     * Lint debug.
     */
    private PaymentValidation() {}

    /**
     * Validates hourly wage.
     *
     * @param hourly hourly wage
     * @return true/false
     */
    public static boolean isHourlyValid(String hourly) {
        if (hourly == null) return false;
        if (hourly.isEmpty()) return false;

        try {
            if (Double.parseDouble(hourly) <= 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Validates hours worked.
     *
     * @param hours hours worked
     * @return true/false
     */
    public static boolean isHoursValid(String hours) {
        if (hours == null) return false;
        if (hours.isEmpty()) return false;

        try {
            if (Double.parseDouble(hours) <= 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Validates star rating.
     *
     * @param stars rating out of 5
     * @return true/false
     */
    public static boolean isRatingValid(Integer stars) {
        if (stars == null) return false;
        return stars != 0;
    }
}