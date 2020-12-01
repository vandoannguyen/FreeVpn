package com.freeproxy.vpnmaster.hotspot2.ui.main.fragment.more;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.freeproxy.vpnmaster.hotspot2.BuildConfig;
import com.freeproxy.vpnmaster.hotspot2.R;
import com.freeproxy.vpnmaster.hotspot2.ui.faq.FAQActivity;
import com.freeproxy.vpnmaster.hotspot2.ui.feedBack.FeedbackActivity;
import com.freeproxy.vpnmaster.hotspot2.ui.main.fragment.FragmentCallback;
import com.freeproxy.vpnmaster.hotspot2.ui.proxy.ProxySettingActivity;
import com.freeproxy.vpnmaster.hotspot2.ui.speedTest.SpeedTest;
import com.freeproxy.vpnmaster.hotspot2.utils.ads.Ads;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoreFragment extends Fragment {
    private String TAG = "MoreFragment";
    @BindView(R.id.frmAdsMore)
    FrameLayout frmAdsMore;
    @BindView(R.id.lineSpeedTestMore)
    LinearLayout lineSpeedTestMore;
    @BindView(R.id.txtVersion)
    TextView txtVersion;
    @BindView(R.id.lineProxy)
    LinearLayout lineProxy;
    @BindView(R.id.lineLike)
    LinearLayout lineLike;
    @BindView(R.id.lineSuggestion)
    LinearLayout lineSuggestion;
    @BindView(R.id.lineUser)
    LinearLayout lineUser;
    FragmentCallback callback;

    public MoreFragment(FragmentCallback callback) {
        // Required empty public constructor
        this.callback = callback;
    }

    public static MoreFragment newInstance(FragmentCallback callback) {
        MoreFragment fragment = new MoreFragment(callback);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        ButterKnife.bind(this, view);
//        frmAdsMore.setVisibility(View.GONE);
        frmAdsMore.setVisibility(View.GONE);
//        Ads.getInstance(getActivity()).banner(frmAdsMore, Ads.AdsSize.MEDIUM);
        txtVersion.setText(BuildConfig.VERSION_NAME);
        return view;
    }

    @OnClick({R.id.lineProxy, R.id.lineLike, R.id.lineSuggestion, R.id.lineUser, R.id.lineSpeedTestMore})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lineProxy:
                Intent intentProxy = new Intent(getActivity(), ProxySettingActivity.class);
                startActivity(intentProxy);
                break;
            case R.id.lineLike:
                //Rate
                rateApp(getActivity());
                break;
            case R.id.lineSuggestion:
                Intent intentFb = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(intentFb);
                break;
            case R.id.lineUser:
                Intent intentFAQ = new Intent(getActivity(), FAQActivity.class);
                startActivity(intentFAQ);
                break;
            case R.id.lineSpeedTestMore: {
                startActivity(new Intent(getActivity(), SpeedTest.class));
                break;
            }
        }
    }

    public void rateApp(Context context) {
        if (callback != null) {
            callback.callBackRate();
        }
    }
}