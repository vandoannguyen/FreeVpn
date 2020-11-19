package com.example.init_app_vpn_native.ui.main.fragment.vpn;

import com.example.init_app_vpn_native.base.MVPView;
import com.example.init_app_vpn_native.data.api.model.Country;

public interface IVpnView extends MVPView {
    void updateSpeed(String finalDown, String finalUp);

    void updateState(int i);

    void updateCountry(Country selectedCountry);

    void showLoading();

    void hideLoading();

    void setConectionStatus(int i);
}
