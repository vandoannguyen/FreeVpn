package com.example.init_app_vpn_native.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.init_app_vpn_native.R;

public class DialogRequireRemove extends Dialog {
    ImageView imgIcon;
    TextView txtName;
    LinearLayout lineApp;
    DialogCallBack callBack;

    public DialogRequireRemove(@NonNull Context context, String packageName, DialogCallBack callBack) {
        super(context);
        this.callBack = callBack;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_require_remove, null, false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        imgIcon = view.findViewById(R.id.imgIcon);
        txtName = view.findViewById(R.id.txtName);
        lineApp = view.findViewById(R.id.lineApp);

        lineApp.setOnClickListener((v) -> {
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + packageName));
            context.startActivity(intent);
            if (callBack != null) {
                callBack.onClickOk();
            }
            dismiss();
        });
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            txtName.setText(applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : "Unknown");
            Drawable appIcon = packageManager.getApplicationIcon(packageName);
            imgIcon.setImageDrawable(appIcon);
        } catch (final PackageManager.NameNotFoundException e) {
        }
    }
}