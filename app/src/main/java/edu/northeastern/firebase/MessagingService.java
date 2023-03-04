package edu.northeastern.firebase;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Objects;

import edu.northeastern.atyourservice.R;
import edu.northeastern.firebase.entity.User;

/**
 * The class for messaging service.
 *
 * @author Lin Han
 */
public class MessagingService extends FirebaseMessagingService {

    private static final String CHANNEL_ID = "group_18_stick_it_to_em";
    private static final String CHANNEL_NAME = "group_18_stick_it_to_em";
    private static final String CHANNEL_DESCRIPTION = "group_19_stick_it_to_em";

    /**
     * The send notification method called when remoteMessage is not null.
     *
     * @param remoteMessage the remote message received in database
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getFrom() != null && remoteMessage.getData().size() > 0) {
            RemoteMessage.Notification notification = remoteMessage.getNotification();
            sendNotification(notification);
        }
    }

    /**
     * Send notification method.
     *
     * @param remoteMessageNotification remote message notification
     */
    private void sendNotification(RemoteMessage.Notification remoteMessageNotification) {
        Intent intent = new Intent(this, SendStickersActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_IMMUTABLE);

        Notification notification;
        NotificationCompat.Builder builder;
        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setDescription(CHANNEL_DESCRIPTION);
        notificationManager.createNotificationChannel(notificationChannel);
        builder = new NotificationCompat.Builder(this, CHANNEL_ID);

        System.out.println("notification title: " + remoteMessageNotification.getTitle());
        System.out.println("notification body: " + remoteMessageNotification.getBody());

        String sticker_id = remoteMessageNotification.getBody();
        String image_id = "R.id." + sticker_id;
        Bitmap bm = BitmapFactory.decodeResource(getResources(), Integer.parseInt(image_id));
        notification = builder.setSmallIcon(R.drawable.ic_stat_notification)
                .setContentTitle(remoteMessageNotification.getTitle())
                .setContentText("This is a sticker.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setLargeIcon(bm)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bm)
                        .bigLargeIcon(null))
                .build();
        notificationManager.notify(0, notification);
    }

}