package com.example.init_app_vpn_native.data;

import android.content.Context;

import com.example.init_app_vpn_native.data.api.ApiHepler;
import com.example.init_app_vpn_native.data.api.IApiHelper;
import com.example.init_app_vpn_native.data.api.model.Country;
import com.example.init_app_vpn_native.data.api.model.Server;
import com.example.init_app_vpn_native.data.share_pref.ISharePreferHelper;
import com.example.init_app_vpn_native.data.share_pref.SharePreferHelper;

import org.json.JSONObject;

import java.util.List;

public class AppDataHelper implements IAppDataHelper {
    private static AppDataHelper instance;
    private IApiHelper apiHepler;
    private ISharePreferHelper sharePreferHelper;

    public static AppDataHelper getInstance() {
        return instance != null ? instance : (instance = new AppDataHelper());
    }

    private AppDataHelper() {
        apiHepler = new ApiHepler();
        sharePreferHelper = new SharePreferHelper();
    }


    @Override
    public void getAdsConfig(CallBack<JSONObject> callBack) {
        apiHepler.getAdsConfig(callBack);
    }

    @Override
    public void getFastConnect(String token, CallBack<Server> callBack) {
        apiHepler.getFastConnect(token, callBack);
    }

    @Override
    public void getConnect(String token, String countryCode, CallBack<Server> callBack) {
        apiHepler.getConnect(token, countryCode, callBack);
    }

    @Override
    public void getCountry(String token, CallBack<List<Country>> callBack) {
        apiHepler.getCountry(token, callBack);
    }

    @Override
    public void getListVpn(String token, CallBack<List<Server>> callBack) {
        apiHepler.getListVpn(token, callBack);
    }

    @Override
    public void postConnect(String token, String status, CallBack<String> callBack) {
        apiHepler.postConnect(token, status, callBack);
    }

    @Override
    public void postLogin(String username, String password, CallBack<String> callbackToken) {
        apiHepler.postLogin(username, password, callbackToken);
    }

    @Override
    public void postCreateAcc(String username, String password, CallBack<String> callbackToken) {
        apiHepler.postCreateAcc(username, password, callbackToken);
    }

    @Override
    public void getCoin(Context context, CallBack<Integer> callBack) {
        sharePreferHelper.getCoin(context, callBack);
    }

    @Override
    public void setCoin(Context context, int coin, CallBack<Object> callBack) {
        sharePreferHelper.setCoin(context, coin, callBack);
    }
}
