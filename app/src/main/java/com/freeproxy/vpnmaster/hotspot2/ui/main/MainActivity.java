package com.freeproxy.vpnmaster.hotspot2.ui.main;

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
import com.freeproxy.vpnmaster.hotspot2.R;
import com.freeproxy.vpnmaster.hotspot2.base.BaseActivity;
import com.freeproxy.vpnmaster.hotspot2.data.AppDataHelper;
import com.freeproxy.vpnmaster.hotspot2.ui.main.adapter.ViewPagerAdapter;
import com.freeproxy.vpnmaster.hotspot2.ui.main.fragment.FragmentCallback;
import com.freeproxy.vpnmaster.hotspot2.ui.main.fragment.more.MoreFragment;
import com.freeproxy.vpnmaster.hotspot2.ui.main.fragment.point.PointFragment;
import com.freeproxy.vpnmaster.hotspot2.ui.main.fragment.vpn.VpnFragment;
import com.freeproxy.vpnmaster.hotspot2.utils.SharedPrefsUtils;
import com.google.android.material.tabs.TabLayout;
import com.oneadx.android.ratedialog.RatingDialog;

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
        ButterKnife.bind(this);
        presenter = new MainPresenter<>(this);
        presenter.onAttact(this);
        presenter.getExample();
        initViewPager();
        initTabLayout();
        rateAuto();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getPoint(this);
    }

    private void initViewPager() {
        VpnFragment vpnFragment = VpnFragment.newInstance(new FragmentCallback() {
            @Override
            public void callGetCoin() {
                super.callGetCoin();
                if (viewPager != null) {
                    viewPager.setCurrentItem(1);
                }
            }

            @Override
            public void setPoint(int point) {
                super.setPoint(point);
                txtCoin.setText(point + "");
                AppDataHelper.getInstance().setCoin(MainActivity.this, point, null);
            }
        });
        PointFragment pointFragment = PointFragment.newInstance(new FragmentCallback() {
            @Override
            public void setPoint(int point) {
                super.setPoint(point);
                setPointMain(point);
            }
        });
        MoreFragment moreFragment = MoreFragment.newInstance(new FragmentCallback() {
            @Override
            public void callBackRate() {
                super.callBackRate();
                rateManual();
            }
        });
        List<Fragment> fragmentList = new ArrayList<>();
        if (!vpnFragment.isAdded())
            fragmentList.add(vpnFragment);
        if (!pointFragment.isAdded()) {
            fragmentList.add(pointFragment);
        }
        if (!moreFragment.isAdded())
            fragmentList.add(moreFragment);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(fragmentList.size());
    }

    public void setPointMain(int point) {
        txtCoin.setText(point + "");
        AppDataHelper.getInstance().setCoin(this, point, null);
    }

    @OnClick({R.id.lineCoinMain})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lineCoinMain:
//                Intent intentCoin = new Intent(getApplicationContext(), PointsDesActivity.class);
//                startActivity(intentCoin);
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