package com.example.csci3130_g2_quick_cash.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.example.csci3130_g2_quick_cash.R;
import com.example.csci3130_g2_quick_cash.repository.UserRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

/**
 * This class displays a user profile.
 * @features Preferences, Close Listing
 * @author Adam Sarty, Arman Singh Sidhu
 */

public class ProfileActivity extends AppCompatActivity {

    private UserRepository userRepository;
    private FloatingActionButton histButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setFloatingActions();
        setWelcomeMessage();
    }

    /**
     * Action Bar Welcome Message - set text and styling.
     */
    private void setWelcomeMessage() {
        this.userRepository = UserRepository.getInstance();
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7C8584")));
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color =#E6E3E4><strong>WorkWorm</strong></font>"));
        userRepository.getDatabaseReference().child(userRepository.getUID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("fullName").getValue(String.class);
                Boolean verified = snapshot.child("verified").getValue(Boolean.class);
                assert name != null;
                assert verified != null;

                TextView proName = findViewById(R.id.profileName);
                TextView proVerified = findViewById(R.id.verifiedStatus);
                if(verified) {
                    proVerified.setVisibility(View.VISIBLE);
                }
                proName.setText(name);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Lint Debug
            }
        });
    }

    /**
     * Instantiates Floating Action to Redirect to Selected Activity
     * @actions Preferences, Close Listing, Back Button
     */
    private void setFloatingActions() {
        FloatingActionButton preferences = findViewById(R.id.preferencesB);
        preferences.setOnClickListener(view -> openPreferences());
        FloatingActionButton backButton = findViewById(R.id.backBut);
        backButton.setOnClickListener(view -> startActivity(new Intent(ProfileActivity.this, WelcomeActivity.class)));
        FloatingActionButton statisticsButton = findViewById(R.id.statisticsButton);
        statisticsButton.setOnClickListener(view -> startActivity(new Intent(ProfileActivity.this, StatisticsActivity.class)));
        histButton = findViewById(R.id.historyButton);
        histButton.setOnClickListener(view -> startActivity(new Intent(ProfileActivity.this, HistoryActivity.class)));
    }

    /**
     * Redirection to Save Preferences Activity.
     */
    private void openPreferences() {
        Intent preferenceIntent = new Intent(ProfileActivity.this, PreferenceActivity.class);
        preferenceIntent.putExtra("type","savePreference");
        startActivity(preferenceIntent);
    }
}