/*
 * Copyright (c) 2012-2016 Arne Schwabe
 * Distributed under the GNU GPL v2 with additional terms. For full terms see the file doc/LICENSE.txt
 */
package de.blinkt.openvpn.core;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.freeproxy.vpnmaster.hotspot2.R;
import com.freeproxy.vpnmaster.hotspot2.data.api.model.Country;
import com.freeproxy.vpnmaster.hotspot2.data.api.model.Server;

import java.util.Calendar;
import java.util.Random;


public class App extends /*com.orm.SugarApp*/ MultiDexApplication {
    public static final String SHARE_CONNECTION_DATA = "connection_data";
    public static final String SHARE_SELECTED_SERVER = "selected_server";
    public static final String SHARE_SELECTED_COUNTRY = "selected_country";
    public static final String SHARE_CONNECTION_STATUS = "connection_status";
    public static final String SHARE_DAY_OF_YEAR = "SHARE_DAY_OF_WEEK";
    public static final String FREE_TIME_CONNECT_AVAILABLE="FREE_TIME_CONNECT_AVAILABLE";

    public static boolean isStart;
    public static int connection_status = 0;
    public static boolean abortConnection = false;
    public static String device_id;
    public static final String CHANNEL_ID = "com.example.vpn_master";
    public static final int NOTIFICATION_ID = new Random().nextInt(601) + 200;
    public static Server selectedServer = null;
    public static boolean isGetServerFailed = false;
    public static Country selectedCountry = null;
    NotificationManager manager;


    /**
     * set tỷ lệ hiển thị quảng cáo
     * @return
     */
    public static boolean ranIsShowAds() {
        int a = new Random().nextInt(100);
        Log.e("TAG", "ranIsShowAds: a" +a );
        return a < 50;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();


        SharedPreferences sp_settings = getSharedPreferences("settings_data", 0);
        device_id = sp_settings.getString("device_id", "NULL");

        if (device_id.equals("NULL")) {
            device_id = getUniqueKey();
            SharedPreferences.Editor Editor = sp_settings.edit();
            Editor.putString("device_id", device_id);
            Editor.putString("device_created", String.valueOf(System.currentTimeMillis()));
            Editor.apply();

        }

        PRNGFixes.apply();
        StatusListener mStatus = new StatusListener();
        mStatus.init(getApplicationContext());

    }

    private void createNotificationChannel() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel serviceChannel = new NotificationChannel(
                        CHANNEL_ID,
                        "COMBUZZVPN",
                        NotificationManager.IMPORTANCE_LOW
                );

                serviceChannel.setSound(null, null);
                manager = getSystemService(NotificationManager.class);
                manager.createNotificationChannel(serviceChannel);
            }
        } catch (Exception e) {
            //Log.e("error", e.getStackTrace()[0].getMethodName());
        }
    }

    private String getUniqueKey() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);
        int millis = now.get(Calendar.MILLISECOND);
        String Time = getResources().getString(R.string.get_time, year, month, day, hour, minute, second, millis);

        String str_api = String.valueOf(android.os.Build.VERSION.SDK_INT); // API
        String str_model = String.valueOf(Build.MODEL); // Model
        String str_manufacturer = String.valueOf(Build.MANUFACTURER); // Manufacturer
        String version;
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            version = "00";
        }

        Log.e("key", Time + str_manufacturer + str_api + str_model + version);
        return Time + str_manufacturer + str_api + str_model + version;
    }


}
