package com.freeproxy.vpnmaster.hotspot2.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.freeproxy.vpnmaster.hotspot2.R;

public class DialogLoading extends Dialog {
    private static DialogLoading dialogLoading;
    private static String mess = null;
    TextView txtDialogMess;
    Context context;

    public DialogLoading(@NonNull Context context, int style) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        txtDialogMess = view.findViewById(R.id.txtDialogMess);
        if (mess != null) {
            txtDialogMess.setText(mess);
        }
        setCancelable(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public static void showDialog(Context context) {
        dialogLoading = new DialogLoading(context, R.style.AppTheme_Dialog);
        dialogLoading.show();
    }

    public static void showDialog(Context context, String mess) {
        DialogLoading.mess = mess;
        dialogLoading = new DialogLoading(context, R.style.AppTheme_Dialog);
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
