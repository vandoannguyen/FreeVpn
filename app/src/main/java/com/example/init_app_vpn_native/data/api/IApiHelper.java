package com.example.init_app_vpn_native.data.api;

import com.example.init_app_vpn_native.data.CallBack;
import com.example.init_app_vpn_native.data.api.model.Country;
import com.example.init_app_vpn_native.data.api.model.Server;

import org.json.JSONObject;

import java.util.List;

public interface IApiHelper {
    void getAdsConfig(CallBack<JSONObject> callBack);

    void getFastConnect(String token,CallBack<Server> callBack);

    void getConnect(String token,String countryCode, CallBack<Server> callBack);

    void getCountry(String token,CallBack<List<Country>> callBack);

    void getListVpn(String token,CallBack<List<Server>> callBack);

    void postConnect(String token,String status, CallBack<String> callBack);

    void postLogin(String username, String password, CallBack<String> callbackToken);

    void postCreateAcc(String username, String password, CallBack<String> callbackToken);
}
