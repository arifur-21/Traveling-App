package com.example.tourproject11111;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;

import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        String transitionType = "";

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER)
        {
            transitionType = "Entered";
        }
        else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT)
        {
            transitionType = "Exited";
        }

        List<String> names = new ArrayList<>();
        List<Geofence> tiggeringGeofences = geofencingEvent.getTriggeringGeofences();

        for (Geofence geofence : tiggeringGeofences)
        {
            names.add(geofence.getRequestId());
        }
        String nameString = TextUtils.join(",",names);

        sendNotificationToUser(context, transitionType, nameString);
    }

    private void sendNotificationToUser(Context context, String transitionType, String nameString) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel01");
            builder.setSmallIcon(R.drawable.ic_baseline_notifications_active_24);
            builder.setContentTitle("TourMate");
            builder.setContentText(transitionType+" "+nameString);
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String name = "Geofence Channel";
            String description = "This channel sends notification regarding geofencing event tirggering";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel01", name, importance);
            notificationManager.createNotificationChannel(channel);
            channel.setDescription(description);
        }
        notificationManager.notify(111, builder.build());

    }
}