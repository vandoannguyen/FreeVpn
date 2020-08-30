package com.example.init_app_vpn_native.ui.main;

import android.app.Activity;
import android.widget.Toast;

import com.example.init_app_vpn_native.base.BasePresenter;
import com.example.init_app_vpn_native.utils.Ads;

public class MainPresenter<V extends IMainActivity> extends BasePresenter<V> implements IMainPresenter<V> {
    Activity activity;

    public MainPresenter(Activity activity) {
        this.activity = activity;
        Ads.getInstance(activity).inter(new Ads.CallBackAds() {
        });
    }

    @Override
    public void getExample() {
        view.showMessage("message");
        if (isAttacted()) {
            Toast.makeText(activity, "on attacted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity, "on detact", Toast.LENGTH_SHORT).show();
        }
    }
}
