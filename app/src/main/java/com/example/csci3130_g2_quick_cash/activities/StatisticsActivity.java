package com.example.csci3130_g2_quick_cash.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

public class StatisticsActivity extends AppCompatActivity {

    private UserRepository userRepository;
    private FloatingActionButton backButton;
    private TextView moneySymbol;
    private TextView numOfPostings;
    private TextView numOfPostings2;
    private TextView numOfStars;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        init();
        setFloatingActions();
        setStatistics();
    }

    private void init() {
        // Set BackgroundDrawable
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7C8584")));
        // Sets Action Bar Text Style
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color =#E6E3E4>Rundown by <strong>WorkWorm</strong>.</font>"));

        backButton = findViewById(R.id.backButton2);
        moneySymbol = findViewById(R.id.moneySymbol);
        numOfPostings = findViewById(R.id.numOfPostings);
        numOfPostings2 = findViewById(R.id.numOfPostings2);
        numOfStars = findViewById(R.id.numOfStars);
    }

    private void setStatistics() {
        this.userRepository = UserRepository.getInstance();
        userRepository.getDatabaseReference().child(userRepository.getUID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer totalIncome = snapshot.child("totalIncome").getValue(Integer.class);
                Integer totalClosedJobs = snapshot.child("totalClosedJobs").getValue(Integer.class);
                Integer totalCompletedJobs = snapshot.child("totalCompletedJobs").getValue(Integer.class);
                Integer totalStars = snapshot.child("rating").getValue(Integer.class);

                moneySymbol.setText("$" + totalIncome);
                numOfPostings.setText(totalCompletedJobs + " Postings");
                numOfPostings2.setText(totalClosedJobs + " Postings");
                numOfStars.setText(totalStars + "/5 ⭐");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Lint Debug
            }
        });
    }

    private void setFloatingActions() {
        backButton.setOnClickListener(view -> startActivity(new Intent(StatisticsActivity.this, ProfileActivity.class)));
    }
}