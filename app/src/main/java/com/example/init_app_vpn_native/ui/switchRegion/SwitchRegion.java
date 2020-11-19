package com.example.init_app_vpn_native.ui.switchRegion;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.init_app_vpn_native.R;
import com.example.init_app_vpn_native.common.Config;
import com.example.init_app_vpn_native.ui.switchRegion.adapter.ItemAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SwitchRegion extends AppCompatActivity {

    @BindView(R.id.icBackSwitchRegion)
    ImageView icBackSwitchRegion;
    @BindView(R.id.icBackRefreshRegion)
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
            public void onClick(int index) {

            }
        });
    }

    @OnClick({R.id.icBackSwitchRegion, R.id.icBackRefreshRegion, R.id.linearProgress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.icBackSwitchRegion:
                break;
            case R.id.icBackRefreshRegion:
                break;
            case R.id.linearProgress:
                break;
        }
    }
}