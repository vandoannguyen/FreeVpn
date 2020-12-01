package com.freeproxy.vpnmaster.hotspot2.ui.load;

import com.freeproxy.vpnmaster.hotspot2.base.MVPPresenter;

public interface ILoadPresenter<V extends ILoadDataView> extends MVPPresenter<V> {
    void login();

    void getAds();

    void getCoin();

    void getQuickLaunch();

    void intentMain();
}
