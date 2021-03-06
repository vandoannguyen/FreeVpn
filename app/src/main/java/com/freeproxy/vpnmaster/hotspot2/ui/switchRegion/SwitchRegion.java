package com.freeproxy.vpnmaster.hotspot2.ui.switchRegion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freeproxy.vpnmaster.hotspot2.R;
import com.freeproxy.vpnmaster.hotspot2.base.BaseActivity;
import com.freeproxy.vpnmaster.hotspot2.common.Config;
import com.freeproxy.vpnmaster.hotspot2.data.AppDataHelper;
import com.freeproxy.vpnmaster.hotspot2.data.CallBack;
import com.freeproxy.vpnmaster.hotspot2.data.api.model.Country;
import com.freeproxy.vpnmaster.hotspot2.ui.dialog.DialogConfirm;
import com.freeproxy.vpnmaster.hotspot2.ui.dialog.DialogLoading;
import com.freeproxy.vpnmaster.hotspot2.ui.switchRegion.adapter.ItemAdapter;
import com.freeproxy.vpnmaster.hotspot2.utils.Common;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SwitchRegion extends BaseActivity implements ISwitchRegionView {
    public static final int RESULT_CODE_NOT_ENOUGH_POINT = 123;
    public static final int SWITCH_COUNTRY = 12;
    @BindView(R.id.frmAdsSwitch)
    FrameLayout frmAdsSwitch;
    @BindView(R.id.linearFastConnect)
    LinearLayout linearFastConnect;
    private String TAG = "SwitchRegion";
    @BindView(R.id.icBackSwitchRegion)
    ImageView icBackSwitchRegion;
    @BindView(R.id.icRefreshRegion)
    ImageView icBackRefreshRegion;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.linearProgress)
    LinearLayout linearProgress;
    ItemAdapter adapter;
    ISwitchPresenter<ISwitchRegionView> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_region);
        ButterKnife.bind(this);
        initRecyclerView();
        presenter = new SwitchVPresenter<>(this);
        presenter.onAttact(this);
        presenter.getCountry();
    }

    @Override
    public void showDialogLoading(boolean b) {
        if (b) {
            DialogLoading.showDialog(this);
        } else DialogLoading.dismish();
    }

    @Override
    public void updateListCountry(List<Country> listCountry) {
        if (adapter != null) {
            adapter.setCountries(listCountry);
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        adapter = new ItemAdapter(Config.listCountry);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        adapter.setOnItemClick(new ItemAdapter.OnItemClick() {
            @Override
            public void onClick(Country index) {
                Config.currentCountry = index;
                showDialogConfirm(index);
            }
        });
        frmAdsSwitch.setVisibility(View.GONE);
//        Ads.getInstance(this).banner(frmAdsSwitch, Ads.AdsSize.BANNER);
    }

    private void showDialogConfirm(Country index) {
//        if (index.getPrice().equals("free")) {
            Intent intent = new Intent();
            intent.putExtra("country", index);
            setResult(SWITCH_COUNTRY, intent);
            finish();
//        } else {
//            int point = Integer.parseInt(index.getPrice());
//            if (point > Common.totalPoint) {
//                Toast.makeText(this, "Sorry you do not have enough point!", Toast.LENGTH_SHORT).show();
//            } else {
//                DialogConfirm dialogConfirm = new DialogConfirm(this, index.getPrice());
//                dialogConfirm.setOnClickListener(new DialogConfirm.OnClickListener() {
//                    @Override
//                    public void clickYes() {
//                        super.clickYes();
//                        Intent intent = new Intent();
//                        intent.putExtra("country", index);
//                        setResult(SWITCH_COUNTRY, intent);
//                        Common.totalPoint -= point;
//                        AppDataHelper.getInstance().setCoin(SwitchRegion.this, Common.totalPoint, null);
//                        finish();
//                    }
//                });
//                dialogConfirm.show();
//            }
//        }
    }

    @OnClick({R.id.icBackSwitchRegion, R.id.icRefreshRegion, R.id.linearProgress, R.id.linearFastConnect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linearFastConnect: {
//                Intent intent = new Intent();
                setResult(SWITCH_COUNTRY, null);
                finish();
                break;
            }
            case R.id.icBackSwitchRegion:
                onBackPressed();
                break;
            case R.id.icRefreshRegion:
                refreshServer();
                break;
            case R.id.linearProgress:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(1);
        finish();
    }

    private void refreshServer() {
        showLoading(true);
        AppDataHelper.getInstance().getCountry(Config.tokenApp, new CallBack<List<Country>>() {
            @Override
            public void onSuccess(List<Country> data) {
                super.onSuccess(data);
                adapter.setCountries(data);
                showLoading(false);
            }

            @Override
            public void onFailed(String mess) {
                super.onFailed(mess);
                showLoading(false);
            }
        });
    }

    private void showLoading(boolean b) {
        if (b)
            linearProgress.setVisibility(View.VISIBLE);
        else {
            linearProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMessage(String mess) {

    }
}