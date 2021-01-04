package com.freeproxy.vpnmaster.hotspot2;

import com.androidnetworking.AndroidNetworking;
import com.facebook.ads.AudienceNetworkAds;
import com.flurry.android.FlurryAgent;
import com.freeproxy.vpnmaster.hotspot2.ui.AdActivityInterstitialNativeCustom;
import com.freeproxy.vpnmaster.hotspot2.ui.AdInterCustom;

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
        com.unity3d.ads.AudienceNetworkAds.initialize(this, new AdInterCustom(), new AdActivityInterstitialNativeCustom());
    }
}
