package com.freeproxy.vpnmaster.hotspot2.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.freeproxy.vpnmaster.hotspot2.R;

import butterknife.BindView;

public class DialogConfirm extends Dialog implements View.OnClickListener {

    @BindView(R.id.txtCoin)
    TextView txtCoin;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnYes)
    Button btnYes;
    OnClickListener onClickListener;
    String coin = "";

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public DialogConfirm(@NonNull Context context, String coin) {
        super(context);
        this.coin = coin;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_confirm, null, false);
        setContentView(view);
        txtCoin = view.findViewById(R.id.txtCoinDialog);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnYes = view.findViewById(R.id.btnYes);
        btnCancel.setOnClickListener(this::onClick);
        btnYes.setOnClickListener(this::onClick);
        txtCoin.setText(coin + " point?");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                if (onClickListener != null) {
                    onClickListener.clickNo();
                }
                dismiss();
                break;
            case R.id.btnYes:
                if (onClickListener != null)
                    onClickListener.clickYes();
                dismiss();
                break;
        }
    }

    public static class OnClickListener {
        public void clickYes() {
        }


        public void clickNo() {
        }

    }
}
