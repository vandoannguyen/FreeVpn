package com.example.init_app_vpn_native.base;

import android.content.Context;

public interface MVPView {
    void showMessage(String mess);
    boolean isNetworkConnected();
}
