package com.freeproxy.vpnmaster.hotspot2.ui.switchRegion;

import android.app.Activity;
import android.util.Log;

import com.freeproxy.vpnmaster.hotspot2.base.BasePresenter;
import com.freeproxy.vpnmaster.hotspot2.common.Config;
import com.freeproxy.vpnmaster.hotspot2.data.AppDataHelper;
import com.freeproxy.vpnmaster.hotspot2.data.CallBack;
import com.freeproxy.vpnmaster.hotspot2.data.api.model.Country;

import java.util.List;

public class SwitchVPresenter<V extends ISwitchRegionView> extends BasePresenter<V> implements ISwitchPresenter<V> {
    Activity activity;

    public SwitchVPresenter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void getCountry() {
        view.showDialogLoading(true);
        AppDataHelper.getInstance().getCountry(Config.tokenApp, new CallBack<List<Country>>() {
            @Override
            public void onSuccess(List<Country> data) {
                super.onSuccess(data);
                Config.listCountry = data;
                view.updateListCountry(Config.listCountry);
                view.showDialogLoading(false);
                Log.e("TAGG", "onSuccess: " + "" );
            }

            @Override
            public void onFailed(String mess) {
                view.showDialogLoading(false);
                Log.e("TAGGGG", "onFailed: " + mess);
                super.onFailed(mess);

            }
        });
    }

}
