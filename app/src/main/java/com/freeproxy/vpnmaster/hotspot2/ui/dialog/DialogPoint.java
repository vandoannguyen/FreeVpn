package com.freeproxy.vpnmaster.hotspot2.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.freeproxy.vpnmaster.hotspot2.R;

public class DialogPoint extends Dialog {
    private static DialogPoint dialogPoint;
    Button btnOk;

    public DialogPoint(@NonNull Context context, OnOkClick callBack) {
        super(context);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_point, null, false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view);

        btnOk = view.findViewById(R.id.btnDialogPointOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClick();
                dismiss();
            }
        });
    }

    public static void showDialog(Context context, OnOkClick callBack) {
        dialogPoint = null;
        dialogPoint = new DialogPoint(context, callBack);
        dialogPoint.show();
    }

    public interface OnOkClick {
        void onClick();
    }
}
