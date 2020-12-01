package com.freeproxy.vpnmaster.hotspot2.ui.main;

import android.app.Activity;
import android.content.Context;

import com.freeproxy.vpnmaster.hotspot2.base.BasePresenter;
import com.freeproxy.vpnmaster.hotspot2.data.AppDataHelper;
import com.freeproxy.vpnmaster.hotspot2.data.CallBack;
import com.freeproxy.vpnmaster.hotspot2.utils.Common;

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
                if (data == -1)
                    Common.totalPoint = 100;
                view.setPointMain(Common.totalPoint);
            }
        });
    }
}
