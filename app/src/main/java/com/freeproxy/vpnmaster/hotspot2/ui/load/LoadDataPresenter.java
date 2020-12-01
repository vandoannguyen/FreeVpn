package com.freeproxy.vpnmaster.hotspot2.ui.load;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.freeproxy.vpnmaster.hotspot2.BuildConfig;
import com.freeproxy.vpnmaster.hotspot2.base.BasePresenter;
import com.freeproxy.vpnmaster.hotspot2.common.Config;
import com.freeproxy.vpnmaster.hotspot2.data.AppDataHelper;
import com.freeproxy.vpnmaster.hotspot2.data.CallBack;
import com.freeproxy.vpnmaster.hotspot2.data.api.model.Auth;
import com.freeproxy.vpnmaster.hotspot2.data.api.model.Country;
import com.freeproxy.vpnmaster.hotspot2.data.api.model.Server;
import com.freeproxy.vpnmaster.hotspot2.data.api.model.User;
import com.freeproxy.vpnmaster.hotspot2.ui.dialog.DialogCallBack;
import com.freeproxy.vpnmaster.hotspot2.ui.dialog.DialogRequireRemove;
import com.freeproxy.vpnmaster.hotspot2.ui.dialog.DialogUpdate;
import com.freeproxy.vpnmaster.hotspot2.ui.main.MainActivity;
import com.freeproxy.vpnmaster.hotspot2.utils.Common;
import com.freeproxy.vpnmaster.hotspot2.utils.SharedPrefsUtils;
import com.freeproxy.vpnmaster.hotspot2.utils.ads.Ads;
import com.google.gson.Gson;

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
    Activity activity;
    boolean isLoadCountrySuccess = false,
            isLoadDataFastConnectSuccess = false,
            isGetCoinSuccess = false,
            isLoadAdsSuccess = false;
    String checkAppData = "";


    public LoadDataPresenter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void login() {
        view.setStatus("Login app ...");
        String data = SharedPrefsUtils.getInstance(activity).getString("login");
//        Log.e(TAG, "login: " + data);
        if (data.isEmpty()) {
            String mail = getRanDomText() + "@mail.com";
            String pass = getRanDomText();
            SharedPrefsUtils.getInstance(activity).putString("login", mail + "-" + pass);
            AppDataHelper.getInstance().postCreateAcc(mail, pass, new CallBack<String>() {
                @Override
                public void onSuccess(String data) {
                    super.onSuccess(data);
                    AppDataHelper.getInstance().postLogin(mail, pass, new CallBack<String>() {
                        @Override
                        public void onSuccess(String data) {
                            super.onSuccess(data);
                            try {
                                JSONObject dt = new JSONObject(data);
                                Config.tokenApp = dt.getString("token");
                                Config.user = new Gson().fromJson(dt.getString("user"), User.class);
                                getData();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(activity, "Login failed !!!", Toast.LENGTH_SHORT).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        activity.finish();
                                    }
                                }, 3000);
                            }

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
                    try {
                        JSONObject dt = new JSONObject(data);
                        Config.tokenApp = dt.getString("token");
                        Config.user = new Gson().fromJson(dt.getString("user"), User.class);
                        getData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "Login failed !!!", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                activity.finish();
                            }
                        }, 3000);
                    }

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
//        isLoadDataFastConnectSuccess = true;
//        intentToMain();
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

    private boolean checkAppIsContain(String require_remove) {
        PackageManager pm = activity.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(require_remove, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    private void intentToMain() {
        Log.e(TAG, "intentToMain: isGetCoinSuccess" + isGetCoinSuccess);
        Log.e(TAG, "intentToMain: isLoadAdsSuccess" + isLoadAdsSuccess);
        Log.e(TAG, "intentToMain: isLoadCountrySuccess" + isLoadCountrySuccess);
        Log.e(TAG, "intentToMain: isLoadDataFastConnectSuccess" + isLoadDataFastConnectSuccess);
        Log.e(TAG, "intentToMain: =================");
        if (isGetCoinSuccess && isLoadAdsSuccess && isLoadCountrySuccess && isLoadDataFastConnectSuccess) {
            Config.isDataLoaded = true;
            if (checkAppData.isEmpty()) {
                intentMain();
            } else {
                try {
                    JSONObject object = new JSONObject(checkAppData);
                    String next_app = object.getString("next_app");
                    String require_remove = object.getString("require_remove");
                    boolean isAlive = object.getBoolean("isAlive");
                    int versionCode = object.getInt("version_code");
                    if (isAlive && !require_remove.isEmpty()) {
//                    String packageName = require_remove;
                        if (checkAppIsContain(require_remove)) {
                            DialogRequireRemove requireRemove = new DialogRequireRemove(activity, require_remove, new DialogCallBack() {
                                @Override
                                public void onClickOk() {
                                    super.onClickOk();
                                    activity.finish();
                                }
                            });
                            requireRemove.show();
                        } else {
                            intentMain();
                        }
                        return;
                    }
                    if ((!isAlive && !next_app.isEmpty()) || (versionCode > BuildConfig.VERSION_CODE && isAlive)) {
                        DialogUpdate update = new DialogUpdate(activity, new DialogCallBack() {
                            @Override
                            public void onClickOk() {
                                super.onClickOk();
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + next_app));
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                activity.startActivity(intent);
                                activity.finish();
                            }

                            @Override
                            public void onClickCancel() {
                                super.onClickCancel();
                                activity.finish();
                            }
                        });
                        update.show();
                        return;
                    }
                    intentMain();
                } catch (JSONException e) {
                    e.printStackTrace();
                    intentMain();
                }
            }
        }
    }

    public void intentMain() {
        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();
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
                        activity.finish();
                    }
                }, 3000);
            }
        });
    }

    public void getCoin() {
        AppDataHelper.getInstance().getCoin(activity, new CallBack<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                super.onSuccess(data);
                if (data == -1) {
                    Common.totalPoint = 100;
                } else {
                    Common.totalPoint = data;
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
                    String rewar_id_admob = "";
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
                        String admobData = SharedPrefsUtils.getInstance(activity).getString("constAdmob");
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
                                rewar_id_admob = model.rewared;
                            } else {
                                model = models.get(new Random(models.size() - 1).nextInt() + 1);
                                banner = model.banner;
                                interstitial = model.inter;
                                nativeAds = model.nativeAds;
                                rewar_id_admob = model.rewared;
                            }
                            if (isConstanceAdmob) {
                                SharedPrefsUtils.getInstance(activity).putString("constAdmob", AdsMordel.toJson(model));
                            }
                        }
                        percents = adObject.getInt("percents_inter");
                        bannerFan = adObject.getString("ad_banner_fan_id");
                        interstitialFan = adObject.getString("ad_inter_fan_id");
                        nativeAdsFan = adObject.getString("ad_native_fan_id");
                        isShowInter = adObject.getBoolean("is_show_inter");
                        isShowBanner = adObject.getBoolean("is_show_banner");
                        isShowNative = adObject.getBoolean("is_show_native");
                        isLoadFailed = adObject.getBoolean("is_load_fail");
                        isAdmob = adObject.getBoolean("isAdmob");
                        checkAppData = adObject.getString("checkApp");
                    }
                    Ads.is_show_inter = isShowInter;
                    Ads.is_show_banner = isShowBanner;
                    Ads.is_load_failed = isLoadFailed;
                    Ads.is_show_admob = isAdmob;
                    Ads.percents = percents;
                    if (!com.freeproxy.vpnmaster.hotspot2.utils.Config.isDebug) {
                        Ads.rewar_id_admob = rewar_id_admob;
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
                    Toast.makeText(activity, "Load data failed!", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            activity.finish();
                        }
                    }, 3000);
                    e.printStackTrace();
                }
            }
        });
    }

    static class AdsMordel {
        private String rewared;

        public AdsMordel(String banner, String inter, String nativeAds, String rewared, int percent) {
            this.banner = banner;
            this.inter = inter;
            this.nativeAds = nativeAds;
            this.percent = percent;
            this.rewared = rewared;
        }

        String banner, inter, nativeAds;
        int percent;

        public static String toJson(AdsMordel mordel) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("ad_banner_id", mordel.banner);
                jsonObject.put("ad_inter_id", mordel.inter);
                jsonObject.put("ad_native_id", mordel.nativeAds);
                jsonObject.put("rewar_id_admob", mordel.rewared);
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
                        jsonObject.getString("rewar_id_admob"),
                        jsonObject.getInt("percents"));
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

        }
    }

    @Override
    public void getQuickLaunch() {
    }

    private String getRanDomText() {
        String text = "";
        for (int i = 0; i < 16; i++) {
            text += new Random().nextInt(9);
        }
        return text;
    }

}
