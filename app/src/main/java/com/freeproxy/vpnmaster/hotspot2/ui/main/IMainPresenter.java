package com.freeproxy.vpnmaster.hotspot2.ui.main;

import android.content.Context;

import com.freeproxy.vpnmaster.hotspot2.base.MVPPresenter;

public interface IMainPresenter<V extends IMainActivity> extends MVPPresenter<V> {
    void getExample();

    void getPoint(Context mainActivity);
}
