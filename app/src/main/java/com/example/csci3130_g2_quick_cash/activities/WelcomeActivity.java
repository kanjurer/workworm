package com.example.csci3130_g2_quick_cash.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.csci3130_g2_quick_cash.RecyclerView.PostRecyclerViewInterface;
import com.example.csci3130_g2_quick_cash.repository.UserRepository;

import com.example.csci3130_g2_quick_cash.R;
import com.example.csci3130_g2_quick_cash.adapters.PostAdapter;
import com.example.csci3130_g2_quick_cash.repository.PostRepository;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Objects;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * This class is the introduction to WorkWorm immediately after sign in.
 *
 * @author Brijesh Bharatb Varsani, Dev Gaurangkuma Sukhadia, Adam Sarty
 */

public class WelcomeActivity extends AppCompatActivity implements PostRecyclerViewInterface{

    DrawerLayout navigatingMenu;
    private PostRepository postRepository;
    private PostAdapter postAdapter;
    private RecyclerView recyclerView;
    private UserRepository userRepository;
    private FusedLocationProviderClient fusedLocationClient;
    private final int LOCATION_PERMISSION_REQUEST = 1111;
    private final int ENABLE_GPS_REQUEST = 1112;
    private Location currLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        init();

        checkLocationPermissions();

        setNavigatingMenu();

        setWelcomeMessage();

        populatePosts();
    }

    private void init() {
        this.userRepository = UserRepository.getInstance();
        this.postRepository = PostRepository.getInstance();

        this.postAdapter = new PostAdapter(this);
        this.postAdapter.setPostRecyclerViewInterface(this);

        this.recyclerView = findViewById(R.id.recycler_view);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(postAdapter);

        Button preferredPosts = findViewById(R.id.preferredListings);
        preferredPosts.setOnClickListener(v -> openPreferredPosts());

        FloatingActionButton nearPosting = findViewById(R.id.refresh);
        nearPosting.setOnClickListener(v -> populatePosts());

        FirebaseMessaging.getInstance().subscribeToTopic("posts");
    }

    private void populatePosts() {
        if (currLocation == null) {
            postRepository.getAllPosts(postAdapter);
        }
        else {
            postRepository.getPostsInMyCity(postAdapter, getApplicationContext(), currLocation);
        }
        setWelcomeMessage();
    }

    /**
     * Sets Welcome Message dependant on Time on Device
     */
    public void setWelcomeMessage() {
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7C8584")));
        userRepository.getDatabaseReference().child(userRepository.getUID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("fullName").getValue(String.class);
                assert name != null;
                name = name.split(" ")[0];
                int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                if (hour >= 17 || hour < 5) {
                    Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color =#E6E3E4>Good evening, " + name + ". &#127770</font>"));
                } else if (hour >= 12) {
                    Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color =#E6E3E4>Good afternoon, " + name  + ". &#127774</font>"));
                } else {
                    Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color =#E6E3E4>Good morning, " + name + ". &#127781</font>"));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Lint Debug
            }
        });
    }

    /**
     * Initialization of Navigating Menu
     */
    public void setNavigatingMenu() {
        // Accessing the menu button through its id
        navigatingMenu = findViewById(R.id.navigatingMenu);

        // Accessing my postings to proceed to payment
        Button myPostings = findViewById(R.id.mypostings);
        myPostings.setOnClickListener(v -> openMyPostings());

        // Accessing profile
        Button profile = findViewById(R.id.profile);
        profile.setOnClickListener(v -> openProfile());

        // Accessing logout button and calling sign out method to sign out the user
        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(v -> logOut());

        // Accessing back button to close drawer
        Button back = findViewById(R.id.back);
        back.setOnClickListener(v -> back());

        // Opens preferred posts
        Button preferredPosts = findViewById(R.id.preferredListings);
        preferredPosts.setOnClickListener(v -> openPreferredPosts());

        // Open search posts
        FloatingActionButton searchPosts = findViewById(R.id.searchPostsBTN);
        searchPosts.setOnClickListener(v -> openSearchPosts());
    }

    /**
     * Perform Profile Activity
     */
    public void openProfile() {
        startActivity(new Intent(WelcomeActivity.this, ProfileActivity.class));
    }

    /**
     * Perform My Postings Activity
     */
    public void openMyPostings() {
        startActivity(new Intent(WelcomeActivity.this, MyPostingsActivity.class));
    }

    /**
     * Perform New Posting Activity
     */
    public void openAddPosting() {
        startActivity(new Intent(WelcomeActivity.this, PredefinedJobsActivity.class));
    }

    /**
     * Perform Close Navigation Bar
     */
    public void back() {
        navigatingMenu.close();
    }

    /**
     * Perform User Sign Out Functionality
     */
    public void logOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        // finish all activity once logged out cant go back in by back button
        finish();
    }

    /**
     * Activate navigating menu by calling openDrawer method
     *
     * @param view Menu ID
     */
    public void openMenu(View view) {
        openDrawer(navigatingMenu);
    }

    /**
     * Initialize each drawer compartment
     *
     * @param navigatingMenu Menu ID
     */
    private void openDrawer(DrawerLayout navigatingMenu) {
        navigatingMenu.openDrawer(GravityCompat.START);
    }

    /**
     * Perform Preferred Posts Activity
     */
    private void openPreferredPosts() {
        startActivity(new Intent(WelcomeActivity.this, PreferredPostingsActivity.class));
    }

    /**
     * Perform Search Posts Activity
     */
    private void openSearchPosts() {
        startActivity(new Intent(WelcomeActivity.this, PreferenceActivity.class));
    }

    /**
     * This method defines behaviour for when a post is clicked
     * @param postId the position of post in post list
     */
    @Override
    public void onPostClick(String postId) {
        Intent intent = new Intent(WelcomeActivity.this, PostActivity.class);
        intent.putExtra("postId", postId);
        startActivity(intent);
    }

    /**
     * This method checks if the precise location permissions are granted and asks for them if they
     * are not
     */
    private void checkLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            if (isGPSEnabled()) {
                // Permission granted and GPS enabled, get the Location
                getLocation();
            }
            else {
                // Permission granted, now turn on GPS as it is not enabled
                turnOnGPS();
            }
        }
        else {
            // Permissions not available, ask for the permissions
            requestPermissions(new String[] { Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST);
        }
    }

    /**
     * This method gets the current location of the user's phone.
     * NOTE: We will only reach on this method if the permissions are granted and GPS is enabled.
     */
    @SuppressLint("MissingPermission")
    public void getLocation() {
        // Precise location access granted and GPS enabled, now get user location.
        fusedLocationClient = LocationServices.
                getFusedLocationProviderClient(getApplicationContext());

        // Get the latest location
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnCompleteListener(locationTask -> {
                    if (locationTask.isSuccessful()) {
                        if (locationTask.getResult() == null) {
                            getLocation();
                        }
                        else {
                            this.currLocation = locationTask.getResult();
                            populatePosts();
                        }
                    }
                    else {
                        // Not able to get location, populate all posts
                        populatePosts();
                    }
                });
    }

    /**
     * This method processes the result of the permissions asked
     * @param requestCode the code of the request
     * @param permissions the permissions which were asked
     * @param grantResults the granted result
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        Toast.makeText(this, "Permission result arrived", Toast.LENGTH_SHORT).show();
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted. Continue the action or workflow
                if (isGPSEnabled()) {
                    getLocation();
                } else {
                    turnOnGPS();
                }
            } else {
                Toast.makeText(this, "GPS permission not granted", Toast.LENGTH_SHORT).show();
                populatePosts();
            }
        }
    }

    /**
     * This method turns on the GPS on device
     */
    public void turnOnGPS() {
        LocationRequest.Builder locationRequestBuilder = new LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY, 1000);
        LocationRequest locationRequest = locationRequestBuilder.build();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices
                .getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());
        result.addOnCompleteListener(task -> {
            try {
                task.getResult(ApiException.class);
                if (task.isSuccessful()) {
                    getLocation();
                }
                else {
                    Toast.makeText(this, "Enable to get Location, please refresh", Toast.LENGTH_SHORT).show();
                }
            } catch (ApiException e) {

                switch (e.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                        try {
                            ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                            resolvableApiException.startResolutionForResult(
                                    WelcomeActivity.this, ENABLE_GPS_REQUEST);
                            resolvableApiException.getResolution();
                        } catch (IntentSender.SendIntentException ex) {
                            ex.printStackTrace();
                        }
                        break;

                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        //Device does not have location feature
                        Toast.makeText(this, "Your device does not support GPS", Toast.LENGTH_SHORT).show();
                        populatePosts();
                        break;
                }
            }
        });
    }

    /**
     * This method will check if GPS is enabled on device
     * @return true if the GPS was enabled, false otherwise
     */
    public boolean isGPSEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    /**
     * This method checks what was the result of the GPS Enable prompt
     * @param requestCode the code of the request
     * @param resultCode the code of the result
     * @param data the data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ENABLE_GPS_REQUEST){
            if(resultCode==RESULT_OK){
                // GPS Enabled, now get the location
                getLocation();
            }
            else if(resultCode==RESULT_CANCELED){
                Toast.makeText(this, "GPS not turned on",
                        Toast.LENGTH_SHORT).show();
                // in case user back press or refuses to open gps, show all posts
                populatePosts();
            }
        }
    }

}