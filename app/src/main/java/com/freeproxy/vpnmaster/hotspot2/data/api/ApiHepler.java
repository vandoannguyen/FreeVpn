package com.freeproxy.vpnmaster.hotspot2.data.api;

import android.util.Base64;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.freeproxy.vpnmaster.hotspot2.common.Config;
import com.freeproxy.vpnmaster.hotspot2.common.Constance;
import com.freeproxy.vpnmaster.hotspot2.data.CallBack;
import com.freeproxy.vpnmaster.hotspot2.data.api.model.Country;
import com.freeproxy.vpnmaster.hotspot2.data.api.model.Server;
import com.freeproxy.vpnmaster.hotspot2.utils.InShotAppUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class ApiHepler implements IApiHelper {

    private static final String TAG = "ApiHepler";

    @Override
    public void getAdsConfig(CallBack<JSONObject> callBack) {
        AndroidNetworking.get("https://api.oneadx.com/configs/5fb3e1a6d1c04c042b151378")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callBack.onSuccess(response);
                        Log.e(TAG, "onResponse: " + response.toString());
                    }

                    @Override
                    public void onError(ANError error) {
                        callBack.onFailed(error.toString());
                        Log.e(TAG, "onError: " + error.getMessage());
                    }
                });
    }

    public void getFastConnect(String token, CallBack<Server> callBack) {
        Log.e(TAG, "getFastConnect: ");
        AndroidNetworking.get(Constance.ROOT_API + "/client/fastconnect/100000")
                .setPriority(Priority.LOW)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String data = response.getString("data");
                            try {
                                String dt = InShotAppUtils.decrypt(Config.user.getId(), data);
                                Server sv = new Gson().fromJson(dt, Server.class);
                                callBack.onSuccess(sv);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e(TAG, "onResponse:getFastConnect decrift " + e);
                                callBack.onFailed(e.getMessage());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "onResponse: getFastConnect" + e.getMessage());
                            callBack.onFailed(e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.e(TAG, "onError: " + error.getMessage());
                        callBack.onFailed(error.toString());
                    }
                });
    }

    @Override
    public void getConnect(String token, String countryCode, CallBack<Server> callBack) {
        AndroidNetworking.get(Constance.ROOT_API + "/client/connect/" + countryCode)
                .setPriority(Priority.LOW)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getInt("code") == 1) {
                                String data = response.getString("data");
                                String dt = InShotAppUtils.decrypt(Config.user.getId(), data);
                                Server sv = new Gson().fromJson(dt, Server.class);
                                callBack.onSuccess(sv);
                            } else {
                                callBack.onFailed("load failed connect");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "getConnect" + e.getMessage() + " " + e.getMessage());
                            callBack.onFailed(e.getMessage());
                        } catch (Exception e) {
                            Log.e(TAG, "getConnect" + e.getMessage());
                            callBack.onFailed(e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.e(TAG, "onError: 11111" + error.getErrorCode() + error.getMessage());
                        callBack.onFailed(error.toString());
                    }
                });
    }

    @Override
    public void getCountry(String token, CallBack<List<Country>> callBack) {
        Log.e(TAG, "getCountry: " + token);
        AndroidNetworking.get(Constance.ROOT_API + "/countries")
                .setPriority(Priority.LOW)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String data = response.getString("data");
                            String dt = InShotAppUtils.decrypt(Config.user.getId(), data);
                            List<Country> countries = new Gson().fromJson(dt, new TypeToken<List<Country>>() {
                            }.getType());
                            callBack.onSuccess(countries);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "onResponse: getCountry" + e.getMessage());
                            callBack.onFailed(e.getMessage());
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse: getCountry" + e.getMessage());
                            callBack.onFailed(e.getMessage());
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Log.e(TAG, "onError:  " + error.getMessage());
                        callBack.onFailed(error.toString());
                    }
                });
    }

    @Override
    public void getListVpn(String token, CallBack<List<Server>> callBack) {
        Log.e(TAG, "getListVpn: ");
        AndroidNetworking.get(Constance.ROOT_API + "/client")
                .setPriority(Priority.LOW)
                .addHeaders("Authorization", "Bearer " + token)
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
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status", status);
            AndroidNetworking.post(Constance.ROOT_API + "/client/connectstatus/" + Config.currentServer.getId())
                    .addHeaders("Authorization", "Bearer " + Config.tokenApp)
                    .addJSONObjectBody(jsonObject)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                        }

                        @Override
                        public void onError(ANError anError) {

                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postLogin(String username, String password, CallBack<String> callbackToken) {
        Log.e(TAG, "postLogin: " + username + " " + password);
        String loginInfo = username + ":" + password;
        try {
            byte[] data = loginInfo.getBytes("UTF-8");
            String base64 = Base64.encodeToString(data, Base64.NO_WRAP);
            Log.e(TAG, "postLogin: " + base64);
            AndroidNetworking.post(Constance.ROOT_API +
                    "/auth?access_token=9t52lPBfVAvkiVggbGItnWubl2UcWupz")
                    .addHeaders("authorization", "Basic " + base64)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                if (response.getInt("code") == 1) {
                                    JSONObject data = response.getJSONObject("data");
                                    String token = data.getString("token");
                                    Log.e(TAG, "onResponse: " + token);
                                    callbackToken.onSuccess(data.toString());
                                } else callbackToken.onFailed("load failed login");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e(TAG, "onResponse: " + e.getMessage());
                                callbackToken.onFailed(e.getMessage());
                            }

                        }

                        @Override
                        public void onError(ANError error) {
                            Log.e(TAG, "onError: " + error.getErrorDetail());
                            Log.e(TAG, "onError: " + error.getErrorBody());
                            Log.e(TAG, "onError: " + error.getErrorCode());
                            Log.e(TAG, "onError: " + error.getResponse());
                            callbackToken.onFailed(error.toString());
                        }
                    });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postCreateAcc(String username, String password, CallBack<String> callbackToken) {
        AndroidNetworking.post(Constance.ROOT_API +
                "/users?access_token=9t52lPBfVAvkiVggbGItnWubl2UcWupz")
                .addUrlEncodeFormBodyParameter("email", username)
                .addUrlEncodeFormBodyParameter("password", password)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e(TAG, "onResponse: " + response.toString());
                            JSONObject data = response.getJSONObject("data");
//                            String token = data.getString("token");
                            callbackToken.onSuccess(data.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "onResponse: " + e.getMessage());
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
    public void postStatus(String token, String status, String id, CallBack<String> callBack) {
        JSONObject js = new JSONObject();
        try {
            js.put("status", status);
            AndroidNetworking.post(Constance.ROOT_API + "/client/connectstatus/" + id)
                    .addHeaders("Authorization", "Bearer " + Config.tokenApp)
                    .addJSONObjectBody(js)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                        }

                        @Override
                        public void onError(ANError anError) {

                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
