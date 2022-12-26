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
import com.example.csci3130_g2_quick_cash.utils.UserValidation;
import com.example.csci3130_g2_quick_cash.repository.UserRepository;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

/**
 * This activity allows a user to login with email and password or optionally switch to
 * creating a new account within RegistrationActivity.java.
 *
 * @author Adam Sarty, Kanav Bhardwaj
 */
public class MainActivity extends AppCompatActivity {

    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);
        this.userRepository = UserRepository.getInstance();

        if (userRepository.userIsLoggedIn()) {
            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
        } else {
            setContentView(R.layout.activity_main);
            setLoginButton();
            setRegisterButton();
            setWelcomeMessage();
        }
    }

    /**
     * Listener after pressing 'Login Button' - displays credential rejection or success.
     * Success - Switches to WelcomeActivity.java.
     * Failure - Displays error message.
     */
    private void setLoginButton() {
        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(view -> {
            String email = getEmailAddress();
            String password = getPassword();

            if (!UserValidation.isValidEmailAddress(email)) {
                setStatusMessage(getResources().getString(R.string.INVALID_EMAIL).trim());
                return;
            }
            if (!UserValidation.isValidPassword(password)) {
                setStatusMessage(getResources().getString(R.string.INVALID_PASSWORD).trim());
                return;
            }

            signIn(email, password);
        });
    }

    /**
     * Moves user to registration activity after onClick of 'I'm a New User'.
     */
    private void setRegisterButton() {
        Button registerButton = findViewById(R.id.newUser);
        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });
    }

    private void setWelcomeMessage() {
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7C8584")));
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color =#E6E3E4><strong>WorkWorm</strong></font>"));
    }

    /**
     * This method signs in a user when called, and compares email and password and will alert user of
     * credential success or failure.
     *
     * @param email    email
     * @param password password
     */
    private void signIn(String email, String password) {
        if (userRepository == null) return;
        userRepository.signIn(email, password, this::updateUI);
    }

    /**
     * Transfers user to Welcome Activity upon successful login, alternatively handles invalid email or password.
     *
     * @param user FirebaseUser
     */
    protected void updateUI(FirebaseUser user) {
        if (user == null) {
            setStatusMessage(getResources().getString(R.string.INVALID_CREDENTIALS).trim());
        } else {
            Toast.makeText(MainActivity.this, "Authentication success.",
                    Toast.LENGTH_SHORT).show();

            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
        }
    }

    private String getEmailAddress() {
        return ((EditText) findViewById(R.id.email)).getText().toString().trim();
    }

    private String getPassword() {
        return ((EditText) findViewById(R.id.password)).getText().toString().trim();
    }

    protected void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.mainStatusLabel);
        statusLabel.setText(message);
    }
}
