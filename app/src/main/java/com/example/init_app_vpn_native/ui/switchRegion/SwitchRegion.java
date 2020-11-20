package com.example.init_app_vpn_native.ui.switchRegion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.init_app_vpn_native.R;
import com.example.init_app_vpn_native.common.Config;
import com.example.init_app_vpn_native.data.AppDataHelper;
import com.example.init_app_vpn_native.data.CallBack;
import com.example.init_app_vpn_native.data.api.model.Country;
import com.example.init_app_vpn_native.ui.dialog.DialogConfirm;
import com.example.init_app_vpn_native.ui.switchRegion.adapter.ItemAdapter;
import com.example.init_app_vpn_native.utils.Common;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SwitchRegion extends AppCompatActivity {
    private String TAG = "SwitchRegion";
    @BindView(R.id.icBackSwitchRegion)
    ImageView icBackSwitchRegion;
    @BindView(R.id.icRefreshRegion)
    ImageView icBackRefreshRegion;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.linearProgress)
    LinearLayout linearProgress;
    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_region);
        ButterKnife.bind(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        adapter = new ItemAdapter(Config.listCountry);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        adapter.setOnItemClick(new ItemAdapter.OnItemClick() {
            @Override
            public void onClick(Country index) {
//                Config.currentCountry = index;
                showDialogConfirm(index);
            }
        });
    }

    private void showDialogConfirm(Country index) {
        DialogConfirm dialogConfirm = new DialogConfirm(this, index.getPrice());
        dialogConfirm.setOnClickListener(new DialogConfirm.OnClickListener() {
            @Override
            public void clickYes() {
                super.clickYes();
                Common.points = Integer.parseInt(index.getPrice());
                Intent intent = new Intent();
                intent.putExtra("country", index);
                setResult(0, intent);
                finish();
            }
        });
    }

    @OnClick({R.id.icBackSwitchRegion, R.id.icRefreshRegion, R.id.linearProgress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.icBackSwitchRegion:
                onBackPressed();
                break;
            case R.id.icRefreshRegion:
                refreshServer();
                break;
            case R.id.linearProgress:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(0);
    }

    private void refreshServer() {
        showLoading(true);
        AppDataHelper.getInstance().getCountry(Config.tokenApp, new CallBack<List<Country>>() {
            @Override
            public void onSuccess(List<Country> data) {
                super.onSuccess(data);
                adapter.setCountries(data);
                showLoading(false);
            }

            @Override
            public void onFailed(String mess) {
                super.onFailed(mess);
                showLoading(false);
            }
        });
    }

    private void showLoading(boolean b) {
        if (b)
            linearProgress.setVisibility(View.VISIBLE);
        else {
            linearProgress.setVisibility(View.GONE);
        }
    }
}