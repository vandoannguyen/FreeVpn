package com.example.init_app_vpn_native.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.init_app_vpn_native.R;

public class DialogLoading extends Dialog {
    private static DialogLoading dialogLoading;

    public DialogLoading(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_loading, null);
        setCancelable(false);
        setContentView(view);
    }

    public static void showDialog(Context context) {
        dialogLoading = new DialogLoading(context);
        dialogLoading.show();
    }

    public static void dismish() {
        if (dialogLoading != null) {
            dialogLoading.dismiss();
            dialogLoading = null;
        }
    }
}
