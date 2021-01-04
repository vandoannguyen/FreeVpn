package com.freeproxy.vpnmaster.hotspot2.ui.load;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.freeproxy.vpnmaster.hotspot2.BuildConfig;
import com.freeproxy.vpnmaster.hotspot2.base.BasePresenter;
import com.freeproxy.vpnmaster.hotspot2.common.Config;
import com.freeproxy.vpnmaster.hotspot2.data.AppDataHelper;
import com.freeproxy.vpnmaster.hotspot2.data.CallBack;
import com.freeproxy.vpnmaster.hotspot2.data.api.model.Country;
import com.freeproxy.vpnmaster.hotspot2.data.api.model.Server;
import com.freeproxy.vpnmaster.hotspot2.data.api.model.User;
import com.freeproxy.vpnmaster.hotspot2.ui.AdInterstitialNativeCustom;
import com.freeproxy.vpnmaster.hotspot2.ui.dialog.DialogCallBack;
import com.freeproxy.vpnmaster.hotspot2.ui.dialog.DialogRequireRemove;
import com.freeproxy.vpnmaster.hotspot2.ui.dialog.DialogUpdate;
import com.freeproxy.vpnmaster.hotspot2.ui.main.MainActivity;
import com.freeproxy.vpnmaster.hotspot2.utils.Common;
import com.freeproxy.vpnmaster.hotspot2.utils.SharedPrefsUtils;
import com.google.gson.Gson;
import com.oneadx.android.oneads.AdInterstitial;
import com.oneadx.android.oneads.AdListener;
import com.oneadx.android.oneads.OneAds;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class LoadDataPresenter<V extends ILoadDataView> extends BasePresenter<V> implements ILoadPresenter<V> {
    private static final String TAG = "LoadDataPresenter";
    AppCompatActivity activity;
    boolean isLoadCountrySuccess = false,
            isLoadDataFastConnectSuccess = false,
            isGetCoinSuccess = false;
    String checkAppData = "";
    private boolean isInitAdsSuccess = false;

    public LoadDataPresenter(AppCompatActivity activity) {
        this.activity = activity;
    }

    AdInterstitial adInterstitial;

    @Override
    public void initAds() {
        OneAds.init(activity, new AdInterstitialNativeCustom(), new OneAds.OneAdsListener() {
            @Override
            public void onSuccess() {
                adInterstitial = new AdInterstitial(activity);
                adInterstitial.showSplashAd(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        isInitAdsSuccess = true;
                        intentToMain();
                    }
                });
            }

            @Override
            public void onError() {
                isInitAdsSuccess = true;
                intentToMain();
            }
        });
    }

    @Override
    public void login() {
        view.setStatus("Login app ...");
        String data = SharedPrefsUtils.getInstance(activity).getString("login");
//        Log.e(TAG, "login: " + data);
        long now = Calendar.getInstance().getTimeInMillis();
        String tokenData = SharedPrefsUtils.getInstance(activity).getString("token");
        String lastTestLogin = "0";
        String token = "";
        String user = SharedPrefsUtils.getInstance(activity).getString("user");
        if (!tokenData.isEmpty()) {
            lastTestLogin = tokenData.split(" ")[1];
            token = tokenData.split(" ")[0];
        }
        if (tokenData.isEmpty() || user.isEmpty() || token.isEmpty() || (now - Long.parseLong(lastTestLogin)) > 24 * 60 * 60 * 1000) {
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
                                    SharedPrefsUtils.getInstance(activity).putString("token", Config.tokenApp + " " + now);
                                    SharedPrefsUtils.getInstance(activity).putString("user", dt.getString("user"));
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
        } else {
            Config.user = new Gson().fromJson(user, User.class);
            Config.tokenApp = token;
            getData();
        }
    }

    private void getData() {
//        getFastConnect();
        getCountryList();
    }

    public void getFastConnect() {
        view.setStatus("Getting fast connect");
//        isLoadDataFastConnectSuccess = true;
//        intentToMain();
        AppDataHelper.getInstance().getFastConnect(Config.tokenApp, 0, new CallBack<Server>() {
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
        if (isGetCoinSuccess && isLoadCountrySuccess && isInitAdsSuccess) {
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
                    Common.totalPoint = 300;
                } else {
                    Common.totalPoint = data;
                }
                isGetCoinSuccess = true;
                intentToMain();

            }
        });
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
