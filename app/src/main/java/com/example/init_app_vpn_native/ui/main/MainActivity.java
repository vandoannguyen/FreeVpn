package com.example.init_app_vpn_native.ui.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.init_app_vpn_native.R;
import com.example.init_app_vpn_native.base.BaseActivity;
import com.example.init_app_vpn_native.data.AppDataHelper;
import com.example.init_app_vpn_native.ui.main.adapter.ViewPagerAdapter;
import com.example.init_app_vpn_native.ui.main.fragment.FragmentCallback;
import com.example.init_app_vpn_native.ui.main.fragment.more.MoreFragment;
import com.example.init_app_vpn_native.ui.main.fragment.point.PointFragment;
import com.example.init_app_vpn_native.ui.main.fragment.vpn.VpnFragment;
import com.example.init_app_vpn_native.ui.point.PointsDesActivity;
import com.example.init_app_vpn_native.utils.Common;
import com.example.init_app_vpn_native.utils.SharedPrefsUtils;
import com.example.init_app_vpn_native.utils.ads.Ads;
import com.example.ratedialog.RatingDialog;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements IMainActivity, RatingDialog.RatingDialogInterFace {
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
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Ads.getInstance(this).initAds();
        ButterKnife.bind(this);
        Ads.getInstance(this).initAds();
        presenter = new MainPresenter<>(this);
        presenter.onAttact(this);
        presenter.getExample();
        initViewPager();
        initTabLayout();
        presenter.getPoint(this);
        rateAuto();
    }

    private void initViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(VpnFragment.newInstance(new FragmentCallback() {
            @Override
            public void callGetCoin() {
                super.callGetCoin();
                if (viewPager != null) {
                    viewPager.setCurrentItem(1);
                }
            }
        }));
        fragmentList.add(PointFragment.newInstance(new FragmentCallback() {
            @Override
            public void setPoint(int point) {
                super.setPoint(point);
                setPointMain(point);
            }
        }));
        fragmentList.add(MoreFragment.newInstance(new FragmentCallback() {
            @Override
            public void callBackRate() {
                super.callBackRate();
                rateManual();
            }
        }));
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3);
    }

    public void setPointMain(int point) {
        txtCoin.setText(point + "");
        AppDataHelper.getInstance().setCoin(this, point, null);
    }

    @OnClick({R.id.lineCoinMain})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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

    //Rate
    public static void rateApp(Context context) {
        Intent intent = new Intent(new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void rateAuto() {
        int rate = SharedPrefsUtils.getInstance(this).getInt("rate");
        if (rate < 1) {
            RatingDialog ratingDialog = new RatingDialog(this);
            ratingDialog.setRatingDialogListener(this);
            ratingDialog.showDialog();
        }
    }

    private void rateManual() {
        RatingDialog ratingDialog = new RatingDialog(this);
        ratingDialog.setRatingDialogListener(this);
        ratingDialog.showDialog();
    }

    @Override
    public void onDismiss() {

    }

    @Override
    public void onSubmit(float rating) {
        if (rating > 3) {
            rateApp(this);
            SharedPrefsUtils.getInstance(this).putInt("rate", 5);
        }
    }

    @Override
    public void onRatingChanged(float rating) {
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}