package com.freeproxy.vpnmaster.hotspot2.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.freeproxy.vpnmaster.hotspot2.R;

public class DialogUpdate extends Dialog {
    Button btnOk;
    DialogCallBack callBack;

    public DialogUpdate(@NonNull Context context, DialogCallBack callBack) {
        super(context);
        this.callBack = callBack;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_update, null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(view);
        btnOk = view.findViewById(R.id.btnDialogUpdateOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBack != null) {
                    callBack.onClickOk();
                }
                dismiss();
            }
        });
    }
}
