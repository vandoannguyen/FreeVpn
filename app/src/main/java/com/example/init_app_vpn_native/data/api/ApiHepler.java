package com.example.init_app_vpn_native.data.api;

import android.util.Base64;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.init_app_vpn_native.common.Constance;
import com.example.init_app_vpn_native.data.CallBack;
import com.example.init_app_vpn_native.data.api.model.Country;
import com.example.init_app_vpn_native.data.api.model.Server;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ApiHepler implements IApiHelper {

    @Override
    public void demoCallBack(String token, CallBack<String> callBack) {
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

    public void getFastConnect(String token, CallBack<Server> callBack) {
        AndroidNetworking.get(Constance.ROOT_API + "/client/fastconnect/1000")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            String data = response.getJSONObject(0).getString("data");
                            Server sv = new Gson().fromJson(data, Server.class);
                            callBack.onSuccess(sv);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callBack.onFailed(e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        callBack.onFailed(error.toString());
                    }
                });
    }

    @Override
    public void getConnect(String token, String countryCode, CallBack<Server> callBack) {
        AndroidNetworking.get(Constance.ROOT_API + "/client/connect/" + countryCode)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            String data = response.getJSONObject(0).getString("data");
                            Server sv = new Gson().fromJson(data, Server.class);
                            callBack.onSuccess(sv);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callBack.onFailed(e.getMessage());
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        callBack.onFailed(error.toString());
                    }
                });
    }

    @Override
    public void getCountry(String token, CallBack<List<Country>> callBack) {
        AndroidNetworking.get(Constance.ROOT_API + "/countries")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            String data = response.getJSONObject(0).getString("data");
                            List<Country> countries = new Gson().fromJson(data, new TypeToken<List<Country>>() {
                            }.getType());
                            callBack.onSuccess(countries);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callBack.onFailed(e.getMessage());
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        callBack.onFailed(error.toString());
                    }
                });
    }

    @Override
    public void getListVpn(String token, CallBack<List<Server>> callBack) {
        AndroidNetworking.get(Constance.ROOT_API + "/client")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            String data = response.getJSONObject(0).getString("data");
                            List<Server> servers = new Gson().fromJson(data, new TypeToken<List<Country>>() {
                            }.getType());
                            callBack.onSuccess(servers);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callBack.onFailed(e.getMessage());
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        callBack.onFailed(error.toString());
                    }
                });
    }

    @Override
    public void postConnect(String token, String status, CallBack<String> callBack) {

    }

    @Override
    public void postLogin(String username, String password, CallBack<String> callbackToken) {
        String loginInfo = username + ":" + password;
        byte[] encodingByte = Base64.encode(loginInfo.getBytes(), Base64.DEFAULT);
        String encoding = new String(encodingByte);
        AndroidNetworking.post(Constance.ROOT_API +
                "/auth?access_token=9t52lPBfVAvkiVggbGItnWubl2UcWupz")
                .addHeaders("Authorization", "Basic " + encoding).build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject data = response.getJSONObject(0).getJSONObject("data");
                            String token = data.getString("token");
                            callbackToken.onSuccess(token);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callbackToken.onFailed(e.getMessage());
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        callbackToken.onFailed(error.toString());
                    }
                });
    }

    @Override
    public void postCreateAcc(String username, String password, CallBack<String> callbackToken) {
        AndroidNetworking.post(Constance.ROOT_API +
                "/users?access_token=9t52lPBfVAvkiVggbGItnWubl2UcWupz")
                .addUrlEncodeFormBodyParameter("email", username)
                .addUrlEncodeFormBodyParameter("password", password)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject data = response.getJSONObject(0).getJSONObject("data");
                            String token = data.getString("token");
                            callbackToken.onSuccess(token);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callbackToken.onFailed(e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        callbackToken.onFailed(error.toString());
                    }
                });
    }
}
