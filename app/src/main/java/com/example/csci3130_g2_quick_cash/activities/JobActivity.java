package com.example.csci3130_g2_quick_cash.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.csci3130_g2_quick_cash.R;
import com.example.csci3130_g2_quick_cash.models.Post;
import com.example.csci3130_g2_quick_cash.repository.PostRepository;
import com.example.csci3130_g2_quick_cash.utils.PostValidation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import kotlinx.coroutines.Job;

/**
 * This class allows a user to generate a job posting.
 * @author Arman Singh Sidhu
 */

public class JobActivity extends AppCompatActivity {
    private EditText titleET, descriptionET, dateET, durationET, placeET, salaryET;
    private Button submitButton;
    private CheckBox urgencyCheckBox;
    private Post jobPost;
    private PostRepository postRepository;
    private String titleText = "";
    private String date = "";
    private String duration ;
    private String salary ;
    private boolean urgency = false;
    private static final String PUSH_NOTIFICATION_ENDPOINT = "https://fcm.googleapis.com/fcm/send";
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        this.postRepository = PostRepository.getInstance();
        Intent i = getIntent();
        titleText = i.getStringExtra("Title");
        date = i.getStringExtra("Date");
        duration = i.getStringExtra("Duration");
        salary = i.getStringExtra("Salary");
        urgency = i.getBooleanExtra("Urgent", false);

        init();

        setSubmitButton();
        setBackButton();
        setWelcomeMessage();

    }

    /**
     * Validation of Job Posting
     * - returns an alert if any argument fails.
     * - returns successful registration and alert upon no test cases failing.
     */
    private void setSubmitButton() {
        submitButton.setOnClickListener(view -> {
            TextView statusLabel = findViewById(R.id.jobStatusLabel);
            String errorMessage;
            if (!PostValidation.isValidTitle(getJobTitle())) {
                errorMessage = getResources().getString(R.string.INVALID_TITLE).trim();
            } else if (!PostValidation.isValidDescription(getJobDescription())) {
                errorMessage = getResources().getString(R.string.INVALID_DESCRIPTION).trim();
            } else if (!PostValidation.isValidDate(getJobDate())) {
                errorMessage = getResources().getString(R.string.INVALID_DATE).trim();
            } else if (!PostValidation.isNotPastDate(getJobDate())) {
                errorMessage = getResources().getString(R.string.PAST_DATE).trim();
            } else if (!PostValidation.isValidDuration(getJobDuration())) {
                errorMessage = getResources().getString(R.string.INVALID_DURATION).trim();
            } else if (!PostValidation.isValidPlace(getJobPlace())) {
                errorMessage = getResources().getString(R.string.INVALID_PLACE).trim();
            } else if (!PostValidation.isValidSalary(getJobSalary())) {
                errorMessage = getResources().getString(R.string.INVALID_SALARY).trim();
            } else {
                jobPost = new Post(null, getJobTitle(), getJobDescription(), getJobUrgency(), getJobDate(),
                        getJobSalary(), getJobDuration(), getJobPlace());
                String postId = postRepository.createPost(jobPost);
                errorMessage = getResources().getString(R.string.EMPTY_STRING).trim();
                Toast.makeText(JobActivity.this, "Job submitted successfully",
                        Toast.LENGTH_SHORT).show();
                requestQueue = Volley.newRequestQueue(this);
                FirebaseDatabase.getInstance().getReference("posts").child(postId).get().addOnCompleteListener(task -> {
                    jobPost = task.getResult().getValue(Post.class);
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    if (this.jobPost != null) {
                            sendNotification(jobPost);

                    }
                    // ERR Don't know what to do here
                });

                startActivity(new Intent(JobActivity.this, MyPostingsActivity.class));
            }
            statusLabel.setText(errorMessage.trim());
        });
    }

    /**
     * Back Button -- Redirects to Welcome page (WelcomeActivity.java).
     */
    private void setBackButton() {
        FloatingActionButton backButton = findViewById(R.id.bacToWelcome);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JobActivity.this, PredefinedJobsActivity.class));
            }
        });
    }

    /**
     * Action Bar Welcome Message - set text and styling.
     */
    private void setWelcomeMessage() {
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7C8584")));
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color =#E6E3E4>New Posting</font>"));
    }

    /**
     * Initialization of Views from ID.
     */
    private void init() {
        titleET = findViewById(R.id.jobTitle);
        titleET.setText(titleText);
        descriptionET = findViewById(R.id.description);
        dateET = findViewById(R.id.date);
        dateET.setText(date);
        durationET = findViewById(R.id.duration);
        durationET.setText(duration);
        placeET = findViewById(R.id.place);
        salaryET = findViewById(R.id.salary);
        salaryET.setText(salary);
        urgencyCheckBox = findViewById(R.id.urgencyCheckBox);
        urgencyCheckBox.setChecked(urgency);

        submitButton = findViewById((R.id.submitButton));
    }

    private String getJobTitle() {
        return titleET.getText().toString().trim();
    }

    private String getJobDescription() {
        return descriptionET.getText().toString().trim();
    }

    private String getJobDate() {
        return dateET.getText().toString().trim();
    }

    private String getJobDuration() {
        return durationET.getText().toString().trim();
    }

    private String getJobPlace() {
        return placeET.getText().toString().trim();
    }

    private String getJobSalary() {
        return salaryET.getText().toString().trim();
    }

    private boolean getJobUrgency() {
        return urgencyCheckBox.isChecked();
    }

    private void sendNotification(Post post) {
        try {
            final JSONObject notificationJSONBody = new JSONObject();
            notificationJSONBody.put("title", post.getPostTitle());
            notificationJSONBody.put("body", post.getPostDescription());

            final JSONObject dataJSONBody = new JSONObject();
            dataJSONBody.put("postId", post.getPostId());

            final JSONObject pushNotificationJSONBody = new JSONObject();
            pushNotificationJSONBody.put("to", "/topics/posts");
            pushNotificationJSONBody.put("notification", notificationJSONBody);
            pushNotificationJSONBody.put("data", dataJSONBody);

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                    PUSH_NOTIFICATION_ENDPOINT,
                    pushNotificationJSONBody,
                    response -> Toast.makeText(JobActivity.this,
                            "push noti has been sent" + response.toString(), Toast.LENGTH_SHORT).show(),
                    Throwable::printStackTrace) {
                @Override
                public Map<String, String> getHeaders() {
                    final Map<String, String> headers = new HashMap<>();
                    headers.put("content-type", "application/json");
                    headers.put("authorization",
                            "key=AAAA6HYf0WY:APA91bFcKdcFDLkYA3s-MHRiUxpzu2IIlD_CHQO5FXE0AkrD85Si7xwO8KIteh-0otoLWrTZUkGqUEkR4INXCpIXzevYJZGooDDP0p1hZ5L6s_SHy14TrPa3lMI_DmoIjEDc4Ym-tMJW");
                    return headers;
                }
            };
            requestQueue.add(req);
        }
        catch (Exception e) {
            Toast.makeText(JobActivity.this, "not able to send notification", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
