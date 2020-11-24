package com.example.init_app_vpn_native.ui.main;

import android.app.Activity;
import android.content.Context;

import com.example.init_app_vpn_native.base.BasePresenter;
import com.example.init_app_vpn_native.data.AppDataHelper;
import com.example.init_app_vpn_native.data.CallBack;
import com.example.init_app_vpn_native.utils.Common;

public class MainPresenter<V extends IMainActivity> extends BasePresenter<V> implements IMainPresenter<V> {
    Activity activity;

    public MainPresenter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void getExample() {
        view.showMessage("message");
    }

    @Override
    public void getPoint(Context mainActivity) {
        AppDataHelper.getInstance().getCoin(mainActivity, new CallBack<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                super.onSuccess(data);
                Common.totalPoint = data;
                view.setPointMain(data);
            }
        });
    }
}
