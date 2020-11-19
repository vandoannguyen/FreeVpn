package com.example.init_app_vpn_native.ui.main.fragment.more;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
<<<<<<< HEAD
import com.example.init_app_vpn_native.ui.proxy.ProxySettingActivity;
import com.example.init_app_vpn_native.ui.faq.FAQActivity;
import com.example.init_app_vpn_native.ui.feedBack.FeedbackActivity;
=======

import com.example.init_app_vpn_native.ui.main.faq.FAQActivity;
>>>>>>> 26fbc38... push again 16h30 1911
import com.example.init_app_vpn_native.R;
import com.example.init_app_vpn_native.ui.main.feedback.FeedbackActivity;
import com.example.init_app_vpn_native.ui.main.proxy.ProxySettingActivity;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoreFragment extends Fragment {
    private String TAG = "MoreFragment";
    @BindView(R.id.lineProxy)
    LinearLayout lineProxy;
    @BindView(R.id.lineLike)
    LinearLayout lineLike;
    @BindView(R.id.lineSuggestion)
    LinearLayout lineSuggestion;
    @BindView(R.id.lineUser)
    LinearLayout lineUser;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoreFragment newInstance(String param1, String param2) {
        MoreFragment fragment = new MoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    @OnClick({R.id.lineProxy, R.id.lineLike, R.id.lineSuggestion, R.id.lineUser})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.lineProxy:
                Intent intentProxy = new Intent(getActivity(), ProxySettingActivity.class);
                startActivity(intentProxy);
                break;
            case R.id.lineLike:
                //Rate
                break;
            case R.id.lineSuggestion:
                Intent intentFb = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(intentFb);
                break;
            case R.id.lineUser:
                Intent intentFAQ = new Intent(getActivity(), FAQActivity.class);
                startActivity(intentFAQ);
                break;
        }
    }
}