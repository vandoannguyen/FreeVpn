package com.freeproxy.vpnmaster.hotspot2.ui.enterCode;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.freeproxy.vpnmaster.hotspot2.R;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;

public class EnterCodesActivity extends AppCompatActivity {
    private String TAG = "EnterCodesActivity";
    @BindView(R.id.btnBackEnterCodes)
    ImageView btnBackEnterCodes;
    @BindView(R.id.edtEnterCodes)
    EditText edtEnterCodes;
    @BindView(R.id.lineSubmit)
    LinearLayout lineSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_codes);
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        edtEnterCodes.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
    @OnClick({R.id.btnBackEnterCodes, R.id.lineSubmit})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.btnBackEnterCodes:
                onBackPressed();
                break;
            case R.id.lineSubmit:
                Toast.makeText(this, "Get Submit", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}