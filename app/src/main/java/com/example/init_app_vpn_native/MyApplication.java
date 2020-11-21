package com.example.init_app_vpn_native;

import com.androidnetworking.AndroidNetworking;
import com.example.init_app_vpn_native.utils.ads.Ads;
import de.blinkt.openvpn.core.App;

public class MyApplication extends App {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
    }
}
