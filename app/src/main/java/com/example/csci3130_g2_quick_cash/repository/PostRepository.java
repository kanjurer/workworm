package com.example.csci3130_g2_quick_cash.repository;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.csci3130_g2_quick_cash.adapters.PostAdapter;
import com.example.csci3130_g2_quick_cash.models.Post;
import com.example.csci3130_g2_quick_cash.models.Preference;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class PostRepository {
    private final FirebaseDatabase db;
    private final FirebaseAuth auth;
    private static PostRepository instance;

    private PostRepository() {
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public static PostRepository getInstance() {
        if (instance == null) {
            instance = new PostRepository();
        }

        return instance;
    }

    private DatabaseReference getDatabaseReference() {
        return db.getReference("posts");
    }

    private String postIdGenerator() {
        return auth.getUid() + System.currentTimeMillis();
    }

    public String createPost(Post post) {
        if (post == null) return null;
        if (auth.getCurrentUser() == null) return null;
        post.setPostCreatedBy(auth.getUid());
        if (!post.getPostCreatedBy().equals(auth.getCurrentUser().getUid())) return null;


        String postID = postIdGenerator();
        post.setPostId(postID);
        getDatabaseReference().child(postID).setValue(post);
        return postID;
    }

    public void deletePost(Post post) {
        if (post == null) return;
        if (auth.getCurrentUser() == null) return;
        if (!post.getPostCreatedBy().equals(auth.getCurrentUser().getUid())) return;

        getDatabaseReference().child(post.getPostId()).removeValue();
    }

    public void editPost(Post post) {
        if (post == null) return;
        if (auth.getCurrentUser() == null) return;
        if (!post.getPostCreatedBy().equals(auth.getCurrentUser().getUid())) return;

        getDatabaseReference().child(post.getPostId()).setValue(post);
    }

    public void completePost(String postId, String uid) {
        if (postId == null || postId.equals("")) return;
        if (uid == null || uid.equals("")) return;

        getDatabaseReference().child(postId).child("completed").setValue(true);
        getDatabaseReference().child(postId).child("employeeUserId").setValue(uid);

    }

    public Task<DataSnapshot> getPost(String postId) {
        if (postId == null || postId.equals("")) return null;

        return getDatabaseReference().child(postId).get();
    }

    public void getAllPosts(PostAdapter adapter) {
        getDatabaseReference().get().addOnCompleteListener((task -> {
            if (task.isSuccessful()) {
                List<Post> list = new ArrayList<>();

                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    list.add(dataSnapshot.getValue(Post.class));
                }

                adapter.addToList(list);
            } else {
                // do nothing
                System.err.println("Idk what to put here");
            }
        }));
    }

    public void getPreferredPosts(PostAdapter adapter, Preference preference) {
        if (adapter == null) return;
        if (preference == null) return;

        getDatabaseReference().get().addOnCompleteListener((task -> {
            if (task.isSuccessful()) {
                List<Post> list = new ArrayList<>();

                Post p;
                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    if (dataSnapshot != null) {
                        p = dataSnapshot.getValue(Post.class);
                        if (preference.preferenceMatch(p)) {
                            list.add(p);
                        }
                    }
                }

                adapter.addToList(list);
            } else {
                // do nothing
                System.err.println("Idk what to put here");
            }

        }));
    }

    public void getUserPreferredPosts(PostAdapter adapter) {
        if (adapter == null) return;
        if (auth.getCurrentUser() == null) return;
        if (auth.getUid() == null) return;


        db.getReference("users").child(auth.getUid()).child("preference").get().addOnCompleteListener(t -> {
            if (t.isSuccessful()) {
                Preference preference = t.getResult().getValue(Preference.class);

                if (preference == null) return;

                getDatabaseReference().get().addOnCompleteListener((task -> {
                    if (task.isSuccessful()) {
                        List<Post> list = new ArrayList<>();

                        Post p;
                        for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                            if (dataSnapshot != null) {
                                p = dataSnapshot.getValue(Post.class);
                                if (preference.preferenceMatch(p)) {
                                    list.add(p);
                                }
                            }
                        }

                        adapter.addToList(list);
                    } else {
                        // do nothing
                        System.err.println("Idk what to put here");
                    }

                }));
            }
        });


    }

    public void getUserPosts(PostAdapter adapter) {
        if (adapter == null) return;
        if (auth.getCurrentUser() == null) {
            return;
        }
        getDatabaseReference().get().addOnCompleteListener((task -> {
            if (task.isSuccessful()) {
                List<Post> list = new ArrayList<>();

                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    if (dataSnapshot != null &&
                            Objects.requireNonNull(dataSnapshot.getKey()).contains(Objects.requireNonNull(auth.getUid()))) {
                        list.add(dataSnapshot.getValue(Post.class));

                    }
                }

                adapter.addToList(list);
            } else {
                // do nothing
                System.err.println("Idk what to put here");
            }
        }));
    }

    public void getUserJobHistory(PostAdapter adapter, Context context) {
        if (adapter == null) return;
        if (auth.getCurrentUser() == null) return;

        getDatabaseReference().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Post> list = new ArrayList<>();

                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    if (dataSnapshot != null) {
                        Post p = dataSnapshot.getValue(Post.class);

                        if (p != null && Boolean.TRUE.equals(dataSnapshot.child("completed").getValue(Boolean.class))
                                && Objects.equals(dataSnapshot.child("employeeUserId").getValue(String.class), auth.getUid())) {
                            list.add(p);
                            Toast.makeText(context, p.isCompleted() + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                adapter.addToList(list);
            } else {
                // do nothing
                System.err.println("Idk what to put here");
            }
        });

    }

    public void getPostsInMyCity(PostAdapter adapter, Context context, android.location.Location myLocation) {
        getDatabaseReference().get().addOnCompleteListener((task -> {
            if (task.isSuccessful()) {
                List<Post> list = new ArrayList<>();
                // Default city will be halifax cause in whatsoever situation,
                // the app should not crash.
                final String[] userCity = {"Halifax"};

                // Getting user city name from it's location
                Geocoder gcd = new Geocoder(context, Locale.getDefault());
                if (myLocation != null) {
                    gcd.getFromLocation(myLocation.getLatitude(), myLocation.getLongitude(),
                            1, new Geocoder.GeocodeListener() {
                                @Override
                                public void onGeocode(@NonNull List<Address> list) {
                                    if (list.size() == 1) {
                                        userCity[0] = list.get(0).getLocality();
                                    }
                                }

                                @Override
                                public void onError(@Nullable String errorMessage) {
                                    Geocoder.GeocodeListener.super.onError(errorMessage);
                                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            });

                }

                // Got the location, not get posts in that city
                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    Post post = dataSnapshot.getValue(Post.class);
                    if (post != null && post.getPostLocation().split(",")[0].equals(userCity[0]) && !post.isCompleted()) {
                        list.add(dataSnapshot.getValue(Post.class));
                    }
                }

                // Update the recycler view for it
                Toast.makeText(context, userCity[0], Toast.LENGTH_SHORT).show();
                adapter.addToList(list);
            } else {
                Toast.makeText(context, "Cannot connect to Database", Toast.LENGTH_SHORT).show();
            }
        }));
    }
}