package com.example.init_app_vpn_native.ui.main.fragment.vpn;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.init_app_vpn_native.ui.speedTest.SpeedTest;
import com.example.init_app_vpn_native.ui.switchRegion.SwitchRegion;

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
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private IVpnPresenter<IVpnView> preseter;

    public VpnFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VpnFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VpnFragment newInstance(String param1, String param2) {
        VpnFragment fragment = new VpnFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
        preseter.getSelectedServer();
//        String countryCode = Config.currentServer.getGeo().getCountry();
        //Country country = Config.listCountry.get(Config.listCountry.indexOf(new Country(countryCode)));
//        if (country != null) {
//            txtCountry.setText(country.getName());
//            Glide.with(this).load("https://www.countryflags.io/" + country.getCode() + "/flat/64.png").into(imgFlag);
//            Glide.with(this).load("https://www.countryflags.io/" + country.getCode() + "/flat/64.png").into(imgFlagConnected);
//        }
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
                preseter.pressDisConnect();
                break;
            case R.id.lineGetCoin:
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

    @Override
    public void setConectionStatus(int i) {
        switch (i) {
            case 0: {
                imgConnect.setImageResource(R.drawable.connect);
                linearConnected.setVisibility(View.GONE);
                cardDisConnect.setVisibility(View.GONE);
                linearConnect.setVisibility(View.VISIBLE);

                break;
            }
            case 1: {
                imgConnect.setImageResource(R.drawable.connecting);
                linearConnected.setVisibility(View.VISIBLE);
                cardDisConnect.setVisibility(View.VISIBLE);
                linearConnect.setVisibility(View.GONE);
                break;
            }
            case 2: {
                imgConnect.setImageResource(R.drawable.connect);
                linearConnected.setVisibility(View.VISIBLE);
                break;
            }
            default: {
                linearConnect.setVisibility(View.GONE);
                imgConnect.setImageResource(R.drawable.connect);
                linearConnected.setVisibility(View.GONE);
            }

        }
    }

    private void connectToVpn() {
        preseter.pressLineConnect();
    }

    private void pressSwitchCountry() {
        preseter.pressLineCountry();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SWITCH_REQUEST_CODE) {
            if (data != null) {
                String countryCode = data.getStringExtra("country_code");
                if (!countryCode.isEmpty()) {
                    Config.currentCountry = Config.listCountry.get(Config.listCountry.indexOf(new Country(countryCode)));
                    Config.isFastConnect = false;
                }
            }
        }
        preseter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        preseter.onResume();
    }

    @Override
    public void updateCountry(Country selectedCountry) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    void intentToOther(Class clas) {
        Intent intent = new Intent(getContext(), clas);
        startActivity(intent);
    }

    void intentToOther(Class clas, int requestCode) {
        Intent intent = new Intent(getContext(), clas);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void updateSpeed(String finalDown, String finalUp) {
        txtUpLoadSpeed.setText(finalUp);
        txtDownloadSpeed.setText(finalDown);
    }
}