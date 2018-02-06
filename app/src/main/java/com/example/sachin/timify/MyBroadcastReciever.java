package com.example.sachin.timify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by SACHIN on 10/9/2017.
 */

public class MyBroadcastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent arg1) {
        showNotification(context);
    }

    private void showNotification(Context context) {
        Log.i("notification", "visible");

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,new Intent(context, View_Schedule.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.applogo)
                        .setContentTitle("Timify")
                        .setContentText("Your schedule for today is ready");
        mBuilder.setContentIntent(contentIntent);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }
}
