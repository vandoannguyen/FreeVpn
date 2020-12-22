package com.freeproxy.vpnmaster.hotspot2.ui.main.fragment.vpn;

import android.content.Intent;

import com.freeproxy.vpnmaster.hotspot2.base.MVPPresenter;

public interface IVpnPresenter<V extends IVpnView> extends MVPPresenter<V> {
    void onResume();

    void onPause();

    void pressLineConnect();

    void pressLineCountry();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void getSelectedServer();

    boolean isEnableConnectButton();

    void intentToApp(String s);

    void pressDisConnect();

    void initAds();
}
