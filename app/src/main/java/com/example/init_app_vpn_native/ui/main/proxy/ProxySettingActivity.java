<<<<<<< HEAD:app/src/main/java/com/example/init_app_vpn_native/ui/proxy/ProxySettingActivity.java
package com.example.init_app_vpn_native.ui.proxy;
=======
package com.example.init_app_vpn_native.ui.main.proxy;
>>>>>>> 26fbc38... push again 16h30 1911:app/src/main/java/com/example/init_app_vpn_native/ui/main/proxy/ProxySettingActivity.java

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.example.init_app_vpn_native.R;
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