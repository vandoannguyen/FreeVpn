package com.freeproxy.vpnmaster.hotspot2.ui.load;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.freeproxy.vpnmaster.hotspot2.BuildConfig;
import com.freeproxy.vpnmaster.hotspot2.R;
import com.freeproxy.vpnmaster.hotspot2.common.Config;
import com.freeproxy.vpnmaster.hotspot2.dialog.DialogCallBack;
import com.freeproxy.vpnmaster.hotspot2.dialog.DialogRequirePermission;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadData extends AppCompatActivity implements ILoadDataView {
    ILoadPresenter<ILoadDataView> presenter;
    @BindView(R.id.txtVersion)
    TextView txtVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_data);
        ButterKnife.bind(this);
        presenter = new LoadDataPresenter<>(this);
        presenter.onAttact(this);
        txtVersion.setText(BuildConfig.VERSION_NAME);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Settings.canDrawOverlays(this)) {
//            showDialogSetting();
//        } else {
            init();
//        }
    }

    private void init() {
        if (Config.isDataLoaded) {
            presenter.intentMain();
        } else {
            presenter.login();
            presenter.getConfigAds();
            presenter.getCoin();
            presenter.getQuickLaunch();
            presenter.initAds();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetact();
    }

    @Override
    public void showMessage(String mess) {
        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void setStatus(String mess) {
//        txtStatus.setText(mess);
    }
}