package com.example.init_app_vpn_native.ui.load;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.init_app_vpn_native.R;
import com.example.init_app_vpn_native.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadData extends AppCompatActivity implements ILoadDataView {
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
        presenter.login();
        presenter.getAds();
        presenter.getCoin();
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