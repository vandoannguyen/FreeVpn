package com.freeproxy.vpnmaster.hotspot2.base;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

import com.freeproxy.vpnmaster.hotspot2.common.Config;

public abstract class BasePresenter<V extends MVPView> implements MVPPresenter<V> {
    Boolean isActivityAlive = false;
    public V view;

    @Override
    public void onAttact(V view) {
        isActivityAlive = true;
        this.view = view;
    }

    @Override
    public void onDetact() {
        isActivityAlive = false;
    }

    @Override
    public boolean isAttacted() {
        return isActivityAlive;
    }

    public boolean hasInternetConnection(Context activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
