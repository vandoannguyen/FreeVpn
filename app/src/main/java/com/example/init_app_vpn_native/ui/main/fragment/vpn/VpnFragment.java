package com.example.init_app_vpn_native.ui.main.fragment.vpn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.init_app_vpn_native.R;

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
    @BindView(R.id.reclcyerQuickLaunch)
    RecyclerView reclcyerQuickLaunch;

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
        preseter = new VpnPreseter<>();
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
        ButterKnife.bind(view);
        return view;
    }

    @OnClick({R.id.lineGetCoin, R.id.lineGetServer, R.id.lineGetSpeedTest, R.id.linearConnected})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lineGetCoin:
                Toast.makeText(getContext(), "getCoin", Toast.LENGTH_SHORT).show();
                break;
            case R.id.lineGetServer:
                Toast.makeText(getContext(), "lineGetServer", Toast.LENGTH_SHORT).show();
                break;
            case R.id.lineGetSpeedTest:
                Toast.makeText(getContext(), "lineGetSpeedTest", Toast.LENGTH_SHORT).show();
                break;
            case R.id.linearConnected:
                Toast.makeText(getContext(), "linearConnected", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void showMessage(String mess) {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }
}