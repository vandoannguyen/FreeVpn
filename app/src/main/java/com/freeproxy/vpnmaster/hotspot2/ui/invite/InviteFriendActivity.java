package com.freeproxy.vpnmaster.hotspot2.ui.invite;

import androidx.appcompat.app.AppCompatActivity;
import com.freeproxy.vpnmaster.hotspot2.R;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.ButterKnife;

public class InviteFriendActivity extends AppCompatActivity {
    private String TAG = "InviteFriendActivity";
    @BindView(R.id.btnBackInvite)
    ImageView btnBackInvite;
    @BindView(R.id.lineSMS)
    LinearLayout lineSMS;
    @BindView(R.id.lineEmail)
    LinearLayout lineEmail;
    @BindView(R.id.lineCopyLink)
    LinearLayout lineCopyLink;
    @BindView(R.id.lineMore)
    LinearLayout lineMore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);
        ButterKnife.bind(this);
    }
    private void initView(){

    }
    @OnClick({R.id.btnBackInvite, R.id.lineSMS, R.id.lineEmail, R.id.lineCopyLink, R.id.lineMore})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.btnBackInvite:
                onBackPressed();
                break;
            case R.id.lineSMS:
                    break;
            case R.id.lineEmail:
                break;
            case R.id.lineCopyLink:
                break;
            case R.id.lineMore:
                break;
        }
    }
    private void sendSMS() {
        SmsManager sms = SmsManager.getDefault();
        PendingIntent sentPI;
        String SENT = "SMS_SENT";

        sentPI = PendingIntent.getBroadcast(this, 0,new Intent(SENT), 0);
        String message = "Send message";
        sms.sendTextMessage("", null, message, sentPI, null);
    }
    private void sendEmail(){
        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_SEND);
        intent2.setType("message/rfc822");
        intent2.putExtra(Intent.EXTRA_SUBJECT, "Email Subject");
        intent2.putExtra(Intent.EXTRA_TEXT, "Your text here" );
        startActivity(intent2);
    }
    private void copyLink(){
        ClipboardManager myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        String text = "abc";
        ClipData myClip = ClipData.newPlainText("text", text);
        myClipboard.setPrimaryClip(myClip);
        Toast.makeText(getApplicationContext(), "Text Copied",Toast.LENGTH_SHORT).show();
    }
    private void more(){
        Intent intent2 = new Intent(); intent2.setAction(Intent.ACTION_SEND);
        intent2.setType("text/plain");
        intent2.putExtra(Intent.EXTRA_TEXT, "Your text here" );
        startActivity(Intent.createChooser(intent2, "Share via"));
    }
}