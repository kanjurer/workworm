package com.example.csci3130_g2_quick_cash.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.csci3130_g2_quick_cash.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class PredefinedJobsActivity extends AppCompatActivity {
    private Button startFromScratch, walkADog, fixAComputer, mowALawn, pickUpGroceries, babySitting, dropToAirport, airportPickup;
    private FloatingActionButton backButton;
    public String mText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android_predefined_jobs);
        init();

        startFromScratchButton();
        backButton.setOnClickListener(view -> startActivity(new Intent(PredefinedJobsActivity.this, MyPostingsActivity.class)));

        optionSelected(walkADog);
        optionSelected(fixAComputer);
        optionSelected(mowALawn);
        optionSelected(pickUpGroceries);
        optionSelected(babySitting);
        optionSelected(dropToAirport);
        optionSelected(airportPickup);
    }

    private void init() {
        startFromScratch = findViewById((R.id.StartFromScratch));
        backButton = findViewById(R.id.backButton);
        walkADog = findViewById(R.id.WalkADog);
        fixAComputer = findViewById(R.id.FixAComputer);
        mowALawn = findViewById(R.id.MowALawn);
        pickUpGroceries = findViewById(R.id.PickUpGroceries);
        babySitting = findViewById(R.id.BabySitting);
        dropToAirport = findViewById(R.id.DropToAirport);
        airportPickup = findViewById(R.id.AirportPickup);

        // Set BackgroundDrawable
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7C8584")));
        // Sets Action Bar Text Style
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color =#E6E3E4>Let someone else do it. &#128564</font>"));
    }

    private void startFromScratchButton() {
        startFromScratch.setOnClickListener((view) -> {
            mText = "";
            Intent intent = new Intent(PredefinedJobsActivity.this, JobActivity.class);
            intent.putExtra("Title", mText);
            startActivity(intent);
        });
    }

    private void optionSelected(Button button) {
        button.setOnClickListener((view) -> {
                    mText = button.getText().toString();
                    Intent intent = new Intent(PredefinedJobsActivity.this, JobActivity.class);
                    intent.putExtra("Title", mText);
                    if(mText.equals("Walk my Dog")){
                        intent.putExtra("Date", currentDate());
                        intent.putExtra("Duration", "2");
                        intent.putExtra("Salary", "20");
                        intent.putExtra("Urgent", true);
                    }
                    else if(mText.equalsIgnoreCase("Fix My Computer")){
                        intent.putExtra("Date", currentDate());
                        intent.putExtra("Duration", "2");
                        intent.putExtra("Salary", "17");
                        intent.putExtra("Urgent", true);
                    }
                    else if(mText.equalsIgnoreCase("Mow My Lawn")){
                        intent.putExtra("Duration", "1");
                        intent.putExtra("Salary", "19");
                        intent.putExtra("Urgent", false);
                    }
                    else if(mText.equalsIgnoreCase("Pickup My Groceries")){
                        intent.putExtra("Date", currentDate());
                        intent.putExtra("Duration", "1");
                        intent.putExtra("Salary", "17");
                        intent.putExtra("Urgent", true);
                    }
                    else if(mText.equalsIgnoreCase("BabySitting")){
                        intent.putExtra("Date", currentDate());
                        intent.putExtra("Duration", "4");
                        intent.putExtra("Salary", "21");
                        intent.putExtra("Urgent", true);
                    }
                    else if(mText.equalsIgnoreCase("Dropoff To airport")){
                        intent.putExtra("Date", currentDate());
                        intent.putExtra("Duration", "2");
                        intent.putExtra("Salary", "19");
                        intent.putExtra("Urgent", true);
                    }
                    else {
                        intent.putExtra("Duration", "2");
                        intent.putExtra("Salary", "19");
                        intent.putExtra("Urgent", false);
                    }
                    startActivity(intent);
                }
        );

    }

    private String currentDate() {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
           return dateFormat.format(new Date());
    }

}
