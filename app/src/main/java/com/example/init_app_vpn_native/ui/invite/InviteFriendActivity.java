package com.example.init_app_vpn_native.ui.invite;

import androidx.appcompat.app.AppCompatActivity;
import com.example.init_app_vpn_native.R;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.ButterKnife;

public class InviteFriendActivity extends AppCompatActivity {
    private String TAG = "InviteFriendActivity";
    @BindView(R.id.btnBackInvite)
    ImageView btnBackInvite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);
        ButterKnife.bind(this);
    }
    private void initView(){

    }
    @OnClick({R.id.btnBackInvite})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.btnBackInvite:
                onBackPressed();
                break;
        }
    }
}