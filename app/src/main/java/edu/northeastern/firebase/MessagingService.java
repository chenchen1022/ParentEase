package edu.northeastern.firebase;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import edu.northeastern.atyourservice.R;

/**
 * The class for messaging service.
 * <p>
 * Citation: Course Module: Week 8 - Firebase Cloud Messaging & Firebase Realtime Database - Notifications Video
 * https://northeastern.instructure.com/courses/136736/pages/notifications?module_item_id=8369837
 *
 * @author Lin Han
 */
public class MessagingService extends FirebaseMessagingService {

    private static final String ID = "group_18_stick_it_to_em";
    private static final String NAME = "group_18_stick_it_to_em";
    private static final String DESCRIPTION = "group_18_stick_it_to_em";

    /**
     * The onCreate method called when the activity is starting.
     */
    @SuppressLint("HardwareIds")
    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * The myClassifier method called when remoteMessage is not null.
     *
     * @param remoteMessage the remote message received in database
     */
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        myClassifier(remoteMessage);
    }

    /**
     * The send notification method is called when the remote message is not null.
     *
     * @param remoteMessage the remote message
     */
    private void myClassifier(RemoteMessage remoteMessage) {
        if (remoteMessage.getFrom() != null && remoteMessage.getData().size() > 0) {
            RemoteMessage.Notification notification = remoteMessage.getNotification();
            sendNotification(notification);
        }
    }

    /**
     * Sends notification method.
     *
     * @param remoteMessageNotification remote message notification
     */
    private void sendNotification(RemoteMessage.Notification remoteMessageNotification) {
        Intent intent = new Intent(this, SendStickersActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Notification notification;
        NotificationCompat.Builder builder;
        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        NotificationChannel notificationChannel = new NotificationChannel(ID, NAME, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setDescription(DESCRIPTION);
        notificationManager.createNotificationChannel(notificationChannel);
        builder = new NotificationCompat.Builder(this, ID);

        String stickerDes = remoteMessageNotification.getBody().toString().substring(13);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), getSticker(stickerDes));
        notification = builder.setSmallIcon(R.drawable.ic_stat_notification)
                .setContentTitle(remoteMessageNotification.getTitle())
                .setContentText("You received a " + stickerDes + " sticker.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap)
                        .bigLargeIcon(null))
                .build();
        notificationManager.notify(0, notification);
    }

    /**
     * Gets sticker id.
     *
     * @param stickerDes the description of the sticker
     * @return an int which is the sticker id
     */
    private int getSticker(String stickerDes) {
        int output = 0;
        switch (stickerDes) {
            case "CLEAR":
                output = R.drawable.clear;
                break;
            case "BOLT":
                output = R.drawable.bolt;
                break;
            case "CLOUDS":
                output = R.drawable.clouds;
                break;
            case "DRIZZLE":
                output = R.drawable.drizzle;
                break;
            case "RAIN":
                output = R.drawable.rain;
                break;
            case "RAINBOW":
                output = R.drawable.rainbow;
                break;
            case "SMOG":
                output = R.drawable.smog;
                break;
            case "SNOW":
                output = R.drawable.snow;
                break;
        }
        return output;
    }
}

