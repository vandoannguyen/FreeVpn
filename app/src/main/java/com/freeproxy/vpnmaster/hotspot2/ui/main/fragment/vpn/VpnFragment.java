package com.freeproxy.vpnmaster.hotspot2.ui.main.fragment.vpn;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.freeproxy.vpnmaster.hotspot2.R;
import com.freeproxy.vpnmaster.hotspot2.common.Config;
import com.freeproxy.vpnmaster.hotspot2.data.api.model.Country;
import com.freeproxy.vpnmaster.hotspot2.ui.dialog.DialogLoading;
import com.freeproxy.vpnmaster.hotspot2.ui.dialog.DialogPoint;
import com.freeproxy.vpnmaster.hotspot2.ui.main.fragment.FragmentCallback;
import com.freeproxy.vpnmaster.hotspot2.ui.speedTest.SpeedTest;
import com.freeproxy.vpnmaster.hotspot2.ui.switchRegion.SwitchRegion;
import com.oneadx.android.oneads.AdSize;
import com.oneadx.android.oneads.adbanner.AdBanner;
import com.oneadx.android.oneads.adnative.AdNative;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VpnFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VpnFragment extends Fragment implements IVpnView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "VpnFragment";
    private static final int SWITCH_REQUEST_CODE = 123;
    @BindView(R.id.cardConnect)
    LinearLayout cardConnect;
    @BindView(R.id.cardDisConnect)
    CardView cardDisConnect;
    @BindView(R.id.lineGetCoin)
    LinearLayout lineGetCoin;
    @BindView(R.id.lineGetServer)
    LinearLayout lineGetServer;
    @BindView(R.id.relativeGetSpeedTest)
    RelativeLayout relativeGetSpeedTest;
    @BindView(R.id.linearConnected)
    LinearLayout linearConnected;
    @BindView(R.id.imgConnect)
    ImageView imgConnect;
    @BindView(R.id.imgFlag)
    ImageView imgFlag;
    @BindView(R.id.txtCountry)
    TextView txtCountry;
    @BindView(R.id.lineSwitchCountry)
    LinearLayout lineSwitchCountry;
    @BindView(R.id.lineDisconnect)
    LinearLayout lineDisconnect;
    @BindView(R.id.txtVirtualIp)
    TextView txtVirtualIp;
    @BindView(R.id.txtDownloadSpeed)
    TextView txtDownloadSpeed;
    @BindView(R.id.txtUpLoadSpeed)
    TextView txtUpLoadSpeed;
    @BindView(R.id.linearConnect)
    LinearLayout linearConnect;
    @BindView(R.id.imgFlagConnected)
    ImageView imgFlagConnected;
    @BindView(R.id.quickFacebook)
    LinearLayout quickFacebook;
    @BindView(R.id.quickGoogle)
    LinearLayout quickGoogle;
    @BindView(R.id.quickGmail)
    LinearLayout quickGmail;
    @BindView(R.id.frameAds)
    FrameLayout frameAds;
    //    @BindView(R.id.cardFlag)
//    CardView cardFlag;
    @BindView(R.id.txtConnectedCountry)
    TextView txtConnectedCountry;
    @BindView(R.id.txtStatusConnect)
    TextView txtStatusConnect;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private IVpnPresenter<IVpnView> presenter;
    FragmentCallback callback;
    private AdNative adNative;
    private AdBanner adBanner;

    public VpnFragment() {
        // Required empty public constructor
    }

    public VpnFragment(FragmentCallback callback) {
        this.callback = callback;
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static VpnFragment newInstance(FragmentCallback callBackFragment) {
        VpnFragment fragment = new VpnFragment(callBackFragment);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new VpnPreseter<IVpnView>(this.getActivity());
        presenter.onAttact(this);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vpn2, container, false);
        ButterKnife.bind(this, view);
//        preseter.getConnectionStatus();
//        String countryCode = Config.currentServer.getGeo().getCountry();
//        App.selectedServer = Config.currentServer;
//        Log.e(TAG, "onCreateView: " + countryCode);
//        Country country = Config.listCountry.get(Config.listCountry.indexOf(new Country(countryCode)));
//        App.selectedCountry = country;
//        Config.currentCountry = country;
//        frameAds.setVisibility(View.GONE);
        initAds();
//        Ads.getInstance(getActivity()).nativeAds(frameAds, null);
//        if (country != null) {
        updateCountry(null);
//        }
        return view;
    }

    private void initAds() {
        adNative = new AdNative(getActivity(), frameAds, new AdNative.AdNativeListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onError() {
                adBanner = new AdBanner(getActivity(), frameAds, null);
                adBanner.setAdSize(AdSize.BIG_BANNER);
                adBanner.load();
            }
        });
        adNative.load();
        presenter.initAds();
    }

    @Override
    public void onDestroy() {
        adNative.destroy();
        if (adBanner != null) adBanner.destroy();
        if (presenter != null)
            presenter.onDetact();
        super.onDestroy();
    }

    @Override
    public void updateState(int i) {
        if (i == 0) {
//            imgConnect
        } else {
            if (i == 1) {

            } else {

            }
        }
    }

    @Override
    public void showMessage(String mess) {
        Toast.makeText(getContext(), mess, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updatePoint(int point) {
        callback.setPoint(point);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @OnClick({R.id.imgConnect, R.id.quickFacebook, R.id.quickGoogle, R.id.quickGmail, R.id.lineSwitchCountry, R.id.lineDisconnect, R.id.lineGetCoin, R.id.lineGetServer, R.id.relativeGetSpeedTest})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgConnect: {
                connectToVpn();
            }
            break;
            case R.id.lineSwitchCountry: {
                intentToOther(SwitchRegion.class, SWITCH_REQUEST_CODE);
            }
            break;
            case R.id.lineDisconnect:
                disConnectVpn();
                break;
            case R.id.lineGetCoin: {
                callback.callGetCoin();
            }
            break;
            case R.id.lineGetServer: {
                intentToOther(SwitchRegion.class, SWITCH_REQUEST_CODE);
            }
            break;
            case R.id.relativeGetSpeedTest: {
                intentToOther(SpeedTest.class);
                break;
            }
            case R.id.quickFacebook: {
                intentQuick("com.facebook.katana");
                break;
            }
            case R.id.quickGoogle: {
                intentQuick("com.google.android.googlequicksearchbox");
                break;
            }
            case R.id.quickGmail: {
                intentQuick("com.google.android.gm");
                break;
            }
        }
    }

    private void intentQuick(String s) {

        if (checkPackage(s)) {
            Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage(s);
            if (launchIntent != null) {
                startActivity(launchIntent);
            }
        } else {
            Toast.makeText(getContext(), "This app is not installed in your device", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void callGetCoin() {
        if (callback != null) callback.callGetCoin();
    }

    private boolean checkPackage(String s) {
        PackageManager pm = getActivity().getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(s, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    private void disConnectVpn() {
        presenter.pressDisConnect();
    }

    private void startAnimation(int view, int animation, boolean show) {
        if (getView() != null) {
            final View Element = getView().findViewById(view);
            if (show) {
                Element.setVisibility(View.VISIBLE);
            } else {
                Element.setVisibility(View.GONE);
            }
//            Animation anim = AnimationUtils.loadAnimation(getContext(), animation);
//            Element.startAnimation(anim);
        }
    }

    @Override
    public void setConectionStatus(int i) {

        switch (i) {
            case 0: {
                if (Config.isFastConnect && Config.currentCountry != null) {
                    imgFlag.setImageResource(R.mipmap.ic_flag_fast);
                    imgFlagConnected.setImageResource(R.mipmap.ic_flag_fast);
                    txtCountry.setText("Fast Connect");
                    txtConnectedCountry.setText("Fast Connect");
                }
                txtStatusConnect.setText("Tab to connect");
                imgConnect.setImageResource(R.drawable.connect);
                startAnimation(R.id.imgConnect, R.anim.fade_in_1000, true);
                startAnimation(R.id.cardConnect, R.anim.fade_in_1000, true);
                startAnimation(R.id.linearConnected, R.anim.fade_out_500, false);
                startAnimation(R.id.cardDisConnect, R.anim.fade_out_500, false);
                break;
            }
            case 1: {
                if (Config.isFastConnect) {
                    imgFlag.setImageResource(R.mipmap.ic_flag_fast);
                    imgFlagConnected.setImageResource(R.mipmap.ic_flag_fast);
                    txtCountry.setText("Fast Connect");
                    txtConnectedCountry.setText("Fast Connect");
                }
                txtStatusConnect.setText("Connecting ...");
                startAnimation(R.id.imgConnect, R.anim.fade_in_1000, true);
                Glide.with(getContext()).load(R.drawable.connect).into(imgConnect);
                break;
            }
            case 2: {
                if (Config.isFastConnect) {
                    txtCountry.setText(Config.currentCountry != null ? Config.currentCountry.getName() : "");
                    txtConnectedCountry.setText(Config.currentCountry != null ? Config.currentCountry.getName() : "");
                    if (Config.currentCountry != null) {
                        Glide.with(getContext()).load("https://www.countryflags.io/" + Config.currentCountry.getCode() + "/flat/64.png").into(imgFlag);
                        Glide.with(getContext()).load("https://www.countryflags.io/" + Config.currentCountry.getCode() + "/flat/64.png").into(imgFlagConnected);
                    }
                }
                txtStatusConnect.setText("Connected");
                startAnimation(R.id.linearConnected, R.anim.fade_in_1000, true);
                startAnimation(R.id.cardDisConnect, R.anim.fade_in_1000, true);
                startAnimation(R.id.cardConnect, R.anim.fade_out_500, false);
                imgConnect.setImageResource(R.drawable.connect);
                txtVirtualIp.setText(Config.currentServer != null ? Config.currentServer.getIp() : "");
                break;
            }
            default: {
                linearConnect.setVisibility(View.VISIBLE);
                imgConnect.setImageResource(R.drawable.connect);
                linearConnected.setVisibility(View.GONE);
                cardDisConnect.setVisibility(View.GONE);
            }

        }
    }

    private void connectToVpn() {
        presenter.pressLineConnect();
    }

    @Override
    public void showDialogPoint() {
        DialogPoint.showDialog(getContext(), new DialogPoint.OnOkClick() {
            @Override
            public void onClick() {
                callGetCoin();
            }
        });
    }

    private void pressSwitchCountry() {
        presenter.pressLineCountry();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void updateCountry(Country country) {
        if (Config.isFastConnect || country == null) {
            imgFlag.setImageResource(R.mipmap.ic_flag_fast);
            imgFlagConnected.setImageResource(R.mipmap.ic_flag_fast);
            txtCountry.setText("Fast Connect");
            txtConnectedCountry.setText("Fast Connect");
        } else {
            txtCountry.setText(country.getName());
            txtConnectedCountry.setText(country.getName());
            Glide.with(this).load("https://www.countryflags.io/" + country.getCode() + "/flat/64.png").into(imgFlag);
            Glide.with(this).load("https://www.countryflags.io/" + country.getCode() + "/flat/64.png").into(imgFlagConnected);
        }
    }

    @Override
    public void showLoading() {
        DialogLoading.showDialog(getContext());
    }

    @Override
    public void hideLoading() {
        DialogLoading.dismish();
    }

    void intentToOther(Class clas) {
        Intent intent = new Intent(getActivity(), clas);
        startActivity(intent);
    }

    void intentToOther(Class clas, int requestCode) {

        Intent intent = new Intent(getActivity(), clas);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void updateSpeed(String finalDown, String finalUp) {
        txtUpLoadSpeed.setText(finalUp);
        txtDownloadSpeed.setText(finalDown);
    }
}