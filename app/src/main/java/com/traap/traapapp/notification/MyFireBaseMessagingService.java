package com.traap.traapapp.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.activities.main.MainActivity;
import com.traap.traapapp.utilities.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class MyFireBaseMessagingService extends FirebaseMessagingService
{
    private static final String TAG = "--MyFireBaseMsgService--";

    private NotificationUtils notificationUtils;

    @Override
    public void onNewToken(@NonNull String s)
    {
        super.onNewToken(s);
        Prefs.putString("FireBaseToken", s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage == null)
        {
            return;
        }
        Logger.e(TAG, "From: " + remoteMessage.getFrom() + " Message : " + remoteMessage + " Data : " + remoteMessage.getData());

        if (remoteMessage.getNotification() != null)
        {
            Logger.e("-Notification title-", remoteMessage.getNotification().getTitle());
            Logger.e("-Notification body-", remoteMessage.getNotification().getBody());

            Intent pushNotification = new Intent(this, MainActivity.class);
            pushNotification.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent  = PendingIntent.getActivity(this, 1398,
                    pushNotification, PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder notificationBuilder = new
                    NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(1398, notificationBuilder.build());

//            if (!NotificationUtils.isAppIsInBackground(getApplicationContext()))
//            {
//                // app is in foreground, broadcast the push message
//                Intent pushNotification = new Intent(TrapConfig.PUSH_NOTIFICATION);
//                pushNotification.putExtra("message", remoteMessage.getNotification().getBody());
//                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
//
//                // play notification sound
//                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//                notificationUtils.playNotificationSound();
//            }
//            else
//            {
//                // If the app is in background, firebase itself handles the notification
//            }
        }

//        if (remoteMessage.getData() != null)
//        {
//            try
//            {
//                String status = remoteMessage.getData().get("status") != null ? remoteMessage.getData().get("status") : "";
//                if (Objects.requireNonNull(status).equals("new_pm"))
//                {
//                    sendNotification();
//                }
//            } catch (NullPointerException e)
//            {
//
//            }
//
//        }
    }

    private void sendNotification()
    {
        PendingIntent pendingIntent;
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder nb = new NotificationCompat.Builder(this, getString(R.string.app_english_name));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel mChannel = new NotificationChannel(getString(R.string.app_english_name),
                    getString(R.string.app_english_name),
                    NotificationManager.IMPORTANCE_HIGH);

            mChannel.setDescription("this is message");
            mChannel.enableLights(true);
            mChannel.enableVibration(true);

            mNotificationManager.createNotificationChannel(mChannel);

            nb.setChannelId(getString(R.string.app_english_name) + getString(R.string.app_english_name));
            nb.setSmallIcon(R.mipmap.ic_launcher);
            nb.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
            nb.setContentTitle("پیام جدید");
            nb.setContentText("شما یک پیام جدید دارید");
            nb.setAutoCancel(true);
            nb.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000, 1000, 1000});
            nb.setPriority(Notification.PRIORITY_MAX);
            nb.setContentIntent(pendingIntent);
            nb.setChannelId(getString(R.string.app_english_name));

            mNotificationManager.notify(0, nb.build());

        }
    }

    private void handleDataMessage(JSONObject json)
    {
        Logger.e(TAG, "push json: " + json.toString());

        try
        {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");

            Logger.e(TAG, "title: " + title);
            Logger.e(TAG, "message: " + message);
            Logger.e(TAG, "isBackground: " + isBackground);
            Logger.e(TAG, "payload: " + payload.toString());
            Logger.e(TAG, "imageUrl: " + imageUrl);
            Logger.e(TAG, "timestamp: " + timestamp);


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext()))
            {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(TrapConfig.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else
            {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                resultIntent.putExtra("message", message);

                // check for image attachment
                if (TextUtils.isEmpty(imageUrl))
                {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else
                {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
            }
        } catch (JSONException e)
        {
            Logger.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e)
        {
            Logger.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent)
    {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl)
    {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}
