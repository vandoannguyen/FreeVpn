package com.example.init_app_vpn_native.ui.main.fragment.vpn;

import android.content.Intent;

import com.example.init_app_vpn_native.base.MVPPresenter;

public interface IVpnPresenter<V extends IVpnView> extends MVPPresenter<V> {
    void onResume();

    void onPause();

    void pressLineConnect();

    void pressLineCountry();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void getSelectedServer();

    boolean isEnableConnectButton();
}
