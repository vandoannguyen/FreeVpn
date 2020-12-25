package com.freeproxy.vpnmaster.hotspot2.ui.load;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.freeproxy.vpnmaster.hotspot2.BuildConfig;
import com.freeproxy.vpnmaster.hotspot2.R;
import com.freeproxy.vpnmaster.hotspot2.common.Config;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadData extends AppCompatActivity implements ILoadDataView {
    ILoadPresenter<ILoadDataView> presenter;
    @BindView(R.id.txtVersion)
    TextView txtVersion;
    @BindView(R.id.imgLoading)
    ImageView imgLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_data);
        ButterKnife.bind(this);
        presenter = new LoadDataPresenter<>(this);
        presenter.onAttact(this);
        txtVersion.setText(BuildConfig.VERSION_NAME);
        init();
        Glide.with(this).asGif().load(R.raw.loading_gift).into(imgLoading);
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