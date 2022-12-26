package com.example.csci3130_g2_quick_cash.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.csci3130_g2_quick_cash.R;
import com.example.csci3130_g2_quick_cash.RecyclerView.PostRecyclerViewInterface;
import com.example.csci3130_g2_quick_cash.adapters.PostAdapter;
import com.example.csci3130_g2_quick_cash.databinding.ActivityMyPostingsBinding;
import com.example.csci3130_g2_quick_cash.repository.PostRepository;
import com.google.android.material.appbar.CollapsingToolbarLayout;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.widget.Button;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class MyPostingsActivity extends AppCompatActivity implements PostRecyclerViewInterface {

    private ActivityMyPostingsBinding binding;

    private PostRepository postRepository;
    private PostAdapter postAdapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyPostingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;

        // Set BackgroundDrawable
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7C8584")));

        // Sets Action Bar Text Style
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color =#E6E3E4><strong>My Postings</strong></font>"));

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> startActivity(new Intent(MyPostingsActivity.this, WelcomeActivity.class)));

        FloatingActionButton postJob = findViewById(R.id.jobPost);
        postJob.setOnClickListener(view -> startActivity(new Intent(MyPostingsActivity.this, PredefinedJobsActivity.class)));

        populateMyPostings();
    }

    private void init() {
        this.postRepository = PostRepository.getInstance();

        this.postAdapter = new PostAdapter(this);
        this.postAdapter.setPostRecyclerViewInterface(this);

        this.recyclerView = findViewById(R.id.recycler_view);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(postAdapter);
    }

    private void populateMyPostings() {
        postRepository.getUserPosts(postAdapter);
    }

    /**
     * This method defines behaviour for when a post is clicked
     * @param postId the position of post in post list
     */
    @Override
    public void onPostClick(String postId) {
        Intent intent = new Intent(MyPostingsActivity.this, PostActivity.class);
        intent.putExtra("postId", postId);
        startActivity(intent);
    }
}