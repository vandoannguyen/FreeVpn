package com.example.init_app_vpn_native.ui.main;

import android.content.Context;

import com.example.init_app_vpn_native.base.MVPPresenter;

public interface IMainPresenter<V extends IMainActivity> extends MVPPresenter<V> {
    void getExample();

    void getPoint(Context mainActivity);
}
