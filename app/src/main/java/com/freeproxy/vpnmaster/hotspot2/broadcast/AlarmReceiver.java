package com.freeproxy.vpnmaster.hotspot2.broadcast;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.freeproxy.vpnmaster.hotspot2.R;
import com.freeproxy.vpnmaster.hotspot2.ui.load.LoadData;

import java.util.Calendar;

import de.blinkt.openvpn.core.App;


public class AlarmReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "CHANNEL_ID" ;
    NotificationManager manager;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("TAGGGG", "onReceive: " + intent.getAction());
//        if (intent.getAction().equals("ALARM")) {
//            createNotificationChannel(context);
//            updateNotification(context, "Hey " + context.getString(R.string.app_name_title) + " miss you!");
//        }
//        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
//            /* Setting the alarm here */
//            Intent alarmIntent = new Intent(context, AlarmReceiver.class);
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(System.currentTimeMillis());
//            calendar.set(Calendar.HOUR_OF_DAY, 20);
//            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//            manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
////            Toast.makeText(context, "Alarm Set", Toast.LENGTH_SHORT).show();
//        }
    }

    private void updateNotification(Context context, String Description) {
        Notification notification = getMyActivityNotification(context, Description);
        manager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(App.NOTIFICATION_ID, notification);
    }
    private void createNotificationChannel(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel serviceChannel = new NotificationChannel(
                        CHANNEL_ID,
                        "COMBUZZVPN",
                        NotificationManager.IMPORTANCE_LOW
                );
                serviceChannel.setShowBadge(true);
                serviceChannel.setSound(null, null);
                manager = context.getSystemService(NotificationManager.class);
                manager.createNotificationChannel(serviceChannel);
            }
            else manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        } catch (Exception e) {

        }
    }
    private Notification getMyActivityNotification(Context context, String Description) {
        Intent notificationIntent = new Intent(context, LoadData.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, notificationIntent, 0);
        int int_temp = R.mipmap.ic_launcher;
        String Title = "Tap to open VPN";
        return new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(Title)
                .setContentText(Description)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), int_temp))
                .setSmallIcon(int_temp)
                .setContentIntent(pendingIntent)
                .build();

    }
}
