package com.freeproxy.vpnmaster.hotspot2.ui.proxy;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.freeproxy.vpnmaster.hotspot2.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProxySettingActivity extends AppCompatActivity {
    private String TAG = "ProxySettingActivity";
    @BindView(R.id.btnBackProxy)
    ImageView btnBackProxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy_setting);
        ButterKnife.bind(this);
    }
    private void initView(){

    }
    @OnClick({R.id.btnBackProxy})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.btnBackProxy:
                onBackPressed();
                break;
        }
    }
}