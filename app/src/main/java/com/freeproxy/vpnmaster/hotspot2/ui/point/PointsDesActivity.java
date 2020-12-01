package com.freeproxy.vpnmaster.hotspot2.ui.point;

import androidx.appcompat.app.AppCompatActivity;
import com.freeproxy.vpnmaster.hotspot2.R;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PointsDesActivity extends AppCompatActivity {
    private String TAG = "PointsDesActivity";
    @BindView(R.id.btnBackPoint)
    ImageView btnBackPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_des);
        ButterKnife.bind(this);
    }
    private void initView(){

    }
    @OnClick({R.id.btnBackPoint})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.btnBackPoint:
                onBackPressed();
                break;
        }
    }
}