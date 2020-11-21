package com.example.init_app_vpn_native.ui.main.fragment.vpn;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.init_app_vpn_native.R;
import com.example.init_app_vpn_native.common.Config;
import com.example.init_app_vpn_native.data.api.model.Country;
import com.example.init_app_vpn_native.ui.dialog.DialogLoading;
import com.example.init_app_vpn_native.ui.main.fragment.FragmentCallback;
import com.example.init_app_vpn_native.ui.speedTest.SpeedTest;
import com.example.init_app_vpn_native.ui.switchRegion.SwitchRegion;
import com.example.init_app_vpn_native.utils.ads.Ads;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.blinkt.openvpn.core.App;

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
    CardView cardConnect;
    @BindView(R.id.cardDisConnect)
    CardView cardDisConnect;
    @BindView(R.id.lineGetCoin)
    LinearLayout lineGetCoin;
    @BindView(R.id.lineGetServer)
    LinearLayout lineGetServer;
    @BindView(R.id.lineGetSpeedTest)
    LinearLayout lineGetSpeedTest;
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
    IVpnPresenter<IVpnView> presenter;
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
    @BindView(R.id.txtConnectedCountry)
    TextView txtConnectedCountry;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private IVpnPresenter<IVpnView> preseter;
    FragmentCallback callback;

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
        preseter = new VpnPreseter<>(getActivity());
        preseter.onAttact(this);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onDestroy() {
        if (preseter != null)
            preseter.onDetact();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vpn2, container, false);
        ButterKnife.bind(this, view);
//        preseter.getConnectionStatus();
        String countryCode = Config.currentServer.getGeo().getCountry();
        App.selectedServer = Config.currentServer;
        Log.e(TAG, "onCreateView: " + countryCode);
        Country country = Config.listCountry.get(Config.listCountry.indexOf(new Country(countryCode)));
        App.selectedCountry = country;
        Ads.getInstance(getActivity()).nativeAds(frameAds, null);
        if (country != null) {
            updateCountry(country);
        }
        return view;
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

    }

    @Override
    public void onPause() {
        super.onPause();
        preseter.onPause();
    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @OnClick({R.id.imgConnect, R.id.lineSwitchCountry, R.id.lineDisconnect, R.id.lineGetCoin, R.id.lineGetServer, R.id.lineGetSpeedTest})
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
            case R.id.lineGetSpeedTest: {
                intentToOther(SpeedTest.class);
            }
            break;
        }
    }

    private void disConnectVpn() {
        preseter.pressDisConnect();
        if (Ads.getRandom()) {
            DialogLoading.showDialog(getContext());
            Ads.getInstance(getActivity()).inter(new Ads.CallBackInter() {
                @Override
                public void adClose() {
                    DialogLoading.dismish();
                }

                @Override
                public void adLoadFailed(int i) {
                    DialogLoading.dismish();
                }
            });
        }
    }

    private void startAnimation(int view, int animation, boolean show) {
        final View Element = getView().findViewById(view);
        if (show) {
            Element.setVisibility(View.VISIBLE);
        } else {
            Element.setVisibility(View.GONE);
        }
        Animation anim = AnimationUtils.loadAnimation(getContext(), animation);
        Element.startAnimation(anim);
    }

    @Override
    public void setConectionStatus(int i) {

        switch (i) {
            case 0: {
                imgConnect.setImageResource(R.drawable.connect);
                startAnimation(R.id.imgConnect, R.anim.fade_in_1000, true);
                startAnimation(R.id.cardConnect, R.anim.fade_in_1000, true);
                startAnimation(R.id.linearConnected, R.anim.fade_out_500, false);
                startAnimation(R.id.cardDisConnect, R.anim.fade_out_500, false);
                break;
            }
            case 1: {
                startAnimation(R.id.imgConnect, R.anim.fade_in_1000, true);
                imgConnect.setImageResource(R.drawable.connecting);
                break;
            }
            case 2: {
                startAnimation(R.id.linearConnected, R.anim.fade_in_1000, true);
                startAnimation(R.id.cardDisConnect, R.anim.fade_in_1000, true);
                startAnimation(R.id.cardConnect, R.anim.fade_out_500, false);
                imgConnect.setImageResource(R.drawable.connect);
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
        if (Ads.getRandom()) {
            DialogLoading.showDialog(getContext());
            Ads.getInstance(getActivity()).inter(new Ads.CallBackInter() {
                @Override
                public void adClose() {
                    preseter.pressLineConnect();
                    DialogLoading.dismish();
                }

                @Override
                public void adLoadFailed(int i) {
                    preseter.pressLineConnect();
                    DialogLoading.dismish();
                }
            });
        } else {
            preseter.pressLineConnect();
        }
    }

    private void pressSwitchCountry() {
        preseter.pressLineCountry();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        preseter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        preseter.onResume();
    }

    @Override
    public void updateCountry(Country country) {
        txtCountry.setText(country.getName());
        txtConnectedCountry.setText(country.getName());
        Glide.with(this).load("https://www.countryflags.io/" + country.getCode() + "/flat/64.png").into(imgFlag);
        Glide.with(this).load("https://www.countryflags.io/" + country.getCode() + "/flat/64.png").into(imgFlagConnected);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    void intentToOther(Class clas) {
        Intent intent = new Intent(getActivity(), clas);
        startActivity(intent);
    }

    void intentToOther(Class clas, int requestCode) {
        Intent intent = new Intent(getActivity(), clas);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void updateSpeed(String finalDown, String finalUp) {
        txtUpLoadSpeed.setText(finalUp);
        txtDownloadSpeed.setText(finalDown);
    }
}