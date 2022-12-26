package com.example.csci3130_g2_quick_cash.repository;

import com.example.csci3130_g2_quick_cash.adapters.SuggestedEmployeeAdapter;
import com.example.csci3130_g2_quick_cash.models.Employee;
import com.example.csci3130_g2_quick_cash.models.Post;
import com.example.csci3130_g2_quick_cash.models.Preference;
import com.example.csci3130_g2_quick_cash.models.User;
import com.example.csci3130_g2_quick_cash.models.referenceUID;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Kanav Bhardwaj
 */

public class UserRepository {
    private final FirebaseDatabase db;
    private final FirebaseAuth auth;
    private static UserRepository instance;

    private UserRepository() {
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }

        return instance;
    }

    public DatabaseReference getDatabaseReference() {
        return db.getReference("users");
    }

    public void createUser(User user, Callback updateUI) {
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = auth.getCurrentUser();
                if (firebaseUser != null) {
                    referenceUID refUID = new referenceUID(user.getEmail(), firebaseUser.getUid());
                    getDatabaseReference().child(firebaseUser.getUid()).setValue(user);
                    saveReferenceUID(refUID, user.getEmail());
                    if (updateUI != null) {
                        updateUI.updateUI(firebaseUser);
                    }
                }
            } else {
                if (updateUI != null) {
                    updateUI.updateUI(null);
                }
            }
        });
    }

    public void saveReferenceUID(referenceUID refUID, String email) {
        String safeEmail = makeEmailDatabaseSafe(email);
        FirebaseDatabase.getInstance().getReference("reference").child(safeEmail).setValue(refUID);
    }

    public String makeEmailDatabaseSafe(String email) {
        String newEmail = email.toLowerCase();
        newEmail = newEmail.replace("@", "A");
        newEmail = newEmail.replace(".", "B");
        newEmail = newEmail.replace("_", "C");
        return newEmail;
    }

    public void signIn(String email, String password, Callback updateUI) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = auth.getCurrentUser();
                if (firebaseUser != null && updateUI != null) {
                    updateUI.updateUI(firebaseUser);
                }
            } else {
                if (updateUI != null) {
                    updateUI.updateUI(null);
                }
            }
        });
    }

    public boolean userIsLoggedIn() {
        return auth != null && auth.getCurrentUser() != null;
    }

    public String getUID() {
        return Objects.requireNonNull(auth.getCurrentUser()).getUid();
    }

    public void savePreference(Preference preference, Callback callback) {
        if (preference == null) return;
        if (auth.getCurrentUser() == null) return;

        getDatabaseReference().child(auth.getCurrentUser().getUid()).child("preference").setValue(preference);

        if (callback != null) {
            callback.updateUI(auth.getCurrentUser());
        }
    }

    public Preference getPreference() {
        if (auth.getCurrentUser() == null) return null;

        return getDatabaseReference().child(auth.getCurrentUser().getUid()).child("preference").get().getResult().getValue(Preference.class);
    }

    public void getSuggestedEmployees(SuggestedEmployeeAdapter adapter, Post post) {
        if (adapter == null || post == null) return;

        getDatabaseReference().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Employee> list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    User emp = dataSnapshot.getValue(User.class);
                    if (shouldBeSuggested(emp, post)) {
                        list.add(emp.toEmployee());
                    }
                }
                adapter.addToList(list);
            } else {
                System.out.println("What the");
            }
        });
    }

    private boolean shouldBeSuggested(User u, Post p) {
        if (u == null || p == null) return false;

        if (u.getPreference() == null) return false;

        if(u.getEmail().equals(Objects.requireNonNull(auth.getCurrentUser()).getEmail())){
            return false;
        }

        if (p.getPostLocation().contains(u.getPreference().getPreferencePlace())) {
            return true;
        }
        if (p.getPostTitle().contains(u.getPreference().getPreferenceTitle())) {
            return true;
        }
        String minSal = u.getPreference().getPreferenceSalary().getMinSalary();

        if (p.getPostSalary().compareTo(minSal) >= 0) {
            return true;
        }

        return false;
    }
}