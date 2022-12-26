package com.example.csci3130_g2_quick_cash.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;

import com.example.csci3130_g2_quick_cash.R;
import com.example.csci3130_g2_quick_cash.RecyclerView.EmployeeRecyclerViewInterface;
import com.example.csci3130_g2_quick_cash.adapters.SuggestedEmployeeAdapter;
import com.example.csci3130_g2_quick_cash.models.Post;
import com.example.csci3130_g2_quick_cash.repository.UserRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class SuggestedEmployeeActivity extends AppCompatActivity implements EmployeeRecyclerViewInterface {
    private UserRepository userRepository;
    private SuggestedEmployeeAdapter suggestedEmployeeAdapter;
    private RecyclerView recyclerView;
    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_employee);

        FloatingActionButton button = findViewById(R.id.closeSuggestions);
        button.setOnClickListener(view-> finish());

        post = getIntent().getSerializableExtra("post", Post.class);

        userRepository = UserRepository.getInstance();

        this.suggestedEmployeeAdapter = new SuggestedEmployeeAdapter(this);
        this.suggestedEmployeeAdapter.setEmployeeRecyclerViewInterface(this);

        this.recyclerView = findViewById(R.id.employee_recycler_view);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(suggestedEmployeeAdapter);

        this.populateSuggestedEmployees();

        setWelcomeMessage();

    }

    private void setWelcomeMessage() {
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7C8584")));
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color =#E6E3E4><strong>WorkWorm</strong></font>"));
    }

    private void populateSuggestedEmployees() {
        userRepository.getSuggestedEmployees(suggestedEmployeeAdapter, post);
    }

    @Override
    public void onEmployeeClick(String postId) {

    }
}