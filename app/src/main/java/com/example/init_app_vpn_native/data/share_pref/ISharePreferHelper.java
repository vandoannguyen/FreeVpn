package com.example.init_app_vpn_native.data.share_pref;

import android.content.Context;

import com.example.init_app_vpn_native.data.CallBack;

public interface ISharePreferHelper {
    void getCoin(Context context, CallBack<Integer> callBack);

    void setCoin(Context context,int coin, CallBack<Object> callBack);
}
