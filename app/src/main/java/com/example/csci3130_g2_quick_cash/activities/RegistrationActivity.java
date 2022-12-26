package com.example.csci3130_g2_quick_cash.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csci3130_g2_quick_cash.R;
import com.example.csci3130_g2_quick_cash.models.CreditCard;
import com.example.csci3130_g2_quick_cash.models.User;
import com.example.csci3130_g2_quick_cash.repository.UserRepository;
import com.example.csci3130_g2_quick_cash.utils.UserValidation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

/**
 * This activity allows a user to register themselves with the optional verification step of providing a credit card number.
 * @author Adam Sarty
 */
public class RegistrationActivity extends AppCompatActivity {

    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        this.userRepository = UserRepository.getInstance();

        setBackButton();
        setRegisterButton();
        setWelcomeMessage();
    }

    /**
     * Back Button -- Redirects to login page (MainActivity.java).
     */
    private void setBackButton() {
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> startActivity(new Intent(RegistrationActivity.this, MainActivity.class)));
    }

    /**
     * onClickListener -- 'Register Button' - displays credential rejection or success.
     */
    private void setRegisterButton() {
        Button registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(view-> {
                    if (!UserValidation.isValidFullName(getFullName())) {
                        setStatusMessage(getResources().getString(R.string.INVALID_FULL_NAME).trim());
                        return;
                    }
                    if (!UserValidation.isValidEmailAddress(getEmailAddress())) {
                        setStatusMessage(getResources().getString(R.string.INVALID_EMAIL).trim());
                        return;
                    }
                    if (!UserValidation.isValidPassword(getPassword())) {
                        setStatusMessage(getResources().getString(R.string.INVALID_PASSWORD).trim());
                        return;
                    }
                    if (!UserValidation.isValidContact(getContact())) {
                        setStatusMessage(getResources().getString(R.string.INVALID_CONTACT).trim());
                        return;
                    }
                    CreditCard card = new CreditCard(getCardNumber(), getExpiry(), getSecurity());
                    if (!UserValidation.isValidCard(card)) {
                        Toast.makeText(RegistrationActivity.this, "Invalid card.",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    User user = new User(getFullName(), getEmailAddress(), getPassword(),getContact(), card);
                    userRepository.createUser(user, this::updateUI);
                }
        );
    }

    /**
     * Action Bar Welcome Message - set text and styling.
     */
    private void setWelcomeMessage() {
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7C8584")));
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color =#E6E3E4>Welcome to <strong>WorkWorm</strong>!</font>"));
    }

    /**
     * Update UI - redirect to WelcomeActivity or Display Error for Invalid Credentials
     * @param user Firebase Registration
     */
    protected void updateUI(FirebaseUser user) {
        if(user == null){
            Toast.makeText(RegistrationActivity.this, "Uh oh! Something went wrong.",
                    Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(RegistrationActivity.this, "Registration success.",
                    Toast.LENGTH_SHORT).show();
            // Switch to Welcome Activity, carry user over.
            Intent welcomeActivityIntent = new Intent(this, WelcomeActivity.class);
            welcomeActivityIntent.putExtra("User", user);
            startActivity(welcomeActivityIntent);
        }
    }

    /**
     * Error Handling upon Invalid Registration Credentials
     * @param message Error
     */
    protected void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.registerStatusLabel);
        statusLabel.setText(message.trim());
    }

    private String getEmailAddress() {
        return ((EditText)findViewById(R.id.regEmail)).getText().toString().trim();
    }

    private String getPassword() {
        return ((EditText)findViewById(R.id.regPassword)).getText().toString().trim();
    }

    private String getFullName() {
        return ((EditText)findViewById(R.id.regFullName)).getText().toString().trim();
    }

    private String getCardNumber() {
        return ((EditText)findViewById(R.id.regCardNumber)).getText().toString().trim();
    }

    private String getSecurity() {
        return ((EditText)findViewById(R.id.regSecurity)).getText().toString().trim();
    }

    private String getExpiry() {
        return ((EditText)findViewById(R.id.regExpiry)).getText().toString().trim();
    }

    private String getContact(){
        return ((EditText)findViewById(R.id.regPhoneNumber)).getText().toString().trim();
    }
}