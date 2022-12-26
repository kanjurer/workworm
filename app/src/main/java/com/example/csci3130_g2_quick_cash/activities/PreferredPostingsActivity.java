package com.example.csci3130_g2_quick_cash.activities;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csci3130_g2_quick_cash.R;
import com.example.csci3130_g2_quick_cash.RecyclerView.PostRecyclerViewInterface;
import com.example.csci3130_g2_quick_cash.adapters.PostAdapter;
import com.example.csci3130_g2_quick_cash.models.Preference;
import com.example.csci3130_g2_quick_cash.repository.PostRepository;
import com.example.csci3130_g2_quick_cash.repository.UserRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

/**
 * This activity filters the job posts according to the preferences/ search queries
 *
 * @author Arman Singh Sidhu
 */
public class PreferredPostingsActivity extends AppCompatActivity implements PostRecyclerViewInterface {
    private UserRepository userRepository;
    private PostRepository postRepository;
    private PostAdapter postAdapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferred_postings);

        init();

        setFloatingActions();

        // Checks whether this activity received Preference Object from PreferenceActivity
        Object obj = getUserPreferenceObject();
        if (obj == null) {
            //Retrieves the user Preference stored in database
            populateFilteredJobs(null);

        } else if (obj instanceof Preference) {
            // filter jobs according to search queries provided by the user
            populateFilteredJobs((Preference) obj);
        }
        // Set BackgroundDrawable
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7C8584")));

        // Sets Action Bar Text Style
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color =#E6E3E4>Filtered to your Preferences.</font>"));
    }

    // Filters the jobs according to the user preferences
    private void populateFilteredJobs(Preference preference) {
        if (preference == null) {
            postRepository.getUserPreferredPosts(postAdapter);

        } else {
            postRepository.getPreferredPosts(postAdapter, preference);

        }
    }

    // Initialization
    private void init() {
        this.userRepository = UserRepository.getInstance();
        this.postRepository = PostRepository.getInstance();

        this.postAdapter = new PostAdapter(this);
        this.postAdapter.setPostRecyclerViewInterface(this);

        this.recyclerView = findViewById(R.id.recycler_view);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(postAdapter);

    }

    // Back button
    public void setFloatingActions() {
        FloatingActionButton backBTN = findViewById(R.id.backBut);
        backBTN.setOnClickListener(view -> openAppropriateView());
    }

    // Switches to the appropriate activity
    private void openAppropriateView() {
        if (getUserPreferenceObject() != null) {
            startActivity(new Intent(PreferredPostingsActivity.this, PreferenceActivity.class));
        } else {
            startActivity(new Intent(PreferredPostingsActivity.this, WelcomeActivity.class));
        }
    }

    // Receives the Preference from PreferenceActivity
    private Object getUserPreferenceObject() {
        return getIntent().getSerializableExtra("userPreference");
    }

    /**
     * This method defines behaviour for when a post is clicked
     * @param postId the position of post in post list
     */
    @Override
    public void onPostClick(String postId) {
        Intent intent = new Intent(PreferredPostingsActivity.this, PostActivity.class);
        intent.putExtra("postId", postId);
        startActivity(intent);
    }
}
