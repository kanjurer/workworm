package com.example.csci3130_g2_quick_cash.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.csci3130_g2_quick_cash.R;
import com.example.csci3130_g2_quick_cash.RecyclerView.EmployeeRecyclerViewInterface;
import com.example.csci3130_g2_quick_cash.RecyclerView.PostRecyclerViewInterface;
import com.example.csci3130_g2_quick_cash.adapters.PostAdapter;
import com.example.csci3130_g2_quick_cash.adapters.SuggestedEmployeeAdapter;
import com.example.csci3130_g2_quick_cash.models.Post;
import com.example.csci3130_g2_quick_cash.repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class PostActivity extends AppCompatActivity implements EmployeeRecyclerViewInterface{
    private UserRepository userRepository;
    private String postId;
    private Post post;
    private FloatingActionButton button;
    private FloatingActionButton button2;
    private Button suggestedEmployeebtn;
    private TextView temp;
    private TextView info;
    private TextView postIdBox;
    private String wage;
    private String title;
    private FloatingActionButton textButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        init();

        setFloatingActions();

        getPhoneNumber();

        getPostInformation();

        postId = getIntent().getStringExtra("postId");
        getPost();
    }

    private void init() {
        this.userRepository = UserRepository.getInstance();

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7C8584")));
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color =#E6E3E4><strong>WorkWorm</strong></font>"));
        temp = findViewById(R.id.postTemp);
        info = findViewById(R.id.postInformation);
        postIdBox = findViewById(R.id.postIdBox);
        textButton = findViewById(R.id.textButton);


        this.suggestedEmployeebtn = findViewById(R.id.suggestedEmployeeBtn);
        this.suggestedEmployeebtn.setOnClickListener(view -> {
            Intent i = new Intent(PostActivity.this, SuggestedEmployeeActivity.class);
            i.putExtra("post", post);
            startActivity(i);
        });

    }



    private void setFloatingActions() {
        FloatingActionButton closePostView = findViewById(R.id.closePostViewBtn);
        closePostView.setOnClickListener(view -> finish());
        button = findViewById(R.id.closeListingB2);
        button.setOnClickListener(view -> transferToPay());
        button2 = findViewById(R.id.callButton);
        button2.setOnClickListener(view -> call());
        textButton.setOnClickListener(view -> message());
    }

    private void transferToPay() {
        String value = postIdBox.getText().toString();
        Intent i = new Intent(PostActivity.this, PayPalActivity.class);
        i.putExtra("key", value);
        i.putExtra("wage", wage);
        startActivity(i);
    }

    private void message() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:" + temp.getText().toString()));
        String x = "Hello! I'm interested in your posting on WorkWorm titled " + title + ".";
        intent.putExtra("sms_body", x);
        startActivity(intent);
    }

    private void call() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + temp.getText().toString()));
        startActivity(intent);
    }

    private void getPhoneNumber() {
        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String uid = snapshot.child("posts/" + postId + "/postCreatedBy").getValue(String.class);
                if (uid == null) {
                    return;
                }
                uid = uid.replace(" ", "");
                String phoneNum = snapshot.child("users/" + uid + "/contact").getValue(String.class);
                temp.setText(phoneNum);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Debug
            }
        });
    }

    private void getPostInformation() {
        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String uid = snapshot.child("posts/" + postId + "/postCreatedBy").getValue(String.class);
                uid = uid.replace(" ", "");
                String name = snapshot.child("users/" + uid + "/fullName").getValue(String.class);
                String date = snapshot.child("posts/" + postId + "/postCreationDate").getValue(String.class);
                String information = date + "\n" + name;
                info.setText(information);
                String id = snapshot.child("posts/" + postId + "/postId").getValue(String.class);
                postIdBox.setText(id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Debug
            }
        });
    }

    private boolean getPost() {
        if (postId == null) {
            return false;
        }

        FirebaseDatabase.getInstance().getReference("posts").child(postId).get().addOnCompleteListener(task -> {
            this.post = task.getResult().getValue(Post.class);
            FirebaseAuth auth = FirebaseAuth.getInstance();
            if (this.post != null) {
                fillPostDetails(this.post);
                if (!Objects.requireNonNull(task.getResult().getKey()).contains(Objects.requireNonNull(auth.getUid()))) {
                    button.setVisibility(View.GONE);
                    findViewById(R.id.suggestedEmployeeBtn).setVisibility(View.GONE);
                } else {
                    button2.setVisibility(View.GONE);
                    textButton.setVisibility(View.GONE);
                }
            }
            // ERR Don't know what to do here
        });
        return false;
    }

    private void fillPostDetails(Post post) {
        ((TextView) findViewById(R.id.postTitle)).setText(post.getPostTitle());
        ((TextView) findViewById(R.id.postDesc)).setText(String.format("%s", post.getPostDescription()));
        ((TextView) findViewById(R.id.postPay)).setText(String.format("$%s/hour", post.getPostSalary()));
        ((TextView) findViewById(R.id.postPlace)).setText(String.format("%s", post.getPostLocation()));
        ((TextView) findViewById(R.id.postDuration)).setText(String.format("Approximately: %s hour(s)", post.getPostExpectedTime()));
        wage = post.getPostSalary();
        title = post.getPostTitle();
        postIdBox.setText(postId);
    }

    @Override
    public void onEmployeeClick(String employeeId) {
        // to be done
    }
}
