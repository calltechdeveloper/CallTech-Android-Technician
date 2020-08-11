package com.calltechservice.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.calltechservice.R;
import com.calltechservice.db.UserPref;
import com.calltechservice.ui.activity.HomeActivity;
import com.calltechservice.ui.activity.LoginActivity;
import com.calltechservice.utils.Constants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONObject;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    UserPref userPref;
    private Intent intent;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        userPref = new UserPref(this);

        if (remoteMessage.getData().size() > 0) {

            Gson gson = new Gson();
            JSONObject jsonObject = new JSONObject(remoteMessage.getData());
            FirebasePushResponse firebasePushResponse = gson.fromJson(jsonObject.toString(), FirebasePushResponse.class);

            // sendNotification(firebasePushResponse);
            addNotification(firebasePushResponse);
        } else {

            Gson gson = new Gson();
            JSONObject jsonObject = new JSONObject(remoteMessage.getData());
            FirebasePushResponse firebasePushResponse = gson.fromJson(jsonObject.toString(), FirebasePushResponse.class);
            firebasePushResponse.title = remoteMessage.getNotification().getTitle();/*getString(R.string.app_name);*/
            firebasePushResponse.description = remoteMessage.getNotification().getBody();

            Log.e("title1","title"+firebasePushResponse.title+"description :"+firebasePushResponse.description+remoteMessage.getNotification());
            // sendNotification(firebasePushResponse);
            addNotification(firebasePushResponse);
        }
    }


    public void addNotification(FirebasePushResponse firebasePushResponse) {
        String CHANNEL_ID = "my_channel_01";
        CharSequence name = "my_channel";
        String Description = "This is my channel";
        int NOTIFICATION_ID = 234;
       /* Random random = new Random();
       int  NOTIFICATION_ID = random.nextInt(9999 - 1000) + 1000;*/
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;
           NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(true);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(mChannel);
            }

        }
        if (userPref != null) {

                if (userPref.isLogin()) {
                    intent = new Intent(this, HomeActivity.class);
                    intent.putExtra(Constants.IS_NOTIFICATION, true);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    intent.putExtra(Constants.IS_NOTIFICATION, true);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                }

            }

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addNextIntent(intent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);
            Log.e("title","title"+firebasePushResponse.title+"description :"+firebasePushResponse.description+firebasePushResponse);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.logo)
                    .setContentTitle((firebasePushResponse.title))
                    .setContentText(firebasePushResponse.description)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(firebasePushResponse.description))
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setGroup("testing")
                    .setContentIntent(resultPendingIntent);


            if (notificationManager != null) {

                notificationManager.notify(NOTIFICATION_ID, builder.build());
            }


        }
    }
