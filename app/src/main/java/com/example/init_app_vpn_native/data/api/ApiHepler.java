package com.example.init_app_vpn_native.data.api;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.init_app_vpn_native.data.CallBack;

import org.json.JSONArray;

public class ApiHepler implements IApiHelper {

    @Override
    public void demoCallBack(CallBack<String> callBack) {
        AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAllUsers/{pageNumber}")
                .addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .addHeaders("token", "1234")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        callBack.onSuccess(response.toString());
                    }

                    @Override
                    public void onError(ANError error) {
                        callBack.onFailed(error.toString());
                    }
                });
    }
}
