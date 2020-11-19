package com.example.init_app_vpn_native.ui.main;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Calendar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.example.init_app_vpn_native.R;
import com.example.init_app_vpn_native.base.BaseActivity;
import com.example.init_app_vpn_native.ui.main.adapter.ViewPagerAdapter;
import com.example.init_app_vpn_native.ui.main.fragment.more.MoreFragment;
import com.example.init_app_vpn_native.ui.main.fragment.point.PointFragment;
import com.example.init_app_vpn_native.ui.main.fragment.vpn.VpnFragment;
import com.example.init_app_vpn_native.utils.ads.Ads;
import com.example.init_app_vpn_native.ui.point.PointsDesActivity;
import com.example.init_app_vpn_native.utils.ads.Ads;
import com.example.init_app_vpn_native.utils.Common;
import com.example.init_app_vpn_native.utils.SharedPrefsUtils;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements IMainActivity {
    private String TAG = "MainActivity";
    MainPresenter<IMainActivity> presenter;
    @BindView(R.id.lineCoinMain)
    LinearLayout lineCoinMain;
    @BindView(R.id.txtAppName)
    TextView txtAppName;
    @BindView(R.id.txtCoin)
    TextView txtCoin;
    @BindView(R.id.tabView)
    TabLayout tabView;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    ViewPagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Ads.getInstance(this).initAds();
        presenter = new MainPresenter<>(this);
        presenter.onAttact(this);
        presenter.getExample();
        initViewPager();
        initTabLayout();
        initView();
    }
    private void initView(){
        //add coin
        int coinAdd = SharedPrefsUtils.getInstance(this).getInt("points");
        coinAdd = Common.points + coinAdd;
        Common.points = 0;
        SharedPrefsUtils.getInstance(this).putInt("points",coinAdd);
        String strCoin = String.valueOf(coinAdd);
        txtCoin.setText(strCoin);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.e(TAG, "onWindowFocusChanged: 123" );
        initView();
    }

    private void initViewPager() {
        List<Fragment> fragmentList =  new ArrayList<>();
        fragmentList.add(VpnFragment.newInstance("", ""));
        fragmentList.add(PointFragment.newInstance("", ""));
        fragmentList.add(MoreFragment.newInstance("", ""));
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
    }
    @OnClick({R.id.lineCoinMain})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.lineCoinMain:
                Intent intentCoin = new Intent(getApplicationContext(), PointsDesActivity.class);
                startActivity(intentCoin);
                break;
        }
    }
    private void initTabLayout() {
        tabView.setupWithViewPager(viewPager);
    }

    @Override
    public void showMessage(String mess) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDetact();
        }
    }
}