package com.example.init_app_vpn_native.ui.load;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.init_app_vpn_native.R;
import com.example.init_app_vpn_native.ui.main.MainActivity;

public class LoadData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_data);
       startActivity(new Intent(LoadData.this, MainActivity.class));
    }
}