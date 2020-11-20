package com.example.init_app_vpn_native.ui.feedBack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.init_app_vpn_native.R;

import butterknife.BindView;
import butterknife.OnClick;

public class FeedbackActivity extends AppCompatActivity {
    private String TAG = "FeedbackActivity";
    @BindView(R.id.btnBackFeedBack)
    ImageView btnBackFeedBack;
    @BindView(R.id.edtFeedback)
    EditText edtFeedback;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.lineSubmitFB)
    LinearLayout lineSubmitFB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initView();
    }
    private void initView(){
        edtFeedback.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
    @OnClick({R.id.btnBackFeedBack, R.id.lineSubmitFB})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.btnBackFeedBack:
                onBackPressed();
                break;
            case R.id.lineSubmitFB:
                break;
        }
    }
}