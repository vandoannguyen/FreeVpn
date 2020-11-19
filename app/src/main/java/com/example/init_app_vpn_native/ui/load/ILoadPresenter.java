package com.example.init_app_vpn_native.ui.load;

import com.example.init_app_vpn_native.base.MVPPresenter;

public interface ILoadPresenter<V extends ILoadDataView> extends MVPPresenter<V> {
    void login();

    void getAds();

    void getCoin();
}
