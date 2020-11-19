package com.example.init_app_vpn_native.ui.main.fragment.vpn;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.example.init_app_vpn_native.base.BasePresenter;
import com.example.init_app_vpn_native.common.Config;
import com.example.init_app_vpn_native.data.CallBack;
import com.example.init_app_vpn_native.data.IAppDataHelper;
import com.example.init_app_vpn_native.data.api.model.Country;
import com.example.init_app_vpn_native.data.api.model.Server;
import com.example.init_app_vpn_native.ui.load.LoadData;
import com.example.init_app_vpn_native.ui.switchRegion.SwitchRegion;
import com.example.init_app_vpn_native.utils.EncryptData;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import de.blinkt.openvpn.LaunchVPN;
import de.blinkt.openvpn.VpnProfile;
import de.blinkt.openvpn.core.App;
import de.blinkt.openvpn.core.ConfigParser;
import de.blinkt.openvpn.core.ConnectionStatus;
import de.blinkt.openvpn.core.IOpenVPNServiceInternal;
import de.blinkt.openvpn.core.OpenVPNService;
import de.blinkt.openvpn.core.ProfileManager;
import de.blinkt.openvpn.core.VpnStatus;

public class VpnPreseter<V extends IVpnView> extends BasePresenter<V> implements VpnStatus.ByteCountListener, VpnStatus.StateListener, IVpnPresenter<V> {
    private static final String TAG = "VpnPreseter";
    private static final int REQUEST_SERVER_CODE = 123;
    ProfileManager pm;
    InputStream inputStream;
    BufferedReader bufferedReader;
    ConfigParser cp;
    VpnProfile vp;
    boolean hasFile = false;
    String File = "NULL";
    Activity activity;
    IOpenVPNServiceInternal mService;
    CountDownTimer ConnectionTimer;
    boolean EnableConnectButton = true;
    String FileID = "NULL", City = "NULL", Image = "NULL";
    InterstitialAd interstitialAd, interstitialAdConnect;
    private Class classIntentScreen = null;
    IAppDataHelper appDataHelper;


    /**
     * biến check click disconnect vpn
     */
    private boolean isClickDisConnectVpn = false;
    CountDownTimer countDownLimitConnect;

    public boolean isEnableConnectButton() {
        return EnableConnectButton;
    }

    public VpnPreseter(Activity activity) {
        this.activity = activity;
        SharedPreferences c = activity.getSharedPreferences(App.SHARE_CONNECTION_DATA, 0);
        App.connection_status = c.getInt(App.SHARE_CONNECTION_STATUS, 0);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = IOpenVPNServiceInternal.Stub.asInterface(service);
            Log.e(TAG, "onServiceConnected: ServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mService = null;
            Log.e(TAG, "onServiceConnected: ServiceDisconnected");

        }
    };

    private void start_vpn(String VPNFile) {
        Bundle params = new Bundle();
        Log.e("device_id", App.device_id);
        setConnectionStatus(1);
        try {
            inputStream = null;
            bufferedReader = null;
            try {
                assert VPNFile != null;
                inputStream = new ByteArrayInputStream(VPNFile.getBytes(Charset.forName("UTF-8")));
            } catch (Exception e) {
                Log.e("device_id", App.device_id);
                Log.e("exception", "MA11" + e.toString());
            }

            try { // M8
                assert inputStream != null;
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream/*, Charset.forName("UTF-8")*/));
            } catch (Exception e) {
                Log.e("device_id", App.device_id);
                Log.e("exception", "MA12" + e.toString());
            }

            cp = new ConfigParser();
            try {
                cp.parseConfig(bufferedReader);
            } catch (Exception e) {
                params = new Bundle();
                Log.e("device_id", App.device_id);
                Log.e("exception", "MA13" + e.toString());
            }
            vp = cp.convertProfile();
            try {
                vp.mName = Build.MODEL;
            } catch (Exception e) {
                Log.e(TAG, "start_vpn: " + e);
            }

            vp.mUsername = Config.FileUsername;
            vp.mPassword = Config.FilePassword;

            try {
                pm = ProfileManager.getInstance(activity);
                pm.addProfile(vp);
                pm.saveProfileList(activity);
                pm.saveProfile(activity, vp);
                vp = pm.getProfileByName(Build.MODEL);
                Intent intent = new Intent(activity, LaunchVPN.class);
                intent.putExtra(LaunchVPN.EXTRA_KEY, vp.getUUID().toString());
                intent.setAction(Intent.ACTION_MAIN);
                activity.startActivity(intent);
                App.isStart = false;
            } catch (Exception e) {
                Log.e("device_id", App.device_id);
                Log.e("exception", "MA16" + e.toString());
            }
        } catch (Exception e) {
            Log.e("device_id", App.device_id);
            Log.e("exception", "MA17" + e.toString());
            Log.e(TAG, "start_vpn: " + e);
        }
    }

    private void setConnectionStatus(int i) {
        App.connection_status = i;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setConectionStatus(i);
                if (i == 2) {
                    if (countDownLimitConnect != null) {
                        countDownLimitConnect.cancel();
                        countDownLimitConnect = null;
                    }
//                    view.hideBanner();
                }
            }
        });
        SharedPreferences c = activity.getSharedPreferences(App.SHARE_CONNECTION_DATA, 0);
        SharedPreferences.Editor editor = c.edit();
        editor.putInt(App.SHARE_CONNECTION_STATUS, i);
        editor.commit();
    }

    public void stop_vpn() {
        App.isStart = false;
        EnableConnectButton = true;
        setConnectionStatus(0);
        startAnimationLoading();
//        view.
        OpenVPNService.abortConnectionVPN = true;
        ProfileManager.setConntectedVpnProfileDisconnected(activity);

        if (mService != null) {

            try {
                mService.stopVPN(false);
            } catch (RemoteException e) {
                Bundle params = new Bundle();
                Log.e("device_id", App.device_id);
                Log.e("exception", "MA18" + e.toString());
            }

            try {
                pm = ProfileManager.getInstance(activity);
                vp = pm.getProfileByName(Build.MODEL);
                pm.removeProfile(activity, vp);
            } catch (Exception e) {
                Bundle params = new Bundle();
//                Log.e("device_id", App.device_id);
//                Log.e("exception", "MA17" + e.toString());
            }
        }
    }

    private boolean hasInternetConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        haveConnectedMobile = true;
            }
        } catch (Exception e) {
            Log.e(TAG, "hasInternetConnection: " + e);
        }

        return haveConnectedWifi || haveConnectedMobile;
    }

    @Override
    public void updateState(String state, String logmessage, int localizedResId, ConnectionStatus level) {
        Log.e(TAG, "updateState: " + state);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                view.isConnectedServer(state.equals("CONNECTED"));
                if (state.equals("CONNECTED")) {
                    App.isStart = true;
                    EnableConnectButton = true;
                    setConnectionStatus(2);
                    startAnimationLoaded();

                }
                if (state.equals("NOPROCESS") && App.connection_status == 2) {
                    EnableConnectButton = true;
                    stop_vpn();
                }
            }
        });
    }

    private void startAnimationLoaded() {
        Handler handlerData = new Handler();
        handlerData.postDelayed(new Runnable() {
            @Override
            public void run() {
//                view.startAnimation(R.id.la_animation_main, R.anim.fade_in_1000, true);
//                view.setAnimationImage(R.raw.connected);
//                view.setAnimationImage("android.resource://" + BuildConfig.APPLICATION_ID + "/raw/connectedun");
//                view.startAnimation(R.id.lineSpeed, R.anim.fade_in_1000, true);
            }
        }, 1000);
    }

    @Override
    public void setConnectedVPN(String uuid) {
        Log.e(TAG, "setConnectedVPN: " + uuid);
    }

    @Override
    public void updateByteCount(long in, long out, long diffIn, long diffOut) {
        String down = "", up = "";
        if (diffIn < 1000) {
            down = diffIn + " byte/s";
        } else if ((diffIn >= 1000) && (diffIn <= 1000_000)) {
            down = diffIn / 1000 + " kb/s";
        } else {
            down = diffIn / 1000_000 + " mb/s";
        }

        if (diffOut < 1000) {
            up = diffOut + " byte/s";
        } else if ((diffOut >= 1000) && (diffOut <= 1000_000)) {
            up = diffOut / 1000 + " kb/s";
        } else {
            up = diffOut / 1000_000 + " mb/s";
        }
        String finalDown = down;
        String finalUp = up;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.updateSpeed(finalDown, finalUp);
            }
        });
//        Log.i(TAG, "updateByteCount: ");
    }

    @Override
    public void onResume() {
        setConnectionStatus(App.connection_status);
        if (App.isGetServerFailed) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mService = null;
//                    activity.unbindService(mConnection);
                    activity.finish();
                }
            }, 3000);
        }
        if (!App.isGetServerFailed && hasInternetConnection()) {
            if (App.isStart) {
//                view.startAnimation(R.id.la_animation_main, R.anim.fade_in_1000, true);
                if (App.connection_status == 1) {
//                    view.setAnimationImage("android.resource://"+BuildConfig.APPLICATION_ID+"/raw/loading");
//                    view.setAnimationImage("android.resource://" + BuildConfig.APPLICATION_ID + "/raw/connectedun");
                } else {
//                    view.setAnimationImage("android.resource://" + BuildConfig.APPLICATION_ID + "/raw/connectedun");
                }
            } else
//                view.setAnimationImage("android.resource://" + BuildConfig.APPLICATION_ID + "/raw/connectedun");
                if (Config.currentServer == null) {
                    try {
                        Intent Welcome = new Intent(activity, LoadData.class);
                        Welcome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        activity.startActivityForResult(Welcome, 10);
                    } catch (Exception e) {
                        Log.e(TAG, "onResume: " + e);
                    }
                } else {
                    SharedPreferences ConnectionDetails = activity.getSharedPreferences(App.SHARE_CONNECTION_DATA, 0);
                    App.selectedCountry = new Gson().fromJson(ConnectionDetails.getString(App.SHARE_SELECTED_COUNTRY, ""), Country.class);
//                configDataServer();
                    view.updateCountry(App.selectedCountry);
                }
            try {
                VpnStatus.addStateListener(this);
                VpnStatus.addByteCountListener(this);
                Intent intent = new Intent(activity, OpenVPNService.class);
                intent.setAction(OpenVPNService.START_SERVICE);
                activity.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
                //bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
            } catch (Exception e) {
                Bundle params = new Bundle();
                params.putString("device_id", App.device_id);
                params.putString("exception", "MA2" + e.toString());
            }
        } else {
            Toast.makeText(activity, "Internet has not connected!", Toast.LENGTH_SHORT).show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mService = null;
//                    activity.unbindService(mConnection);
                    activity.finish();
                }
            }, 3000);
        }

    }

    @Override
    public void onPause() {
        if (mService != null)
            activity.unbindService(mConnection);
    }

    void connectToVpn() {
        App.connection_status = 1;
        if (!Config.isFastConnect)
            appDataHelper.getConnect(Config.tokenApp, Config.currentCountry.getCode(),
                    new CallBack<Server>() {
                        @Override
                        public void onSuccess(Server data) {
                            Log.e(TAG, "onSuccess:00000" + data);
                            connectVpn(data);
                        }

                        @Override
                        public void onFailed(String mess) {
                            startAnimationLoading();
                            App.connection_status = 0;
                            Toast.makeText(activity, "Failed to load server", Toast.LENGTH_SHORT).show();
                        }
                    });
        else {
            connectVpn(Config.currentServer);
        }
    }

    private void connectVpn(Server data) {
        if (data != null) {
            Log.e(TAG, "o/nSuccess: " + data.toString());
            App.selectedServer = data;
        }
        Runnable r = new Runnable() {
            @Override
            public void run() {
                if (!App.isStart) {
                    if (hasInternetConnection()) {
                        try {
                            configDataServer();
                            Log.e(TAG, "run: " + File);
                            start_vpn(File);
                            isClickDisConnectVpn = false;
                            startAnimationLoading();
                            EnableConnectButton = false;
                            App.isStart = true;
                            if (countDownLimitConnect != null) {
                                countDownLimitConnect.cancel();
                                countDownLimitConnect = null;
                            }
                            countDownLimitConnect = new CountDownTimer(30000, 1) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    if (App.isStart && App.connection_status == 1) {
                                        stop_vpn();
                                        Toast.makeText(activity, "Connect timeout!", Toast.LENGTH_SHORT).show();
                                        isClickDisConnectVpn = false;
                                        startAnimationLoading();
                                        setConnectionStatus(0);
                                    }
                                }
                            };
                            countDownLimitConnect.start();
                        } catch (Exception e) {
                            Bundle params = new Bundle();
                            params.putString("device_id", App.device_id);
                            params.putString("exception", "MA5" + e.toString());
                            Log.e("app_param_error", String.valueOf(params));
                        }

                    } else {
                        Toast.makeText(activity, "Internet not available!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        r.run();
    }

    @Override
    public void pressLineConnect() {
        startAnimationLoading();
//        if (App.connection_status == 0) {
            connectToVpn();
//        }
    }

    public void pressDisConnect() {
        if (App.connection_status == 2) {
            try {
                stop_vpn();
            } catch (Exception e) {
                Bundle params = new Bundle();
                params.putString("device_id", App.device_id);
                params.putString("exception", "MA6" + e.toString());
            }
            App.isStart = false;
        }
    }
//    private void setTextLineConnect(String s) {
//        view.setTextLineConnect(s);
//    }

    private void configDataServer() {
//        SharedPreferences sef = activity.getSharedPreferences(App.SHARE_CONNECTION_DATA, 0);
//        SharedPreferences.Editor editor = sef.edit();
//        editor.putString(App.SHARE_SELECTED_COUNTRY, new Gson().toJson(App.selectedServer));
//        editor.commit();
        EncryptData En = new EncryptData();
        File = En.decrypt(App.selectedServer.getVpnConfig());
    }

    private void showLoadingAds(boolean b) {
        if (b) {
            new Handler().post(
                    new Runnable() {
                        @Override
                        public void run() {
                            view.showLoading();
                        }
                    }
            );
        } else {
            new Handler().post(new Runnable() {
                                   @Override
                                   public void run() {
                                       view.hideLoading();
                                   }
                               }
            );
        }
    }

    private void startAnimationLoading() {
//        final Handler handlerToday = new Handler();
//        handlerToday.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                view.startAnimation(R.id.la_animation_main, R.anim.fade_in_1000, true);
//                view.setAnimationImage("android.resource://" + BuildConfig.APPLICATION_ID + "/raw/loading");
//            }
//        }, 500);
    }

    @Override
    public void pressLineCountry() {
        stop_vpn();
        Intent intent = new Intent(activity, SwitchRegion.class);
        activity.startActivityForResult(intent, REQUEST_SERVER_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SERVER_CODE) {
            if (data != null && data.getStringExtra("data").equals("ok")) {
                Log.e(TAG, "onActivityResult: " + App.selectedCountry.getName());
                view.updateCountry(App.selectedCountry);
            }
            if (requestCode == 10 && resultCode == -1) {
                Toast.makeText(activity, "Request timeout!", Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mService = null;
                        activity.finish();
                    }
                }, 3000);
            }
        }
    }

    @Override
    public void getSelectedServer() {
        SharedPreferences share = activity.getSharedPreferences(App.SHARE_CONNECTION_DATA, Context.MODE_PRIVATE);
        String data = share.getString(App.SHARE_SELECTED_COUNTRY, "");
        if (!data.equals("")) {
            App.selectedCountry = new Gson().fromJson(data, Country.class);
        }
    }


}