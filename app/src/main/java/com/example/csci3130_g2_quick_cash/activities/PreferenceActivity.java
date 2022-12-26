package com.example.csci3130_g2_quick_cash.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.text.Html;

import com.example.csci3130_g2_quick_cash.R;
import com.example.csci3130_g2_quick_cash.models.Preference;
import com.example.csci3130_g2_quick_cash.models.Salary;
import com.example.csci3130_g2_quick_cash.models.TimeRange;
import com.example.csci3130_g2_quick_cash.repository.UserRepository;
import com.example.csci3130_g2_quick_cash.utils.PreferenceValidation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

/**
 * This class allows a user to register Preferences.
 *
 * @author Gavin Jayeshkum Jariwala, Arman Singh Sidhu
 */

public class PreferenceActivity extends AppCompatActivity {

    private EditText jobSalaryExpectationsMin;
    private EditText jobSalaryExpectationsMax;
    private EditText jobDurationPreferenceMin;
    private EditText jobDurationPreferenceMax;
    private EditText jobPlacePreference;
    private EditText jobDatePreferenceMin;
    private EditText jobTitlePreference;
    private EditText jobDatePreferenceMax;
    private Button confirmButton;
    private CheckBox urgencyCheckBox;
    private UserRepository userRepository;
    private String errorMessage;
    private static final String DEFAULT_ERROR = "No error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        // Initialize
        init();
        updateUI();
        /*
            Validate Preferences Test Casing
            Success - Switch back to Welcome Activity.
            Failure - Displays alert containing error.
         */
        confirmButton.setOnClickListener(view -> {
            TextView statusLabel = findViewById(R.id.preferenceStatusLabel);
            errorMessage = DEFAULT_ERROR;

            checkUpToDurationRange();

            if (errorMessage.equals(DEFAULT_ERROR)) {
                checkUpToPlacePreference();
            }
            // No error
            if (errorMessage.equals(DEFAULT_ERROR)) {
                createPreference();
            }
            statusLabel.setText(errorMessage.trim());
        });

        // Set BackgroundDrawable
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7C8584")));

        // Sets Action Bar Text Style
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color =#E6E3E4>You're your own boss, set <em>your<em> terms.</font>"));

        // Back Button to Welcome Activity or ProfileActivity - User changed mind.
        backToPreviousActivity();
    }

    // Initialization
    private void init() {
        jobTitlePreference = findViewById(R.id.jobTitlePreference);
        jobDatePreferenceMin = findViewById(R.id.jobDatePreference);
        jobDatePreferenceMax = findViewById(R.id.jobDatePreference2);
        jobDurationPreferenceMin = findViewById(R.id.jobDurationPreference);
        jobDurationPreferenceMax = findViewById(R.id.jobDurationPreference2);
        jobPlacePreference = findViewById(R.id.jobPlacePreference);
        jobSalaryExpectationsMin = findViewById(R.id.jobSalaryPreference);
        jobSalaryExpectationsMax = findViewById(R.id.jobSalaryPreference2);
        urgencyCheckBox = findViewById(R.id.jobUrgencyPreference);
        confirmButton = findViewById((R.id.confirmPreference));
        userRepository = UserRepository.getInstance();
    }

    /**
     * This is a getter method for job minimum date preference
     *
     * @return the job date
     */
    private String getTitlePreference() {
        return jobTitlePreference.getText().toString().trim();
    }

    /**
     * This is a getter method for job minimum date preference
     *
     * @return the job date
     */
    private String getMinDatePreference() {
        return jobDatePreferenceMin.getText().toString().trim();
    }

    /**
     * This is a getter method for job maximum date preference
     *
     * @return the job date
     */
    private String getMaxDatePreference() {
        return jobDatePreferenceMax.getText().toString().trim();
    }

    /**
     * This is a getter method for minimum job duration preference
     *
     * @return the job duration
     */
    private String getMinDurationPreference() {
        return jobDurationPreferenceMin.getText().toString().trim();
    }

    /**
     * This is a getter method for maximum job duration preference
     *
     * @return the job duration
     */
    private String getMaxDurationPreference() {
        return jobDurationPreferenceMax.getText().toString().trim();
    }

    /**
     * This is a getter method for job place preference
     *
     * @return the job place
     */
    private String getPlacePreference() {
        return jobPlacePreference.getText().toString().trim();
    }

    /**
     * This is a getter method for minimum job salary preference
     *
     * @return the job salary
     */
    private String getMinSalaryPreference() {
        return jobSalaryExpectationsMin.getText().toString().trim();
    }

    /**
     * This is a getter method for maximum job salary preference
     *
     * @return the job salary
     */
    private String getMaxSalaryPreference() {
        return jobSalaryExpectationsMax.getText().toString().trim();
    }

    /**
     * This is a getter method for job urgency preference
     *
     * @return the job urgency
     */
    private boolean getUrgencyPreference() {
        return urgencyCheckBox.isChecked();
    }

    /*
        Preference saving success or failure.
        SUCCESS - Display successful registration of Preferences and switch to Welcome Activity.
        FAILURE - Error (Backend)
     */
    private void updateUI(FirebaseUser firebaseUser) {
        if (firebaseUser == null) {
            Toast.makeText(PreferenceActivity.this, "Uh oh! Please try again later.",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(PreferenceActivity.this, "Preferences saved successfully.",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PreferenceActivity.this, WelcomeActivity.class));
        }
    }

    /**
     * This method checks if there is any error up to Duration Range
     */
    private void checkUpToDurationRange() {
        if (!PreferenceValidation.isValidTitle(getTitlePreference())) {
            errorMessage = (getResources().getString(R.string.INVALID_TITLE).trim());
        } else if (!PreferenceValidation.isValidSalary(getMinSalaryPreference())) {
            errorMessage = (getResources().getString(R.string.INVALID_SALARY).trim());
        } else if (!PreferenceValidation.isValidSalary(getMaxSalaryPreference())) {
            errorMessage = (getResources().getString(R.string.INVALID_SALARY).trim());
        } else if (!PreferenceValidation.isValidRange(getMinSalaryPreference(),
                getMaxSalaryPreference())) {
            errorMessage = (getResources().getString(R.string.INVALID_SALARY_RANGE).trim());
        } else if (!PreferenceValidation.isValidDuration(getMinDurationPreference())) {
            errorMessage = (getResources().getString(R.string.INVALID_DURATION).trim());
        } else if (!PreferenceValidation.isValidDuration(getMaxDurationPreference())) {
            errorMessage = (getResources().getString(R.string.INVALID_DURATION).trim());
        } else if (!PreferenceValidation.isValidRange(getMinDurationPreference(),
                getMaxDurationPreference())) {
            errorMessage = (getResources().getString(R.string.INVALID_DURATION_RANGE).trim());
        }
    }

    /**
     * This method checks if there is any error up to Place Preference
     */
    private void checkUpToPlacePreference() {
        if (!PreferenceValidation.isValidDate(getMinDatePreference())) {
            errorMessage = (getResources().getString(R.string.INVALID_DATE).trim());
        } else if (!PreferenceValidation.isNotPastDate(getMinDatePreference())) {
            errorMessage = (getResources().getString(R.string.PAST_DATE).trim());
        } else if (!PreferenceValidation.isValidDate(getMaxDatePreference())) {
            errorMessage = (getResources().getString(R.string.INVALID_DATE).trim());
        } else if (!PreferenceValidation.isNotPastDate(getMaxDatePreference())) {
            errorMessage = (getResources().getString(R.string.PAST_DATE).trim());
        } else if (!PreferenceValidation.isValidDateRange(getMinDatePreference(),
                getMaxDatePreference())) {
            errorMessage = (getResources().getString(R.string.INVALID_DATE_RANGE).trim());
        } else if (!PreferenceValidation.isValidPlace(getPlacePreference())) {
            errorMessage = (getResources().getString(R.string.INVALID_PLACE).trim());
        }
    }

    /**
     * This method creates a Preference from the user input
     */
    private void createPreference() {
        TimeRange timeRange = new TimeRange(getMinDatePreference(),
                getMaxDatePreference(), getMinDurationPreference(), getMaxDurationPreference());
        Salary salary = new Salary(getMinSalaryPreference(), getMaxSalaryPreference());
        Preference preference = new Preference(getTitlePreference(), timeRange, salary, getPlacePreference(),
                getUrgencyPreference());
        errorMessage = getResources().getString(R.string.EMPTY_STRING).trim();

        if (isAccessedFromProfileActivity()) {
            userRepository.savePreference(preference, this::updateUI);
        } else {
            Intent searchIntent = new Intent(PreferenceActivity.this,
                    PreferredPostingsActivity.class);
            searchIntent.putExtra("userPreference", preference);
            startActivity(searchIntent);
        }
    }

    // Takes back to the previous activity
    private void backToPreviousActivity() {
        FloatingActionButton backButton = findViewById(R.id.backBut);
        backButton.setOnClickListener(view -> {
            if (isAccessedFromProfileActivity()) {
                startActivity(new Intent(PreferenceActivity.this, ProfileActivity.class));
            } else {
                startActivity(new Intent(PreferenceActivity.this, WelcomeActivity.class));
            }
        });
    }

    private void updateUI() {
        if(!isAccessedFromProfileActivity()) {
            TextView textView = findViewById(R.id.textView7);
            textView.setText(R.string.filter);
            confirmButton.setText(R.string.search2);
            TextView textView1 = findViewById(R.id.textView10);
            textView1.setVisibility(View.INVISIBLE);
            ImageView imageView = findViewById(R.id.right_peek);
            imageView.setVisibility(View.INVISIBLE);
        }
        else {
            ImageView imageView2 = findViewById(R.id.left_peek);
            imageView2.setVisibility(View.INVISIBLE);
        }
    }

    // Checks whether this activity is accessed from Profile activity
    private boolean isAccessedFromProfileActivity() {
        Intent preferenceIntent = getIntent();
        String type = preferenceIntent.getStringExtra("type");

        return type != null && type.equals("savePreference");
    }
}