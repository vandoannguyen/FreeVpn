package com.example.init_app_vpn_native.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.init_app_vpn_native.R;

public class DialogLoading extends Dialog {
    private static DialogLoading dialogLoading;
    private static String mess = null;
    TextView txtDialogMess;

    public DialogLoading(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_loading, null);
        txtDialogMess = view.findViewById(R.id.txtDialogMess);
        if (mess != null) {
            txtDialogMess.setText(mess);
        }
        setCancelable(false);
        setContentView(view);
    }

    public static void showDialog(Context context) {
        dialogLoading = new DialogLoading(context);
        dialogLoading.show();
    }

    public static void showDialog(Context context, String mess) {
        DialogLoading.mess = mess;
        dialogLoading = new DialogLoading(context);
        dialogLoading.show();
    }

    public static void dismish() {
        if (dialogLoading != null) {
            dialogLoading.dismiss();
            dialogLoading = null;
            mess = null;
        }
    }
}
