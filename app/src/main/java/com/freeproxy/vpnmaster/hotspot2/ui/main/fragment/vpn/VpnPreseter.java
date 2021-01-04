package com.freeproxy.vpnmaster.hotspot2.ui.main.fragment.vpn;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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

import com.freeproxy.vpnmaster.hotspot2.base.BasePresenter;
import com.freeproxy.vpnmaster.hotspot2.common.Config;
import com.freeproxy.vpnmaster.hotspot2.data.AppDataHelper;
import com.freeproxy.vpnmaster.hotspot2.data.CallBack;
import com.freeproxy.vpnmaster.hotspot2.data.api.model.Country;
import com.freeproxy.vpnmaster.hotspot2.data.api.model.Server;
import com.freeproxy.vpnmaster.hotspot2.ui.dialog.DialogLoading;
import com.freeproxy.vpnmaster.hotspot2.ui.switchRegion.SwitchRegion;
import com.freeproxy.vpnmaster.hotspot2.utils.Common;
import com.freeproxy.vpnmaster.hotspot2.utils.EncryptData;
import com.google.gson.Gson;
import com.oneadx.android.oneads.AdInterstitial;
import com.oneadx.android.oneads.AdListener;

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
    //    String FileID = "NULL", City = "NULL", Image = "NULL";
//    InterstitialAd interstitialAd, interstitialAdConnect;
//    private Class classIntentScreen = null;
    private AdInterstitial inter;
//    IAppDataHelper appDataHelper;


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
                if (isAttacted())
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
                if (state.equals("CONNECTED")) {
                    App.isStart = true;
                    EnableConnectButton = true;
                    setConnectionStatus(2);
                    AppDataHelper.getInstance().postStatus(Config.tokenApp, "connected", Config.currentServer.getId(), null);
                    startAnimationLoaded();
                }
                if (state.equals("AUTH_FAILED")) {
                    AppDataHelper.getInstance().postStatus(Config.tokenApp, "unable to connect", Config.currentServer.getId(), null);
//                    Toast.makeText(activity, "auth failed", Toast.LENGTH_SHORT).show();
                    EnableConnectButton = true;
                    stop_vpn();
                }
                if (state.equals("NOPROCESS") && App.connection_status == 2) {
                    EnableConnectButton = true;
                    stop_vpn();
                }
            }
        });
    }

    private void startAnimationLoaded() {
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
                    activity.finish();
                }
            }, 3000);
        }
        if (!App.isGetServerFailed && hasInternetConnection()) {
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
        if (Common.totalPoint < Common.minCountryCoin) {
            setConnectionStatus(0);
            view.showDialogPoint();
        } else {
            view.showLoading();
            inter.show(new AdListener() {
                @Override
                public void onAdClosed() {
                    connect();
                    view.hideLoading();
                }
            });
        }

    }

    private void connect() {
        if (!Config.isFastConnect) {
            AppDataHelper.getInstance().getConnect(Config.tokenApp, Config.currentCountry.getCode(),
                    new CallBack<Server>() {
                        @Override
                        public void onSuccess(Server data) {
                            Config.currentServer = data;
                            connectVpn();
                        }

                        @Override
                        public void onFailed(String mess) {
                            startAnimationLoading();
                            setConnectionStatus(0);
                            Toast.makeText(activity, "Failed to load server", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            AppDataHelper.getInstance().getFastConnect(Config.tokenApp, Common.totalPoint, new CallBack<Server>() {
                @Override
                public void onSuccess(Server data) {
                    super.onSuccess(data);
                    Config.currentServer = data;
                    Config.currentCountry = Config.listCountry.get(Config.listCountry.indexOf(new Country(Config.currentServer.getGeo().getCountry())));
                    connectVpn();
                }

                @Override
                public void onFailed(String mess) {
                    startAnimationLoading();
                    App.connection_status = 0;
                    setConnectionStatus(0);
                    Toast.makeText(activity, "Failed to load server", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void connectVpn() {
        int point = Integer.parseInt(Config.currentCountry.getPrice());
        if (point <= Common.totalPoint) {
            connectVpn(Config.currentServer);
            Common.totalPoint -= point;
            view.updatePoint(Common.totalPoint);
        } else {
            setConnectionStatus(0);
            view.showDialogPoint();
        }
    }

    private void connectVpn(Server data) {

        if (data != null) {
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
                                        AppDataHelper.getInstance().postStatus(Config.tokenApp, "unable to connect", Config.currentServer.getId(), null);
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
    public void intentToApp(String s) {
        if (isPackageExisted(s)) {
            Intent launchIntent = activity.getPackageManager().getLaunchIntentForPackage(s);
            if (launchIntent != null) {
                activity.startActivity(launchIntent);//null pointer check in case package name was not found
            }
        } else
            Toast.makeText(activity, "Your device is not install this app", Toast.LENGTH_SHORT).show();
    }

    public boolean isPackageExisted(String targetPackage) {
        PackageManager pm = activity.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    public void pressLineConnect() {
        connectToVpn();
    }

    @Override
    public void pressDisConnect() {
        stop_vpn();
        App.isStart = false;
        DialogLoading.showDialog(activity);
        inter.show(new AdListener() {
            @Override
            public void onAdClosed() {
                DialogLoading.dismish();
            }
        });
    }

    @Override
    public void initAds() {
        inter = new AdInterstitial(activity);
        inter.load();
    }

    @Override
    public void onDetact() {
        super.onDetact();
        inter.destroy();
    }

    private void configDataServer() {
        Log.e(TAG, "configDataServer: " + Config.currentServer);
        EncryptData En = new EncryptData();
        File = En.decrypt(Config.currentServer.getVpnConfig());
        Log.e(TAG, "configDataServer: " + File);
    }

    private void startAnimationLoading() {
    }

    @Override
    public void pressLineCountry() {
        stop_vpn();
        Intent intent = new Intent(activity, SwitchRegion.class);
        activity.startActivityForResult(intent, REQUEST_SERVER_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult: " + requestCode);
        if (requestCode == REQUEST_SERVER_CODE) {
            if (resultCode == SwitchRegion.SWITCH_COUNTRY) {
                if (data != null && data.getSerializableExtra("country") != null) {
                    Config.isFastConnect = false;
                    Config.currentCountry = (Country) data.getSerializableExtra("country");
                    Log.e(TAG, "onActivityResult: " + Config.currentCountry.toString());
                    App.selectedCountry = Config.currentCountry;
                    stop_vpn();
                    connectToVpn();
                    view.updateCountry(App.selectedCountry);
                } else {
                    stop_vpn();
                    getFastConnect();
                }
            }
            if (resultCode == SwitchRegion.RESULT_CODE_NOT_ENOUGH_POINT) {
                view.callGetCoin();
            }


        }
        if (requestCode == 10 && resultCode == -1) {
            Toast.makeText(activity, "Request timeout!", Toast.LENGTH_SHORT).show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mService = null;
//                    activity.finish();
                }
            }, 3000);
        }
    }

    private void getFastConnect() {
        DialogLoading.showDialog(activity, "Getting Fast connect ...");
        AppDataHelper.getInstance().getFastConnect(Config.tokenApp, Common.totalPoint, new CallBack<Server>() {
            @Override
            public void onSuccess(Server data) {
                DialogLoading.dismish();
                super.onSuccess(data);
                Config.isFastConnect = true;
                Config.currentServer = data;
                App.selectedServer = data;
                Country country = Config.listCountry.get(Config.listCountry.indexOf(new Country(data.getGeo().getCountry())));
                App.selectedCountry = country;
                Config.currentCountry = country;
                view.updateCountry(country);
                connectToVpn();
            }

            @Override
            public void onFailed(String mess) {
                super.onFailed(mess);
                Toast.makeText(activity, "Failed to load Fast Connect", Toast.LENGTH_SHORT).show();
                DialogLoading.dismish();
            }
        });
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
