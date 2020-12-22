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
    private static final int REQUEST_OVERLAY_PERMISSION = 2;
    ILoadPresenter<ILoadDataView> presenter;
    @BindView(R.id.txtStatus)
    TextView txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_data);
        ButterKnife.bind(this);
        presenter = new LoadDataPresenter<>(this);
        presenter.onAttact(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (!Settings.canDrawOverlays(this)) {
                showDialogSetting();
            } else {
                init();
            }
        } else {
            init();
        }
    }

    private void showDialogSetting() {
        DialogRequirePermission dialog = new DialogRequirePermission(this);
        dialog.setCallBack(new DialogCallBack() {
            @Override
            public void onClickOk() {
                super.onClickOk();

                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_OVERLAY_PERMISSION);
            }

            @Override
            public void onClickNo() {
                super.onClickNo();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 3000);
            }
        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_OVERLAY_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(LoadData.this)) {
                    init();
                }
            }
        }
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
        txtStatus.setText(mess);
    }
}