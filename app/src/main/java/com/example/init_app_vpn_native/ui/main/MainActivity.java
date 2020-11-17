package com.example.init_app_vpn_native.ui.main;


import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.init_app_vpn_native.R;
import com.example.init_app_vpn_native.base.BaseActivity;
import com.example.init_app_vpn_native.ui.main.adapter.ViewPagerAdapter;
import com.example.init_app_vpn_native.ui.main.fragment.more.MoreFragment;
import com.example.init_app_vpn_native.ui.main.fragment.point.PointFragment;
import com.example.init_app_vpn_native.ui.main.fragment.vpn.VpnFragment;
import com.example.init_app_vpn_native.utils.Ads;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements IMainActivity {
    MainPresenter<IMainActivity> presenter;
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

    }

    private void initViewPager() {
        List<Fragment> fragmentList =  new ArrayList<>();
        fragmentList.add(VpnFragment.newInstance("", ""));
        fragmentList.add(PointFragment.newInstance("", ""));
        fragmentList.add(MoreFragment.newInstance("", ""));
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
    }

    private void initTabLayout() {
//        tabView.addTab(tabView.newTab().setText("Songs").setIcon(R.drawable.ic_vpn));
//        tabView.addTab(tabView.newTab().setText("Albums").setIcon(R.drawable.ic_vpn));
//        tabView.addTab(tabView.newTab().setText("Artists").setIcon(R.drawable.ic_vpn));
//        tabView.getTabAt(0).setIcon(R.drawable.ic_vpn);
//        tabView.getTabAt(1).setIcon(R.drawable.ic_vpn);
//        tabView.getTabAt(2).setIcon(R.drawable.ic_vpn);
//        tabView.getTabAt(0).setText("VPN");
//        tabView.getTabAt(1).setText("Points");
//        tabView.getTabAt(2).setText("Mores");
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