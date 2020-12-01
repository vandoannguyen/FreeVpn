package com.freeproxy.vpnmaster.hotspot2;

import com.androidnetworking.AndroidNetworking;
import com.flurry.android.FlurryAgent;

import de.blinkt.openvpn.core.App;

public class MyApplication extends App {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
        new FlurryAgent.Builder()
                .withLogEnabled(true)
                .build(this, "BKKKB23HSXBHCC7MF8V7");
    }
}