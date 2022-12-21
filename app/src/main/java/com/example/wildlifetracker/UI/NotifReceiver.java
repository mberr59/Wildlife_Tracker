package com.example.wildlifetracker.UI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.wildlifetracker.R;

public class NotifReceiver extends BroadcastReceiver {
    String channelID = "notification";
    static int notificationID;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, intent.getStringExtra("key"), Toast.LENGTH_LONG).show();
        createNotificationChannel(context, channelID);
        Notification n = new NotificationCompat.Builder(context, channelID)
                .setContentTitle("Wildlife Out of Range")
                .setContentText(intent.getStringExtra("key"))
                .setSmallIcon(R.drawable.ic_baseline_track_changes_24).build();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notificationID++, n);
    }

    private void createNotificationChannel(Context context, String channelID) {
        CharSequence name = "Wildlife Tracker Notification";
        String description = "This is the notification channel for the Wildlife Tracker app";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(channelID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
