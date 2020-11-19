package com.example.init_app_vpn_native.ui.load;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.init_app_vpn_native.base.BasePresenter;
import com.example.init_app_vpn_native.common.Config;
import com.example.init_app_vpn_native.data.AppDataHelper;
import com.example.init_app_vpn_native.data.CallBack;
import com.example.init_app_vpn_native.data.api.model.Country;
import com.example.init_app_vpn_native.data.api.model.Server;
import com.example.init_app_vpn_native.ui.main.MainActivity;
import com.example.init_app_vpn_native.utils.SharedPrefsUtils;
import com.example.init_app_vpn_native.utils.ads.Ads;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class LoadDataPresenter<V extends ILoadDataView> extends BasePresenter<V> implements ILoadPresenter<V> {
    private static final String TAG = "LoadDataPresenter";
    Activity context;
    boolean isLoadCountrySuccess = false,
            isLoadDataFastConnectSuccess = false,
            isGetCoinSuccess = false,
            isLoadAdsSuccess = false;
    String checkAppData = "";


    public LoadDataPresenter(Activity context) {
        this.context = context;
    }

    @Override
    public void login() {
        view.setStatus("Login app ...");
        String data = SharedPrefsUtils.getInstance(context).getString("login");
//        Log.e(TAG, "login: " + data);
        if (data.isEmpty()) {
            String mail = getRanDomText() + "@mail.com";
            String pass = getRanDomText();
            SharedPrefsUtils.getInstance(context).putString("login", mail + "-" + pass);
            AppDataHelper.getInstance().postCreateAcc(mail, pass, new CallBack<String>() {
                @Override
                public void onSuccess(String data) {
                    super.onSuccess(data);
                    AppDataHelper.getInstance().postLogin(mail, pass, new CallBack<String>() {
                        @Override
                        public void onSuccess(String data) {
                            super.onSuccess(data);
                            Config.tokenApp = data;
                            getData();
                        }
                    });
                }
            });
        } else {
            String mail = data.split("-")[0];
            String pass = data.split("-")[1];
            Log.e(TAG, "login: " + mail + " " + pass);
            AppDataHelper.getInstance().postLogin(mail, pass, new CallBack<String>() {
                @Override
                public void onSuccess(String data) {
                    super.onSuccess(data);
                    Config.tokenApp = data;
                    getData();
                }
            });
        }
    }

    private void getData() {
        getFastConnect();
        getCountryList();
    }

    public void getFastConnect() {
        view.setStatus("Getting fast connect");
        AppDataHelper.getInstance().getFastConnect(Config.tokenApp, new CallBack<Server>() {
            @Override
            public void onSuccess(Server data) {
                super.onSuccess(data);
                Config.currentServer = data;
                Log.e(TAG, "onSuccess: getFastConnect");
                isLoadDataFastConnectSuccess = true;
                intentToMain();
            }

            @Override
            public void onFailed(String mess) {
                super.onFailed(mess);
            }
        });
    }

    private void intentToMain() {
        if (isGetCoinSuccess && isLoadAdsSuccess && isLoadCountrySuccess && isLoadDataFastConnectSuccess) {
            context.startActivity(new Intent(context, MainActivity.class));
        }
    }

    public void getCountryList() {
        AppDataHelper.getInstance().getCountry(Config.tokenApp, new CallBack<List<Country>>() {
            @Override
            public void onSuccess(List<Country> data) {
                Log.e(TAG, "onSuccess: getCountryList");
                super.onSuccess(data);
                Config.listCountry = data;
                isLoadCountrySuccess = true;
                intentToMain();
            }

            @Override
            public void onFailed(String mess) {
                super.onFailed(mess);
                view.showMessage("Load country failed");
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        context.finish();
                    }
                }, 3000);
            }
        });
    }

    public void getCoin() {
        AppDataHelper.getInstance().getCoin(context, new CallBack<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                super.onSuccess(data);
                if (data == -1) {
                    Config.coin = 100;
                } else {
                    Config.coin = data;
                }
                isGetCoinSuccess = true;
                intentToMain();

            }
        });
    }

    public void getAds() {
        AppDataHelper.getInstance().getAdsConfig(new CallBack<JSONObject>() {
            @Override
            public void onSuccess(JSONObject s) {
                super.onSuccess(s);
                try {
                    String banner = "";
                    String interstitial = "";
                    String nativeAds = "";
                    String bannerFan = "";
                    String interstitialFan = "";
                    String nativeAdsFan = "";
                    boolean isShowInter = false;
                    boolean isShowBanner = false;
                    boolean isLoadFailed = false;
                    boolean isShowNative = false;
                    boolean isAdmob = false;
                    int percents = 0;
                    int free_time_connect = 15;

                    if (s != null) {
                        JSONObject adObject = s.getJSONObject("configs");
                        boolean isConstanceAdmob = adObject.getBoolean("isConstanceAdmob");
                        String admobData = SharedPrefsUtils.getInstance(context).getString("constAdmob");
                        if (isConstanceAdmob && !admobData.isEmpty()) {
                            AdsMordel model = AdsMordel.fromJson(admobData);
                            banner = model.banner;
                            interstitial = model.inter;
                            nativeAds = model.nativeAds;
                            Log.e(TAG, "onPostExecute: get constanceAdmob");
                        } else {
                            JSONArray arr = adObject.getJSONArray("list");
                            ArrayList<AdsMordel> models = new ArrayList<>();
                            for (int i = 0; i < arr.length(); i++) {
                                models.add(AdsMordel.fromJson(arr.getString(i)));

                            }
                            Collections.sort(models, new Comparator<AdsMordel>() {
                                @Override
                                public int compare(AdsMordel mordel, AdsMordel t1) {
                                    return (t1.percent - mordel.percent);
                                }
                            });
                            int ran = new Random().nextInt(100);
                            AdsMordel model;
                            if (ran < models.get(0).percent) {
                                model = models.get(0);
                                banner = model.banner;
                                interstitial = model.inter;
                                nativeAds = model.nativeAds;
                            } else {
                                model = models.get(new Random(models.size() - 1).nextInt() + 1);
                                banner = model.banner;
                                interstitial = model.inter;
                                nativeAds = model.nativeAds;
                            }
                            if (isConstanceAdmob) {
                                SharedPrefsUtils.getInstance(context).putString("constAdmob", AdsMordel.toJson(model));
                            }
                        }
                        percents = adObject.getInt("percents_inter");
                        bannerFan = adObject.getString("ad_banner_fan_id");
                        interstitialFan = adObject.getString("ad_inter_fan_id");
                        nativeAdsFan = adObject.getString("ad_native_fan_id");
                        isShowInter = adObject.getBoolean("is_show_inter");
                        isShowBanner = adObject.getBoolean("is_show_banner");
                        isShowNative = adObject.getBoolean("is_show_native");
                        isLoadFailed = adObject.getBoolean("is_load_failed");
                        isAdmob = adObject.getBoolean("isAdmob");
                        checkAppData = adObject.getString("checkApp");
                    }
                    Ads.is_show_inter = isShowInter;
                    Ads.is_show_banner = isShowBanner;
                    Ads.is_load_failed = isLoadFailed;
                    Ads.percents = percents;
                    if (!Config.isDebug) {
                        Ads.is_show_admob = isAdmob;
                        Ads.banner_id_admob = banner;
                        Ads.inter_id_admob = interstitial;
                        Ads.native_id_admob = nativeAds;
                        Ads.banner_id_fan = bannerFan;
                        Ads.inter_id_fan = interstitialFan;
                        Ads.native_id_fan = nativeAdsFan;
                    }
                    isLoadAdsSuccess = true;
                    intentToMain();
                } catch (
                        Exception e) {
                    Log.e("TAG", "onPostExecute: " + e);
                    isLoadAdsSuccess = false;
                    Toast.makeText(context, "Load data failed!", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            context.finish();

                        }
                    }, 3000);
                    e.printStackTrace();
                }
            }
        });
    }

    static class AdsMordel {
        public AdsMordel(String banner, String inter, String nativeAds, int percent) {
            this.banner = banner;
            this.inter = inter;
            this.nativeAds = nativeAds;
            this.percent = percent;
        }

        String banner, inter, nativeAds;
        int percent;

        public static String toJson(AdsMordel mordel) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("ad_banner_id", mordel.banner);
                jsonObject.put("ad_inter_id", mordel.inter);
                jsonObject.put("ad_native_id", mordel.nativeAds);
                jsonObject.put("percents", mordel.percent);
                return jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        public static AdsMordel fromJson(String json) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                return new AdsMordel(jsonObject.getString("ad_banner_id"),
                        jsonObject.getString("ad_inter_id"),
                        jsonObject.getString("ad_native_id"),
                        jsonObject.getInt("percents"));
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

        }
    }

    private String getRanDomText() {
        String text = "";
        for (int i = 0; i < 16; i++) {
            text += new Random().nextInt(9);
        }
        return text;
    }

}
