package com.freeproxy.vpnmaster.hotspot2.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.freeproxy.vpnmaster.hotspot2.R;


public class DialogRequirePermission extends Dialog {
    Button btnOk,btnNo;
    DialogCallBack callBack;

    public void setCallBack(DialogCallBack callBack) {
        this.callBack = callBack;
    }

    public DialogRequirePermission(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_permission);
        btnOk = findViewById(R.id.btnDialogPermissionOk);
        btnNo = findViewById(R.id.btnDialogPermissionNo);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBack != null) {
                    callBack.onClickOk();
                }
                dismiss();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBack != null) {
                    callBack.onClickNo();
                }
                dismiss();
            }
        });
    }
}
