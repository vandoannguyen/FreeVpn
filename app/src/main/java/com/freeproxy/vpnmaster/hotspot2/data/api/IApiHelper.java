package com.freeproxy.vpnmaster.hotspot2.data.api;

import com.freeproxy.vpnmaster.hotspot2.data.CallBack;
import com.freeproxy.vpnmaster.hotspot2.data.api.model.Country;
import com.freeproxy.vpnmaster.hotspot2.data.api.model.Server;

import org.json.JSONObject;

import java.util.List;

public interface IApiHelper {
    void getAdsConfig(CallBack<JSONObject> callBack);

    void getFastConnect(String token,int Coin, CallBack<Server> callBack);

    void getConnect(String token, String countryCode, CallBack<Server> callBack);

    void getCountry(String token, CallBack<List<Country>> callBack);

    void getListVpn(String token, CallBack<List<Server>> callBack);

    void postConnect(String token, String status, CallBack<String> callBack);

    void postLogin(String username, String password, CallBack<String> callbackToken);

    void postCreateAcc(String username, String password, CallBack<String> callbackToken);

    void postStatus(String token, String status, String id, CallBack<String> callBack);
}
