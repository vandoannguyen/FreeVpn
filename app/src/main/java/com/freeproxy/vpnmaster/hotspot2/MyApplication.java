package com.freeproxy.vpnmaster.hotspot2;

import com.androidnetworking.AndroidNetworking;
import com.facebook.ads.AudienceNetworkAds;
import com.flurry.android.FlurryAgent;
import com.ss.android.UgcTrillNetwork;

import de.blinkt.openvpn.core.App;

public class MyApplication extends App {
    @Override
    public void onCreate() {
        super.onCreate();
        AudienceNetworkAds.initialize(this);
        AndroidNetworking.initialize(getApplicationContext());
        new FlurryAgent.Builder()
                .withLogEnabled(true)
                .build(this, "BKKKB23HSXBHCC7MF8V7");
        UgcTrillNetwork.init(this, "5fd09e73207a8a088c378210");
    }
}
