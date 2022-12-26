package com.example.csci3130_g2_quick_cash.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;

import com.example.csci3130_g2_quick_cash.R;
import com.example.csci3130_g2_quick_cash.adapters.PostAdapter;
import com.example.csci3130_g2_quick_cash.repository.PostRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private FloatingActionButton backButton;
    private PostRepository postRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        init();
        setFloatingActions();
        populateMyPostings();
    }

    private void init() {
        // Set BackgroundDrawable
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7C8584")));
        // Sets Action Bar Text Style
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color =#E6E3E4>Rundown Postings by <strong>WorkWorm</strong>.</font>"));

        backButton = findViewById(R.id.backBut2);
        postAdapter = new PostAdapter(this);
        this.postRepository = PostRepository.getInstance();
        this.postAdapter = new PostAdapter(this);
        this.recyclerView = findViewById(R.id.recycler_view);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(postAdapter);
    }

    private void populateMyPostings() {
        postRepository.getUserJobHistory(postAdapter, this);
    }

    /**
     * Instantiates Floating Action to Redirect to Selected Activity
     *
     * @actions Back Button
     */
    private void setFloatingActions() {
        backButton.setOnClickListener(view -> startActivity(new Intent(HistoryActivity.this, ProfileActivity.class)));

    }

}