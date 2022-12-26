package com.example.csci3130_g2_quick_cash.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.csci3130_g2_quick_cash.activities.PostActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        if (message.getNotification() == null) {
            return;
        }

        final String title = message.getNotification().getTitle();
        final String body = message.getNotification().getBody();

        final Map<String, String> data = message.getData();
        final String postId = data.get("postId");

        Intent intent = new Intent(this, PostActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("body", body);
        intent.putExtra("postId", postId);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 10, intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, "POSTS").
                        setContentTitle(title)
                        .setContentText(body)
                        .setSmallIcon(com.google.firebase.messaging.R.drawable.notification_icon_background);

        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        int id = (int)System.currentTimeMillis();

        NotificationChannel channel = new NotificationChannel("POSTS", "POSTS",
                NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);

        notificationManager.notify(id, notificationBuilder.build());
    }
}
