package com.example.samplestickerapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.support.constraint.Constraints.TAG;

public class SportsNotificationService extends FirebaseMessagingService {
    public SportsNotificationService() {
    }
    @Override
    public void onNewToken(String token) {
            Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(token);
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        sendNotification(remoteMessage.getNotification().getBody());
    }

    private void sendNotification(String message){
        Intent intent = new Intent(this,EntryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        Uri defaultUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        NotificationCompat.Builder notifcationBuilder = new NotificationCompat.Builder(this,"M_CH_ID");
        notifcationBuilder.setSmallIcon(R.drawable.ic_notify);
        notifcationBuilder.setContentTitle("Sports Stickers");
        notifcationBuilder.setContentText(message);
        notifcationBuilder.setAutoCancel(true);
        notifcationBuilder.setSound(defaultUri);
        notifcationBuilder.setContentIntent(pendingIntent);

        NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notifManager.notify(0,notifcationBuilder.build());
    }
}
