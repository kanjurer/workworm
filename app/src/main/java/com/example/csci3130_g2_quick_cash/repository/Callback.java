package com.example.csci3130_g2_quick_cash.repository;

import com.google.firebase.auth.FirebaseUser;

public interface Callback {
    void updateUI(FirebaseUser user);
}
