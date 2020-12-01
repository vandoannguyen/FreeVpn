package com.freeproxy.vpnmaster.hotspot2.data.share_pref;

import android.content.Context;

import com.freeproxy.vpnmaster.hotspot2.data.CallBack;
import com.freeproxy.vpnmaster.hotspot2.utils.SharedPrefsUtils;

public class SharePreferHelper implements ISharePreferHelper {
    @Override
    public void getCoin(Context context, CallBack<Integer> callBack) {
        int coin = SharedPrefsUtils.getInstance(context).getInt("coin", -1);
        callBack.onSuccess(coin);
    }

    @Override
    public void setCoin(Context context, int coin, CallBack<Object> callBack) {
        SharedPrefsUtils.getInstance(context).putInt("coin", coin);
    }
}
