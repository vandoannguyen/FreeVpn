package com.example.init_app_vpn_native.ui.feedBack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.init_app_vpn_native.R;

import butterknife.BindView;
import butterknife.ButterKnife;
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
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        edtFeedback.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @OnClick({R.id.btnBackFeedBack, R.id.lineSubmitFB})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBackFeedBack:
                onBackPressed();
                break;
            case R.id.lineSubmitFB:
                if (edtFeedback.getText().toString().isEmpty() && edtEmail.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter text", Toast.LENGTH_SHORT).show();
                } else {
                    if (edtEmail.getText().toString().equals("@gmail.com")) {
                        sendEmail();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter again email", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void sendEmail() {
        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_SEND);
        intent2.setType("message/rfc822");
        String EMAIL1 = "abc@gmail.com";
        String emailApp = "oneadx@gmaiil.com";
        String ytext = edtFeedback.getText().toString();
        intent2.putExtra(Intent.EXTRA_EMAIL, new String[]{EMAIL1});
        intent2.putExtra(Intent.EXTRA_SUBJECT, emailApp);
        intent2.putExtra(Intent.EXTRA_TEXT, ytext);
        startActivity(intent2);
    }
}