package com.freeproxy.vpnmaster.hotspot2.data.share_pref;

import android.content.Context;

import com.freeproxy.vpnmaster.hotspot2.data.CallBack;

public interface ISharePreferHelper {
    void getCoin(Context context, CallBack<Integer> callBack);

    void setCoin(Context context,int coin, CallBack<Object> callBack);
}
